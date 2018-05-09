package cdioil.persistence.impl;

import cdioil.domain.Category;
import cdioil.domain.MarketStructure;
import cdioil.domain.Product;
import cdioil.persistence.MarketStructureRepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.BaseJPARepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author António Sousa [1161371]
 */
public class MarketStructureRepositoryImpl extends BaseJPARepository<MarketStructure, Long> implements MarketStructureRepository {

    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

    /**
     * Method that finds all categories with a certain identifier pattern
     * <br>Uses Native Queries since JPQL doesn't allow the use of regex functions in queries
     *
     * @param identifierPattern String with the identifier pattern to search
     * @return List with all categories found, or null if an error occured
     */
    public List<Category> findCategoriesByIdentifierPattern(String identifierPattern) {
        EntityManager em = entityManager();
        String queryString = "select c from Category c where c.categoryPath regexp '" + identifierPattern + "'";
        Query queryRegexed = em.createQuery(queryString, Category.class);
        return (List<Category>) queryRegexed.getResultList();
    }

    /**
     * Method that finds all categories with a certain path pattern
     * <br>Uses Native Queries since JPQL doesn't allow the use of regex functions in queries
     *
     * @param pathPattern String with the path pattern to search
     * @return List with all categories found, or null if an error occured
     */
    public List<Category> findCategoriesByPathPattern(String pathPattern) {
        EntityManager em = entityManager();
        String queryString = "select c from Category c where c.categoryPath regexp '" + pathPattern + "'";
        Query queryRegexed = em.createQuery(queryString, Category.class);
        if ((List<Category>) queryRegexed.getResultList() == null) {
            return null;
        }
        return (List<Category>) queryRegexed.getResultList();
    }

    /**
     * Returns the market structure persisted in the database.
     *
     * @return market structure
     */
    public MarketStructure findMarketStructure() {
        Query query = entityManager().createQuery("SELECT e FROM " + MarketStructure.class.getSimpleName() + " e");
        List<MarketStructure> list = query.getResultList();
        return !list.isEmpty() ? list.get(0) : null;
    }

    /**
     * Method that finds products by name
     * <br>Uses Native Queries since JPQL doesn't allow the use of regex functions in queries
     *
     * @param productName String with the name of the Product
     * @return List of all products found, or null if there was any error
     */
    public List<Product> findProductByName(String productName) {
        EntityManager em = entityManager();
        String queryString = "SELECT P from Product p where p.name regexp '" + productName + "'";
        Query query = em.createQuery(queryString, Product.class);
        return (List<Product>) query.getResultList() != null ? query.getResultList() : null;
    }

     /**
     * Method that verifies if a product exists by its name
     * <br>Uses Native Queries since JPQL doesn't allow the use of regex functions in queries
     *
     * @param product String with the name of the Product
     * @return true is the product exists, or false if not
     */
    public boolean findIfProductExist(String product) {
        EntityManager em = entityManager();
        String queryString = "SELECT P from Product p where p.name regexp '" + product + "'";
        Query query = em.createNativeQuery(queryString, Product.class);
        return (List<Product>) query.getResultList() != null || 
                ((List<Product>) query.getResultList()).isEmpty();
    }
}
