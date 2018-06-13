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

    //TODO: fix this method since it is completely redundant
    @Override
    public List<Category> findCategoriesByIdentifierPattern(String identifierPattern) {
        Query queryRegexed = getCategoriesQueryByPathPattern(identifierPattern);
        queryRegexed.setParameter(1,identifierPattern);
        return (List<Category>) queryRegexed.getResultList();
    }
    
    @Override
    public List<Category> findCategoriesByPathPattern(String pathPattern) {
        Query queryRegexed = getCategoriesQueryByPathPattern(pathPattern);
        if ((List<Category>) queryRegexed.getResultList() == null) {
            return null;
        }
        return (List<Category>) queryRegexed.getResultList();
    }

    @Override
    public MarketStructure findMarketStructure() {
        Query query = entityManager().createQuery("SELECT e FROM " + MarketStructure.class.getSimpleName() + " e");
        List<MarketStructure> list = query.getResultList();
        return !list.isEmpty() ? list.get(0) : null;
    }

    @Override
    public List<Product> findProductByName(String productName) {
        EntityManager em = entityManager();
        Query query = em.createQuery("SELECT P from Product p where p.name regexp ?1");
        query.setParameter(1,productName);
        return (List<Product>) query.getResultList() != null ? query.getResultList() : null;
    }

    @Override
    public boolean findIfProductExist(String product) {
        List<Product> products=findProductByName(product);
        return products!=null && !products.isEmpty();
    }
    /**
     * Method that creates a Query for finding all categories that have certain path
     * @param pattern String with the categories path pattern
     * @return Query with the query for finding all categories that have a certain path
     */
    private Query getCategoriesQueryByPathPattern(String pattern){
        return entityManager().createQuery("SELECT C FROM Category C WHERE C.categoryPath regexp ?1")
                .setParameter(1,pattern);
    }
    
    @Override
    public Category findCategoryByPath(String path) {
        Query q = entityManager().createQuery("SELECT c FROM Category c WHERE c.categoryPath = :p_path");
        q.setParameter("p_path", path);

        return (Category) q.getSingleResult();
    }
}
