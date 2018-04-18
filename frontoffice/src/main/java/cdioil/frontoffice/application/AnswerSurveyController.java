package cdioil.frontoffice.application;

import cdioil.domain.Survey;
import cdioil.persistence.impl.SurveyRepositoryImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Controller class for Answer Survey User Story
 */
public class AnswerSurveyController {

    /**
     * A list of all Surveys with state 'Active'
     */
    private List<Survey> activeSurveys;

    /**
     * Constructs a new instance of AnswerSurveyController
     */
    public AnswerSurveyController() {
        activeSurveys = findActiveSurveys();
    }

    /**
     * Initializes activeSurveys with a list of Surveys with state 'Active'
     * @return LinkedList of active surveys
     */
    private List<Survey> findActiveSurveys() {
        SurveyRepositoryImpl repo = new SurveyRepositoryImpl();
        return repo.findAllActiveSurveys();
    }

    /**
     * Adds each Survey's toString() method to a new list of strings
     * @return Linked List of active surveys's toString()
     */
    public List<String> getActiveSurveyIDs() {
        List<String> surveyIDs = new LinkedList<>();

        for (Survey survey :
                activeSurveys) {
            surveyIDs.add(survey.toString());
        }

        return surveyIDs;
    }

    /**
     * Answers the Survey corresponding to the position
     * in the activeSurveys list with the given index
     *
     * @param index given index
     */
    public void answerSurvey(String index) {
        throw new UnsupportedOperationException();
    }


}
