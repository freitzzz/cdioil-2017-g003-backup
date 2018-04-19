/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.Product;
import cdioil.domain.SKU;
import cdioil.files.InvalidFileFormattingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests of the class CSVProductsReader.
 * 
 * @author Ana Guerra (1161191)
 */
public class CSVProductsReaderTest {
     /**
     * Instance of CSVProductsReader for test purposes.
     */
    CSVProductsReader productsReader;

    @Test(expected = InvalidFileFormattingException.class)
    public void ensureIsProductsFileValidThrowsException() {

        CSVProductsReader reader = new CSVProductsReader("Invalid_Products.csv");

        reader.readProducts();
    }

//    @Test
//    public void testReadProducts() {
//        System.out.println("readProducts");
//
//        CSVProductsReader reader = new CSVProductsReader("Products.csv");
//        
//        Map<String, List<Product>> readProducts = reader.readProducts();
//
//        Map<String, List<Product>> expected = new HashMap<>();
//
//        String key1 = "10DC-10UN-1002CAT-4SCAT-2UB";
//        Product product1 = new Product("teste1", new SKU("12"), "1 L");
//        Product product2 = new Product("teste2", new SKU("34"), "1 L");
//        String key2 = "10DC-10UN-1002CAT-6SCAT-1UB";
//        Product product3 = new Product("teste3", new SKU("56789"), "1 L");
//        
//        List<Product> products1 = new LinkedList<>();
//        products1.add(product1);
//        products1.add(product2);
//        List<Product> products2 = new LinkedList<>();
//        products2.add(product3);
//
//        expected.put(key1, products1);
//
//        expected.put(key2, products2);
//        
//        assertEquals(expected, readProducts);
//    }

}
