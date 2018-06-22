/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.ImportCategoriesController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;
import cdioil.domain.MarketStructure;

/**
 * User Interface for the User Story 201 - Import Categories from a File.
 *
 * @author Rita Gonçalves (1160912)
 */
public class ImportCategoriesUI {

    /**
     * Localization handler to load messages in several langugaes.
     */
    private final BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();

    /**
     * Represents the exit code for the User Interface.
     */
    private final String exitCode = localizationHandler.getMessageValue("option_exit");

    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private final String exitMessage = localizationHandler.getMessageValue("info_exit_message");

    /**
     * Represents a message that indicates the user to insert the path of the file to import.
     */
    private final String filePathMessage = localizationHandler.getMessageValue("request_file_path");

    /**
     * Represents a message that informs the user that the inserted filepath was not found.
     */
    private final String errorFilePathNotFound = localizationHandler.getMessageValue("error_importing_categories") + exitMessage;

    /**
     * Represents a message that informs the user about how many categories were imported.
     */
    private final String infoNumberCategoriesImported = localizationHandler.getMessageValue("info_number_categories_imported");

    /**
     * Represents a message that informs the user that no category was imported.
     */
    private final String errorInvalidCategories = localizationHandler.getMessageValue("error_no_imported_categories");
/**
     * Represents a message that informs the user that exist the category.
     */
    private final String errorExistCategories = localizationHandler.getMessageValue("error_exist_categories");

    /**
     * Separator used for clarity.
     */
    private final String separator = localizationHandler.getMessageValue("separator");

    /**
     * Instance of Controller that intermediates the interactions between the administrator and the system.
     */
    private final ImportCategoriesController ctrl;

    /**
     * Creates a new User Interface.
     */
    public ImportCategoriesUI() {
        ctrl = new ImportCategoriesController();
        importCategories();
    }

    /**
     * Method that intermediates the interactions with the administrator (creates the UI itself).
     */
    private void importCategories() {
        System.out.println(separator);
        System.out.println(exitMessage);
        boolean catched = false;
        while (!catched) {
            String filePath = Console.readLine(filePathMessage);
            if (filePath.equalsIgnoreCase(exitCode)) {
                return;
            }
            MarketStructure em = ctrl.readCategories(filePath);

            if (em == null) {
                String option = Console.readLine(errorFilePathNotFound);
                if (option.equalsIgnoreCase(exitCode)) {
                    return;
                }
            } else {
                if (ctrl.getNumberOfCategoriesRead() == 0) {
                    System.out.println(errorInvalidCategories);
                    System.out.println(errorExistCategories);
                } else {
                    System.out.println(infoNumberCategoriesImported + " " + ctrl.getNumberOfCategoriesRead());
                    catched = true;
                }
            }
        }
    }

}
