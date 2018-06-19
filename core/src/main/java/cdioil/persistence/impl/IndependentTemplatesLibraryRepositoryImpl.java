package cdioil.persistence.impl;

import cdioil.domain.IndependentTemplatesLibrary;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.IndependentTemplatesLibraryRepository;
import cdioil.persistence.PersistenceUnitNameCore;
import java.util.List;
import javax.persistence.Query;

/**
 * Class representing a repository used for storing IndependentQuestionsLibrary.
 *
 * @author António Sousa [1161371]
 */
public class IndependentTemplatesLibraryRepositoryImpl extends BaseJPARepository<IndependentTemplatesLibrary, Long> implements IndependentTemplatesLibraryRepository {

    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

    @Override
    public IndependentTemplatesLibrary findLibrary() {
        Query q = entityManager().createQuery("SELECT l FROM IndependentTemplatesLibrary l");

        List<IndependentTemplatesLibrary> list = q.getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

}
