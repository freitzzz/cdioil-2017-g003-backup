package cdioil.application;

import cdioil.domain.Survey;
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
        iterableAllSurveys.forEach(nextSurvey->{allSurveys.add(nextSurvey);});
        return allSurveys;
    }
}
