package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.ChangeLanguageController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.backoffice.utils.Console;
import cdioil.langs.Language;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * User Interface for changing the application's language.
 *
 * @author Antonio Sousa
 */
public class ChangeLanguageUI {

    private final String REQUEST_SELECT_LANGUAGE = BackOfficeLocalizationHandler.getInstance().getMessageValue("request_select_language");

    private final String OPTION_EXIT = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_exit");

    private final String ERROR_INVALID_OPTION = BackOfficeLocalizationHandler.getInstance().getMessageValue("error_invalid_option");

    private final ChangeLanguageController controller;
    
    public ChangeLanguageUI() {
        controller = new ChangeLanguageController();
        showUI();
    }

    private void showUI() {

        int option = -1;

        while (option != 0) {

            Language languages[] = Language.values();

            for (int i = 0; i < languages.length; i++) {
                System.out.println(i + 1 + ". " + languages[i].toString());
            }

            System.out.println(0 + ". " + OPTION_EXIT);

            option = Console.readInteger(REQUEST_SELECT_LANGUAGE);

            try {
                switch (option) {

                    case 0:
                        break;
                    case 1:
                        controller.changeLanguage(Language.pt_PT);
                        break;
                    case 2:
                        controller.changeLanguage(Language.en_US);
                        break;

                    default:
                        System.out.println(ERROR_INVALID_OPTION);
                        break;

                }
            } catch (IOException | SAXException | ParserConfigurationException e) {
                Logger.getLogger(ChangeLanguageUI.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }

}
