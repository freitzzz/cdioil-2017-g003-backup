package cdioil.backoffice.webapp.manager;

import cdioil.application.authz.AuthenticationController;
import cdioil.backoffice.webapp.MainLayoutView;

import cdioil.backoffice.webapp.authz.LoginView;
import cdioil.backoffice.webapp.utils.PopupNotification;
import com.vaadin.icons.VaadinIcons;

import com.vaadin.navigator.View;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * @author <a href="https://github.com/freitzzz">freitzzz</a>
 */
public class ManagerPanelView extends MainLayoutView implements View {
    /**
     * Constant that represents the current page view name
     */
    public static final String VIEW_NAME="Manager Panel";

    /**
     * Constant that represents the description of the logout button
     */
    private static final String LOGOUT_BUTTON_DESCRIPITION = "Logout";

    /**
     * Constant that represents the title of the popup notification that shows up if
     * the user logout was successful
     */
    private static final String SUCCESSFUL_LOGOUT_TITLE="Logout bem sucedido";

    /**
     * Constant that represents the title of the popup notification that shows up if
     * the user logout was successful
     */
    private static final String SUCCESSFUL_LOGOUT_MESSAGE="A sua conta foi desconectada com successo!";
    /**
     * Constant that represents the title of the popup notification that shows up if
     * the user logout was not successful
     */
    private static final String INVALID_LOGOUT_TITLE="Erro no logout";

    /**
     * Constant that represents the title of the popup notification that shows up if
     * the user logout was successful
     */
    private static final String INVALID_LOGOUT_MESSAGE="Ocorreu um erro ao desconectar a sua conta";

    /**
     * Survey Button Caption
     */
    private static final String SURVEY_BTN_CAPTION =
            "Inquéritos";

    /**
     * Survey Button
     */
    private Button surveyButton;

    /**
     * Logout Button
     */
    private Button logoutBtn;

    /**
     * Authentication Controller Class
     */
    private AuthenticationController authenticationController;

    /**
     * Builds a new ManagerPanelView
     * authentication controller
     */
    public ManagerPanelView(AuthenticationController authenticationController){
        super();

        this.authenticationController = authenticationController;

        configuration();
        setRightPanelContents(new SurveyComponent(authenticationController));
    }

    /**
     * Configures current page
     */
    private void configuration(){
        configureSurveyButton();
        configureLogoutBtn();
    }

    /**
     * Configures Survey Button
     */
    private void configureSurveyButton() {
        surveyButton = new Button(SURVEY_BTN_CAPTION, VaadinIcons.CLIPBOARD_CHECK);
        surveyButton.addClickListener(clickEvent ->
            setRightPanelContents(new SurveyComponent(authenticationController))
        );
        addNewButtonToLeftPanel(surveyButton);
    }

    private void configureLogoutBtn() {
        logoutBtn = new Button(LOGOUT_BUTTON_DESCRIPITION);
        logoutBtn.setIcon(VaadinIcons.EXIT);
        logoutBtn.addClickListener((Button.ClickEvent clickEvent) -> {
            if(authenticationController.logout()){
                PopupNotification.show(SUCCESSFUL_LOGOUT_TITLE,SUCCESSFUL_LOGOUT_MESSAGE
                        , Notification.Type.ASSISTIVE_NOTIFICATION, Position.TOP_RIGHT);
                UI.getCurrent().getNavigator().navigateTo(LoginView.VIEW_NAME);
            }else{
                PopupNotification.show(INVALID_LOGOUT_TITLE,INVALID_LOGOUT_MESSAGE
                        ,Notification.Type.ERROR_MESSAGE,Position.TOP_RIGHT);
            }
        });

        addNewButtonToLeftPanel(logoutBtn);
    }
}
