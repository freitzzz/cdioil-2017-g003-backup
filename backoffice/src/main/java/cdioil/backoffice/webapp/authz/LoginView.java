package cdioil.backoffice.webapp.authz;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

/**
 * @author <a href="https://github.com/freitzzz">freitzzz</a>
 */
public class LoginView extends LoginDesign implements View{
    /**
     * Constant that represents the description of the username textfield
     */
    private static final String USERNAME_TOP_DESCRIPTION="Username";
    /**
     * Constant that represents the description of the password textfield
     */
    private static final String PASSWORD_TOP_DESCRIPTION="Password";
    /**
     * Constant that represents the description of the login button
     */
    private static final String LOGIN_DESCRIPTION="Login";
    /**
     * Constant that represents the current page view name
     */
    public static final String VIEW_NAME="LoginUI";
    /**
     * Current Navigator
     */
    private Navigator navigator;

    /**
     * Creates a new LoginView
     */
    public LoginView(){
        navigator= UI.getCurrent().getNavigator();
        configuration();
    }

    /**
     * Configurates current web-page componments
     */
    private void configuration(){
        username=new TextField(USERNAME_TOP_DESCRIPTION);
        password=new PasswordField(PASSWORD_TOP_DESCRIPTION);
        btnLoggin=new Button(LOGIN_DESCRIPTION);
    }
}
