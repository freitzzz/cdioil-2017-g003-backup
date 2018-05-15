package cdioil.application.authz;

import cdioil.application.domain.authz.AuthenticationService;
import cdioil.application.domain.authz.UserSession;
import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.User;
import cdioil.persistence.impl.AdminRepositoryImpl;
import cdioil.persistence.impl.ManagerRepositoryImpl;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.persistence.impl.UserSessionRepositoryImpl;
import java.io.Serializable;

/**
 * AuthenticationController that controls all user actions
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 4.0 of FeedbackMonkey
 */
public final class AuthenticationController implements Serializable {
    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 13L;
    /**
     * UserSession with the current user session
     */
    private UserSession currentUserSession;
    /**
     * User with the current authenticated user
     */
    private User currentUser;
    /**
     * Method that logins a certain user into the application
     * @param email String with the user email
     * @param password String with the user password
     * @return boolean true if the user logged in successfully, false if not
     */
    public boolean login(String email,String password){
        if(tryToLogin(email,password)){
            logSessionStart();
            return true;
        }else{
            return false;
        }
    }
    /**
     * Method that logouts the current user of the application
     * @return boolean true if the user logged out successfully, false if not
     */
    public boolean logout(){
        if(currentUserSession==null){
            return false;
        }
        logSessionEnd();
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
        return currentUser instanceof Admin;
    }
    /**
     * Method that checks if the current logged in user can access the manager backoffice
     * @return boolean true if the user can access the manager backoffice, false if not
     */
    public boolean canAccessManagerBackoffice(){
        return currentUser instanceof Manager;
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
     * Returns the current authenticated user
     * @return User with the current authenticated user
     */
    public User getUser(){
        return currentUser;
    }
    /**
     * Returns the current authenticated user token
     * @return String with the authenticated user token
     */
    public String getUserToken(){return currentUserSession.getUserToken();}
    /**
     * Method that returns the current session user
     * <br>Method to be deprecated very soon, only is here due to need on some 
     * backoffice classes
     * @return User with the current session user
     */
    private Admin getAdmin(){
        return currentUserSession!=null ? new AdminRepositoryImpl()
                .findBySystemUser(currentUserSession.getUser())
                : null;
    }
    /**
     * Method that returns the current session user
     * <br>Method to be deprecated very soon, only is here due to need on some 
     * backoffice classes
     * @return User with the current session user
     */
    private Manager getManager(){
        return currentUserSession!=null ? new ManagerRepositoryImpl()
                .findBySystemUser(currentUserSession.getUser())
                : null;
    }
    /**
     * Method that returns the current session user
     * <br>Method to be deprecated very soon, only is here due to need on some 
     * backoffice classes
     * @return User with the current session user
     */
    private RegisteredUser getRegisteredUser(){
        return currentUserSession!=null ? new RegisteredUserRepositoryImpl()
                .findBySystemUser(currentUserSession.getUser())
                : null;
    }
    /**
     * Method that tries to login a certain user into the application
     * @param email String with the user email
     * @param password String with the user password
     * @return boolean true if the user logged in successfully, false if not
     */
    private boolean tryToLogin(String email,String password){
        if(currentUserSession!=null){
            return false;
        }
        this.currentUserSession=AuthenticationService.create().login(email,password);
        if((currentUser=getRegisteredUser())!=null){
            return true;
        }
        if((currentUser=getAdmin())!=null){
            return true;
        }
        return (currentUser=getManager())!=null;
    }
    /**
     * Logs the start of the user session
     */
    private void logSessionStart(){
        new UserSessionRepositoryImpl().add(currentUserSession);
    }
    /**
     * Logs the end of the user session
     */
    private void logSessionEnd(){
        currentUserSession.logSessionEnd();
        currentUserSession=new UserSessionRepositoryImpl().merge(currentUserSession);
    }
}
