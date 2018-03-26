package cdioil.backoffice.webapp.authz;

import cdioil.backoffice.webapp.utils.PopupNotification;
import cdioil.domain.authz.Email;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * Login View class that extends LoginDesign class designed with Vaadin Design
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class LoginView extends LoginDesign implements View{
    /**
     * Constant that represents the current page view name
     */
    public static final String VIEW_NAME="LoginUI";
    /**
     * Constant that represents the name of the error dialog that pops up if the 
     * user tries to login with an invalid username
     */
    private static final String INVALID_USERNAME_NAME="Email Inválido!";
    /**
     * Constant that represents the message that pops up if the user 
     * tries to login with an invalid username
     */
    private static final String INVALID_USERNAME_DESCRIPTION="Por favor insira um email válido";
    /**
     * Constant that represents the name of the error dialog that pops up if the 
     * user tries to login with an invalid username
     */
    private static final String INVALID_LOGIN_CREDENTIALS_NAME="Credenciais Erradas!";
    /**
     * Constant that represents the message that pops up if the user 
     * tries to login with an invalid username
     */
    private static final String INVALID_LOGIN_CREDENTIALS_DESCRIPTION="As credenciais do login estão erradas";
    /**
     * Current Navigator
     */
    private final Navigator navigator;

    /**
     * Creates a new LoginView
     */
    public LoginView(){
        navigator= UI.getCurrent().getNavigator();
        configurateLogin();
    }
    /**
     * Configuares login
     */
    private void configurateLogin(){configurateLoginButton();}
    /**
     * Configurates login button
     */
    private void configurateLoginButton(){
        btnLogin.addClickListener((event)->{
            if(checkForUsername()){
                if(checkForLoginCredentials()){
                    //TO-DO: ADD NAVIGATOR FOR EACH TYPE OF BACKOFFICE USER (MANAGER + ADMIN)
                }else{
                    showInvalidLoginCredentialsNotification();
                }
            }else{
                showInvalidUsernameNotification();
            }
        });
    }
    /**
     * Checks if the username that the user is trying to login is valid
     * @return boolean true if the username is valid for login, false if not
     */
    private boolean checkForUsername(){
        try{
            new Email(txtUsername.getValue());
            return true;
        }catch(IllegalArgumentException e){
            return false;
        }
    }
    /**
     * Checks if the user credentials are valid and if it can login in the backoffice
     * @return boolean true if the user credentials are valid, false if not
     */
    private boolean checkForLoginCredentials(){
        //TO-DO: ADD SERVICE FOR LOGIN
        return false;
    }
    /**
     * Pops up a notification informing the user that the username is invalid
     */
    private void showInvalidUsernameNotification(){
        PopupNotification.show(INVALID_USERNAME_NAME,INVALID_USERNAME_DESCRIPTION
                    ,Notification.Type.ERROR_MESSAGE,Position.TOP_RIGHT);
    }
    /**
     * Pops up a notification informing the user that login credentials are invalid
     */
    private void showInvalidLoginCredentialsNotification(){
        PopupNotification.show(INVALID_LOGIN_CREDENTIALS_NAME,INVALID_LOGIN_CREDENTIALS_DESCRIPTION
                    ,Notification.Type.ERROR_MESSAGE,Position.TOP_RIGHT);
    }
}
