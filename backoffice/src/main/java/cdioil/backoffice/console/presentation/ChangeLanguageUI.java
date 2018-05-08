package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.ChangeLanguageController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;
import cdioil.langs.Language;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * User Interface for changing the application's language.
 *
 * @author Antonio Sousa
 */
public class ChangeLanguageUI {
    
    private final BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();

    private String requestSelectLanguage = localizationHandler.getMessageValue("request_select_language");

    private String optionExit = localizationHandler.getMessageValue("option_exit");

    private String errorInvalidOption = localizationHandler.getMessageValue("error_invalid_option");

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

            System.out.println(0 + ". " + optionExit);

            option = Console.readInteger(requestSelectLanguage);

            try {
                switch (option) {

                    case 0:
                        break;
                    case 1:
                        controller.changeLanguage(Language.PT);
                        refreshLocalizedMessages();
                        break;
                    case 2:
                        controller.changeLanguage(Language.EN_US);
                        refreshLocalizedMessages();
                        break;

                    default:
                        System.out.println(errorInvalidOption);
                        break;

                }
            } catch (IOException e) {
                Logger.getLogger(ChangeLanguageUI.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }

    /**
     * Updates UI messages.
     */
    private void refreshLocalizedMessages() {

        requestSelectLanguage = localizationHandler.getMessageValue("request_select_language");
        optionExit = localizationHandler.getMessageValue("option_exit");
        errorInvalidOption = localizationHandler.getMessageValue("error_invalid_option");
    }

}
