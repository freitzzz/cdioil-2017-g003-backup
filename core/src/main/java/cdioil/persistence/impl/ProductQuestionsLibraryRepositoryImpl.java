package cdioil.persistence.impl;

import cdioil.domain.ProductQuestionsLibrary;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.ProductQuestionsLibraryRepository;
import javax.persistence.Query;
import java.util.List;

/**
 * ProductQuestionsLibrary Repository class
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ProductQuestionsLibraryRepositoryImpl extends BaseJPARepository<ProductQuestionsLibrary, Long> implements ProductQuestionsLibraryRepository {
    /**
     * Returns the PU's name
     *
     * @return string with the persistence unit's name
     */
    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
    /**
     * Finds the product questions library 
     * @return ProductQuestionsLibrary instance
     */
    public ProductQuestionsLibrary findProductQuestionLibrary() {
        Query query = entityManager().createQuery("select p from " + ProductQuestionsLibrary.class.getSimpleName() + " p");
        List<ProductQuestionsLibrary> list = query.getResultList();
        return list.get(0);

    }
}
