package cdioil.backoffice.webapp.authz;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Image;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * !! DO NOT EDIT THIS FILE !!
 * <p>
 * This class is generated by Vaadin Designer and will be overwritten.
 * <p>
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class LoginDesign extends CssLayout {
    protected HorizontalLayout horizontalLayout;
    protected Image logo;
    protected Panel panelLogin;
    protected VerticalLayout panelLoginLayout;
    protected Label lblUsername;
    protected TextField txtUsername;
    protected Label lblPassword;
    protected PasswordField txtPassword;
    protected HorizontalLayout buttonsLayout;
    protected Button btnLogin;
    protected Button btnSignUp;

    public LoginDesign() {
        Design.read(this);
    }
}
