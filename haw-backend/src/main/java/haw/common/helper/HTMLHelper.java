package haw.common.helper;

import java.util.logging.Logger;

/**
 * This helper provides static methods for adding HTML Tags and retrieving
 * Webpages through a headless browser.
 *
 * @author Katharina
 */
public class HTMLHelper {

        private final static Logger logger = Logger.getLogger(HTMLHelper.class.getName());

        /**
         * The Constant NEW_LINE.
         */
        public static final String NEW_LINE = "<br />";

        /**
         * The Constant TABLE_START.
         */
        public static final String TABLE_START = "<table>";

        /**
         * The Constant TABLE_END.
         */
        public static final String TABLE_END = "</table>";

        /**
         * The Constant TD_START.
         */
        public static final String TD_START = "<td valign=\"top\">";

        /**
         * The Constant TD_END.
         */
        public static final String TD_END = "</td>";

        /**
         * The Constant TR_START.
         */
        public static final String TR_START = "<tr>";

        /**
         * The Constant TR_END.
         */
        public static final String TR_END = "</tr>";

        /**
         * The Constant FAT_START.
         */
        public static final String FAT_START = "<b>";

        /**
         * The Constant FAT_END.
         */
        public static final String FAT_END = "</b>";

        /**
         * The Constant TITLE1_START.
         */
        public static final String TITLE1_START = "<h1>";

        /**
         * The Constant TITLE1_END.
         */
        public static final String TITLE1_END = "</h1>";

        /**
         * The Constant TITLE2_START.
         */
        public static final String TITLE2_START = "<h2>";

        /**
         * The Constant TITLE2_END.
         */
        public static final String TITLE2_END = "</h2>";

        /**
         * The Constant TITLE3_START.
         */
        public static final String TITLE3_START = "<h3>";

        /**
         * The Constant TITLE3_END.
         */
        public static final String TITLE3_END = "</h3>";

        /**
         * The Constant ADDRESS_START.
         */
        public static final String ADDRESS_START = "<address>";

        /**
         * The Constant ADDRESS_END.
         */
        public static final String ADDRESS_END = "</address>";

        /**
         * The Constant ITALIC_START.
         */
        public static final String ITALIC_START = "<span style='font-style:italic;'>";

        /**
         * The Constant ITALIC_END.
         */
        public static final String ITALIC_END = "</span>";

        /**
         * The Constant A_HREF_START.
         */
        public static final String A_HREF_START = "<a href='";

        /**
         * The Constant A_HREF_CLOSE.
         */
        public static final String A_HREF_CLOSE = "'>";

        /**
         * The Constant A_HREF_END.
         */
        public static final String A_HREF_END = "</a>";

        /**
         * Modify the given {@link StringBuilder} by adding the given text
         * surrounded by HTML td (tablecell) tags.
         *
         * @param builder to modify
         * @param text to add
         */
        public static void addTdTag(StringBuilder builder, String text) {
                builder.append(TD_START);
                builder.append(text);
                builder.append(TD_END);
        }

        /**
         * Modify the given {@link StringBuilder} by adding the given text
         * surrounded by HTML fat tag.
         *
         * @param builder to modify
         * @param text to add
         */
        public static void addFatTag(StringBuilder builder, String text) {
                builder.append(FAT_START);
                builder.append(text);
                builder.append(FAT_END);
        }

        /**
         * Modify the given {@link StringBuilder} by adding the given text
         * surrounded by HTML tr (table) tag.
         *
         * @param builder to modify
         * @param text to add
         */
        public static void addTrTag(StringBuilder builder, String text) {
                builder.append(TR_START);
                builder.append(text);
                builder.append(TR_END);
        }

        /**
         * Modify the given {@link StringBuilder} by adding the given text
         * surrounded by HTML td and fat tag.
         *
         * @param builder to modify
         * @param text to add
         */
        public static void addTdAndFatTag(StringBuilder builder, String text) {
                builder.append(TD_START);
                builder.append(FAT_START);
                builder.append(text);
                builder.append(FAT_END);
                builder.append(TD_END);
        }

        /**
         * Surround the given {@link String} by adding HTML td (tablecell) tags.
         *
         * @param text to surround
         * @return text with td tags
         */
        public static String addTdTag(String text) {
                return TD_START + text + TD_END;
        }

        /**
         * Surround the given {@link String} by adding HTML tr (table) tags.
         *
         * @param text to surround
         * @return text with tr tags
         */
        public static String addTrTag(String text) {
                return TR_START + text + TR_END;
        }

        /**
         * Surround the given {@link String} by adding HTML fat tags.
         *
         * @param text to surround
         * @return text with fat tags
         */
        public static String addFatTag(String text) {
                return FAT_START + text + FAT_END;
        }

        /**
         * Adds the title1 tag.
         *
         * @param text the text
         * @return the string with title tags
         */
        public static String addTitle1Tag(String text) {
                return TITLE1_START + text + TITLE1_END;
        }

        /**
         * Adds the title2 tag.
         *
         * @param text the text
         * @return the string with title tags
         */
        public static String addTitle2Tag(String text) {
                return TITLE2_START + text + TITLE2_END;
        }

        /**
         * Adds the title3 tag.
         *
         * @param text the text
         * @return the string with title tags
         */
        public static String addTitle3Tag(String text) {
                return TITLE3_START + text + TITLE3_END;
        }

        /**
         * Adds the address tag.
         *
         * @param text the text
         * @return the string with address tags.
         */
        public static String addAddressTag(String text) {
                return ADDRESS_START + text + ADDRESS_END;
        }

        /**
         * Adds the HREF reference for URLs.
         *
         * @param ref the reference
         * @return the string with address tags.
         */
        public static String addAhrefTag(String ref) {
                return A_HREF_START + ref + A_HREF_CLOSE + ref + A_HREF_END;
        }

        /**
         * Adds the italic style for text.
         *
         * @param text the text
         * @return the string with address tags.
         */
        public static String addItalicStyle(String text) {
                return ITALIC_START + text + ITALIC_END;
        }

        /**
         * Provides a HTML row for a table with fatText leading and otherText
         * following
         *
         * @param fatText the leading text
         * @param otherText optional List of Strings to be appended to the row
         * @return the converted string
         */
        public static String convForHTMLRow(String fatText, String... otherText) {
                StringBuilder builder = new StringBuilder();
                builder.append(HTMLHelper.TR_START);
                HTMLHelper.addTdAndFatTag(builder, fatText);
                for (String text : otherText) {
                        HTMLHelper.addTdTag(builder, text);
                }
                builder.append(HTMLHelper.TR_END);

                return builder.toString();
        }
}
