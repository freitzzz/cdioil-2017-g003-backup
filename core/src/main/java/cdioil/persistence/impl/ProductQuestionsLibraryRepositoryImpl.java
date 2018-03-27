package cdioil.persistence.impl;

import cdioil.domain.ProductQuestionsLibrary;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.ProductQuestionsLibraryRepository;

/**
 * Repository for ProductQuestionsLibrary class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ProductQuestionsLibraryRepositoryImpl extends BaseJPARepository<ProductQuestionsLibrary, Long>
        implements ProductQuestionsLibraryRepository {

    /**
     * Returns the persistence's unit name
     *
     * @return string with the PU's name
     */
    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
}
