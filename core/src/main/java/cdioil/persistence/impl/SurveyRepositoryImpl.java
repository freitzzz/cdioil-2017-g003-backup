package cdioil.persistence.impl;

import cdioil.domain.Survey;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.SurveyRepository;

import java.util.List;

/**
 * Class that represents the implementation of the Survey repository
 * @see cdioil.persistence.SurveyRepository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class SurveyRepositoryImpl extends BaseJPARepository<Survey,Integer> implements SurveyRepository {
    /**
     * Constant that represents the lii
     */
    private static final int LAZY_LOADING_LIMIT=100;
    /**
     * Method that returns the persistence unit name that the repository uses
     * @return String with the persistence unit name that the repository uses
     */
    @Override
    protected String persistenceUnitName(){
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

    /**
     * Method that returns all returns all Surveys that are listed on a certain range
     * <br>Range is based on the following expression
     * [INDEX*LAZY_LOAD_LIMIT,((INDEX*LAZY_LOAD_LIMIT)+LAZY_LOAD_LIMIT)
     * @param lazyLoadIndex Integer with the index of the target range
     * @return List with all Surveys on a certain range
     */
    public List<Survey> getSurveysByLazyLoadingIndex(int lazyLoadIndex){
        int minLimit=lazyLoadIndex*LAZY_LOADING_LIMIT;
        int maxLimit=minLimit+LAZY_LOADING_LIMIT;
        return (List<Survey>)entityManager().createQuery("SELECT s FROM Survey s LIMIT "+minLimit+","+maxLimit
               ,Survey.class).getResultList();
    }
}
