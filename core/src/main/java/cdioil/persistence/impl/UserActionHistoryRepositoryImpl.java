package cdioil.persistence.impl;

import cdioil.application.domain.authz.UserAction;
import cdioil.application.domain.authz.UserActionHistory;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.UserActionHistoryRepository;
import cdioil.time.TimePeriod;
import java.time.LocalDateTime;

/**
 * Class that represents the UserActionHistory repository implementation
 * @see UserActionHistoryRepository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */
public class UserActionHistoryRepositoryImpl extends BaseJPARepository<UserActionHistory,Long> implements UserActionHistoryRepository{
    /**
     * Method that returns the persistence unit name that the repository uses
     * @return String with the persistence unit name that the repository uses
     */
    @Override
    protected String persistenceUnitName(){
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
    /**
     * Method that gets the total number of actions made during a certain period 
     * of time
     * @param dateTimeX LocalDateTime with the date time of the period start
     * @param dateTimeY LocalDateTime with the date time of the period end
     * @return Long with the total number of actions made during a certain period 
     * of time
     */
    @Override
    public long getNumberOfUserActions(LocalDateTime dateTimeX, LocalDateTime dateTimeY) {
        checkPeriodOfTime(dateTimeX,dateTimeY);
        return (long)entityManager().createQuery("SELECT COUNT(UAH) FROM UserActionHistory UAH "
                + "WHERE UAH.actionDateTime BETWEEN :dateTimeX AND :dateTimeY")
                .setParameter("dateTimeX",dateTimeX)
                .setParameter("dateTimeY",dateTimeY)
                .getSingleResult();
    }
    /**
     * Method that gets the total number of a certain action during a certain period 
     * of time
     * @param dateTimeX LocalDateTime with the date time of the period start
     * @param dateTimeY LocalDateTime with the date time of the period end
     * @param userAction UserAction with a certain action being searched
     * @return Long with the total number of a certain action during a certain period of 
     * time
     */
    @Override
    public long getNumberOfCertainUserAction(LocalDateTime dateTimeX, LocalDateTime dateTimeY, UserAction userAction) {
        checkPeriodOfTime(dateTimeX,dateTimeY);
        return (long)entityManager().createQuery("SELECT COUNT(UAH) FROM UserActionHistory UAH "
                + "WHERE UAH.actionDateTime BETWEEN :dateTimeX AND :dateTimeY "
                + "AND UAH.userAction = :userAction")
                .setParameter("dateTimeX",dateTimeX)
                .setParameter("dateTimeY",dateTimeY)
                .setParameter("userAction",userAction)
                .getSingleResult();
    }
    /**
     * Method that verifies if a certain period of time is valid or not
     * @param dateTimeX LocalDateTime with the date time of the period start
     * @param dateTimeY LocalDateTime with the date time of the period end
     */
    private void checkPeriodOfTime(LocalDateTime dateTimeX, LocalDateTime dateTimeY){
        TimePeriod validTimePeriod = new TimePeriod(dateTimeX,dateTimeY);
        validTimePeriod=null;
    }
    
}
