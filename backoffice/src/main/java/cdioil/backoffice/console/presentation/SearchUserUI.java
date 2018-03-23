/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.application.authz.SearchUserController;
import cdioil.backoffice.console.utils.Console;
import cdioil.domain.authz.SystemUser;
import java.util.List;

/**
 * User Interface for the User Story US136 - Search the users of the system by e-mail.
 *
 * @author Ana Guerra (1161191)
 */
public class SearchUserUI {
     /**
     * Represents the exit code for the User Interface.
     */
    private static final String EXIT_CODE = "Sair";

    /**
     * Represents a message that indicates the administrator to enter the exit code in order to exit.
     */
    private static final String EXIT_MESSAGE = "A qualquer momento digite \"" + EXIT_CODE + "\" para sair.";

    /**
     * Represents a message that indicates the administrator to insert the email to search.
     */
    private static final String MESSAGE_EMAIL = "Por favor indique o email do "
            + "uilizador a pesquisar:";
    
   /**
     * Represents a message that informs the administrator that no user found.
     */
    private static final String MENSAGEM_USER_NOT_FOUND = "Nenhum utilizador encontrado!";
    /**
     * Represents a message that delimitates the list of the users.
     */
    private static final String[] MENSAGEM_LIST_USER = {"#####Utilizadores#####",
        "#####                       #####"};
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
            if (email.equalsIgnoreCase(EXIT_CODE)) {
                return;
            }
            List<SystemUser> listaU = ctrl.usersByEmail(email);
            if (listaU.isEmpty()) {
                System.out.println(MENSAGEM_USER_NOT_FOUND);
            } else {
                System.out.println(MENSAGEM_LIST_USER[0]);
                listaU.forEach((c) -> {
                    System.out.println(c.toString());
                });
                System.out.println(MENSAGEM_LIST_USER[1]);
                catched = true;
            }
        }
    }
}

