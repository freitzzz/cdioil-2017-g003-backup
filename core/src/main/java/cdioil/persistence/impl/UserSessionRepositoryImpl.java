package cdioil.persistence.impl;

import cdioil.application.domain.authz.UserSession;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.UserRepository;
import cdioil.persistence.UserSessionRepository;

/**
 * Class that represents the UserSession repository implementation
 * @see UserRepository 
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 4.0 of FeedbackMonkey
 */
public class UserSessionRepositoryImpl extends BaseJPARepository<UserSession,Long> implements UserSessionRepository{
    /**
     * Method that returns the persistence unit name that the repository uses
     * @return String with the persistence unit name that the repository uses
     */
    @Override
    protected String persistenceUnitName(){
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
}
