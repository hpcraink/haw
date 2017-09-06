/**
 * 
 */
package haw.common.helper;

import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

/**
 * Provides several helper methods for Strings.
 * 
 * @author Katharina
 */
public class StringHelper {

	/**
	 * Substitution for the additionalInformation.
	 */
	public static final String ADDITIONAL_INFORMATION = "...";

	/** The lineseparator. */
	public static String lineseparator = System.getProperty("line.separator");

	/**
	 * Makes the first character to lower case and returns the result.
	 * 
	 * @param s
	 *            the String to modify
	 * @return the given String with first character as lower case letter.
	 */
	public static String firstCharToLowerCaser(String s) {
		return s.substring(0, 1).toLowerCase() + s.substring(1, s.length());
	}

	/**
	 * Makes the first character to upper case and returns the result.
	 * 
	 * @param s
	 *            the String to modify
	 * @return the given String with first character as upper case letter.
	 */
	public static String firstCharToUpperCaser(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
	}

	/**
	 * Checks whether the given String is empty. An String is empty if
	 * <ul>
	 * <li>the String is null</li>
	 * <li>the String exists only of whitespace</li>
	 * </ul>
	 * 
	 * @see String#isEmpty()
	 * @param compareString
	 *            String to be checked
	 * @return true if the given String contains no sign
	 */
	public static boolean isEmpty(String compareString) {
		return (compareString==null || compareString.trim().isEmpty());
	}

	/**
	 * Checks whether the given String is not empty. An String is empty if
	 * <ul>
	 * <li>the String is null</li>
	 * <li>the String exists only of whitespace</li>
	 * </ul>
	 * 
	 * @param compareString
	 *            String to be checked
	 * @return true if the given String contains at least one sign
	 */
	public static boolean isNotEmpty(String compareString) {
		return !isEmpty(compareString);
	}

	/**
	 * Generate a random {@link String} with the given length. The
	 * {@link String} contains chars and numbers
	 * 
	 * @param length
	 *            the length of the returned String
	 * @return the generated {@link String}
	 */
	public static String generateString(int length) {
		Random rng = new Random();
		String pattern = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = pattern.charAt(rng.nextInt(pattern.length()));
		}
		return new String(text);
	}

	/**
	 * Trims the given {@link String} if not null.
	 * 
	 * @see String#trim()
	 * @param value
	 *            the value to trim
	 * @return the trimmed string
	 */
	public static String trim(String value) {
		return (value != null ? value.trim() : null);
	}
	
	/**
	 * Converts the map to a readable string in the format:
	 * {@code [<Key>:<Value>; ...]}
	 * 
	 * @param parameters The parameters
	 * @return The string containing the parameters
	 */
	public static String mapToString(Map<?, ?> parameters) {
		StringBuilder buffer = new StringBuilder();
		for(Entry<?, ?> parameter : parameters.entrySet()) {
			buffer.append(parameter.getKey());
			buffer.append(":");
			buffer.append(parameter.getValue());
			buffer.append("; ");
		}
		return buffer.toString();
	}
	
	/**
	 * Checks if a value is numeric.
	 * Pattern: \d+
	 * 
	 * @param value the value to ckeck
	 * @return true if numeric
	 */
	public static boolean isNumeric(String value) {
		boolean isNumeric = false;
		if(isNotEmpty(value)) {
			isNumeric = value.matches("\\d+");
		}
		return isNumeric;
	}

	public static String escapeWhitespace(String value){
		String replaceAll = null;
		if(value != null && value.length()>0){
			replaceAll = value.replaceAll(" ", "_");
			replaceAll = replaceAll.replaceAll("%20", "_");
		}
		return replaceAll;
	}

}
