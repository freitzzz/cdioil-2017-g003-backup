package cdioil.application.domain.authz;

import cdioil.application.domain.authz.exceptions.AuthenticationException;
import cdioil.application.domain.authz.exceptions.AuthenticationException.AuthenticationExceptionCause;
import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.SystemUser;
import cdioil.persistence.impl.AdminRepositoryImpl;
import cdioil.persistence.impl.ManagerRepositoryImpl;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.persistence.impl.UserRepositoryImpl;

/**
 * AuthenticationService class that authenticates a user into the application
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 4.0 of FeedbackMonkey
 */
public final class AuthenticationService {
    /**
     * Constant that represents the message that ocures if the user trying to login 
     * has inserted invalid credentials
     */
    private static final String INVALID_CREDENTIALS_MESSAGE="Credenciais erradas!";
    /**
     * Constant that represents the message that ocures if the user trying to login 
     * has his account not activated
     */
    private static final String USER_ACCOUNT_NOT_ACTIVATED_MESSAGE="A conta não está activada";
    /**
     * Constant that represents the message that ocures if the user trying to login 
     * has his account not activated
     */
    private static final String ACCOUNT_ALREADY_ACTIVATED_MESSAGE="A conta não está activada";
    /**
     * Hides default constructor
     */
    private AuthenticationService(){}
    /**
     * Creates a new AuthenticationService for the user to authenticate on the application
     * @return AuthenticationService with the authentication service for the user to 
     * authenticate on the application
     */
    public static AuthenticationService create(){return new AuthenticationService();}
    /**
     * Method that authenticates a certain user into the application
     * @param email String with the user email
     * @param password String with the user password
     * @return UserSession with the user session
     */
    public UserSession login(String email,String password){
        long userID=getSystemUser(email,password);
        UserSession registeredUserSession=createSessionForRegisteredUser(userID);
        if(registeredUserSession!=null){
            return registeredUserSession;
        }
        UserSession adminSession=createSessionForAdmin(userID);
        if(adminSession!=null){
            return adminSession;
        }
        return createSessionForManager(userID);
    }
    /**
     * Method that activates a certain user
     * @param email String with the user email
     * @param password String with the user password
     * @param activationCode String with the activation code
     * @return boolean true if the account was activated with success, false if not
     */
    public boolean activate(String email,String password,String activationCode){
        UserRepositoryImpl userRepo=new UserRepositoryImpl();
        long userID=userRepo.login(new Email(email),password);
        if(userID==-1){
            throw new AuthenticationException(INVALID_CREDENTIALS_MESSAGE,
                    AuthenticationExceptionCause.INVALID_CREDENTIALS);
        }
        SystemUser user=userRepo.find(userID);
        if(user.isUserActivated()){
            throw new AuthenticationException(ACCOUNT_ALREADY_ACTIVATED_MESSAGE,
                AuthenticationExceptionCause.ALREADY_ACTIVATED);
        }
        if(user.activateAccount(activationCode)){
            userRepo.merge(user);
            return true;
        }
        return false;
    }
    /**
     * Method that creates a session for a registered user
     * @param userID Long with the user ID
     * @return UserSession with the registered user session
     */
    private UserSession createSessionForRegisteredUser(long userID){
        RegisteredUser registeredUser=getRegisteredUser(userID);
        return registeredUser!=null ? new UserSession(registeredUser.getID()) : null;
    }
    /**
     * Method that creates a session for an admin
     * @param userID Long with the user ID
     * @return UserSession with the admin session
     */
    private UserSession createSessionForAdmin(long userID){
        Admin admin=getAdmin(userID);
        return admin!=null ? new UserSession(admin.getID()) : null;
    }
    /**
     * Method that creates a session for a manager
     * @param userID Long with the user ID
     * @return UserSession with the manager session
     */
    private UserSession createSessionForManager(long userID){
        Manager manager=getManager(userID);
        return manager!=null ? new UserSession(manager.getID()) : null;
    }
    /**
     * Returns the SystemUser ID that is trying to login with certain credentials
     * <br>Throws an <b>IllegalAuthenticationService</b> if the user credentials are invalid,
     * or if the account is not activated
     * @param email String with the user email
     * @param password String with the user password
     * @return Long with the user ID of the user trying to login
     */
    private long getSystemUser(String email,String password){
        UserRepositoryImpl userRepo=new UserRepositoryImpl();
        long userID=userRepo.login(new Email(email),password);
        if(userID==-1){
            throw new AuthenticationException(INVALID_CREDENTIALS_MESSAGE
                ,AuthenticationExceptionCause.INVALID_CREDENTIALS);
        }
        if(!userRepo.find(userID).isUserActivated())
            throw new AuthenticationException(USER_ACCOUNT_NOT_ACTIVATED_MESSAGE
                    ,AuthenticationExceptionCause.NOT_ACTIVATED);
        return userID;
    }
    /**
     * Method that gets the administrator with a certain user ID
     * @param userID Long with the user ID
     * @return Admin with the admin that has a certain user ID, null if no admin was found
     */
    private Admin getAdmin(long userID){
        return new AdminRepositoryImpl().findByUserID(userID);
    }
    /**
     * Method that gets the manager with a certain user ID
     * @param userID Long with the user ID
     * @return Manager with the manager that has a certain user ID, null if no manager was found
     */
    private Manager getManager(long userID){
        return new ManagerRepositoryImpl().findByUserID(userID);
    }
    /**
     * Method that gets the RegisteredUser with a certain user ID
     * @param userID Long with the user ID
     * @return RegisteredUser with the registered user that has a certain user ID, 
     * null if no registered user was found
     */
    private RegisteredUser getRegisteredUser(long userID){
        return new RegisteredUserRepositoryImpl().findByUserID(userID);
    }
}
