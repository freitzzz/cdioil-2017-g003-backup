package cdioil.persistence.impl;

import cdioil.application.domain.authz.UserSession;
import cdioil.domain.authz.SystemUser;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.UserRepository;
import cdioil.persistence.UserSessionRepository;
import javax.persistence.Query;

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
    /**
     * Method that returns the user which session has a certain authentication token
     * @param authenticationToken String with the user session authentication token
     * @return SystemUser with the user which session has a certain authentication token
     */
    @Override
    public SystemUser getSystemUserByAuthenticationToken(String authenticationToken){
        Query queryUserByAuthenticationToken=entityManager().createQuery("SELECT US.sessionUser FROM UserSession US "
                + "WHERE US.userToken= :authenticationToken")
                .setParameter("authenticationToken",authenticationToken);
        return !queryUserByAuthenticationToken.getResultList().isEmpty() 
                ? (SystemUser)queryUserByAuthenticationToken.getSingleResult() 
                : null;
    }
    /**
     * Method that gets a certain UserSession by a certain authentication token
     * @param authenticationToken String with the user authentication token
     * @return UserSession with the the user session which has a certain authentication token, 
     * false if not
     */
    public UserSession getUserSessionByAuthenticationToken(String authenticationToken){
        Query userSessionQuery=entityManager().createQuery("SELECT US FROM UserSession US "
                + "WHERE US.userToken= :authenticationToken")
                .setParameter("authenticationToken",authenticationToken);
        return !userSessionQuery.getResultList().isEmpty() ? (UserSession)userSessionQuery.getSingleResult() : null;
    }
}
