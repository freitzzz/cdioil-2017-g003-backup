package cdioil.persistence.impl;

import cdioil.domain.Review;
import cdioil.domain.ReviewState;
import cdioil.domain.Survey;
import cdioil.domain.authz.Profile;
import cdioil.domain.authz.RegisteredUser;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.ReviewRepository;
import java.util.List;

/**
 * Class that represents the implementation of the Review repository
 * @see cdioil.persistence.ReviewRepository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class ReviewRepositoryImpl extends BaseJPARepository<Review,Long> implements ReviewRepository{
    /**
     * Method that returns the persistence unit name that the repository uses
     * @return String with the persistence unit name that the repository uses
     */
    @Override
    protected String persistenceUnitName(){
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
    /**
     * Method that gets all reviews made for a certain survey
     * @param survey Survey with the survey being retrieved all reviews
     * @return List with all reviews made for a certain survey
     */
    @Override
    public List<Review> getReviewsBySurvey(Survey survey){
        if(survey==null)return null;
        return (List<Review>)entityManager().createQuery("SELECT r FROM Review r "
                + "WHERE r.survey= :survey").setParameter("survey",survey).getResultList();
    }
    /**
     * TODO Update method with lazy loading
     * Method that returns all the pending reviews of a certain user
     * @param loggedUserProfile profile of a user
     * @return list of all the pending reviews user that a registered user has
     */
    @Override
    public List<Review> findUserPendingReviews(Profile loggedUserProfile) {
        if(loggedUserProfile == null) return null;
        return (List<Review>)entityManager().createQuery("SELECT r from Review r,"
                + "Profile p WHERE r.reviewState= :reviewState AND p = :profile")
                .setParameter("reviewState",ReviewState.PENDING)
                .setParameter("profile", loggedUserProfile).getResultList();
    }  
}
