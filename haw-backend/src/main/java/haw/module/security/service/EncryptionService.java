package haw.module.security.service;

import haw.common.error.handler.HawErrorHandler;
import haw.common.exception.HawAuthenticationException;
import haw.common.exception.HawServerException;
import haw.common.helper.HexHelper;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

/**
 * Service for token and password encryption and decryption. The service must be
 * application scoped, because a common secret key is used.
 *
 * @author martin
 *
 */
@ApplicationScoped
public class EncryptionService {

    private final static String TOKEN_CHARSETNAME = "UTF-8";
    private final static String ALGORITHM = "AES";

    private byte[] encodedKey;

    /**
     * The error handler
     */
    @Inject
    private HawErrorHandler errorHandler;

    @Inject
    private Logger logger;

    /**
     * creates the random key on startup
     */
    @PostConstruct
    protected void initializeKey() {
        // generate random key
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            String message = String.format("Can't initialize secret key. Algorithm %s is unknown!", ALGORITHM);
            logger.error(message, e);
            throw new IllegalStateException(e.getMessage(), e);
        }
        // cryptograph. secure random
        SecureRandom random = new SecureRandom();
        keyGen.init(random);
        SecretKey secretKey = keyGen.generateKey();
        encodedKey = secretKey.getEncoded();
    }

    /**
     * Encrypts the provided token
     *
     * @param decryptedToken the Token to encrypt
     * @return the encrypted token
     * @throws HawServerException is thrown if an error occurs during encryption
     */
    public String encryptToken(String decryptedToken) throws HawServerException {
        String encryptedToken = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKey secretKey = new SecretKeySpec(encodedKey, ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(decryptedToken.getBytes(TOKEN_CHARSETNAME));
            encryptedToken = HexHelper.byteArrayToHexString(encryptedBytes);
            //encryptedToken = new String(encryptedBytes, TOKEN_CHARSETNAME);
        } catch (UnsupportedEncodingException | InvalidKeyException
                | NoSuchAlgorithmException | BadPaddingException
                | IllegalBlockSizeException | NoSuchPaddingException e) {
            String message = String.format("Error during token encryption phase occured. %s: %s", e.getClass().getName(), e.getMessage());
            logger.error(message, e);
            throw new HawServerException("Unexpected error during authentication.");
        }
        return encryptedToken;
    }

    /**
     * Decrypts the provided encrypted token
     *
     * @param encryptedToken the Token to decrypt
     * @return the decrypted token
     * @throws HawServerException is thrown if an error occurs during decryption
     * @throws HawAuthenticationException is thrown if token is invalid
     */
    public String decryptToken(String encryptedToken) throws HawServerException, HawAuthenticationException {
        String decryptedToken = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKey secretKey = new SecretKeySpec(encodedKey, ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(HexHelper.hexStringToByteArray(encryptedToken));
            //byte[] decryptedBytes = cipher.doFinal(encryptedToken.getBytes(TOKEN_CHARSETNAME));
            decryptedToken = new String(decryptedBytes, TOKEN_CHARSETNAME);
        } catch (BadPaddingException e) {
            throw new HawAuthenticationException("Invalid Token");
        } catch (UnsupportedEncodingException | InvalidKeyException
                | NoSuchAlgorithmException | IllegalBlockSizeException
                | NoSuchPaddingException e) {
            String message = String.format("Error during token decryption phase occured. %s: %s", e.getClass().getName(), e.getMessage());
            logger.error(message, e);
            throw new HawServerException("Unexpected error during authentication.");
        }
        return decryptedToken;
    }

    /**
     * Method that encrypts the password via sha256 basically it does:
     * sha(sha(password)+salt)
     *
     * @param password The actual password
     * @param salt The salt to digest the password with
     * @return String salted password
     * @throws HawServerException Thrown if algorithm could not be initialized
     */
    public String getSaltedPassFromPlain(String password, String salt) throws HawServerException {
        String saltedPassString = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] shaPassByte = md.digest();
            String shaPassSalt = HexHelper.byteArrayToHexString(shaPassByte) + salt;
            md.update(shaPassSalt.getBytes());
            byte[] saltedPass = md.digest();
            saltedPassString = HexHelper.byteArrayToHexString(saltedPass);
        } catch (NoSuchAlgorithmException e) {
            errorHandler.handleTechnicalInitializationError(e, "SHA-256 algorithm");
        }
        return saltedPassString;
    }

}
