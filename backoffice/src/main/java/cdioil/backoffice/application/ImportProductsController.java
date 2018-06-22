/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.application;

import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import cdioil.application.utils.ProductsReaderFactory;
import cdioil.application.utils.ProductsReader;
import cdioil.domain.MarketStructure;
import cdioil.domain.Category;
import cdioil.domain.Product;

import javax.xml.transform.TransformerException;
import java.util.Map.Entry;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Controller for use case US-203 (import products from CSV, JSON or XML file).
 *
 * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 */
public class ImportProductsController {

    /**
     * Import products from a CSV, XML or JSON file.
     *
     * @param filePath Path of the file
     * @param repeatedProducts Map that will hold all already existent products for the user to decide if they need to be updated
     * @return number of succesfully imported products
     *
     * @throws cdioil.files.InvalidFileFormattingException if the file's formatting is not consistent with the file guidelines
     */
    public Integer importProducts(String filePath, Map<Category, List<Product>> repeatedProducts) throws TransformerException {
        ProductsReader productsReader = ProductsReaderFactory.create(filePath, repeatedProducts);
        Set<Product> successfullyImportedProducts = new HashSet<>();

        MarketStructure marketStructure = new MarketStructureRepositoryImpl().findMarketStructure();

        if (productsReader == null) {
            return null;

        }
        Map<Category, List<Product>> newProductsByCategory = productsReader.readProducts();
        if (newProductsByCategory == null) {
            return null;
        }

        for (Entry<Category, List<Product>> mapEntry : newProductsByCategory.entrySet()) {
            Category category = mapEntry.getKey();
            List<Product> productsList = mapEntry.getValue();
            for (Product product : productsList) {
                if (marketStructure.addProduct(product, category)) {
                    successfullyImportedProducts.add(product);
                }
            }
        }
        new MarketStructureRepositoryImpl().merge(marketStructure);

        return successfullyImportedProducts.size();
    }

    /**
     * Updates a certain product in the market structure.
     *
     * @param category Category of the product
     * @param product Product to update
     *
     * @return true if the product has been updated. Otherwise, returns false
     */
    public boolean updateProduct(Category category, Product product) {
        MarketStructureRepositoryImpl marketStructureRepository = new MarketStructureRepositoryImpl();
        MarketStructure marketStructure = marketStructureRepository.findMarketStructure();
        if (category != null) {
            marketStructure.updateProduct(category, product);
            new MarketStructureRepositoryImpl().merge(marketStructure);
            return true;
        }
        return false;
    }
}
