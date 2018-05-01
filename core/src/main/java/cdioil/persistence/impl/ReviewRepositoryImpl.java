package cdioil.persistence.impl;

import cdioil.domain.Review;
import cdioil.domain.Survey;
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
     * <br>Needs to be this way since the query parser fails with 
     * abstract classes<br>
     * @param survey Survey with the survey being retrieved all reviews
     * @return List with all reviews made for a certain survey
     */
    @Override
    public List<Review> getReviewsBySurvey(Survey survey){
        if(survey==null)return null;
        long surveyID = (long)entityManager().createQuery("SELECT s.id FROM " 
                + survey.getClass().getSimpleName()
                + " s WHERE s= :survey").setParameter("survey",survey).getSingleResult();
        return (List<Review>)entityManager().createNativeQuery("SELECT * FROM Review r "
                + "WHERE r.survey_id = ?1",Review.class).setParameter(1,surveyID).getResultList();
    }
}
