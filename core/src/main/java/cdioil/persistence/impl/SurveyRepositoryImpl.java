package cdioil.persistence.impl;

import cdioil.domain.GlobalSurvey;
import cdioil.domain.Product;
import cdioil.domain.Survey;
import cdioil.domain.SurveyState;
import cdioil.domain.TargetedSurvey;
import cdioil.domain.authz.RegisteredUser;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.SurveyRepository;
import java.util.ArrayList;

import javax.persistence.Query;
import java.util.List;

/**
 * Class that represents the implementation of the Survey repository
 *
 * @see cdioil.persistence.SurveyRepository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class SurveyRepositoryImpl extends BaseJPARepository<Survey, Long> implements SurveyRepository {

    /**
     * Constant that represents the lii
     */
    private static final int LAZY_LOADING_LIMIT = 100;
    /**
     * Constant that represents the limit of surveys that can be sent
     */
    private static final int SURVEYS_FETCH_LIMIT=25;

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
        return (List<Survey>) entityManager().createQuery("SELECT S FROM Survey S")
                .setFirstResult(nextLimit)
                .setMaxResults(nextLimit+LAZY_LOADING_LIMIT)
                .getResultList();
    }

    /**
     * Finds all surveys with state 'Active'
     *
     * @return List of Surveys
     */
    public List<Survey> findAllActiveSurveys() {
        return (List<Survey>) entityManager()
                .createQuery("SELECT s FROM Survey s WHERE s.state = :surveyState")
                .setParameter("surveyState", SurveyState.ACTIVE)
                .getResultList();
    }
    
    /**
     * Returns all Targeted Surveys
     *
     * @return list of targeted surveys
     */
    @Override
    public List<TargetedSurvey> getActiveTargetedSurveys() {
        Query q = entityManager().createQuery("SELECT t FROM TargetedSurvey t WHERE t.state = :surveyState")
                .setParameter("surveyState", SurveyState.ACTIVE);
        if (q.getResultList().isEmpty()) {
            return null;
        }
        return q.getResultList();
    }
    
    /**
     * Returns all Targeted Surveys from a user.
     * 
     * @param user a long with id od the user
     * @return list of targeted surveys
     */
    public List<Survey> getUserTargetedSurveys(RegisteredUser user){
        Query q = entityManager().createQuery("SELECT t FROM TargetedSurvey t WHERE t.state = :surveyState "
                + "AND :idUser MEMBER OF t.targetAudience.users")
                .setParameter("surveyState", SurveyState.ACTIVE)
                .setParameter("idUser",user);
        if (q.getResultList().isEmpty()) {
            return null;
        }
        return q.getResultList();
    }
    /**
     * Method that gets all user surveys that he can currently answer
     * @param registeredUser RegisteredUser with the user getting the surveys he can answer
     * @param paginationIndex Integer with the next pagination page
     * @return List with all the surveys that a certain user can currently answer
     */
    public List<Survey> getAllUserSurveys(RegisteredUser registeredUser,int paginationIndex){
        List<Survey> allUserSurveys=new ArrayList(SURVEYS_FETCH_LIMIT);
        List<Survey> userTargetedSurveys=getUserTargetedSurveys(registeredUser,paginationIndex);
        if(userTargetedSurveys!=null)allUserSurveys.addAll(userTargetedSurveys);
        int surveysRemaining=SURVEYS_FETCH_LIMIT-allUserSurveys.size();
        if(surveysRemaining==0)return allUserSurveys;
        List<Survey> activeGlobalSurveys=getActiveGlobalSurveysByIndexLimit(paginationIndex,surveysRemaining);
        if(activeGlobalSurveys!=null)allUserSurveys.addAll(activeGlobalSurveys);
        return allUserSurveys;
    }
    /**
     * Method that gets the current active targeted surveys of a current user by 
     * a certain pagination id
     * @param user RegisteredUser with the user being fetch'd the targeted surveys
     * @param paginationID Integer with the next pagination page
     * @return List with the current active targeted surveys that a certain user can answer 
     * by a certain pagination id
     */
    private List<Survey> getUserTargetedSurveys(RegisteredUser user,int paginationID){
        int nextLimit=paginationID*SURVEYS_FETCH_LIMIT;
        Query queryTargetedPagination = entityManager()
                .createQuery("SELECT t FROM TargetedSurvey t WHERE t.state = :surveyState "
                + "AND :idUser MEMBER OF t.targetAudience.users")
                .setParameter("surveyState", SurveyState.ACTIVE)
                .setParameter("idUser",user)
                .setFirstResult(paginationID)
                .setMaxResults(nextLimit+SURVEYS_FETCH_LIMIT);
        if (queryTargetedPagination.getResultList().isEmpty()) {
            return null;
        }
        return queryTargetedPagination.getResultList();
    }
    /**
     * Method that gets active global surveys based on a certain index for pagination
     * @param paginationIndex Integer with the next pagination page
     * @param leftFetchedSurveys Integer with the remaining surveys to fill the limit to fetch
     * @return List with all the global surveys based on certain index for pagination
     */
    private List<Survey> getActiveGlobalSurveysByIndexLimit(int paginationIndex,int leftFetchedSurveys){
        int nextLimit=paginationIndex*SURVEYS_FETCH_LIMIT;
        Query query=entityManager().createQuery("SELECT GB FROM GlobalSurvey GB "
                + "WHERE GB.state=:activeState")
                .setParameter("activeState",SurveyState.ACTIVE)
                .setFirstResult(nextLimit)
                .setMaxResults((nextLimit+SURVEYS_FETCH_LIMIT)-leftFetchedSurveys);
        return !query.getResultList().isEmpty() ? query.getResultList() : null;
    }
    
    /**
     * Method that retrieves all currently active surveys in which the given
     * product is part of the survey's item list and the user is part of the
     * target audience.
     *
     * @param product Product that's part of a survey's item list
     * @param registeredUser RegisteredUser user that's part of a survey's target audience
     * @return List with all currently active surveys in which the given product
     * is part of the survey's item list and the user is part of the target
     * audience.
     *
     */
    public List<Survey> getActiveSurveysByProductAndRegisteredUser(Product product, RegisteredUser registeredUser) {

        if (product == null || registeredUser == null) {
            return null;
        }

        //A query is used in order to avoid using a getDatabaseID method
        Query q = entityManager().createQuery("SELECT p.id FROM Product p WHERE p = :product");
        q.setParameter("product", product);

        Long productID = (Long) q.getSingleResult();

        if (productID == null) {
            return null;
        }

        //A native query is used since JPQL does not allow direct interaction with join tables
        //Two lists are used rather than on, since a sequence ID generation could cause clashing of IDs
        q = entityManager().createNativeQuery("SELECT t.TARGETEDSURVEY_ID FROM TARGETEDSURVEY_SURVEYITEM t "
                + "WHERE t.ITEMLIST_ID = ?");
        q.setParameter(1, productID);
        List<Long> targetedSurveyIDs = (List<Long>) q.getResultList();

        q = entityManager().createNativeQuery("SELECT g.GLOBALSURVEY_ID FROM GLOBALSURVEY_SURVEYITEM g "
                + "WHERE g.ITEMLIST_ID = ?");
        q.setParameter(1, productID);
        List<Long> globalSurveyIDs = (List<Long>) q.getResultList();

        if (targetedSurveyIDs.isEmpty() && globalSurveyIDs.isEmpty()) {
            return null;
        }

        List<Survey> surveys = new ArrayList<>();

        //The fetching of the different types of surveys is split, due to errors when fetching them all at once
        q = entityManager().createQuery("SELECT t FROM TargetedSurvey t WHERE :registeredUser"
                + " MEMBER OF t.targetAudience.users"
                + " AND t.id IN :targetedSurveyIDs AND t.state = :surveyState");
        q.setParameter("registeredUser", registeredUser);
        q.setParameter("targetedSurveyIDs", targetedSurveyIDs);
        q.setParameter("surveyState", SurveyState.ACTIVE);

        surveys.addAll((List<TargetedSurvey>) q.getResultList());

        q = entityManager().createQuery("SELECT g FROM GlobalSurvey g WHERE "
                + "g.id IN :globalSurveyIDs AND g.state = :surveyState");
        q.setParameter("globalSurveyIDs", globalSurveyIDs);
        q.setParameter("surveyState", SurveyState.ACTIVE);

        surveys.addAll((List<GlobalSurvey>) q.getResultList());

        return surveys.isEmpty() ? null : surveys;
    }
    
    /**
     * Method that returns the ID of a given survey.
     *
     * @param survey survey
     * @return the survey's ID or null if the survey was not found in the
     * database.
     */
    public Long getSurveyID(Survey survey) {
        if (survey == null) {
            return null;
        }
        String queryString = "SELECT s.id FROM " + survey.getClass().getSimpleName() + " s WHERE s = :survey";
        Query q = entityManager().createQuery(queryString);
        q.setParameter("survey", survey);

        return q.getResultList().isEmpty() ? null : (Long) q.getSingleResult();
    }
}
