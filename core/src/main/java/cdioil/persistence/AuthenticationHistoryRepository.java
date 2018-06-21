package cdioil.persistence;

import cdioil.application.domain.authz.AuthenticationAction;
import cdioil.application.domain.authz.UserAction;
import java.time.LocalDateTime;

/**
 * Interface for the AuthenticationHistory repository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */
public interface AuthenticationHistoryRepository {
    /**
     * Method that gets the total number of authentication actions made during a certain period 
     * of time
     * @param dateTimeX LocalDateTime with the date time of the period start
     * @param dateTimeY LocalDateTime with the date time of the period end
     * @return Long with the total number of authentication actions made during a certain period 
     * of time
     */
    public long getNumberOfAuthenticationActions(LocalDateTime dateTimeX,LocalDateTime dateTimeY);
    /**
     * Method that gets the total number of a certain authentication action during a certain period 
     * of time
     * @param dateTimeX LocalDateTime with the date time of the period start
     * @param dateTimeY LocalDateTime with the date time of the period end
     * @param authenticationAction AuthenticationAction with a certain authentication action being searched
     * @return Long with the total number of a certain authentication action during a certain 
     * period of time
     */
    public long getNumberOfCertainAuthenticationAction(LocalDateTime dateTimeX,LocalDateTime dateTimeY,AuthenticationAction authenticationAction);
}
