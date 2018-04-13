package cdioil.persistence.impl;

import cdioil.domain.CategoryTemplatesLibrary;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.CategoryTemplatesLibraryRepository;
import cdioil.persistence.PersistenceUnitNameCore;

/**
 * CategoryTemplatesLibrary Repository class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class CategoryTemplatesLibraryRepositoryImpl extends BaseJPARepository<CategoryTemplatesLibrary, Long>
        implements CategoryTemplatesLibraryRepository {

    /**
     * Returns the PU's name
     *
     * @return string with the persistence unit's name
     */
    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
}
