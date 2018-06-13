/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.Product;
import cdioil.files.InvalidFileFormattingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * Tests of the class CSVProductsReader.
 *
 * @author Ana Guerra (1161191)
 */
public class CSVProductsReaderTest {

    @Test(expected = InvalidFileFormattingException.class)
    public void ensureIsProductsFileValidThrowsException() {

        Map<String, List<Product>> map = new HashMap<>();
        CSVProductsReader reader
                = new CSVProductsReader("Invalid_Products.csv", map);

        reader.readProducts();
    }
}
