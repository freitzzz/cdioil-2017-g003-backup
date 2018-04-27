package cdioil.persistence;

import cdioil.domain.Review;
import cdioil.domain.authz.Profile;
import java.util.List;

/**
 * Interface for Profile Repository
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public interface ProfileRepository {

    /**
     * Method that returns all the pending reviews of a certain user
     *
     * @param loggedUserProfile profile of a user
     * @return list of all the pending reviews user that a registered user has
     */
    public abstract List<Review> findUserPendingReviews(Profile loggedUserProfile);
}
