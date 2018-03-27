package cdioil.persistence.impl;

import cdioil.domain.CategoryQuestionsLibrary;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.CategoryQuestionsLibraryRepository;
import cdioil.persistence.PersistenceUnitNameCore;

/**
 * Implementation of the CategoryQuestionsLibrary repository.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class CategoryQuestionsLibraryRepositoryImpl extends BaseJPARepository<CategoryQuestionsLibrary, Long>
        implements CategoryQuestionsLibraryRepository {

    /**
     * Returns the name of the persistence unit.
     *
     * @return string with the name of the persistence unit
     */
    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

}
