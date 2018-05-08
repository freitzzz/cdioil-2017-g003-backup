/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.authz.CategoryManagementController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;
import cdioil.domain.authz.Manager;

/**
 * User Interface used for US150 - Associar Categorias a um Gestor and US152 - Remover Categorias de um Gestor.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 */
public class CategoryManagementUI {

    /**
     * Localization handler to load messages in several langugaes.
     */
    private final BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();

    /**
     * Separator used for clarity.
     */
    private final String separator = localizationHandler.getMessageValue("separator");

    /**
     * Represents the exit code for the User Interface.
     */
    private final String exitCode = localizationHandler.getMessageValue("option_exit");

    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private final String exitMessage = localizationHandler.getMessageValue("info_exit_message");

    /**
     * Message to indicate the list of all managers being presented.
     */
    private final String managerList = localizationHandler.getMessageValue("info_all_managers_list");

    /**
     * Message to indicate that a manager must be picked.
     */
    private final String pickManager = localizationHandler.getMessageValue("request_manager_email");

    /**
     * Message to indicate that the admin must insert a category identifier.
     */
    private final String insertCategoryId = localizationHandler.getMessageValue("request_category_identifier_association");

    /**
     * Error message for emails that are written incorrectly or don't exist.
     */
    private final String emailError = localizationHandler.getMessageValue("error_invalid_email");

    /**
     * Error message for categories that don't exist or that the manager already is associated to it.
     */
    private final String categoryError = localizationHandler.getMessageValue("error_invalid_category");

    /**
     * Success message indicating that the categories were successfully associated to the manager.
     */
    private final String addedSuccessMessage = localizationHandler.getMessageValue("info_categories_added");

    /**
     * Success message indicating that the categories were successfully removed from the manager.
     */
    private final String removedSuccessMessage = localizationHandler.getMessageValue("info_categories_removed");

    /**
     * Error message indicating that there aren't any managers.
     */
    private final String noManagers = localizationHandler.getMessageValue("error_no_managers");

    /**
     * Controller class responsible for handling the communication between the UI classes and the domain classes.
     */
    private final CategoryManagementController ctrl = new CategoryManagementController();

    /**
     * Method that lists all managers in the UI for the user to choose one.
     *
     * @return true, if the user chooses a valid manager. False if the user decides to exit
     */
    public final boolean pickManager() {
        System.out.println(separator);

        Iterable<Manager> managers = ctrl.listAllManagers();

        if (ctrl.size(managers) == 0) {
            System.out.println(noManagers);
            System.out.println(separator);
            return false;
        }

        System.out.println(managerList);
        managers.forEach(manager -> 
            System.out.println(manager.toString())
        );

        System.out.println(separator);

        System.out.println(exitMessage);

        System.out.println(separator);

        boolean emailFlag = false;
        while (!emailFlag) {
            try {
                String email = Console.readLine(pickManager);
                if (email.equalsIgnoreCase(exitCode)) {
                    return false;
                }

                if (ctrl.setManager(email)) {
                    System.out.println(separator);
                    emailFlag = true;
                } else {
                    System.out.println(emailError);
                }
            } catch (IllegalArgumentException ex) {
                System.out.println(emailError);
            }
        }
        return true;
    }

    /**
     * Method that allows the user to insert the path of the categories to manage.
     *
     * @return true, if the user chooses a valid category. False if the user decides to exit
     */
    public final boolean pickCategory() {
        boolean categoryFlag = false;
        while (!categoryFlag) {
            String path = Console.readLine(insertCategoryId);
            if (path.equalsIgnoreCase(exitCode)) {
                return false;
            }
            if (ctrl.setPath(path)) {
                categoryFlag = true;
            } else {
                System.out.println(categoryError);
            }
        }
        return true;
    }

    /**
     * Method that allows the user to associate categories to a manager.
     */
    public final void addCategories() {
        if (!pickManager()) {
            return;
        }

        if (!pickCategory()) {
            return;
        }

        if (ctrl.addCategories()) {
            System.out.println(addedSuccessMessage);
        }
    }

    /**
     * Method that allows the user to remove categories from a manager.
     */
    public final void removeCategories() {
        if (!pickManager()) {
            return;
        }

        if (!pickCategory()) {
            return;
        }

        if (ctrl.removeCategories()) {
            System.out.println(removedSuccessMessage);
        }
    }
}
