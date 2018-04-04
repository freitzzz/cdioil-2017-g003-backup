package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.authz.ListUsersController;
import cdioil.domain.authz.SystemUser;

/**
 * UI class of US130 - Listar Todos Os Utilizadores Do Sistema
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ListUsersUI {

    /**
     * Separator used for clarity.
     */
    private static final String SEPARATOR = "=============================";
    /**
     * Message that identifies that this is US130 - Listar Todos Os Utilizadores
     * Do Sistema.
     */
    private static final String ALL_USERS = "Lista de Todos os Utilizadores"
            + " do Sistema\n";
    /**
     * Controller responsible for the communication between the UI classes
     * and the domain classes.
     */
    private ListUsersController controller = new ListUsersController();

    /**
     * Method that runs US130 - Listar Todos Os Utilizadores Do Sistema.
     */
    public void listAllUsers() {
        Iterable<SystemUser> allUsers = controller.listAllUsers();

        System.out.println(SEPARATOR);
        System.out.println(ALL_USERS);
        allUsers.forEach((t) -> {
            System.out.println(t.toString());
        });
        System.out.println(SEPARATOR);
    }
}
