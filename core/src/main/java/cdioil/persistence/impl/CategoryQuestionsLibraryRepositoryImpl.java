package cdioil.persistence.impl;

import cdioil.domain.CategoryQuestionsLibrary;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.CategoryQuestionsLibraryRepository;
import cdioil.persistence.PersistenceUnitNameCore;

/**
 * CategoryQuestionsLibrary Repository class
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class CategoryQuestionsLibraryRepositoryImpl extends BaseJPARepository<CategoryQuestionsLibrary, Long> implements CategoryQuestionsLibraryRepository {

    /**
     * Returns the PU's name.
     *
     * @return string with the persistence unit's name
     */
    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

}
