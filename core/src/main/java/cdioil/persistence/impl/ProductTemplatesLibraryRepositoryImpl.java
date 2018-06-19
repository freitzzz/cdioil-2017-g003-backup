package cdioil.persistence.impl;

import cdioil.domain.ProductTemplatesLibrary;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.ProductTemplatesLibraryRepository;
import java.util.List;
import javax.persistence.Query;

/**
 * Class representing a repository for ProductTemplatesLibrary.
 *
 * @author António Sousa [1161371]
 */
public class ProductTemplatesLibraryRepositoryImpl extends BaseJPARepository<ProductTemplatesLibrary, Long>
        implements ProductTemplatesLibraryRepository {

    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

    @Override
    public ProductTemplatesLibrary findLibrary() {
        Query q = entityManager().createQuery("SELECT l FROM ProductTemplatesLibrary l");

        List<ProductTemplatesLibrary> list = q.getResultList();

        return list.isEmpty() ? null : list.get(0);
    }
}
