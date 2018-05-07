package cdioil.application.domain.authz;

import cdioil.domain.authz.SystemUser;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * UserSession class that classifies the session of a User on the application
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 4.0 of FeedbackMonkey
 */
@Entity
public class UserSession implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * LocalDateTime with the session start date
     */
    private LocalDateTime sessionStartDate;
    /**
     * LocalDateTime with the session end date
     */
    @Column(updatable = true,nullable = true)
    private LocalDateTime sessionEndDate;
    /**
     * User with the session user
     */
    @OneToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    private SystemUser sessionUser;
    /**
     * Builds a new UserSession with the user that is being logged on the session
     * @param sessionUser SystemUser with the user that is being logged on the session
     */
    public UserSession(SystemUser sessionUser){
        this.sessionUser=sessionUser;
        this.sessionStartDate=LocalDateTime.now();
    }
    /**
     * Protected constructor in order to allow JPA persistence
     */
    protected UserSession(){}
    /**
     * Logs the end of the user session
     */
    public void logSessionEnd(){
        this.sessionEndDate=LocalDateTime.now();
    }
    /**
     * Method that returns the current session user
     * <br>Method to be deprecated very soon, only is here due to need on some 
     * backoffice classes
     * @return User with the current session user
     */
    public SystemUser getUser(){return sessionUser;}
}
