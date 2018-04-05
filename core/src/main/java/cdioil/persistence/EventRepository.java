package cdioil.persistence;

import cdioil.domain.Questionnaire;
import java.util.List;

/**
 * Event Repository
 *
 * @author João
 */
public interface EventRepository {

    /**
     * Returns all Questionnaires
     *
     * @return list of questionnaires
     */
    public abstract List<Questionnaire> getQuestionnaires();
}
