package cdioil.persistence.impl;

import cdioil.domain.Event;
import cdioil.domain.Questionnaire;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.EventRepository;
import cdioil.persistence.PersistenceUnitNameCore;
import java.util.List;
import javax.persistence.Query;

/**
 * Event Repository
 *
 * @author João
 */
public class EventRepositoryImpl extends BaseJPARepository<Event, Long> implements EventRepository {

    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

    /**
     * Returns all Questionnaires
     *
     * @return list of questionnaires
     */
    @Override
    public List<Questionnaire> getQuestionnaires() {
        Query q = entityManager().createQuery("SELECT ques FROM Questionnaire");
        if (q.getResultList().isEmpty()) {
            return null;
        }
        return q.getResultList();
    }

}
