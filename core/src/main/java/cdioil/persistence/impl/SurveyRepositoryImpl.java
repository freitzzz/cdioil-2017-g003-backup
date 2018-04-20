package cdioil.persistence.impl;

import cdioil.domain.Survey;
import cdioil.domain.SurveyState;
import cdioil.domain.TargetedSurvey;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.SurveyRepository;

import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that represents the implementation of the Survey repository
 *
 * @see cdioil.persistence.SurveyRepository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class SurveyRepositoryImpl extends BaseJPARepository<Survey, Integer> implements SurveyRepository {

    /**
     * Constant that represents the lii
     */
    private static final int LAZY_LOADING_LIMIT = 100;

    /**
     * Method that returns the persistence unit name that the repository uses
     *
     * @return String with the persistence unit name that the repository uses
     */
    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

    /**
     * Method that returns all returns all Surveys that are listed on a certain
     * range
     * <br>Range is based on the following expression
     * [INDEX*LAZY_LOAD_LIMIT,((INDEX*LAZY_LOAD_LIMIT)+LAZY_LOAD_LIMIT)
     *
     * @param lazyLoadIndex Integer with the index of the target range
     * @return List with all Surveys on a certain range
     */
    public List<Survey> getSurveysByLazyLoadingIndex(int lazyLoadIndex) {
        int nextLimit = lazyLoadIndex * LAZY_LOADING_LIMIT;
        return (List<Survey>) entityManager().createNativeQuery("SELECT * FROM Survey s OFFSET " + nextLimit + " ROWS FETCH NEXT " + LAZY_LOADING_LIMIT + " ROWS ONLY",
                Survey.class).getResultList();
    }

    /**
     * Finds all surveys with state 'Active'
     *
     * @return List of Surveys
     */
    public List<Survey> findAllActiveSurveys() {
        List<Survey> activeSurveys = (List<Survey>) entityManager()
                .createQuery("SELECT s FROM Survey s WHERE s.state = :surveyState")
                .setParameter("surveyState", SurveyState.ACTIVE)
                .getResultList();

        return activeSurveys;
    }

    /**
     * Returns all Targeted Surveys
     *
     * @return list of targeted surveys
     */
    @Override
    public List<TargetedSurvey> getTargetedSurveys() {
        Query q = entityManager().createQuery("SELECT tSurvey FROM TargetedSurvey tSurvey");
        if (q.getResultList().isEmpty()) {
            return null;
        }
        return q.getResultList();
    }
}
