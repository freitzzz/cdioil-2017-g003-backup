package cdioil.backoffice.console.presentation;

import cdioil.backoffice.utils.BackOfficeLocalizationHandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class for the application's backoffice.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class BackOfficeMain {
    public static void main(String[] args) {

        if (args.length != 0 && "-load".equals(args[0])) {
                new LoadAnswersUI();
                return;
            }

        //Load localize strings
        try {
            BackOfficeLocalizationHandler.getInstance().loadStrings();
        } catch (IOException ex) {
            Logger.getLogger(BackOfficeMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        new BackOfficeLogin().backofficeLogin();
    }
}