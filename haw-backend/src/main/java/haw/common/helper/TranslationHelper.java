/**
 * 
 */
package haw.common.helper;


/**
 * This class provides helper methods for translating Objects.
 * 
 * @author Katharina
 */
public class TranslationHelper {

	/**
	 * Translates the given boolean to ja/nein.
	 * 
	 * @param toTranslate
	 *            boolean to translate
	 * @return the string
	 */
	public static String translationBoolean(boolean toTranslate) {
		if (toTranslate) {
			return "ja";
		}
		return "nein";
	}

}
