package cdioil.application.domain.authz;

import cdioil.domain.authz.Email;
import cdioil.domain.authz.SystemUser;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * Class that's responsible for mananing the login attempts of users in the
 * backoffice and frontoffice (e.g. If a user tries to unsucessfully login X
 * ammount of times, this class is responsible for prohibiting that user from
 * attempting to login any further for Y ammount of time)
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class LoginManagement implements Serializable {

    /**
     * Maximum number of unsucessful login attempts that a user can do before
     * they're locked from trying again.
     */
    private static final int MAXIMUM_NUMBER_OF_UNSUCCESSFUL_LOGIN_ATTEMPTS = 3;
    /**
     * Time interval (in minutes) for which the user is locked from logging in
     * the application.
     */
    private static final int LOCK_USER_TIME_MINUTES = 5;
    /**
     * Number of login attempts that have been made with a certain Email.
     */
    private int loginAttempts;
    /**
     * Email that was used to attempt a login.
     */
    @EmbeddedId
    private Email usedEmail;
    /**
     * Moment in which the user was blocked from trying to login again.
     */
    //The NOSONAR comment below is due to LocalDateTime not being serializable in JPA 2.1
    private LocalDateTime lockedUserTimestamp; //NOSONAR

    /**
     * Builds an instance of LoginManagement receiving a SystemUser
     *
     * @param usedEmail email that was used to attempt a login
     */
    public LoginManagement(Email usedEmail) {
        this.usedEmail = usedEmail;
    }

    /**
     * Protected constructor for JPA.
     */
    protected LoginManagement() {
    }

    /**
     * Increments the number of login attempts that were made using an email.
     */
    public void incrementLoginAttempts() {
        loginAttempts++;
        if (loginAttempts == MAXIMUM_NUMBER_OF_UNSUCCESSFUL_LOGIN_ATTEMPTS) {
            lockUser();
        }
    }

    /**
     * Checks whether the user is still locked or not from logging in
     *
     * @return true if the user is locked, false if otherwise
     */
    public boolean isUserLocked() {
        if (lockedUserTimestamp == null) {
            return false;
        }
        if (LocalDateTime.now().getMinute() - lockedUserTimestamp.getMinute() <= LOCK_USER_TIME_MINUTES) {
            return true;
        } else {
            unlockUser();
            return false;
        }
    }

    /**
     * Registers the local date time for when the user was locked from trying to
     * login.
     */
    private void lockUser() {
        if (loginAttempts == MAXIMUM_NUMBER_OF_UNSUCCESSFUL_LOGIN_ATTEMPTS) {
            lockedUserTimestamp = LocalDateTime.now();
        }
    }

    /**
     * Sets the login attempts counter back to 0 and the lockedUserTimestamp to
     * null.
     */
    private void unlockUser() {
        loginAttempts = 0;
        lockedUserTimestamp = null;
    }
}
