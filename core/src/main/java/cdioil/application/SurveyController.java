package cdioil.application;

import cdioil.domain.Survey;
import cdioil.domain.authz.RegisteredUser;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * Survey controller that controls surveys
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class SurveyController {
    /**
     * Method that returns all existing surveys
     * @return List with all existing Survey
     */
    public List<Survey> getAllSurveys(){
        List<Survey> allSurveys=new ArrayList<>();
        Iterable<Survey> iterableAllSurveys=new SurveyRepositoryImpl().findAll();
        iterableAllSurveys.forEach(nextSurvey->allSurveys.add(nextSurvey));
        return allSurveys;
    }
    /**
     * Method that gets the surveys that a certain user can answer, based on a 
     * certain pagination ID
     * @param user RegisteredUser with the registered user getting his surveys that 
     * he can answer
     * @param paginationID Short with the pagination ID
     * @return List with the surveys that a certain user can answer based on a certain pagination ID
     */
    public List<Survey> getUserSurveys(RegisteredUser user,short paginationID){
        return new SurveyRepositoryImpl().getAllUserSurveys(user,paginationID);
    }
}
