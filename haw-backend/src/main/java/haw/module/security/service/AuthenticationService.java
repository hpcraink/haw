package haw.module.security.service;

import haw.application.configuration.ApplicationConfiguration;
import haw.common.exception.HawAuthenticationException;
import haw.common.exception.HawResourceNotExistsException;
import haw.common.exception.HawServerException;
import haw.common.exception.HawValidationException;
import haw.common.helper.Base64Helper;
import haw.common.resource.entity.Login;
import haw.common.resource.entity.Person;
import haw.module.person.service.PersonService;
import haw.module.security.model.Token;

import java.util.Calendar;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

/**
 * The Class AuthenticationService.
 *
 * @author martin
 */
@ApplicationScoped
public class AuthenticationService {

    /**
     * the application configuration
     */
    @Inject
    private ApplicationConfiguration applicationConfiguration;

    /**
     * The member service.
     */
    @Inject
    private PersonService personService;

    /**
     * The encryption service.
     */
    @Inject
    private EncryptionService encryptionService;

    @Inject
    private Logger logger;

    /**
     * Login base64 auth.
     *
     * @param base64Auth the base64 auth
     * @param fromIP the client's IP address this request came from
     * @return the string
     * @throws HawAuthenticationException - Thrown if the Username or Password
     * is invalid
     * @throws HawServerException - Thrown if problem on database occurs.
     */
    public String loginBase64Auth(String base64Auth, String fromIP)
            throws HawAuthenticationException, HawServerException {
        if (base64Auth == null) {
            logger.warn("[{}] Invalid Email or password from: {}", Calendar.getInstance().getTime(), fromIP);
            throw new HawAuthenticationException("Invalid Username or Password");
        }
        String userPass[] = Base64Helper.extractUsernamePassword(base64Auth);
        if (userPass.length != 2) {
            logger.warn("[{}] Invalid Email or password from: {}", Calendar.getInstance().getTime(), fromIP);
            throw new HawAuthenticationException("Invalid Email or Password");
        }
        return login(userPass[0], userPass[1], fromIP);
    }

    /**
     * creates the encrypted Password gets the the member from the db puts the
     * member with a timestamp in a token encrypts the token and returns it.
     *
     * @param email the email
     * @param password the password
     * @param fromIP the client's IP address this request came from
     * @return String
     * @throws HawServerException - Thrown if problem on database occurs.
     * @throws HawAuthenticationException - Thrown if the Email or Password
     * is invalid
     */
    public String login(String email, String password, String fromIP)
            throws HawServerException, HawAuthenticationException {
        Person person;
        try {
            person = personService.getPersonByEmail(email);
        } catch (HawResourceNotExistsException e) {
            logger.warn("[{}] Invalid Email: {} from: {}", Calendar.getInstance().getTime(), email, fromIP);
            throw new HawAuthenticationException("Invalid Email or Password");
        }
        String salt = person.getLogin().getSalt();
        String saltedPassDB = person.getLogin().getPassword();
        String saltedPassInput = encryptionService.getSaltedPassFromPlain(
                password, salt);
        if (!saltedPassInput.equals(saltedPassDB)) {
            logger.warn("[{}] Invalid Email: {} from: {}", Calendar.getInstance().getTime(), email, fromIP);
            throw new HawAuthenticationException("Invalid Email or Password");
        }
        logger.info("[{}] Valid Email: {} from: {}", Calendar.getInstance().getTime(), email, fromIP);
        String encryptedToken = createEncryptedTokenByMember(person);
        return encryptedToken;
    }

    /**
     * Update cookie timestamp.
     *
     * @param person the current person
     * @return the string
     * @throws HawServerException - Thrown if problem on database occurs.
     */
    public String createEncryptedTokenByMember(Person person)
            throws HawServerException {
        Login credentials = person.getLogin();
        Token token = new Token(credentials.getPerson().getEmail(), Calendar.getInstance().getTime());
        return encryptionService.encryptToken(token.getDecryptedToken());
    }

    /**
     * Implement authentication decrypts the encrypted Token and checks if the
     * timestamp is within 30 minutes.
     *
     * @param encryptedToken the encrypted token
     * @return the member
     * @throws HawServerException - Thrown if problem on database occurs.
     * @throws HawAuthenticationException - Thrown if the Email or password
     * is invalid
     */
    public Person authenticate(String encryptedToken)
            throws HawServerException, HawAuthenticationException {
        String decryptedToken = encryptionService.decryptToken(encryptedToken);
        Token token = new Token(decryptedToken);
        String email = token.getEmail();
        Person person;
        try {
            person = personService.getPersonByEmail(email);
        } catch (HawResourceNotExistsException e) {
            throw new HawAuthenticationException("Invalid Email or Password");
        }
        Calendar currentTimestamp = Calendar.getInstance();
        if (isTokenValid(token, currentTimestamp)) {
            return person;
        }
        throw new HawAuthenticationException("Login expired");
    }

    /**
     * Checks if timestamp is valid.
     *
     * @param token the token
     * @param currentTimestamp the current timestamp
     * @return true, if timestamp is valid
     */
    public boolean isTokenValid(Token token,
            Calendar currentTimestamp) {
        Calendar tokenTimestamp = token.getTimestampAsCalendar();
        Calendar smallestValidTimestamp = Calendar.getInstance();
        smallestValidTimestamp.setTime(currentTimestamp.getTime());
        int tokenTimeoutMinutes = applicationConfiguration.getSessionTimeoutMin();
        smallestValidTimestamp.add(Calendar.MINUTE, -tokenTimeoutMinutes);
        if (tokenTimestamp.before(smallestValidTimestamp)) {
            return false;
        }
        return true;
    }

    /**
     * Creates a new password for the given email.
     *
     * @param email the email
     * @throws HawValidationException Thrown if passed email is not valid.
     * @throws HawResourceNotExistsException Thrown if passed email does not
 exist.
     * @throws HawServerException Thrown if problem on database occurs.
     */
    public void forgotPassword(String email) throws HawValidationException,
            HawResourceNotExistsException, HawServerException {
        Person person = personService.getPersonByEmail(email);
        try {
            personService.changePW(person.getId(), null, null, true);
        } catch (HawAuthenticationException e) {
            // this block will be never reached because this kind of Exception
            // will be only thrown by checking the oldPassword.
        }
    }

}
