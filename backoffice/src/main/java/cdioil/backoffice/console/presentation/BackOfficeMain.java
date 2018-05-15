package cdioil.backoffice.console.presentation;

import cdioil.backoffice.utils.BackOfficeLocalizationHandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * Main class for the application's backoffice.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil DurÃ£o</a>
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
            Logger.getLogger(BackOfficeMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        new BackOfficeLogin().backofficeLogin();
    }
}