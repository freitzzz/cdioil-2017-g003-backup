package cdioil.files;

import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;

/**
 * FileWriter class that writes content to a file
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class FileWriter {
    /**
     * Hides default constructor
     */
    private FileWriter(){}
    /**
     * Method that writes content to a file
     * @param file File with the file that is going be written
     * @param content String with the content to be writed on the file
     * @return boolean true if the content was written to the file with success, false if an error occured
     */
    public static boolean writeFile(File file,String content){
        if(file==null||content==null){
            return false;
        }
        try{
            Files.write(file.toPath(),content.getBytes());
            return true;
        }catch(IOException e){
            ExceptionLogger.logException(LoggerFileNames.UTIL_LOGGER_FILE_NAME,
                    Level.SEVERE, e.getMessage());
            return false;
        }
    }
    /**
     * Method that writes content to a file
     * <br><b>Note:</b>
     * <br>- Using StringBuilder to concatenate Strings of the List since the creation 
     * of a String with all the content is <b>way</b> faster with StringBuilder
     * <br>Test with a List of over 60,000 Strings:
     * <br>- With pure String concatenation (<b>+=</b>) ~ 17 S (Seconds)
     * <br>- With StringBuilder (<b>.append() + toString())</b> ~17 <b>MS</b> (Milliseconds)
     * <br>Yup that's <b>1000%</b> faster
     * @param file File with the file that is going to be written
     * @param content List with the content that is going to be writed on the file
     * @return boolean true if the content was written to the file with success, false if an error occured 
     */
    public static boolean writeFile(File file,List<String> content){
        if(file==null){
            return false;
        }
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<content.size();i++){
            builder.append(content.get(i)).append('\n');
        }
        return writeFile(file,builder.toString());
    }
}
