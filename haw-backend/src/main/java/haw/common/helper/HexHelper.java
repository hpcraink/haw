package haw.common.helper;

/**
 * Hex convenience methods.
 * 
 * @author Martin Hans
 */
public abstract class HexHelper {

	/**
	 * Helper Method to create Hex Values from the Input.
	 * 
	 * @param inputByte
	 *            the input byte
	 * @return String Hex Version of the Input String
	 */
	public static String byteArrayToHexString(byte[] inputByte) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < inputByte.length; i++) {
			sb.append(Integer.toString((inputByte[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();
	}

	/**
	 * Helper method to create a byte[] from a input string
	 * which is a Hex representation of a binary.
	 * 
	 * @param s
	 *            the String
	 * @return the byte[]
	 */
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
}
