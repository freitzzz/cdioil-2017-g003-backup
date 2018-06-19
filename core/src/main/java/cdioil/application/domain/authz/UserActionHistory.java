package cdioil.application.domain.authz;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * UserActionHistory class that keeps track of user actions on the application
 * <br>Should not be confused as UserSession since user session is used to keep track 
 * of user sessions (with authentication tokens)
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */
@Entity
@SequenceGenerator(name="userHistorySeq",allocationSize = 1,initialValue = 1)
public class UserActionHistory implements Serializable {
    @Id
    @GeneratedValue(generator = "userHistorySeq",strategy = GenerationType.SEQUENCE)
    private long id;
    /**
     * UserSession with the session which the user did
     */
    /*Many User Actions belong to the same user session*/
    /*User Session should already exist befor the user action, so it should only refresh from the database*/
    @ManyToOne(cascade = CascadeType.REFRESH)
    private UserSession userSession;
    /**
     * LocalDateTime with the date time of the user action
     */
    //The NOSONAR comment below is due to LocalDateTime not being serializable in JPA 2.1
    private LocalDateTime actionDateTime; //NOSONAR
    /**
     * UserAction with the action which the user did
     */
    @Enumerated
    private UserAction userAction;
    /**
     * Builds a new UserActionHistory with the action which a certain user did on 
     * a certain session
     * @param userSession UserSession with the user session which the action was made
     * @param userAction UserAction with the action which the user did
     */
    public UserActionHistory(UserSession userSession,UserAction userAction){
        this.userSession=userSession;
        this.userAction=userAction;
        actionDateTime=LocalDateTime.now();
    }
    /**
     * Protected constructor in order to allow JPA persistence
     */
    protected UserActionHistory(){}
}
