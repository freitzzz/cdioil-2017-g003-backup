package cdioil.application.utils;

import cdioil.domain.Category;
import cdioil.domain.Product;
import cdioil.files.CommonFileExtensions;
import java.util.List;
import java.util.Map;

/**
 *
 * ProductsReader Factory.
 *
 * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 */
public class ProductsReaderFactory {

    /**
     * Hides the default public constructor.
     */
    private ProductsReaderFactory() {
    }

    /**
     * Creates a ProductsReader instance according to the format of the file being read.
     *
     * @param filePath Path of the file to import
     * @param repeatedProducts Map that will hold all repeated products
     * @return an instance of ProductsReader
     */
    public static ProductsReader create(String filePath, Map<Category, List<Product>> repeatedProducts) {
        if (filePath.endsWith((CommonFileExtensions.CSV_EXTENSION))) {
            return new CSVProductsReader(filePath, repeatedProducts);
        } else if (filePath.endsWith(CommonFileExtensions.XML_EXTENSION)) {
            return new XMLProductsReader(filePath, repeatedProducts);
        }
        return null;
    }
}
