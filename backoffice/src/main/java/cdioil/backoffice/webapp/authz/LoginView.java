package cdioil.backoffice.webapp.authz;

import cdioil.application.authz.AuthenticationController;
import cdioil.application.domain.authz.exceptions.AuthenticationException;
import cdioil.backoffice.webapp.admin.AdminPanelView;
import cdioil.backoffice.webapp.manager.ManagerPanelView;
import cdioil.backoffice.webapp.utils.ImageUtils;
import cdioil.backoffice.webapp.utils.PopupNotification;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Login View class that extends LoginDesign class designed with Vaadin Design
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
     * Constant that represents the title of the popup notification of the activation code
     */
    private static final String ACTIVATION_CODE_TITLE="Código Ativação";
    /**
     * Constant that represents the message of the error dialog that pops up if the user inserts a 
     * invalid activation code
     */
    private static final String INVALID_ACTIVATION_MESSAGE="O código de ativação que inseriu é inválido!";
    /**
     * Constant that represents the message of the information dialog that pops up if the user inserts a 
     * valid activation code
     */
    private static final String VALID_ACTIVATION_MESSAGE="A sua conta foi ativada com sucesso!";
    /**
     * Constant that represents the message that pops up if the user
     * tries to login with an invalid user for backoffice
     */
    private static final String INVALID_USER_LOGIN_DESCRIPTION = "Não tem permissões para aceder à plataforma";
    /**
     * Constant that represents the background image path
     */
    private static final String BACKGROUND_IMAGE_PATH ="/WEB-INF/logos/Feedback_Monkey_Full.png";
    /**
     * Current Navigator
     */
    private final Navigator navigator;
    /**
     * Current Authentication Controller
     */
    private final AuthenticationController authenticationController;

    /**
     * Popup View for the Register User button
     */
    private PopupView registeredUserPopupView;
    /**
     * PopupView with the current act
     */
    private PopupView sendActivationCodePopupView;

    /**
     * Creates a new LoginView
     */
    public LoginView() {
        navigator = UI.getCurrent().getNavigator();
        authenticationController=new AuthenticationController();
        prepareComponents();
    }
    /**
     * Prepares current view components
     */
    private void prepareComponents() {
        txtUsername.setIcon(VaadinIcons.USER);
        txtPassword.setIcon(VaadinIcons.PASSWORD);
        txtUsername.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        txtPassword.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        setLogo();
        configurateLogin();
    }

    /**
     * Configures login
     */
    private void configurateLogin() {
        configureRegisterUserPopupView();
        configurateLoginButton();
        configureSignupButton();
        configureSendActivationCodePopupView();
    }

    /**
     * Configures the Register User Popup View
     */
    private void configureRegisterUserPopupView() {
        registeredUserPopupView = new PopupView(new RegisterPopupViewContent());
        registeredUserPopupView.setHideOnMouseOut(false);
        panelLoginLayout.addComponentsAndExpand(registeredUserPopupView);
    }
    /**
     * Configures the send activation code popup view
     */
    private void configureSendActivationCodePopupView(){
        sendActivationCodePopupView=new PopupView(new ActivationCodePopupViewContent());
        sendActivationCodePopupView.setHideOnMouseOut(false);
        panelLoginLayout.addComponentsAndExpand(sendActivationCodePopupView);
        configureSendActivationCodeButton();
    }
    /**
     * Configures the send activation code button
     */
    private void configureSendActivationCodeButton(){
        Button btnSendActivationCode=((ActivationCodePopupViewContent)sendActivationCodePopupView.getContent()).getActivationCodeButton();
        btnSendActivationCode.addClickListener((sendActivationCodeButtonEvent)->{
            if(authenticationController.activateAccount(txtUsername.getValue(),txtPassword.getValue()
                    ,((ActivationCodePopupViewContent)sendActivationCodePopupView.getContent()).getActivationCode())){
                showValidActivationCodeNotification();
            }else{
                showInvalidActivationCodeNotification();
            }
        });
    }
    /**
     * Gets logo from resource
     */
    private void setLogo() {
        logo.setSource(ImageUtils.imagePathAsResource(BACKGROUND_IMAGE_PATH));
    }

    /**
     * Configurates login button
     */
    private void configurateLoginButton() {
        btnLogin.addClickListener((loginButtonClickEvent) -> {
            if(tryToLogin()){
                if(authenticationController.canAccessAdminBackoffice()){
                    navigator.addView(AdminPanelView.VIEW_NAME,new AdminPanelView(authenticationController));
                    navigator.navigateTo(AdminPanelView.VIEW_NAME);
                }else if(authenticationController.canAccessManagerBackoffice()){
                    navigator.addView(ManagerPanelView.VIEW_NAME,new ManagerPanelView(authenticationController));
                    navigator.navigateTo(ManagerPanelView.VIEW_NAME);
                }else{
                    showInvalidUserNotification();
                }
            }
        });
    }

    /**
     * Configures Signup button
     */
    private void configureSignupButton() {
        buttonsLayout.addComponentsAndExpand(new PopupView(new RegisterPopupViewContent()));
        btnSignUp.addClickListener((Button.ClickEvent clickEvent) -> 
            registeredUserPopupView.setPopupVisible(true)
        );
    }
    /**
     * Method that tries to login into the webapp
     * @return boolean true if the user logged in succesfully, false if not
     */
    private boolean tryToLogin(){
        try{
            return authenticationController.login(txtUsername.getValue(),txtPassword.getValue());
        }catch(AuthenticationException authenticationException){
            treatAuthenticationFailure(authenticationException);
        }catch(IllegalArgumentException invalidFieldsException){
            showInvalidUsernameNotification();
        }
        return false;
    }
    /**
     * Treats the Authentication failure that ocures if a AuthenticationException is thrown
     * @param authenticationException AuthenticationException with the exception thrown if the authentication failed
     */
    private void treatAuthenticationFailure(AuthenticationException authenticationException){
        if(authenticationException.getAuthenticatioExceptionCause()
                .equals(AuthenticationException.AuthenticationExceptionCause.INVALID_CREDENTIALS)){
            showInvalidLoginCredentialsNotification();
        }else{
            showActivationCodePopup();
        }
    }
    /**
     * Pops up a notification informing the user that login credentials are invalid
     */
    private void showInvalidLoginCredentialsNotification() {
        PopupNotification.show(INVALID_LOGIN_CREDENTIALS_NAME, INVALID_LOGIN_CREDENTIALS_DESCRIPTION
                , Notification.Type.ERROR_MESSAGE, Position.TOP_RIGHT);
    }
    /**
     * Pops up a notification informing the user that the username is invalid
     */
    private void showInvalidUsernameNotification() {
        PopupNotification.show(INVALID_USERNAME_NAME, INVALID_USERNAME_DESCRIPTION
                , Notification.Type.ERROR_MESSAGE, Position.TOP_RIGHT);
    }
    
    /**
     * Pops up a notification informing the user that he is not valid to access backoffice
     */
    private void showInvalidUserNotification() {
        PopupNotification.show(INVALID_USER_LOGIN_NAME, INVALID_USER_LOGIN_DESCRIPTION
                , Notification.Type.ERROR_MESSAGE, Position.TOP_RIGHT);
    }
    /**
     * Pops up a notification informing the user the activation code that he inserted is invalid
     */
    private void showValidActivationCodeNotification(){
        PopupNotification.show(ACTIVATION_CODE_TITLE,VALID_ACTIVATION_MESSAGE,Notification.Type.ASSISTIVE_NOTIFICATION,Position.TOP_RIGHT);
    }
    /**
     * Pops up a notification informing the user the activation code that he inserted is invalid
     */
    private void showInvalidActivationCodeNotification(){
        PopupNotification.show(ACTIVATION_CODE_TITLE,INVALID_ACTIVATION_MESSAGE,Notification.Type.ERROR_MESSAGE,Position.TOP_RIGHT);
    }
    /**
     * Shows a popup dialog that asks the user about the activation code
     */
    private void showActivationCodePopup(){
        sendActivationCodePopupView.setPopupVisible(true);
    }
}
