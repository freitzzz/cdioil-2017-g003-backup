package cdioil.files;

import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;

/**
 * FileReader class that reads the content of a file
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class FileReader {
    /**
     * Hides default constructor
     */
    private FileReader(){}
    /**
     * Method that reads the content of a file
     * @param file String with the file path
     * @return List with the content of the file, or null if an error occured
     */
    public static List<String> readFile(File file){
        if(file==null)return null;
        try{
            return Files.readAllLines(file.toPath());
        }catch(IOException e){
            ExceptionLogger.logException(LoggerFileNames.UTIL_LOGGER_FILE_NAME,
                    Level.SEVERE, e.getMessage());
            return null;
        }
    }
}
