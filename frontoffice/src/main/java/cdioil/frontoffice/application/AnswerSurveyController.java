package cdioil.frontoffice.application;

import cdioil.domain.QuestionOption;
import cdioil.domain.QuestionOption_;
import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.persistence.impl.ReviewRepositoryImpl;
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
     * The Survey that was chosen by the user
     */
    private Survey chosenSurvey;

    /**
     * Review associated to the chosen survey
     */
    private Review surveyReview;

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
     * Sets the Survey that was chosen by the User
     * @param index survey's position in the activeSurveys list
     */
    public void selectSurvey(int index) {
        chosenSurvey = activeSurveys.get(index);

        if (chosenSurvey == null) {
            throw new IllegalArgumentException();
        }

        surveyReview = new Review(chosenSurvey);
    }

    /**
     * Gets the current question that is being answered
     * @return current question from Survey
     */
    public String getQuestion() {
        return surveyReview.getCurrentQuestion().toString();
    }

    public List<String> getCurrentQuestionOptions(){
        List<QuestionOption> currentQuestionOptions = surveyReview.getCurrentQuestion().getOptionList();
        List<String> result = new ArrayList<>();

        for(QuestionOption option : currentQuestionOptions){
            result.add(option.toString());
        }

        return result;
    }

    /**
     *
     */
    public boolean answerQuestion(int index) {
        return surveyReview.answerQuestion(
                surveyReview.getCurrentQuestion().getOptionList().get(index));
    }
    
    public boolean saveReview(){
        surveyReview.setUpQuestionIDs();
        return new ReviewRepositoryImpl().add(surveyReview) != null;
    }
    
    public boolean submitSuggestion(String text){
        return surveyReview.submitSuggestion(text);
    }

}
