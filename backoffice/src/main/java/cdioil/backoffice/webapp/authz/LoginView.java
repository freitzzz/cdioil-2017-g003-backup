package cdioil.backoffice.webapp.authz;

import cdioil.domain.authz.Email;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
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
                    //TO-DO: ADD NOTIFICATION POP-UP FOR VALID LOGIN
                }
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
            Notification.show(INVALID_USERNAME_NAME,INVALID_USERNAME_DESCRIPTION,Notification.Type.ERROR_MESSAGE);
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
}
