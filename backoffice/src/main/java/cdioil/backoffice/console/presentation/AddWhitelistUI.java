package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.authz.AddWhitelistController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;

/**
 * User story 103's user interface.
 *
 * @author António Sousa [1161371]
 */
public class AddWhitelistUI {

    private static final String SEPARATOR = "===========================\n";

    private final String infoExiting = BackOfficeLocalizationHandler.getInstance().getMessageValue("info_exiting");
    private final String infoExitInput = BackOfficeLocalizationHandler.getInstance().getMessageValue("info_exit_input");

    private final String optionWhitelistDomain = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_whitelist_domain");
    private final String optionExit = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_exit");

    private final String requestSelectOption = BackOfficeLocalizationHandler.getInstance().getMessageValue("request_select_option");
    private final String requestNewDomain = BackOfficeLocalizationHandler.getInstance().getMessageValue("request_new_domain");

    private final String errorInvalidOption = BackOfficeLocalizationHandler.getInstance().getMessageValue("error_invalid_option");
    /**
     * The use case's controller.
     */
    private final AddWhitelistController controller;

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
            System.out.println("1. " + optionWhitelistDomain);
            System.out.println("0. " + optionExit);
            option = Console.readInteger(requestSelectOption);

            switch (option) {

                case 0:
                    System.out.println(infoExiting);
                    break;

                case 1:
                    addNewDomain();
                    break;

                default:
                    System.out.println(errorInvalidOption);
                    break;
            }
        }
    }

    /**
     * Adds a new domain to the whitelist.
     */
    private void addNewDomain() {
        Iterable<String> whitelistedDomains = controller.getExistingEntries();

        for (String domain : whitelistedDomains) {
            System.out.println(domain);
        }

        System.out.println(SEPARATOR);

        String newDomain = Console.readLine(requestNewDomain + "\n" + infoExitInput);
        if (newDomain.equalsIgnoreCase(optionExit)) {
            return;
        }
        controller.addAuthorizedDomain(newDomain);
    }
}
