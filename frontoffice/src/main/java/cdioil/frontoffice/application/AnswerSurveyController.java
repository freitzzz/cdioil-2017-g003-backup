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
 * TODO Update getting surveys and pending reviews with fetch lazy Controller
 * class for Answer Survey User Story
 */
public class AnswerSurveyController {

    private Map<Survey, Review> surveyReviewMap;

    /**
     * The Survey that was chosen by the user
     */
    private Survey selectedSurvey;

    /**
     * Review associated to the chosen survey
     */
    private Review currentReview;

    /**
     * User that is currently reviewing a survey.
     */
    private RegisteredUser loggedUser;

    /**
     * Constructs a new instance of AnswerSurveyController
     *
     * @param loggedUser
     */
    public AnswerSurveyController(RegisteredUser loggedUser) {
        this.loggedUser = loggedUser;
    }

    /**
     * Retrieves all the currently available surveys and the user's pending
     * reviews.
     */
    public void findActiveSurveys() {

        surveyReviewMap = new LinkedHashMap<>();

        List<Review> pendingReviews = new ProfileRepositoryImpl().findUserPendingReviews(loggedUser.getProfile());
        List<Survey> activeSurveys = new SurveyRepositoryImpl().findAllActiveSurveys();

        for (Survey s : activeSurveys) {
            surveyReviewMap.put(s, null);
        }
        for (Review r : pendingReviews) {
            surveyReviewMap.put(r.getSurvey(), r);
        }
    }

    /**
     * Retrieves a text representation of the currently available surveys and
     * whether or not they have pending reviews.
     *
     * @return text representation of the currently available surveys.
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
     * Selects a survey with a given index.
     *
     * @param index the survey's index
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

        for (QuestionOption option : currentQuestionOptions) {
            result.add(option.toString());
        }

        return result;
    }

    /**
     * Answers a question of the survey
     *
     * @param index
     * @return
     */
    public boolean answerQuestion(int index) {
        return currentReview.answerQuestion(
                currentReview.getCurrentQuestion().getOptionList().get(index));
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

    /**
     * Adds a suggestion the the review
     *
     * @param text text of the suggestion
     * @return true if the suggestion was added, false if otherwise
     */
    public boolean submitSuggestion(String text) {
        return currentReview.submitSuggestion(text);
    }

    /**
     * Updates the current registerd user profile due to changes on reviews
     * <br>Introduced due to bug on duplication reviews (since review uses a
     * sequence, the id of the object is only generated after posted to the
     * database)
     */
    private void updateRegisteredUserProfile() {
        loggedUser = new RegisteredUserRepositoryImpl().findBySystemUser(loggedUser.getID());
    }
}
