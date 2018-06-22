/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.ImportProductsController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.files.InvalidFileFormattingException;
import cdioil.console.Console;
import cdioil.domain.Category;
import cdioil.domain.Product;

import javax.xml.transform.TransformerException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
    private final String optionExit = localizationHandler.getMessageValue("option_exit");

    /**
     * Represents the code for the option 'yes'.
     */
    private final String yesOption = localizationHandler.getMessageValue("option_yes");

    /**
     * Represents a message that indicates the user to insert the path of the file to import.
     */
    private final String requestFilePath = localizationHandler.getMessageValue("request_file_path");

    /**
     * Represents a message that informs the user that the input file was not found.
     */
    private final String errorFileNotFound = localizationHandler.getMessageValue("error_file_not_found");

    /**
     * Represents a message that indicates the number of imported products.
     */
    private final String infoNumProductsImported = localizationHandler.getMessageValue("info_num_products_imported");

    /**
     * Represents a message that requests the user to indicate if a certain product needs to be updated.
     */
    private final String infoUpdateProduct = localizationHandler.getMessageValue("info_update_products");

    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private final String infoExitInput = localizationHandler.getMessageValue("info_exit_input");

    /**
     * Represents a message that indicates the user that the file format is not valid.
     */
    private final String errorInvalidFileFormat = localizationHandler.getMessageValue("error_invalid_file_format");

    /**
     * Represents a message that indicates the user that no products were imported.
     */
    private final String errorNoImportedProducts = localizationHandler.getMessageValue("error_no_imported_products");

    /**
     * Instance of Controller that intermediates the interactions between the administrator and the system.
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
     * Shows the UI itself.
     */
    private void importProducts() {

        String filePath = Console.readLine(requestFilePath + "\n" + infoExitInput);
        if (filePath.equalsIgnoreCase(optionExit)) {
            return;
        }

        try {
            Map<Category, List<Product>> repeatedProducts = new HashMap<>();
            Integer numImportedProducts = controller.importProducts(filePath, repeatedProducts);

            if (!repeatedProducts.isEmpty()) {
                Set<Entry<Category, List<Product>>> entries = repeatedProducts.entrySet();
                for (Entry<Category, List<Product>> mapEntry : entries) {
                    List<Product> productList = mapEntry.getValue();
                    for (Product pro : productList) {
                        String op = Console.readLine(infoUpdateProduct + "\n" + pro.productName());
                        if (op.trim().equalsIgnoreCase(yesOption)) {
                            if (controller.updateProduct(mapEntry.getKey(), pro)) {
                                numImportedProducts++;
                            }
                        }
                    }
                }
            }
            if (numImportedProducts == null) {
                System.out.println(errorFileNotFound);
            } else if (numImportedProducts == 0) {
                System.out.println(errorNoImportedProducts);
            } else if (numImportedProducts > 0) {
                System.out.println(infoNumProductsImported + " " + numImportedProducts);
            }
        } catch (InvalidFileFormattingException e) {
            System.out.println(errorInvalidFileFormat);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
