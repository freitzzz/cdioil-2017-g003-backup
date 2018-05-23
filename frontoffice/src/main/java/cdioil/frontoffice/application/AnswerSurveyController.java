package cdioil.frontoffice.application;

import cdioil.domain.QuestionOption;
import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.domain.authz.RegisteredUser;
import cdioil.persistence.impl.ProfileRepositoryImpl;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.persistence.impl.ReviewRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Controller class for answering a survey.
 *
 * TODO Update getting surveys and pending reviews with fetch lazy Controller class for Answer Survey User Story
 *
 * @author <a href="1150782@isep.ipp.pt">Pedro Portela</a>
 * @author <a href="1161371@isep.ipp.pt">António Sousa</a>
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 */
public class AnswerSurveyController {

    /**
     * Map with the surveys and its reviews.
     */
    private Map<Survey, Review> surveyReviewMap;

    /**
     * The Survey that was chosen by the user.
     */
    private Survey selectedSurvey;

    /**
     * Review associated to the chosen survey.
     */
    private Review currentReview;

    /**
     * User that is currently reviewing a survey.
     */
    private RegisteredUser loggedUser;

    /**
     * Constructs a new instance of AnswerSurveyController
     *
     * @param loggedUser Logged User
     */
    public AnswerSurveyController(RegisteredUser loggedUser) {
        this.loggedUser = loggedUser;
    }

    /**
     * Constructs a new instance of AnswerSurveyController
     *
     * @param loggedUser Logged User
     * @param surveyID ID of the Survey to answer
     */
    public AnswerSurveyController(RegisteredUser loggedUser, String surveyID) {
        this.loggedUser = loggedUser;
        findSurveyByID(surveyID);
        findActiveSurveys();
    }

    /**
     * Retrieves a text representation of the currently available surveys and whether or not they have pending reviews.
     *
     * @return text representation of the currently available surveys
     */
    public List<String> getSurveyDescriptions() {
        List<String> result = new LinkedList<>();

        for (Map.Entry<Survey, Review> entry : surveyReviewMap.entrySet()) {
            String surveyReviewStatus = entry.getKey().toString();
            //check if survey currently has a pending review
            if (entry.getValue() != null) {
                surveyReviewStatus += "\t(Pending Review)";
            }
            result.add(surveyReviewStatus);
        }
        return result;
    }

    /**
     * Gets the current question that is being answered
     *
     * @return current question from Survey
     */
    public String getQuestion() {
        return currentReview.getCurrentQuestion().toString();
    }

    /**
     * Returns the list of the current question's options
     *
     * @return list of strings with the current question's options
     */
    public List<String> getCurrentQuestionOptions() {
        List<QuestionOption> currentQuestionOptions = currentReview.getCurrentQuestion().getOptionList();
        List<String> result = new ArrayList<>();

        currentQuestionOptions.forEach(option
                -> result.add(option.toString())
        );

        return result;
    }

    /**
     * Selects a survey with a given index.
     *
     * @param index Index of the survey
     */
    public void selectSurvey(int index) {
        int i = 0;

        //since maps don't retrieve items by index, the collection must be iterated over until the indices match
        for (Map.Entry<Survey, Review> entry : surveyReviewMap.entrySet()) {
            if (i == index) {
                selectedSurvey = entry.getKey();
                currentReview = entry.getValue();
                //add a new review to the profile in case there wasn't one already
                if (currentReview == null) {
                    currentReview = new Review(selectedSurvey);
                    loggedUser.getProfile().addReview(currentReview);
                }
                break;
            }
            i++;
        }
    }

    /**
     * Answers a question of the survey with a given index.
     *
     * @param index Index of the option in the option list of the question
     * @return true, if the question is successfully answered.
     * <p>
     * Otherwise, returns false
     */
    public boolean answerQuestionByIndex(int index) {
        return currentReview.answerQuestion(
                currentReview.getCurrentQuestion().getOptionList().get(index));
    }

    /**
     * Answers a question of the survey with a QuestionOption.
     *
     * @param option Chosen question option
     * @return true, if the question is successfully answered.
     * <P>
     * Otherwise, returns false
     */
    public boolean answerQuestion(QuestionOption option) {
        if (option == null) {
            return false;
        }
        return currentReview.answerQuestion(option);
    }

    /**
     * Reverts last answered question.
     *
     * @return true if answer can be undone
     * <p>
     * false - otherwise
     */
    public boolean undoAnswer() {
        return currentReview.undoAnswer();
    }

    /**
     * Adds a suggestion the the review
     *
     * @param text text of the suggestion
     * @return true if the suggestion was added, false if otherwise
     */
    public boolean submitSuggestion(String text) {
        return currentReview.submitSuggestion(text);
    }

    /* DATABASE METHODS */
    /**
     * Updates the current registerd user profile due to changes on reviews
     * <br>Introduced due to bug on duplication reviews (since review uses a sequence, the id of the object is only generated after posted to the database)
     */
    private void updateRegisteredUserProfile() {
        loggedUser = new RegisteredUserRepositoryImpl().findBySystemUser(loggedUser.getID());
    }

    /**
     * Access method to a Review via its ID.
     *
     * @param reviewID ID of the review
     * @return the Review
     */
    public Review getReviewByID(String reviewID) {
        currentReview = new ReviewRepositoryImpl().find(Long.parseLong(reviewID));
        return currentReview;
    }

    /**
     * Retrieves all the currently available surveys and the user's pending reviews.
     */
    public void findActiveSurveys() {
        surveyReviewMap = new LinkedHashMap<>();

        List<Review> pendingReviews = new ProfileRepositoryImpl().findUserPendingReviews(loggedUser.getProfile());
        List<Survey> activeSurveys = new SurveyRepositoryImpl().findAllActiveSurveys();

        pendingReviews.forEach(r
                -> surveyReviewMap.put(r.getSurvey(), r)
        );

        activeSurveys.forEach(s
                -> surveyReviewMap.put(s, null)
        );

    }

    /**
     * Finds a survey in the database by its database ID.
     *
     * @param surveyID ID of the Survey in the database
     * @return the Survey that was found
     */
    public Survey findSurveyByID(String surveyID) {
        selectedSurvey = new SurveyRepositoryImpl().find(Long.parseLong(surveyID));
        return selectedSurvey;
    }

    /**
     * Creates a new review about a survey.
     *
     * @param survey Survey in question
     * @return the database ID of the created Review
     */
    public String createNewReview(Survey survey) {
        Review review = new Review(survey);
        ReviewRepositoryImpl reviewRepository = new ReviewRepositoryImpl();
        reviewRepository.add(review);
        Long id = reviewRepository.getReviewID(review);
        return Long.toString(id);
    }

    /**
     * Saves the review to the database
     *
     * @return true if the review was saved, false if otherwise
     */
    public boolean saveReview() {
        //if the value is null then the review is being saved for the first time
        if (surveyReviewMap.get(selectedSurvey) == null) {
            loggedUser.getProfile().addReview(currentReview);
            return new ProfileRepositoryImpl().merge(loggedUser.getProfile()) != null;
        }

        currentReview = new ReviewRepositoryImpl().merge(currentReview);
        updateRegisteredUserProfile();
        return currentReview != null;
    }
}
