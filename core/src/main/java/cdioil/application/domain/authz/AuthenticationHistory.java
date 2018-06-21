package cdioil.application.domain.authz;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * AuthenticationHistory class that keeps track of user authentications actions on the system
 * <br>Should not be confused as UserSession since user session is used to keep track 
 * of user sessions (with authentication tokens)
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */
@Entity
@SequenceGenerator(name="authenticationHistorySeq",allocationSize = 1,initialValue = 1)
public class AuthenticationHistory implements Serializable{
    @Id
    @GeneratedValue(generator = "authenticationHistorySeq",strategy = GenerationType.SEQUENCE)
    private long id;
    /**
     * LocalDateTime with the date time of the authentication action
     */
    //The NOSONAR comment below is due to LocalDateTime not being serializable in JPA 2.1
    private LocalDateTime authenticationActionDateTime; //NOSONAR
    /**
     * AuthenticationAction with the user authentication action
     */
    @Enumerated(EnumType.STRING)
    private AuthenticationAction authenticationAction;
    /**
     * Builds a new AuthenticationHistory with the user authentication action
     * @param authenticationAction AuthenticationAction with the user authentication action
     */
    public AuthenticationHistory(AuthenticationAction authenticationAction){
        this.authenticationAction=authenticationAction;
        this.authenticationActionDateTime=LocalDateTime.now();
    }
    /**
     * Protected constructor in order to allow JPA Persistence
     */
    protected AuthenticationHistory(){}
}
