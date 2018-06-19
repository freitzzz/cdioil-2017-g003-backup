package cdioil.persistence;

import cdioil.application.domain.authz.UserAction;
import java.time.LocalDateTime;

/**
 * Interface for the UserActionHistory repository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */
public interface UserActionHistoryRepository {
    /**
     * Method that gets the total number of actions made during a certain period 
     * of time
     * @param dateTimeX LocalDateTime with the date time of the period start
     * @param dateTimeY LocalDateTime with the date time of the period end
     * @return Long with the total number of actions made during a certain period 
     * of time
     */
    public long getNumberOfUserActions(LocalDateTime dateTimeX,LocalDateTime dateTimeY);
    /**
     * Method that gets the total number of a certain action during a certain period 
     * of time
     * @param dateTimeX LocalDateTime with the date time of the period start
     * @param dateTimeY LocalDateTime with the date time of the period end
     * @param userAction UserAction with a certain action being searched
     * @return Long with the total number of a certain action during a certain period of 
     * time
     */
    public long getNumberOfCertainUserAction(LocalDateTime dateTimeX,LocalDateTime dateTimeY,UserAction userAction);
}
