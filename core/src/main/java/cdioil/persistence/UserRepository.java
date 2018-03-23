package cdioil.persistence;

import cdioil.domain.authz.Email;
import cdioil.domain.authz.SystemUser;
import java.util.List;

/**
 * Interface for the SystemUser repository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public interface UserRepository {
    /**
     * Method that persists a certain list of valid users in the database
     * @param users List with all valid users to be saved on the database
     * @return boolean true if the operation was successful, false if not
     */
    public abstract boolean saveAll(List<SystemUser> users);

    /**
     * Method that finds a certain SystemUser by his email
     * @param email Email with the email being searched
     * @return SystemUser with the user that has the email that was being searched
     */
    public abstract SystemUser findByEmail(Email email);
    
     /**
      * Method that finds all users with a certain email pattern 
     * @param emailPattern String with the email pattern that is being searched
     * @return List with all users found with a certain email pattern, or null if an 
     * error occured
     */
    public abstract List<SystemUser> usersByPattern(String emailPattern);
}
