package cdioil.frontoffice.application;

import cdioil.domain.QuestionOption;
import cdioil.domain.QuestionOption_;
import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.domain.authz.RegisteredUser;
import cdioil.persistence.impl.ProfileRepositoryImpl;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.persistence.impl.ReviewRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO Update getting surveys and pending reviews with fetch lazy Controller
 * class for Answer Survey User Story
 */
public class AnswerSurveyController {

    /**
     * A list of all Surveys with state 'Active'
     */
    private List<Survey> activeSurveys;

    /**
     * A list of the pending reviews the user has.
     */
    private List<Review> pendingReviews;

    /**
     * The Survey that was chosen by the user
     */
    private Survey chosenSurvey;

    /**
     * Review associated to the chosen survey
     */
    private Review surveyReview;

    /**
     * User that is currently reviewing a survey.
     */
    private RegisteredUser loggedUser;

    /**
     * Constructs a new instance of AnswerSurveyController
     */
    public AnswerSurveyController(RegisteredUser loggedUser) {
        activeSurveys = findActiveSurveys();
        this.loggedUser = loggedUser;
    }

    /**
     * Initializes activeSurveys with a list of Surveys with state 'Active'
     *
     * @return LinkedList of active surveys
     */
    private List<Survey> findActiveSurveys() {
        SurveyRepositoryImpl repo = new SurveyRepositoryImpl();
        return repo.findAllActiveSurveys();
    }

    /**
     * Initializes pendingReviews with a list of the user's Reviews with state
     * 'Pending'
     *
     * @return LinkedList of the user's pending reviews
     */
    private List<Review> findPendingReviews() {
        ProfileRepositoryImpl repo = new ProfileRepositoryImpl();
        return repo.findUserPendingReviews(loggedUser.getProfile());
    }

    /**
     * Adds each Survey's toString() method to a new list of strings
     *
     * @return Linked List of active surveys's toString()
     */
    public List<String> getActiveSurveyIDs() {
        List<String> surveyIDs = new LinkedList<>();

        for (Survey survey
                : activeSurveys) {
            surveyIDs.add(survey.toString());
        }

        return surveyIDs;
    }

    /**
     * Adds each Review's toString() method to a new list of strings
     *
     * @return Linked List of pending review's toString()
     */
    public List<String> getPendingReviewIDs() {
        List<String> pendingReviewIDs = new LinkedList<>();
        pendingReviews = findPendingReviews();
        for (Review pendingReview
                : pendingReviews) {
            pendingReviewIDs.add(pendingReview.toString());
        }

        return pendingReviewIDs;
    }

    /**
     * Sets the Survey that was chosen by the User
     *
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
     * Sets the survey that the user wants to continue to review
     *
     * @param index review's position in the pendingReviews list
     */
    public void selectPendingReview(int index) {
        if (pendingReviews == null) {
            throw new IllegalArgumentException();
        }
        surveyReview = pendingReviews.get(index);
        chosenSurvey = surveyReview.getSurvey();
    }

    /**
     * Gets the current question that is being answered
     *
     * @return current question from Survey
     */
    public String getQuestion() {
        return surveyReview.getCurrentQuestion().toString();
    }

    /**
     * Returns the list of the current question's options
     *
     * @return list of strings with the current question's options
     */
    public List<String> getCurrentQuestionOptions() {
        List<QuestionOption> currentQuestionOptions = surveyReview.getCurrentQuestion().getOptionList();
        List<String> result = new ArrayList<>();

        for (QuestionOption option : currentQuestionOptions) {
            result.add(option.toString());
        }

        return result;
    }

    /**
     * Answers a question of the survey
     */
    public boolean answerQuestion(int index) {
        return surveyReview.answerQuestion(
                surveyReview.getCurrentQuestion().getOptionList().get(index));
    }

    /**
     * Saves the review to the database
     *
     * @param firstTimeSaving boolean to check if the review being done already
     * is in the database or not
     * @return true if the review was saved, false if otherwise
     */
    public boolean saveReview(boolean firstTimeSaving) {
        if (firstTimeSaving) {
            loggedUser.getProfile().addReview(surveyReview);
            return new ProfileRepositoryImpl().merge(loggedUser.getProfile()) != null;
        }
        return new ReviewRepositoryImpl().merge(surveyReview) != null;
    }

    /**
     * Adds a suggestion the the review
     *
     * @param text text of the suggestion
     * @return true if the suggestion was added, false if otherwise
     */
    public boolean submitSuggestion(String text) {
        return surveyReview.submitSuggestion(text);
    }
}
