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

        Map<String, List<Product>> map = new HashMap<>();
        CSVProductsReader reader = 
                new CSVProductsReader("Invalid_Products.csv", "Nenhum.csv", map);

        reader.readProducts();
    }
    //FIXME Test is failing
    public void testReadProducts() {
        System.out.println("readProducts");

        Map<String, List<Product>> existsProducts = new HashMap<>();

        CSVProductsReader reader = new CSVProductsReader("Lista_Produtos_v01.csv", "Export.csv", existsProducts);

        Map<String, List<Product>> readProducts = reader.readProducts();

        Map<String, List<Product>> expected = new HashMap<>();
        
        String key1 = "10DC-10UN-1001CAT-1SCAT-1UB";
        Product product1 = new Product("Óleo alimentar Continente", new SKU("351067"), "1l");
        Product product2 = new Product("Óleo alimentar Continente", new SKU("351166"), "3l");
        Product product3 = new Product("Óleo alimentar Continente Seleção", new SKU("351265"), "1l");
        Product product4 = new Product("Óleo Fula", new SKU("401364"), "1l");
        Product product5 = new Product("Óleo Fula", new SKU("401463"), "3l");
        Product product6 = new Product("Óleo alimentar E", new SKU("361562"), "1l");
        Product product7 = new Product("Óleo alimentar Vaqueiro", new SKU("421661"), "1l");
        Product product8 = new Product("Óleo alimentar Vaqueiro", new SKU("421760"), "3l");
        
        String key2 = "10DC-10UN-1001CAT-1SCAT-2UB";
        Product product9 = new Product("Óleo de girassol Contiente", new SKU("351859"), "1l");
        Product product10 = new Product("Óleo AAA girassol", new SKU("181958"), "1l");
       
        String key3 = "10DC-10UN-1001CAT-1SCAT-3UB";
        Product product11 = new Product("Óleo de amendoím Continente especial fritos", new SKU("352058"), "1l");
        List<Product> products1 = new LinkedList<>();
        products1.add(product1);
        products1.add(product2);
        products1.add(product3);
        products1.add(product4);
        products1.add(product5);
        products1.add(product6);
        products1.add(product7);
        products1.add(product8);
        List<Product> products2 = new LinkedList<>();
        products2.add(product9);
        products2.add(product10);
        List<Product> products3 = new LinkedList<>();
        products3.add(product11);

        expected.put(key1, products1);
        expected.put(key2, products2);
        expected.put(key3, products3);
        
        assertEquals(expected, readProducts);
    }
    

}
