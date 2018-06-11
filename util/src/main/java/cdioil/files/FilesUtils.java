package cdioil.files;

import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * FileUtils class that serves as a utiliary class regarding files
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class FilesUtils {
    /**
     * Constant that represents the regular expression that matches a single invalid character 
     * contained on a file name
     */
    private static final String INVALID_FILE_NAME_REGEX=createInvalidFileNameRegex();
    
    private static final String UTF8_ENCODER = "UTF-8";
    
    /**
     * Hides default constructor
     */
    private FilesUtils(){}
    /**
     * Method that checks if a certain file name is valid or not
     * @param fileName String with the file name being validated
     * @return boolean true if the file name is valid, false if not
     */
    public static boolean isFileNameValid(String fileName){
        return !fileName.trim().isEmpty() ? true : !Pattern.compile(INVALID_FILE_NAME_REGEX).matcher(fileName).find();
    }
    /**
     * Method that creates a regular expression that matches a single invalid character 
     * contained on a file name
     * @return String with the regular expression that matches a single invalid character 
     * contained on a file name
     */
    private static String createInvalidFileNameRegex(){
        return "([:][*]?[?]?[|]?[<]?[>]?[/]?[\\]?[\"]?)"
                + "|([:]?[*][?]?[|]?[<]?[>]?[/]?[\\]?[\"]?)"
                + "|([:]?[*]?[?][|]?[<]?[>]?[/]?[\\]?[\"]?)"
                + "|([:]?[*]?[?]?[|][<]?[>]?[/]?[\\]?[\"]?)"
                + "|([:]?[*]?[?]?[|]?[<][>]?[/]?[\\]?[\"]?)"
                + "|([:]?[*]?[?]?[|]?[<]?[>][/]?[\\]?[\"]?)"
                + "|([:]?[*]?[?]?[|]?[<]?[>]?[/][\\]?[\"]?)"
                + "|([:]?[*]?[?]?[|]?[<]?[>]?[/]?[\\][\"]?)"
                + "|([:]?[*]?[?]?[|]?[<]?[>]?[/]?[\\]?[\"])";
    }

    /**
     * Converts a class resource path String to an UTF-8 compliant String.
     *
     * @param resourceFilePath relative resource file path
     * @return UTF-8 compliant Class resource path String.
     */
    public static String convertStringToUTF8(String resourceFilePath) {

        String result = null;

        try {
            result = URLDecoder.decode(resourceFilePath, UTF8_ENCODER);
        } catch (UnsupportedEncodingException ex) {
            ExceptionLogger.logException(LoggerFileNames.UTIL_LOGGER_FILE_NAME, Level.SEVERE, ex.getMessage());
            Logger.getLogger(FilesUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
    /**
     * Method that converts a list into a pure String
     * <br>Each item of the list is a new line
     * @param <T> Generic-Type with the type of List items
     * @param content List with the content being
     * @return String with the content of the list as a pure string
     */
    public static <T> String listAsString(List<T> content){
        StringBuilder builder=new StringBuilder();
        content.forEach((line)->{builder.append(line).append('\n');});
        return builder.toString();
    }
}
