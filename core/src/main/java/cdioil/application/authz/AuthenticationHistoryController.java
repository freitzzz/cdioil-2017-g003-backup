package cdioil.application.authz;

import cdioil.application.domain.authz.AuthenticationAction;
import cdioil.application.domain.authz.AuthenticationHistory;
import cdioil.persistence.impl.AuthenticationHistoryRepositoryImpl;

/**
 * AuthenticationHistoryController with the controller that controlls all authentication 
 * actions
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */
public final class AuthenticationHistoryController {
    /**
     * Method that logs a certain authentication action
     * @param authenticationAction AuthenticationAction with authentication action being logged
     * @return boolean treu if the authenication action was logged with success, false if not
     */
    public boolean logAuthenticationAction(AuthenticationAction authenticationAction){
        return new AuthenticationHistoryRepositoryImpl().add(new AuthenticationHistory(authenticationAction))!=null;
    }
}
