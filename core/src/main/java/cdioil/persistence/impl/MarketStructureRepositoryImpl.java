package cdioil.persistence.impl;

import cdioil.domain.Category;
import cdioil.domain.MarketStructure;
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
        Query queryRegexed = em.createNativeQuery("select * from CATEGORY c where lower(c.path) regexp '" + identifierPattern + "'", Category.class);
        return (List<Category>) queryRegexed.getResultList();
    }
}
