/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.files.CommonFileExtensions;

import java.io.File;

/**
 * Factory of CategoriesReader.
 *
 * @author Rita Gonçalves (1160912)
 */
public final class CategoriesReaderFactory {

    /**
     * Hides the default constructor.
     */
    private CategoriesReaderFactory() {
    }

    /**
     * Creates an instance of CategoriesReader.
     *
     * @param file File to read
     * @return an instance of CategoriesReader
     */
    public static CategoriesReader create(File file) {
        if (file.getAbsolutePath().endsWith(CommonFileExtensions.CSV_EXTENSION)) {
            return new CSVCategoriesReader(file);
        }
        return null;
    }

}
