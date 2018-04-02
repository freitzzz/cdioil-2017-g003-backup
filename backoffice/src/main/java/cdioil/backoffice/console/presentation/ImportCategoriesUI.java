/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.application.ImportCategoriesController;
import cdioil.backoffice.utils.Console;
import cdioil.domain.Category;
import java.util.List;

/**
 * User Interface for the User Story 201 - Import Categories from a File.
 *
 * @author Rita Gonçalves (1160912)
 */
public class ImportCategoriesUI {

    /**
     * Represents the exit code for the User Interface.
     */
    private static final String EXIT_CODE = "Sair";

    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private static final String EXIT_MESSAGE = "A qualquer momento digite \"" + EXIT_CODE + "\" para sair.";

    /**
     * Represents a message that indicates the user to insert the path of the file to import.
     */
    private static final String FILEPATH_MESSAGE = "Por favor indique o caminho do "
            + "ficheiro que pretende importar:";

    /**
     * Represents a message that informs the user that the inserted filepath was not found.
     */
    private static final String FILEPATH_NOT_FOUND_MESSAGE = "Caminho de ficheiro "
            + "não encontrado!\nPretende continuar? " + EXIT_MESSAGE;

    /**
     * Represents a message that informs the user that the format of the file is not valid.
     */
    private static final String INVALID_FORMAT_MESSAGE = "Formato de ficheiro inválido!"
            + "\nNenhuma categoria foi importada.";

    /**
     * Represents a message that delimitates the imported categories.
     */
    private static final String[] IMPORTED_CATEGORIES_MESSAGE = {"#####Categorias Importadas#####",
        "#####                       #####"};

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
        System.out.println(EXIT_MESSAGE);
        boolean catched = false;
        while (!catched) {
            String filePath = Console.readLine(FILEPATH_MESSAGE);
            if (filePath.equalsIgnoreCase(EXIT_CODE)) {
                return;
            }

            List<Category> categories = ctrl.readCategories(filePath);
            if (categories == null) {
                String option = Console.readLine(FILEPATH_NOT_FOUND_MESSAGE);
                if (option.equalsIgnoreCase(EXIT_CODE)) {
                    return;
                }
            } else {
                if (categories.isEmpty()) {
                    System.out.println(INVALID_FORMAT_MESSAGE);
                } else {
                    System.out.println(IMPORTED_CATEGORIES_MESSAGE[0]);
                    categories.forEach((c) -> {
                        System.out.println(c.toString());
                    });
                    System.out.println(categories.size());
                    System.out.println(IMPORTED_CATEGORIES_MESSAGE[1]);
                    catched = true;
                }
            }
        }
    }

}
