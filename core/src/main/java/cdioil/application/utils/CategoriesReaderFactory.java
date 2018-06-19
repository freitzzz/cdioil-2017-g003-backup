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
    public static CategoriesReader create(String file) {
        if (file.endsWith((CommonFileExtensions.CSV_EXTENSION))) {
            return new CSVCategoriesReader(file);
        } else if (file.endsWith(CommonFileExtensions.XML_EXTENSION)) {
            return new XMLCategoriesReader(file);
        } else if (file.endsWith(CommonFileExtensions.JSON_EXTENSION)) {
            return new JSONCategoriesReader(file);
        }
        return null;
    }

}
