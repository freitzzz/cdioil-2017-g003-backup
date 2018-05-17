package cdioil.backoffice.webapp;

import cdioil.application.authz.AuthenticationController;
import cdioil.backoffice.webapp.authz.LoginView;
import cdioil.backoffice.webapp.utils.ImageUtils;
import cdioil.backoffice.webapp.utils.PopupNotification;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * The Main Web App View
 *
 * If a new web app view is created, it should extend this class,
 * to ensure layout coherence
 */
public class MainLayoutView extends MainLayoutDesign implements View {
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
     * Constant that represents the path that holds the default image of an user
     */
    private static final String DEFAULT_USER_IMAGE_WEBINF_PATH="/WEB-INF/users/DEFAULT_USER_IMAGE.png";
    /**
     * Constant that represents the description of the logout button
     */
    private static final String LOGOUT_BUTTON_DESCRIPITION="Logout";
    /**
     * Current Authentication Controller
     */
    private final AuthenticationController authenticationController;
    /**
     * Current View Navigator
     */
    private final Navigator currentNavigator;
    /**
     * Constructor
     * @param authenticationController AuthenticationController with the current authentication controller
     */
    public MainLayoutView(AuthenticationController authenticationController) {
        this.authenticationController=authenticationController;
        this.currentNavigator=UI.getCurrent().getNavigator();
        userAvatar.setSource(ImageUtils.imagePathAsResource(DEFAULT_USER_IMAGE_WEBINF_PATH));
        //TODO Temporary method
        prepareLogoutButton();
    }
    /**
     * Prepares the logout button
     */
    private void prepareLogoutButton() {
        Button logoutBtn = new Button(LOGOUT_BUTTON_DESCRIPITION);
        logoutBtn.setIcon(VaadinIcons.EXIT);
        logoutBtn.addClickListener((Button.ClickEvent clickEvent) -> {
            if(authenticationController.logout()){
                PopupNotification.show(SUCCESSFUL_LOGOUT_TITLE,SUCCESSFUL_LOGOUT_MESSAGE
                        ,Notification.Type.ASSISTIVE_NOTIFICATION,Position.TOP_RIGHT);
                currentNavigator.navigateTo(LoginView.VIEW_NAME);
            }else{
                PopupNotification.show(INVALID_LOGOUT_TITLE,INVALID_LOGOUT_MESSAGE
                        ,Notification.Type.ERROR_MESSAGE,Position.TOP_RIGHT);
            }
        });
        addNewButtonToLeftPanel(logoutBtn);
    }

    /**
     * Adds a button to the left panel
     * @param btn desired button
     */
    protected void addNewButtonToLeftPanel(Button btn) {
        btn.setStyleName("v-button-borderless");
        btn.setWidthUndefined();
        btn.setHeightUndefined();
        btnPanel.addComponent(btn);
    }

    /**
     * Changes right panel's content
     * @param layout new layout/content
     */
    protected final void setRightPanelContents(VerticalLayout layout) {
        rightPanel.setContent(layout);
    }
}
