package cdioil.persistence;

import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.domain.authz.RegisteredUser;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for the Review Repository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public interface ReviewRepository {
    /**
     * Method that gets all reviews made for a certain survey
     * @param survey Survey with the survey being retrieved all reviews
     * @return List with all reviews made for a certain survey
     */
    public abstract List<Review> getReviewsBySurvey(Survey survey);
    
    /**
     * Method that returns the ID of a given review.
     * 
     * @param review Review in question
     * @return the ID of the review
     */
    public abstract Long getReviewID(Review review);
    /**
     * Method that retrieves a certain Review of a certain user by it's ID
     * @param registeredUser RegisteredUser with the user being fetched the review
     * @param reviewID Long with the ID of the review
     * @return Review with the fetched review of the user with a certain ID, or null 
     * if no review was found
     */
    public abstract Review getUserReviewByID(RegisteredUser registeredUser,long reviewID);
    /**
     * Method that retrives the reviews average time to answer between a certain time period
     * @param dateTimeX LocalDateTime with the time period start
     * @param dateTimeY LocalDateTime with the time period end
     * @return Float with the average time to answer between a certain time period
     */
    public abstract float getReviewsAverageTime(LocalDateTime dateTimeX,LocalDateTime dateTimeY);
}
