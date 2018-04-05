package cdioil.persistence.impl;

import cdioil.domain.GlobalLibrary;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.GlobalLibraryRepository;
import cdioil.persistence.PersistenceUnitNameCore;
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

    public GlobalLibrary findByQuery(){
       Query query = entityManager().createQuery("SELECT e FROM " + GlobalLibrary.class.getSimpleName() + " e");
       return (GlobalLibrary) query.getSingleResult();
   }
}
