package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.authz.AddWhitelistController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.backoffice.utils.Console;

/**
 * User story 103's user interface.
 *
 * @author António Sousa [1161371]
 */
public class AddWhitelistUI {

    private final String SEPARATOR = "===========================\n";
    
    private final String INFO_EXITING = BackOfficeLocalizationHandler.getInstance().getMessageValue("info_exiting");
    private final String INFO_EXIT_INPUT = BackOfficeLocalizationHandler.getInstance().getMessageValue("info_exit_input");

    private final String OPTION_WHITELIST_DOMAIN = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_whitelist_domain");
    private final String OPTION_EXIT = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_exit");

    private final String REQUEST_SELECT_OPTION = BackOfficeLocalizationHandler.getInstance().getMessageValue("request_select_option");
    private final String REQUEST_NEW_DOMAIN = BackOfficeLocalizationHandler.getInstance().getMessageValue("request_new_domain");

    private final String ERROR_INVALID_OPTION = BackOfficeLocalizationHandler.getInstance().getMessageValue("error_invalid_option");
    /**
     * The use case's controller.
     */
    private AddWhitelistController controller;

    /**
     * Instantiates the user interface.
     */
    public AddWhitelistUI() {

        controller = new AddWhitelistController();
        showUI();
    }

    private void showUI() {

        int option = -1;

        while (option != 0) {

            System.out.println(SEPARATOR);
            System.out.println("1. " + OPTION_WHITELIST_DOMAIN);
            System.out.println("0. " + OPTION_EXIT);
            option = Console.readInteger(REQUEST_SELECT_OPTION);

            switch (option) {

                case 0:
                    System.out.println(INFO_EXITING);
                    break;

                case 1:
                    
                    Iterable<String> whitelistedDomains = controller.getExistingEntries();

                    for (String domain : whitelistedDomains) {
                        System.out.println(domain);
                    }

                    System.out.println(SEPARATOR);
                    
                    String newDomain = Console.readLine(REQUEST_NEW_DOMAIN + "\n" + INFO_EXIT_INPUT);
                    if (newDomain.equalsIgnoreCase(OPTION_EXIT)) {
                        break;
                    }
                    controller.addAuthorizedDomain(newDomain);
                    break;

                default:
                    System.out.println(ERROR_INVALID_OPTION);
                    break;

            }
        }

    }

}
