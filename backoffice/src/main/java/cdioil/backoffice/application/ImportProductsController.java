/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.application;

import cdioil.application.utils.ProductsReader;
import cdioil.application.utils.ProductsReaderFactory;
import cdioil.domain.Category;
import cdioil.domain.Product;
import cdioil.files.InvalidFileFormattingException;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import cdioil.persistence.impl.ProductRepositoryImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

/**
 * Controller for use cases US-203 (import products from file).
 *
 * @author Ana Guerra (1161191)
 */
public class ImportProductsController {

    /**
     * Intance of ProductRepositoryImpl
     */
    private final ProductRepositoryImpl productRepositoryImpl;

    /**
     * Instantiates a controller for importing products.
     */
    public ImportProductsController() {
        this.productRepositoryImpl = new ProductRepositoryImpl();
    }

    /**
     * Import products from a file.
     *
     * @param fileName Name of the file
     * @return number of succesfully imported products
     * @throws cdioil.files.InvalidFileFormattingException if the file's formatting is not consistent with the file guidelines
     */
    public Integer importProducts(String fileName) throws InvalidFileFormattingException {

        Set<Product> successfullyImportedProducts = new HashSet<>();

        ProductsReader productsReader = ProductsReaderFactory.create(fileName);
        if (productsReader != null) {

        }
        Map<String, List<Product>> productByCatPath = productsReader.readProducts();
        Map<String, Product> produtsImported = new HashMap<>();

        MarketStructureRepositoryImpl marketStructureRepository = new MarketStructureRepositoryImpl();

        Set<Map.Entry<String, List<Product>>> entries = productByCatPath.entrySet();

        for(Map.Entry<String, List<Product>> mapEntry : entries) {

            String path = mapEntry.getKey();
            List<Product> productList = mapEntry.getValue();

            List<Category> categoryList = marketStructureRepository.findCategoriesByPathPattern(path);

            if (categoryList == null) {
                continue;
            }
            if (categoryList.contains(path)) {
                productList.forEach((pro) -> {
                    successfullyImportedProducts.add(pro);
                    produtsImported.put(path, pro);
                });
            }
        }
        productRepositoryImpl.merge(produtsImported);

        return successfullyImportedProducts.size();
    }

}
