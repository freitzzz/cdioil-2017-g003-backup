package cdioil.application;

import cdioil.domain.Product;
import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.domain.authz.RegisteredUser;
import cdioil.persistence.impl.ProductRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Survey controller that controls surveys
 *
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class SurveyController {
    
    /**
     * Size for the answered surveys list.
     */
    private static final int ANSWERED_SURVEYS_LIST_SIZE = 25;

    /**
     * Method that returns all existing surveys
     *
     * @return List with all existing Survey
     */
    public List<Survey> getAllSurveys() {
        List<Survey> allSurveys = new ArrayList<>();
        Iterable<Survey> iterableAllSurveys = new SurveyRepositoryImpl().findAll();
        iterableAllSurveys.forEach(nextSurvey -> allSurveys.add(nextSurvey));
        return allSurveys;
    }

    /**
     * Method that gets the surveys that a certain user can answer, based on a
     * certain pagination ID
     *
     * @param user RegisteredUser with the registered user getting his surveys
     * that he can answer
     * @param paginationID Short with the pagination ID
     * @return List with the surveys that a certain user can answer based on a
     * certain pagination ID
     */
    public List<Survey> getUserSurveys(RegisteredUser user, short paginationID) {
        return new SurveyRepositoryImpl().getAllUserSurveys(user, paginationID);
    }
    
    /**
     * Method that gets the surveys that a user has answered (which means the review
     * has to be in a finished state)
     * 
     * TODO Implement pagination
     * 
     * @param user RegisteredUser that is requesting the surveys they have answered
     * @param paginationID Short with the pagination ID as to not show all the surveys at once
     * @return Paginated List with surveys the user has answered 
     */
    public List<Survey> getUserAnsweredSurveys(RegisteredUser user, short paginationID){
        List<Survey> answeredSurveys = new ArrayList(ANSWERED_SURVEYS_LIST_SIZE);
        int startingIndex = ANSWERED_SURVEYS_LIST_SIZE * paginationID;
        int endingIndex = startingIndex + ANSWERED_SURVEYS_LIST_SIZE;
        List<Review> userReviews = user.getProfile().getReviews();
        
        List<Review> userFinishedReviews = new LinkedList<>();
        
        userReviews.stream().filter((review) -> (review.isFinished())).forEachOrdered((review) -> {
            userFinishedReviews.add(review);
        });
        
        int userFinishedReviewsSize = userFinishedReviews.size();
        
        if(userFinishedReviewsSize >= endingIndex){
            for(int i = startingIndex; i < endingIndex; i++){
                answeredSurveys.add(userFinishedReviews.get(i).getSurvey());
            }
        }else{
            for(int i = startingIndex; i < userFinishedReviewsSize; i++){
                answeredSurveys.add(userFinishedReviews.get(i).getSurvey());
            }
        }

        return answeredSurveys;
    }

    /**
     * Returns list of products that contains a certain code
     *
     * @param code product's code
     * @return list of products that contains the code
     */
    public List<Product> getProductsByCode(String code) {
        List<Product> products = new ProductRepositoryImpl().getProductsByCode(code);
        return products;
    }

    /**
     * Returns a list of surveys about a product
     *
     * @param products list of products to filter by
     * @param registeredUser user
     * @return list of surveys
     */
    public List<Survey> getSurveysByProduct(List<Product> products, RegisteredUser registeredUser) {
        List<Survey> surveyList = new ArrayList<>();
        SurveyRepositoryImpl surveyRepo = new SurveyRepositoryImpl();
        for (Product p : products) {
            List<Survey> productSurveys = surveyRepo.getActiveSurveysByProductAndRegisteredUser(p, registeredUser);
            if (productSurveys != null) {
                for (Survey s : productSurveys) {
                    if (!surveyList.contains(s)) {
                        surveyList.add(s);
                    }
                }
            }
        }
        return surveyList;
    }
}
