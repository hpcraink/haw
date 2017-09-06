package haw.common.helper;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 * The Class FileHelper.
 */
public abstract class FileHelper {

        /**
         * Gets the file extension of the document.
         *
         * @param fileName the file name
         * @return the file extension.
         */
        public static String getFileExt(String fileName) {
                String adjustedName;
                if (fileName.startsWith(".")) {
                        adjustedName = fileName.substring(1);
                } else {
                        adjustedName = fileName;
                }
                String ext;
                int idxOfDot = adjustedName.lastIndexOf('.');
                int length = adjustedName.length();
                if (idxOfDot >= 0 && idxOfDot < (length - 1)) {
                        ext = adjustedName.substring(idxOfDot + 1, length);
                } else {
                        ext = "";
                }
                return ext;
        }

        /**
         * Gets the simple file name (without extension).
         *
         * @param fileName the file name
         * @return the simple file name
         */
        public static String getSimpleFileName(String fileName) {
                String adjustedName;
                if (fileName.startsWith(".")) {
                        adjustedName = fileName.substring(1);
                } else {
                        adjustedName = fileName;
                }
                String simpleName;
                int idxOfDot = adjustedName.lastIndexOf('.');
                if (idxOfDot >= 0) {
                        simpleName = adjustedName.substring(0, idxOfDot);
                } else {
                        simpleName = adjustedName;
                }
                return simpleName;
        }

        /**
         * Gets a "clean" file name (without any dots for sub-directories).
         *
         * @param fileName the file name
         * @return the clean file name
         */
        public static String getCleanFileName(String fileName) {
                // As a precaution, replace any ./ or ../ into nul:
                String f = fileName.replaceAll("\\.\\./", "/")
                        .replaceAll("\\./", "/")
                        .replaceAll("//+", "/");
                return f;
        }

        /**
         * Reads the file of name fileName as String.
         *
         * The file is read in chunks of 4kB. There's no further checking on the
         * suitability of the fileName, no checking on the file's content,
         * whether it is presentable as String, no size checks etc. Specifically
         * the fileName should be checked beforehand, to make sure, no
         * System-Files are being read and leaked.
         *
         * @param fileName the file name including it's full Path
         * @return the file's content as String, or null if not found
         */
        public static String readStringFromFile(String fileName) {
                String s;
                try {
                        // No hassle, read in using Apache Commons IO
                        s = FileUtils.readFileToString(new File (fileName), "utf8");
                } catch (IOException ex) {
                        return null;
                }
                return s;
        }

        /**
         * Writes the String content passed into the file of name fileName.
         *
         * This writes the content into the file; if there are sub-directories
         * in the fileName, these directories are created. There's no further
         * checking on the suitability of the fileName, no checking on the
         * content, Specifically the fileName should be checked beforehand, to
         * make sure, no System-Files are being read and leaked.
         *
         * @param fileName the file name
         * @param content the content to write
         */
        public static void writeStringToFile(String fileName, String content) {
                try {
                        // Create subdirectory first -- if it's specified
                        int lastIndexDirSep = fileName.lastIndexOf("/");
                        if (lastIndexDirSep > 0) {
                                String dirName = fileName.substring(0, lastIndexDirSep);
                                new File(dirName).mkdirs();
                        }
                        
                        // No hassle, write out using Apache Commons IO
                        FileUtils.writeStringToFile(new File(fileName), content, "utf8");
                } catch (IOException ex) {
                }
        }

}
