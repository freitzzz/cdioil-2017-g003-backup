package cdioil.application.authz;

import cdioil.application.domain.authz.AuthenticationService;
import cdioil.application.domain.authz.UserSession;
import cdioil.domain.authz.User;

/**
 * AuthenticationController that controls all user actions
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 4.0 of FeedbackMonkey
 */
public final class AuthenticationController {
    /**
     * UserSession with the current user session
     */
    private UserSession currentUserSession;
    /**
     * Method that logins a certain user into the application
     * @param email String with the user email
     * @param password String with the user password
     * @return boolean true if the user logged in successfully, false if not
     */
    public boolean login(String email,String password){
        if(currentUserSession!=null)return false;
        this.currentUserSession=AuthenticationService.create().login(email,password);
        return true;
    }
    /**
     * Method that logouts the current user of the application
     * @return boolean true if the user logged out successfully, false if not
     */
    public boolean logout(){
        if(currentUserSession!=null)return false;
        currentUserSession=null;
        return true;
    }
    /**
     * Method that activates a certain user account
     * @param email String with the user email
     * @param password String with the user password
     * @param activationCode String with the user activation code
     * @return boolean true if the account was activated with success, false if not
     */
    public boolean activateAccount(String email,String password,String activationCode){
        return AuthenticationService.create().activate(email,password,activationCode);
    }
    /**
     * Method that checks if the current logged in user can access administrator backoffice
     * @return boolean true if the user can access the administrator backoffice, false if not
     */
    public boolean canAccessAdminBackoffice(){
        return currentUserSession!=null ? currentUserSession.isSessionForAdmin(): false;
    }
    /**
     * Method that checks if the current logged in user can access the manager backoffice
     * @return boolean true if the user can access the manager backoffice, false if not
     */
    public boolean canAccessManagerBackoffice(){
        return currentUserSession!=null ?currentUserSession.isSessionForManager(): false;
    }
    /**
     * Method that checks if the current logged in user can access backoffice
     * @return boolean true if the user can access backoffice, false if not
     */
    public boolean canAccessBackoffice(){
        return canAccessAdminBackoffice() || canAccessManagerBackoffice();
    }
    /**
     * Method that checks if the current logged in user can access frontoffice
     * @return boolean true if the user can access frontoffice, false if not
     */
    public boolean canAccessFrontoffice(){
        return currentUserSession!=null;
    }
    /**
     * Method that returns the current session user
     * <br>Method to be deprecated very soon, only is here due to need on some 
     * backoffice classes
     * @return User with the current session user
     */
    public User getUser(){return currentUserSession.getUser();}
}
