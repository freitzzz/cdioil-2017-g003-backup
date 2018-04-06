package cdioil.persistence.impl;

import cdioil.domain.Category;
import cdioil.domain.Code;
import cdioil.domain.GlobalLibrary;
import cdioil.domain.MarketStructure;
import cdioil.domain.Product;
import cdioil.persistence.MarketStructureRepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.BaseJPARepository;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
        Query queryRegexed = em.createNativeQuery("select * from CATEGORY c where c.path regexp '" + identifierPattern + "'", Category.class);
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
        Query queryRegexed = em.createNativeQuery("select * from CATEGORY c where c.path regexp '" + pathPattern + "'", Category.class);
        if((List<Category>) queryRegexed.getResultList()==null) return null;
        return (List<Category>) queryRegexed.getResultList();
    }
    
    /**
     * Returns the market structure persisted in the database.
     * @return market structure
     */
    public MarketStructure findMarketStructure(){
        Query query = entityManager().createQuery("SELECT e FROM " + MarketStructure.class.getSimpleName() + " e");
        List<MarketStructure> list = query.getResultList();
        return !list.isEmpty() ? list.get(0) : null;
    }
}
