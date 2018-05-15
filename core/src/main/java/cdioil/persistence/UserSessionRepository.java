package cdioil.persistence;

import cdioil.domain.authz.SystemUser;

/**
 * Interface for the UserSession repository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 4.0 of FeedbackMonkey
 */
public interface UserSessionRepository {
    /**
     * Method that returns the user which session has a certain authentication token
     * @param authenticationToken String with the user session authentication token
     * @return SystemUser with the user which session has a certain authentication token
     */
    public abstract SystemUser getSystemUserByAuthenticationToken(String authenticationToken);
}
