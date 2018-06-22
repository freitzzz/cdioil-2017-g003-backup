package cdioil.persistence.impl;

import cdioil.application.domain.authz.AuthenticationAction;
import cdioil.application.domain.authz.AuthenticationHistory;
import cdioil.persistence.AuthenticationHistoryRepository;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import java.time.LocalDateTime;

/**
 * Class that represents the AuthenticationHistory repository implementation
 * @see AuthenticationHistory
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */
public class AuthenticationHistoryRepositoryImpl extends BaseJPARepository<AuthenticationHistory,Long> implements AuthenticationHistoryRepository{
    /**
     * Method that returns the persistence unit name that the repository uses
     * @return String with the persistence unit name that the repository uses
     */
    @Override
    protected String persistenceUnitName(){
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
    /**
     * Method that gets the total number of authentication actions made during a certain period 
     * of time
     * @param dateTimeX LocalDateTime with the date time of the period start
     * @param dateTimeY LocalDateTime with the date time of the period end
     * @return Long with the total number of authentication actions made during a certain period 
     * of time
     */
    @Override
    public long getNumberOfAuthenticationActions(LocalDateTime dateTimeX, LocalDateTime dateTimeY) {
        return (long)entityManager().createQuery("SELECT COUNT(AH) FROM AuthenticationHistory AH "
                + "WHERE CAST(AH.authenticationActionDateTime as timestamp) BETWEEN :dateTimeX AND :dateTimeY")
                .setParameter("dateTimeX",dateTimeX)
                .setParameter("dateTimeY",dateTimeY)
                .getSingleResult();
    }
     /**
     * Method that gets the total number of a certain authentication action during a certain period 
     * of time
     * @param dateTimeX LocalDateTime with the date time of the period start
     * @param dateTimeY LocalDateTime with the date time of the period end
     * @param authenticationAction AuthenticationAction with a certain authentication action being searched
     * @return Long with the total number of a certain authentication action during a certain 
     * period of time
     */
    @Override
    public long getNumberOfCertainAuthenticationAction(LocalDateTime dateTimeX, LocalDateTime dateTimeY, AuthenticationAction authenticationAction) {
        return (long)entityManager().createQuery("SELECT COUNT(AH) FROM AuthenticationHistory AH "
                + "WHERE CAST(AH.authenticationActionDateTime as timestamp) BETWEEN :dateTimeX AND :dateTimeY "
                + "AND AH.authenticationAction = :authenticationAction")
                .setParameter("dateTimeX",dateTimeX)
                .setParameter("dateTimeY",dateTimeY)
                .setParameter("authenticationAction",authenticationAction)
                .getSingleResult();
    }
}
