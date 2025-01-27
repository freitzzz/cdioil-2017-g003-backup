package cdioil.persistence.impl;

import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.domain.authz.RegisteredUser;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.ReviewRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Class that represents the implementation of the Review repository
 *
 * @see cdioil.persistence.ReviewRepository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class ReviewRepositoryImpl extends BaseJPARepository<Review, Long> implements ReviewRepository {

    /**
     * Method that returns the persistence unit name that the repository uses
     *
     * @return String with the persistence unit name that the repository uses
     */
    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

    /**
     * Method that gets all reviews made for a certain survey
     * <br>Needs to be this way since the query parser fails with abstract classes<br>
     *
     * @param survey Survey with the survey being retrieved all reviews
     * @return List with all reviews made for a certain survey
     */
    @Override
    public List<Review> getReviewsBySurvey(Survey survey) {
        if (survey == null) {
            return null;
        }
        long surveyID = (long) entityManager().createQuery("SELECT s.id FROM "
                + survey.getClass().getSimpleName()
                + " s WHERE s= :survey").setParameter("survey", survey).getSingleResult();
        return (List<Review>) entityManager().createNativeQuery("SELECT * FROM Review r "
                + "WHERE r.survey_id = ?1", Review.class).setParameter(1, surveyID).getResultList();
    }

    /**
     * Method that returns the ID of a given review.
     *
     * @param review Review in question
     * @return the ID of the review
     */
    @Override
    public Long getReviewID(Review review) {
        if(review == null){
            return null;
        }
        
        EntityManager em = entityManager();
        Query q = em.createQuery("SELECT r.id FROM " + review.getClass().getSimpleName() + " r WHERE r = :review");
        q.setParameter("review", review); 
        
        return q.getResultList().isEmpty() ? null : (Long) q.getSingleResult();
    }
    /**
     * Method that retrieves a certain Review of a certain user by it's ID
     * @param registeredUser RegisteredUser with the user being fetched the review
     * @param reviewID Long with the ID of the review
     * @return Review with the fetched review of the user with a certain ID, or null 
     * if no review was found
     */
    @Override
    public Review getUserReviewByID(RegisteredUser registeredUser, long reviewID){
        Query queryUserReviewID=entityManager().createQuery("SELECT RV FROM Review RV,Profile PF "
                + "WHERE RV.id= :reviewID "
                + "AND PF.registeredUser= :registeredUser "
                + "AND RV MEMBER OF PF.reviews")
                .setParameter("reviewID",reviewID)
                .setParameter("registeredUser",registeredUser);
        return !queryUserReviewID.getResultList().isEmpty() ? (Review)queryUserReviewID.getSingleResult() : null;
    }
    /**
     * Method that retrives the reviews average time to answer between a certain time period
     * @param dateTimeX LocalDateTime with the time period start
     * @param dateTimeY LocalDateTime with the time period end
     * @return Double with the average time to answer between a certain time period
     */
    @Override
    public double getReviewsAverageTime(LocalDateTime dateTimeX,LocalDateTime dateTimeY){
        Query queryReviewsAverageTime=entityManager().createQuery("SELECT AVG(RE.timeToAnswer) FROM Review RE "
                + "WHERE CAST(RE.finishedReviewDateTime AS timestamp) BETWEEN :dateTimeX AND :dateTimeY")
                .setParameter("dateTimeX",dateTimeX)
                .setParameter("dateTimeY",dateTimeY);
        Double reviewsAverageTime=(Double)queryReviewsAverageTime.getSingleResult();
        return reviewsAverageTime!=null ? reviewsAverageTime : 0;
    }
}
