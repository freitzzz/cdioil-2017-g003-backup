package cdioil.application.domain.authz;

import cdioil.domain.authz.SystemUser;
import cdioil.encryptions.DigestUtils;
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
    /**
     * Constant that represents the hashing being used for the user's authentication token
     */
    private static final String SHA_384="SHA-384";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * LocalDateTime with the session start date
     */
    //The NOSONAR comment below is due to LocalDateTime not being serializable in JPA 2.1
    private LocalDateTime sessionStartDate; //NOSONAR
    /**
     * LocalDateTime with the session end date
     */
    @Column(updatable = true,nullable = true)
    //The NOSONAR comment below is due to LocalDateTime not being serializable in JPA 2.1
    private LocalDateTime sessionEndDate; //NOSONAR
    /**
     * User with the session user
     */
    @OneToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    private SystemUser sessionUser;
    /**
     * String with the current user token
     * <br>Currently it's a combination of the user ID + logged session timestamp
     * <br>The authentication token is being hashed with SHA-384
     */
    private String userToken;
    /**
     * Builds a new UserSession with the user that is being logged on the session
     * @param sessionUser SystemUser with the user that is being logged on the session
     */
    public UserSession(SystemUser sessionUser){
        this.sessionUser=sessionUser;
        this.sessionStartDate=LocalDateTime.now();
        this.userToken=DigestUtils.hashify(sessionUser.getID()+sessionStartDate.toString(),SHA_384);
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
    /**
     * Method that returns the current user token
     * @return String with the current user token
     */
    public String getUserToken(){return userToken;}
}
