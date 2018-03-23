/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

/**
 * Factory of CategoriesReader.
 *
 * @author Rita Gonçalves (1160912)
 */
public final class CategoriesReaderFactory {

    /**
     * Identifier of the .csv file format.
     */
    public static final String CSV_FORMAT = ".csv";

    /**
     * Creates an instance of CategoriesReader.
     *
     * @param filename Name of the file to read
     * @return an instance of CategoriesReader
     */
    public static CategoriesReader create(String filename) {
        if (filename.endsWith(CSV_FORMAT)) {
            return new CSVCategoriesReader(filename);
        }
        return null;
    }

    /**
     * Hides the private constructor.
     */
    private CategoriesReaderFactory() {
    }
}
