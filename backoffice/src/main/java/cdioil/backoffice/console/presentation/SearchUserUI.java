/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.authz.SearchUserController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;
import cdioil.domain.authz.SystemUser;
import java.util.List;

/**
 * User Interface for the User Story US136 - Search the users of the system by e-mail.
 *
 * @author Ana Guerra (1161191)
 */
public class SearchUserUI {

    /**
     * Single instance of <code>BackOfficeLocalizationHandler</code>.
     */
    private final BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();
    /**
     * Represents the exit code for the User Interface.
     */
    private final String OPTION_EXIT = localizationHandler.getMessageValue("option_exit");
    /**
     * Represents a message that indicates the administrator to enter the exit code in order to exit.
     */
    private final String EXIT_MESSAGE = localizationHandler.getMessageValue("info_exit_input");
    /**
     * Represents a message that indicates the administrator to insert the email to search.
     */
    private final String MESSAGE_EMAIL = localizationHandler.getMessageValue("request_user_email");
    /**
     * Represents a message that informs the administrator that no user found.
     */
    private final String MENSAGEM_USER_NOT_FOUND = localizationHandler.getMessageValue("error_user_not_found");
    /**
     * Represents a message that delimitates the list of the users.
     */
    private final String MENSAGEM_LIST_USER = localizationHandler.getMessageValue("info_users");
    /**
     * Separator used for clarity.
     */
    private final String SEPARATOR = localizationHandler.getMessageValue("separator");

    /**
     * Instance of Controller that intermediates the interactions between the administrator and the system.
     */
    private final SearchUserController ctrl;

    /**
     * Creates a new User Interface.
     */
    public SearchUserUI() {
        ctrl = new SearchUserController();
        searchUsers();
    }

    /**
     * Method that intermediates the interactions with the administrator (creates the UI itself).
     */
    private void searchUsers() {

        System.out.println(EXIT_MESSAGE);
        boolean catched = false;
        while (!catched) {
            String email = Console.readLine(MESSAGE_EMAIL);
            if (email.equalsIgnoreCase(OPTION_EXIT)) {
                return;
            }
            List<SystemUser> listaU = ctrl.usersByEmail(email);
            if (listaU.isEmpty()) {
                System.out.println(MENSAGEM_USER_NOT_FOUND);
            } else {
                System.out.println(MENSAGEM_LIST_USER);
                listaU.forEach((c) -> {
                    System.out.println(c.toString());
                });
                System.out.println(SEPARATOR);
                catched = true;
            }
        }
    }
}
