package haw.common.helper;

import org.apache.commons.codec.binary.Base64;

/**
 * The Class Base64Helper. Helps to decode Base64 encoded Strings
 * Form "username:password"
 * best practice: use extractUsernamePassword with the encoded string
 * as a parameter
 * 
 * @author martin
 */
public class Base64Helper {

	/**
	 * Extract username.
	 * 
	 * @param base64String
	 *            the base64 string
	 * @return String username
	 */
	public static String extractUsername(String base64String){
		String[] decodedString = decode(base64String);
		return decodedString[0];
	}

	/**
	 * Extract password.
	 * 
	 * @param base64String
	 *            the base64 string
	 * @return password
	 */
	public static String extractPassword(String base64String){
		String[] decodedString = decode(base64String);
		return decodedString[1];
	}

	/**
	 * just a method to encapsulate the decryption.
	 * 
	 * @param base64String
	 *            the base64 string
	 * @return String[0] = username
	 *         String[1] = password
	 */
	public static String[] extractUsernamePassword(String base64String){
		String[] decodedString = decode(base64String);
		return decodedString;
	}

	/**
	 * Decodes the given String.
	 * 
	 * @param base64String
	 *            the base64 string
	 * @return String[0] = username
	 *         String[1] = password
	 */
	public static String[] decode(String base64String){
		Base64 decoder = new Base64();
		String uPass = base64String.replace("Basic", "").trim();
		byte[] decoded = decoder.decode(uPass.getBytes());
		String decodedString = new String (decoded);
		return (decodedString.split(":"));
	}
}
