package cdioil.persistence.impl;

import cdioil.domain.IndependentQuestionsLibrary;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.IndependentQuestionsLibraryRepository;
import cdioil.persistence.PersistenceUnitNameCore;

/**
 * IndependentQuestionsLibrary Repository class
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class IndependentQuestionsLibraryRepositoryImpl extends BaseJPARepository<IndependentQuestionsLibrary, Long>
        implements IndependentQuestionsLibraryRepository {

    /**
     * Returns the persistence unit's name.
     *
     * @return the PU's name
     */
    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

}
