package cdioil.persistence.impl;

import cdioil.domain.GlobalLibrary;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.GlobalLibraryRepository;
import cdioil.persistence.PersistenceUnitNameCore;

/**
 * GlobalLibrary Repository implementation.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class GlobalLibraryRepositoryImpl extends BaseJPARepository<GlobalLibrary, Long> implements GlobalLibraryRepository {

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
