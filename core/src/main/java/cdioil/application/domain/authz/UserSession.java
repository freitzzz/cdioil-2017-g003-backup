package cdioil.application.domain.authz;

import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.User;
import java.time.LocalDateTime;

/**
 * UserSession class that classifies the session of a User on the application
 * <br>TO-DO: Mark class as an entity in order to add logging on the database
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 4.0 of FeedbackMonkey
 */
public final class UserSession {
    /**
     * LocalDateTime with the session start date
     */
    private LocalDateTime sessionStartDate;
    /**
     * User with the session user
     */
    private User sessionUser;
    /**
     * Builds a new UserSession with the user that is being logged on the session
     * @param sessionUser User with the user that is being logged on the session
     */
    public UserSession(User sessionUser){
        this.sessionUser=sessionUser;
        this.sessionStartDate=LocalDateTime.now();
    }
    /**
     * Method that checks if the current session user is an administrator
     * @return boolean true if the current session user is an administrator, false if not
     */
    public boolean isSessionForAdmin(){return sessionUser instanceof Admin;}
    /**
     * Method that checks if the current session user is a manager
     * @return boolean true if the current session user is a manager, false if not
     */
    public boolean isSessionForManager(){return sessionUser instanceof Manager;}
    /**
     * Method that checks if the current session user is a registered user
     * @return boolean true if the current session user is a registered user, false if not
     */
    public boolean isSessionForRegisteredUser(){return sessionUser instanceof RegisteredUser;}
    /**
     * Hides default constructor
     */
    protected UserSession(){}
}
