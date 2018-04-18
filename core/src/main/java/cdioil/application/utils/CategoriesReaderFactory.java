/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.files.CommonFileExtensions;

/**
 * Factory of CategoriesReader.
 *
 * @author Rita Gonçalves (1160912)
 */
public final class CategoriesReaderFactory {

    /**
     * Creates an instance of CategoriesReader.
     *
     * @param filename Name of the file to read
     * @return an instance of CategoriesReader
     */
    public static CategoriesReader create(String filename) {
        if (filename.endsWith(CommonFileExtensions.CSV_EXTENSION)) {
            return new CSVCategoriesReader(filename);
        }
        return null;
    }

    /**
     * Hides the default constructor.
     */
    private CategoriesReaderFactory() {
    }
}
