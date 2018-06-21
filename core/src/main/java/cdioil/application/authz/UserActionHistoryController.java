package cdioil.application.authz;

import cdioil.application.domain.authz.UserAction;
import cdioil.application.domain.authz.UserActionHistory;
import cdioil.application.domain.authz.UserSession;
import cdioil.persistence.impl.UserActionHistoryRepositoryImpl;

/**
 * UserActionHistoryController with the controller that controlls all user actions
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */
public final class UserActionHistoryController {
    /**
     * UserSession with the current
     */
    private final UserSession userActionSession;
    /**
     * Builds a new UserActionHistoryController with the current user session
     * @param userSession UserSession with the current user session
     */
    public UserActionHistoryController(UserSession userSession){
        this.userActionSession=userSession;
    }
    /**
     * Logs a certain user action
     * @param userAction UserAction with the user action being logged
     * @return boolean true if the user action was logged with success, false if not
     */
    public boolean logUserAction(UserAction userAction){
        return new UserActionHistoryRepositoryImpl().add(new UserActionHistory(userActionSession,userAction))!=null;
    }
}
