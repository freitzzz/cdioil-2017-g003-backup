package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.authz.ListUsersController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.domain.authz.SystemUser;

/**
 * UI class of US130 - Listar Todos Os Utilizadores Do Sistema
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ListUsersUI {

    /**
     * Localization handler to load messages in several langugaes.
     */
    private final BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();
    /**
     * Separator used for clarity.
     */
    private static final String SEPARATOR = "=============================";
    /**
     * Message that identifies that this is US130 - Listar Todos Os Utilizadores
     * Do Sistema.
     */
    private final String infoAllUsers = localizationHandler.getMessageValue("info_all_users_list");
    /**
     * Controller responsible for the communication between the UI classes and
     * the domain classes.
     */
    private ListUsersController controller = new ListUsersController();

    /**
     * Method that runs US130 - Listar Todos Os Utilizadores Do Sistema.
     */
    public void listAllUsers() {
        Iterable<SystemUser> allUsers = controller.listAllUsers();

        System.out.println(SEPARATOR);
        System.out.println(infoAllUsers);
        allUsers.forEach(t -> {
            System.out.println(t.toString());
        });
        System.out.println(SEPARATOR);
    }
}
