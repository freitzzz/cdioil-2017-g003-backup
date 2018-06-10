package cdioil.application.utils;

import cdioil.domain.Product;
import java.util.List;
import java.util.Map;

/**
 * Interface for reading products from files.
 * 
 * @author Ana Guerra (1161191)
 */
public interface ProductsReader {
     /**
     * Imports products associated to categories from a file.
     *
     * @return map with category' path as key and product related to
     * that category as values.
     */
    Map<String, List<Product>> readProducts();
    
}
