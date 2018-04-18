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
import cdioil.files.InvalidFileFormattingException;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import cdioil.persistence.impl.ProductRepositoryImpl;
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
     * @return number of succesfully imported products
     * @throws cdioil.files.InvalidFileFormattingException if the file's formatting is not consistent with the file guidelines
     */
    public Integer importProducts(String fileName) throws InvalidFileFormattingException {

        Set<Product> successfullyImportedProducts = new HashSet<>();
    
        ProductsReader productsReader = ProductsReaderFactory.create(fileName);
        if (productsReader != null) {

        
        Map<String, List<Product>> productByCatPath = productsReader.readProducts();


        MarketStructureRepositoryImpl marketStructureRepository = new MarketStructureRepositoryImpl();
        MarketStructure marketStructure = marketStructureRepository.findMarketStructure();

        Set<Map.Entry<String, List<Product>>> entries = productByCatPath.entrySet();

        for (Map.Entry<String, List<Product>> mapEntry : entries) {
            String path = mapEntry.getKey();
            List<Product> productList = mapEntry.getValue();

            List<Category> categoryList = marketStructure.getAllCategories();
            if (categoryList == null) {
                continue;
            }
            for (Category cat : categoryList) {
                
                if (cat.categoryPath().equalsIgnoreCase(path)) {
                    productList.forEach((pro) -> {
                        successfullyImportedProducts.add(pro);
                       marketStructure.addProduct(pro, cat);

                    });
                }//else{
//                    System.out.println("A categoria " + path + " não existe na estrutura mercadologica");
//                }
            }

        }

//        marketStructureRepository.merge(marketStructure);
        }
        return successfullyImportedProducts.size();
    }

}
