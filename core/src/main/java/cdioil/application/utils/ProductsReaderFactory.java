/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.files.CommonFileExtensions;

/**
 *
 * Factory de ProductsReader.
 * 
 * @author Ana Guerra (1161191)
 */
public class ProductsReaderFactory {
    /**
     * Creates a ProductsReader instance according to the format of the file being read.
     *
     * @param filename file name
     * @return ProductsReader
     */
    public static ProductsReader create(String filename) {
        if (filename.endsWith((CommonFileExtensions.CSV_EXTENSION))) {
            return new CSVProductsReader(filename);
        }
        return null;
    }
    /**
     * Hides default constructoro
     */
    private ProductsReaderFactory() {
    }
    
}
