package cdioil.backoffice.webapp.authz;

import cdioil.backoffice.webapp.admin.AdminPanelView;
import cdioil.backoffice.webapp.manager.ManagerPanelView;
import cdioil.backoffice.webapp.utils.ImageUtils;
import cdioil.backoffice.webapp.utils.PopupNotification;
import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.persistence.impl.AdminRepositoryImpl;
import cdioil.persistence.impl.ManagerRepositoryImpl;
import cdioil.persistence.impl.UserRepositoryImpl;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * Login View class that extends LoginDesign class designed with Vaadin Design
 *
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class LoginView extends LoginDesign implements View {
    /**
     * Constant that represents the current page view name
     */
    public static final String VIEW_NAME = "LoginUI";
    /**
     * Constant that represents the name of the error dialog that pops up if the
     * user tries to login with an invalid username
     */
    private static final String INVALID_USERNAME_NAME = "Email Inválido!";
    /**
     * Constant that represents the message that pops up if the user
     * tries to login with an invalid username
     */
    private static final String INVALID_USERNAME_DESCRIPTION = "Por favor insira um email válido";
    /**
     * Constant that represents the name of the error dialog that pops up if the
     * user tries to login with invalid credentials
     */
    private static final String INVALID_LOGIN_CREDENTIALS_NAME = "Credenciais Erradas!";
    /**
     * Constant that represents the message that pops up if the user
     * tries to login with invalid credentials
     */
    private static final String INVALID_LOGIN_CREDENTIALS_DESCRIPTION = "As credenciais do login estão erradas";
    /**
     * Constant that represents the name of the error dialog that pops up if the
     * user tries to login with an invalid user for backoffice
     */
    private static final String INVALID_USER_LOGIN_NAME = "Acesso Invalido!";
    /**
     * Constant that represents the message that pops up if the user
     * tries to login with an invalid user for backoffice
     */
    private static final String INVALID_USER_LOGIN_DESCRIPTION = "Não tem permissões para aceder à plataforma";
    /**
     * Current Navigator
     */
    private final Navigator navigator;
    /**
     * Admin that just logged in
     */
    private Admin admin;
    /**
     * Manager that just logged in
     */
    private Manager manager;

    /**
     * Creates a new LoginView
     */
    public LoginView() {
        navigator = UI.getCurrent().getNavigator();
        setLogo();
        configurateLogin();
    }

    /**
     * Configuares login
     */
    private void configurateLogin() {
        configurateLoginButton();
    }

    /**
     * Gets logo from resource
     */
    private void setLogo() {
        logo.setSource(ImageUtils.imagePathAsResource("/WEB-INF/logos/Feedback_Monkey_Full.png"));
    }

    /**
     * Configurates login button
     */
    private void configurateLoginButton() {
        btnLogin.addClickListener((event) -> {
            if (checkForUsername()) {
                if (checkForLoginCredentials()) {
                    if (admin != null) {
                        navigator.navigateTo(AdminPanelView.VIEW_NAME);
                    } else if (manager != null) {
                        navigator.navigateTo(ManagerPanelView.VIEW_NAME);
                    } else {
                        showInvalidUserNotification();
                    }

                } else {
                    showInvalidLoginCredentialsNotification();
                }
            } else {
                showInvalidUsernameNotification();
            }
        });
    }

    /**
     * Checks if the username that the user is trying to login is valid
     *
     * @return boolean true if the username is valid for login, false if not
     */
    private boolean checkForUsername() {
        try {
            new Email(txtUsername.getValue());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Checks if the user credentials are valid and if it can login in the backoffice
     *
     * @return Admin if the
     */
    private boolean checkForLoginCredentials() {
        long userID = new UserRepositoryImpl().login(new Email(txtUsername.getValue())
                , txtPassword.getValue());
        if (userID == -1) return false;
        admin = new AdminRepositoryImpl().findByUserID(userID);
        if (admin == null) manager = new ManagerRepositoryImpl().findByUserID(userID);
        return true;
    }

    /**
     * Pops up a notification informing the user that the username is invalid
     */
    private void showInvalidUsernameNotification() {
        PopupNotification.show(INVALID_USERNAME_NAME, INVALID_USERNAME_DESCRIPTION
                , Notification.Type.ERROR_MESSAGE, Position.TOP_RIGHT);
    }

    /**
     * Pops up a notification informing the user that login credentials are invalid
     */
    private void showInvalidLoginCredentialsNotification() {
        PopupNotification.show(INVALID_LOGIN_CREDENTIALS_NAME, INVALID_LOGIN_CREDENTIALS_DESCRIPTION
                , Notification.Type.ERROR_MESSAGE, Position.TOP_RIGHT);
    }

    /**
     * Pops up a notification informing the user that he is not valid to access backoffice
     */
    private void showInvalidUserNotification() {
        PopupNotification.show(INVALID_USER_LOGIN_NAME, INVALID_USER_LOGIN_DESCRIPTION
                , Notification.Type.ERROR_MESSAGE, Position.TOP_RIGHT);
    }
}
