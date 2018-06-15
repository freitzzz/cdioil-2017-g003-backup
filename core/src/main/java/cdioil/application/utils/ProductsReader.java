package cdioil.application.utils;

import cdioil.domain.Category;
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
     * Imports products from a file.
     *
     * @return a map with the categories as keys and their products as values.
     */
    Map<Category, List<Product>> readProducts();
}
