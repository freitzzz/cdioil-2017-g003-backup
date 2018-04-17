package cdioil.persistence.impl;

import cdioil.domain.CategoryTemplatesLibrary;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.CategoryTemplatesLibraryRepository;
import cdioil.persistence.PersistenceUnitNameCore;

import javax.persistence.Query;
import java.util.List;

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

    public CategoryTemplatesLibrary findTemplatesForCategory() {
        Query query = entityManager().createQuery("select t from " + CategoryTemplatesLibrary.class.getSimpleName() + " t");
        List<CategoryTemplatesLibrary> list = query.getResultList();
        return list.get(0);
    }
}
