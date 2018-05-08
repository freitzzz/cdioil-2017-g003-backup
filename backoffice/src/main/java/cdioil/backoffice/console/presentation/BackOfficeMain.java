package cdioil.backoffice.console.presentation;

import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Main class for the application's backoffice.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class BackOfficeMain {

    public static void main(String[] args) {

        if (args.length != 0) {
            if (args[0].equals("-load")) {
                new LoadAnswersUI();
                return;
            }
        }

        //Load localize strings
        try {
            BackOfficeLocalizationHandler.getInstance().loadStrings();
        } catch (IOException ex) {
            ExceptionLogger.logException(LoggerFileNames.BACKOFFICE_LOGGER_FILE_NAME,
                    Level.SEVERE, ex.getMessage());
        }
        new BackOfficeLogin().backofficeLogin();
    }
}
