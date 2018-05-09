package cdioil.application.utils;

import cdioil.domain.Product;
import cdioil.files.CommonFileExtensions;
import java.util.List;
import java.util.Map;

/**
 *
 * Factory de ProductsReader.
 *
 * @author Ana Guerra (1161191)
 */
public class ProductsReaderFactory {

    /**
     * Hides default constructor
     */
    private ProductsReaderFactory() {
    }

    /**
     * Creates a ProductsReader instance according to the format of the file
     * being read.
     *
     * @param filename file name
     * @param fileExport file of the export
     * @param existsProducts List with products
     * @return an instance of QuestionsReader
     */
    public static ProductsReader create(String filename, String fileExport, Map<String, List<Product>> existsProducts) {
        if (filename.endsWith((CommonFileExtensions.CSV_EXTENSION))) {
            return new CSVProductsReader(filename, fileExport, existsProducts);
        }
        return null;
    }

}
