package cdioil.persistence;

import cdioil.domain.Category;
import cdioil.domain.MarketStructure;
import cdioil.domain.Product;
import java.util.List;

/**
 * Interface that defines the MarketStructureRepository behaviour.
 *
 * @author António Sousa [1161371]
 */
public interface MarketStructureRepository {

    /**
     * Method that finds all categories with a certain identifier pattern
     * <br>Uses Native Queries since JPQL doesn't allow the use of regex
     * functions in queries
     *
     * @param identifierPattern String with the identifier pattern to search
     * @return List with all categories found, or null if an error occured
     */
    public List<Category> findCategoriesByIdentifierPattern(String identifierPattern);

    /**
     * Method that finds all categories with a certain path pattern
     * <br>Uses Native Queries since JPQL doesn't allow the use of regex
     * functions in queries
     *
     * @param pathPattern String with the path pattern to search
     * @return List with all categories found, or null if an error occured
     */
    public List<Category> findCategoriesByPathPattern(String pathPattern);

    /**
     * Returns the market structure persisted in the database.
     *
     * @return market structure
     */
    public MarketStructure findMarketStructure();

    /**
     * Method that finds products by name
     * <br>Uses Native Queries since JPQL doesn't allow the use of regex
     * functions in queries
     *
     * @param productName String with the name of the Product
     * @return List of all products found, or null if there was any error
     */
    public List<Product> findProductByName(String productName);

    /**
     * Method that verifies if a product exists by its name
     * <br>Uses Native Queries since JPQL doesn't allow the use of regex
     * functions in queries
     *
     * @param product String with the name of the Product
     * @return true is the product exists, or false if not
     */
    public boolean findIfProductExists(String product);

    /**
     * Retrieves a single Category with a fully matching pattern.
     *
     * @param path string representing a Category's path
     * @return <code>Category</code> with a fully matching path or
     * <code>null</code> if no Category is found.
     */
    public Category findCategoryByPath(String path);

}
