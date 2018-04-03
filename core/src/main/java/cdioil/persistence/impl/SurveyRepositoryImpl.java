package cdioil.persistence.impl;

import cdioil.domain.Survey;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.SurveyRepository;

/**
 * Class that represents the implementation of the Survey repository
 * @see cdioil.persistence.SurveyRepository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class SurveyRepositoryImpl extends BaseJPARepository<Survey,Integer> implements SurveyRepository {
    /**
     * Method that returns the persistence unit name that the repository uses
     * @return String with the persistence unit name that the repository uses
     */
    @Override
    protected String persistenceUnitName(){
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
}
