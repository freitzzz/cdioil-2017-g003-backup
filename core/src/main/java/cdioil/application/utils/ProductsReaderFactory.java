package cdioil.application.utils;

import cdioil.domain.Product;
import cdioil.files.CommonFileExtensions;
import java.util.List;
import java.util.Map;

/**
 *
 * Factory de ProductsReader.
 *
 * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 */
public class ProductsReaderFactory {

    /**
     * Hides default constructor
     */
    private ProductsReaderFactory() {
    }

    /**
     * Creates a ProductsReader instance according to the format of the file being read.
     *
     * @param filePath Path of the file to import
     * @param existentProducts Map with all existent products
     * @return an instance of QuestionsReader
     */
    public static ProductsReader create(String filePath, Map<String, List<Product>> existentProducts) {
        if (filePath.endsWith((CommonFileExtensions.CSV_EXTENSION))) {
            return new CSVProductsReader(filePath, existentProducts);
        } else if (filePath.endsWith(CommonFileExtensions.XML_EXTENSION)) {
            return new XMLProductsReader(filePath);
        }
        return null;
    }
}
