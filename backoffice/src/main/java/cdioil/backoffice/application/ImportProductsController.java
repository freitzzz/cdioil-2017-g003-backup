/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.application;

import cdioil.application.utils.ProductsReader;
import cdioil.application.utils.ProductsReaderFactory;
import cdioil.domain.Category;
import cdioil.domain.MarketStructure;
import cdioil.domain.Product;
import cdioil.domain.SKU;
import cdioil.files.InvalidFileFormattingException;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Controller for use cases US-203 (import products from file).
 *
 * @author Ana Guerra (1161191)
 */
public class ImportProductsController {

    /**
     * Import products from a file.
     *
     * @param fileName Name of the file
     * @param fileExp Name of the fil to export
     * @param existsProducts Mpa with the path of the category and the product
     * @return number of succesfully imported products
     * @throws cdioil.files.InvalidFileFormattingException if the file's formatting is not consistent with the file guidelines
     */
    public Integer importProducts(String fileName, String fileExp, Map<String, List<Product>> existsProducts) throws InvalidFileFormattingException {

        ProductsReader productsReader = ProductsReaderFactory.create(fileName, fileExp, existsProducts);
        Set<Product> successfullyImportedProducts = new HashSet<>();

        MarketStructureRepositoryImpl marketStructureRepository = new MarketStructureRepositoryImpl();
        MarketStructure marketStructure = marketStructureRepository.findMarketStructure();

        if (productsReader != null) {
            Map<String, List<Product>> productByCatPath = productsReader.readProducts();
            Set<Map.Entry<String, List<Product>>> entries = productByCatPath.entrySet();

            for (Map.Entry<String, List<Product>> mapEntry : entries) {
                String path = mapEntry.getKey();
                List<Product> productList = mapEntry.getValue();
                List<Category> categoryList = marketStructureRepository.findCategoriesByPathPattern(path);
                if (categoryList != null) {
                    for (Category cat : categoryList) {
                        if (cat.categoryPath().equalsIgnoreCase(path)) {
                            productList.forEach((pro) -> {
                                if (marketStructure.addProduct(pro, cat)) {
                                    successfullyImportedProducts.add(pro);
                                }
                            });
                        }
                    }
                }
            }
            new MarketStructureRepositoryImpl().merge(marketStructure);

        }
        return successfullyImportedProducts.size();
    }

    public int updateProducts(Map<String, Product> updatedProducts) {
        MarketStructureRepositoryImpl marketStructureRepository = new MarketStructureRepositoryImpl();
        MarketStructure marketStructure = marketStructureRepository.findMarketStructure();
        for (String path : updatedProducts.keySet()) {
            List<Category> categoryList = marketStructureRepository.findCategoriesByPathPattern(path);
            if (categoryList != null) {
                for (Category cat : categoryList) {
                    if (cat.categoryPath().equalsIgnoreCase(path)) {
                        marketStructure.updateProduct(cat, updatedProducts.get(path));
                        new MarketStructureRepositoryImpl().merge(marketStructure);
                        return 1;
                    }
                }
            }
        }
        return 0;
    }
}
