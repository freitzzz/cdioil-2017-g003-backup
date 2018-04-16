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
     * Message to indicate the list of all managers being presented.
     */
    private final String MANAGER_LIST = localizationHandler.getMessageValue
        ("info_all_managers_list");

    /**
     * Message to indicate that a manager must be picked.
     */
    private final String PICK_MANAGER = localizationHandler.getMessageValue
        ("request_manager_email");

    /**
     * Message to indicate that the admin must insert a category identifier.
     */
    private final String CATEGORY_ID_MESSAGE = localizationHandler.getMessageValue
        ("request_category_identifier_removal");

    /**
     * Error message for emails that are written incorrectly or don't exist.
     */
    private final String EMAIL_ERROR = localizationHandler.getMessageValue
        ("error_invalid_email");

    /**
     * Error message for categories that don't exist or that the manager isn't
     * associated to.
     */
    private final String CATEGORY_ERROR = localizationHandler.getMessageValue
        ("error_invalid_category");

    /**
     * Success message indicating that the categories were successfully removed
     * from the manager.
     */
    private final String SUCCESS_MESSAGE = localizationHandler.getMessageValue
        ("info_categories_removed");

    /**
     * Controller class responsible for handling the communication between the
     * UI classes and the domain classes.
     */
    private CategoryManagementController controller = new CategoryManagementController();

    /**
     * Method that runs US152 - Remover Categorias a um Gestor.
     */
    public void doShow() {
        Iterable<Manager> managerList = controller.listAllManagers();
        boolean emailFlag = false;
        boolean categoryFlag = false;
        System.out.println(SEPARATOR);
        System.out.println(MANAGER_LIST);
        managerList.forEach((t) -> {
            System.out.println(t.toString());
        });
        System.out.println(SEPARATOR);
        while (!emailFlag) {
            try {
                if (controller.setManager(Console.readLine(PICK_MANAGER))) {
                    System.out.println(SEPARATOR);
                    emailFlag = true;
                    while (!categoryFlag) {
                        if (controller.removeCategories(Console.readLine(CATEGORY_ID_MESSAGE))) {
                            System.out.println(SUCCESS_MESSAGE);
                            categoryFlag = true;
                        } else {
                            System.out.println(CATEGORY_ERROR);
                        }
                    }
                } else {
                    System.out.println(EMAIL_ERROR);
                }
                System.out.println(SEPARATOR);
            } catch (IllegalArgumentException e) {
                System.out.println(EMAIL_ERROR);
            }
        }
    }
}
