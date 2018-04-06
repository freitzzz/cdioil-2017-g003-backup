package cdioil.persistence.impl;

import cdioil.domain.GlobalLibrary;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.GlobalLibraryRepository;
import cdioil.persistence.PersistenceUnitNameCore;
import java.util.List;
import javax.persistence.Query;

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

    /**
     * Returns the global library that exists in the database.
     *
     * @return GlobalLibrary instance
     */
    public GlobalLibrary findGlobalLibrary() {
        Query query = entityManager().createQuery("SELECT e FROM GlobalLibrary e");
        List<GlobalLibrary> list = query.getResultList();
        return !list.isEmpty() ? list.get(0) : null;
    }
}
