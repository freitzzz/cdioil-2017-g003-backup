package cdioil.files;

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
}
