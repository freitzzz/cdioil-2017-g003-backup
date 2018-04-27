package cdioil.persistence;

import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.domain.authz.Profile;
import cdioil.domain.authz.RegisteredUser;
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
}
