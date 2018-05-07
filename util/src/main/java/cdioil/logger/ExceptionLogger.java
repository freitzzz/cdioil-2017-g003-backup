package cdioil.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Class that is responsible for logging every exception that can occur in the
 * app.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @since Version 5.0 of FeedbackMonkey
 */
public class ExceptionLogger {
    /**
     * Private constructor to hide the implicit public one.
     */
    private ExceptionLogger() {}
    /**
     * Logs an exception to a file
     *
     * @param loggerFile file that contains exception logs
     * @param exceptionLevel level of the exception
     * @param message message of the exception
     */
    public static void logException(String loggerFile, Level exceptionLevel,
            String message) {
        try {
            FileHandler fileHandler = new FileHandler(loggerFile);
            LogRecord logRecord = new LogRecord(exceptionLevel, message);
            fileHandler.publish(logRecord);
            fileHandler.close();
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(ExceptionLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
