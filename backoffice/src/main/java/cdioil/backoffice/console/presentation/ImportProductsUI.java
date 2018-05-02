/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.ImportProductsController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;
import cdioil.domain.Product;
import cdioil.files.InvalidFileFormattingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User Interface for use cases US-203 (import products from file)
 *
 * @author Ana Guerra (1161191)
 */
public class ImportProductsUI {

    /**
     * Single instance of <code>BackOfficeLocalizationHandler</code>.
     */
    private final BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();
    /**
     * Represents the exit code for the User Interface.
     */
    private final String OPTION_EXIT = localizationHandler.getMessageValue("option_exit");
    /**
     * Represents the exit code for the User Interface.
     */
    private final String OPTION_YES = localizationHandler.getMessageValue("option_yes");
    /**
     * Represents a message that indicates the user to insert the path of the file to import.
     */
    private final String REQUEST_FILE_PATH = localizationHandler.getMessageValue("request_file_path");
    /**
     * Represents a message that indicates the user to insert the path of the file to export.
     */
    private final String REQUEST_FILE_PATH_EXPORT = localizationHandler.getMessageValue("request_file_path_export");
    /**
     * Represents a message that informs the user that the inserted filepath was not found.
     */
    private final String ERROR_FILE_NOT_FOUND = localizationHandler.getMessageValue("error_file_not_found");
    /**
     * Represents a message that delimitates the imported products.
     */
    private final String INFO_NUM_PRODUCTS_IMPORTED = localizationHandler.getMessageValue("info_num_products_imported");
    /**
     * Represents a message that delimitates the exported products.
     */
    private final String INFO_NUM_PRODUCTS_EXPORTED = localizationHandler.getMessageValue("info_num_products_exported");

    /**
     * Represents a message that informs if product .
     */
    private final String INFO_ATUALIZAR_PRODUTO = localizationHandler.getMessageValue("info_atualization_products");
    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private final String INFO_EXIT_INPUT = localizationHandler.getMessageValue("info_exit_input");
    /**
     * Represents a message that indicate the invalid file format.
     */
    private final String ERROR_INVALID_FILE_FORMAT = localizationHandler.getMessageValue("error_invalid_file_format");
    /**
     * Represents a message that indicate that no imported products
     */
    private final String ERROR_NO_IMPORTED_PRODUCTS = localizationHandler.getMessageValue("error_no_imported_products");
    /**
     * Instance of Controller that intermediates the interactions between the admin and the system.
     */
    private final ImportProductsController controller;

    /**
     * Creates a new User Interface for use case US-203.
     */
    public ImportProductsUI() {
        controller = new ImportProductsController();
        importProducts();
    }

    /**
     * Method for showing the UI itself.
     */
    private void importProducts() {

        String fileRead = Console.readLine(REQUEST_FILE_PATH + "\n" + INFO_EXIT_INPUT);
        if (fileRead.equalsIgnoreCase(OPTION_EXIT)) {
            return;
        }
        String fileExport = Console.readLine(REQUEST_FILE_PATH_EXPORT + "\n" + INFO_EXIT_INPUT);

        if (fileExport.equalsIgnoreCase(OPTION_EXIT)) {
            return;
        }
        Map<String, List<Product>> existsProducts = new HashMap<>();
        Map<String, Product> atualizationProducts = new HashMap<>();

        try {
            Integer numImportedProducts = controller.importProducts(fileRead, fileExport, existsProducts);
            if (!existsProducts.isEmpty()) {
                Set<Map.Entry<String, List<Product>>> entries = existsProducts.entrySet();
                for (Map.Entry<String, List<Product>> mapEntry : entries) {
                    String path = mapEntry.getKey();
                    List<Product> productList = mapEntry.getValue();
                    for (Product pro : productList) {
                        String op = Console.readLine(INFO_ATUALIZAR_PRODUTO + "\n" + pro.toString());
                        if (op.equalsIgnoreCase(OPTION_YES)) {
                            atualizationProducts.put(path, pro);
                            numImportedProducts += controller.updateProducts(atualizationProducts);

                        }
                    }
                }
            }
            if (numImportedProducts == null) {
                System.out.println(ERROR_FILE_NOT_FOUND);
            } else if (numImportedProducts == 0) {
                System.out.println(ERROR_NO_IMPORTED_PRODUCTS);
            } else if (numImportedProducts > 0) {
                System.out.println(INFO_NUM_PRODUCTS_IMPORTED + " " + numImportedProducts);
            }

        } catch (InvalidFileFormattingException e) {
            System.out.println(ERROR_INVALID_FILE_FORMAT);
        }
    }

}
