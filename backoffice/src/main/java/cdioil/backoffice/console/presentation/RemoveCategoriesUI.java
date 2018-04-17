package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.authz.CategoryManagementController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.backoffice.utils.Console;
import cdioil.domain.authz.Manager;

/**
 * UI class for US152 - Remover Categorias a um Gestor.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class RemoveCategoriesUI {

    /**
     * Localization handler to load messages in several langugaes.
     */
    private final BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();

    /**
     * Separator used for clarity.
     */
    private static final String SEPARATOR = "=============================";

    /**
     * Represents the exit code for the User Interface.
     */
    private final String EXIT_CODE = localizationHandler.getMessageValue("option_message");

    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private final String EXIT_MESSAGE = localizationHandler.getMessageValue("exit_message");

    /**
     * Message to indicate the list of all managers being presented.
     */
    private final String MANAGER_LIST = localizationHandler.getMessageValue("info_all_managers_list");

    /**
     * Message to indicate that a manager must be picked.
     */
    private final String PICK_MANAGER = localizationHandler.getMessageValue("request_manager_email");

    /**
     * Message to indicate that the admin must insert a category identifier.
     */
    private final String CATEGORY_ID_MESSAGE = localizationHandler.getMessageValue("request_category_identifier_removal");

    /**
     * Error message for emails that are written incorrectly or don't exist.
     */
    private final String EMAIL_ERROR = localizationHandler.getMessageValue("error_invalid_email");

    /**
     * Error message for categories that don't exist or that the manager isn't associated to.
     */
    private final String CATEGORY_ERROR = localizationHandler.getMessageValue("error_invalid_category");

    /**
     * Success message indicating that the categories were successfully removed from the manager.
     */
    private final String SUCCESS_MESSAGE = localizationHandler.getMessageValue("info_categories_removed");

    /**
     * Error message indicating that there aren't any managers.
     */
    private final String NO_MANAGERS = localizationHandler.getMessageValue("error_no_managers");

    /**
     * Controller class responsible for handling the communication between the UI classes and the domain classes.
     */
    private CategoryManagementController ctrl = new CategoryManagementController();

    /**
     * Method that runs US152 - Remover Categorias a um Gestor.
     */
    public void doShow() {
        System.out.println(SEPARATOR);

        Iterable<Manager> managerList = ctrl.listAllManagers();

        if (ctrl.size(managerList) == 0) {
            System.out.println(NO_MANAGERS);
            System.out.println(SEPARATOR);
            return;
        }

        System.out.println(EXIT_MESSAGE);

        System.out.println(MANAGER_LIST);
        managerList.forEach((t) -> {
            System.out.println(t.toString());
        });

        System.out.println(SEPARATOR);

        boolean emailFlag = false;
        while (!emailFlag) {
            try {
                String email = Console.readLine(PICK_MANAGER);
                if (email.equalsIgnoreCase(EXIT_CODE)) return;

                if (ctrl.setManager(email)) {
                    System.out.println(SEPARATOR);
                    emailFlag = true;
                } else {
                    System.out.println(EMAIL_ERROR);
                }
            } catch (IllegalArgumentException ex) {
                System.out.println(EMAIL_ERROR);
            }
        }

        boolean categoryFlag = false;
        while (!categoryFlag) {
            String path = Console.readLine(CATEGORY_ID_MESSAGE);
            if (path.equalsIgnoreCase(EXIT_CODE)) return;

            if (ctrl.checkPath(path) && ctrl.removeCategories(path)) {
                System.out.println(SUCCESS_MESSAGE);
                categoryFlag = true;
            } else {
                System.out.println(CATEGORY_ERROR);
            }
        }
    }
}
