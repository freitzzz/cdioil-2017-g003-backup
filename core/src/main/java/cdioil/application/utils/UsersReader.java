package cdioil.application.utils;

import cdioil.domain.authz.SystemUser;
import java.util.List;

/**
 * Interface that represents the read of users through files
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public interface UsersReader {
    /**
     * Method that reads and parses the users contained in a certain file
     * @return List with all SystemUser read from the file, or null if an error occured 
     */
    public abstract List<SystemUser> read();
}
