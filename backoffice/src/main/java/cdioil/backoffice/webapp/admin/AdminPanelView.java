package cdioil.backoffice.webapp.admin;

import cdioil.application.authz.AuthenticationController;
import cdioil.backoffice.webapp.MainLayoutView;
import cdioil.backoffice.webapp.authz.LoginView;
import cdioil.backoffice.webapp.utils.PopupNotification;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * @author <a href="https://github.com/freitzzz">freitzzz</a>
 */
public class AdminPanelView extends MainLayoutView implements View {

    /**
     * Constant that represents the current page view name
     */
    public static final String VIEW_NAME="Admin Panel";
    /**
     * Admin Manager Panel Button Caption
     */
    private static final String MANAGERS_BTN_CAPTION =
            "Gestores";
    /**
     * Dashboard Button Caption
     */
    private static final String DASHBOARD_BTN_CAPTION =
            "Dashboard";
    /**
     * Admin Users Panel Button
     */
    private static final String USERS_BTN_CAPTION =
            "Utilizadores";
    /**
     * Admin Market Struct Panel Button
     */
    private static final String MARKET_STRUCT_BTN_CAPTION =
            "Estrutura Mercadológica";

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
     * Dashboard Button
     */
    private Button dashboardBtn;
    
    /**
     * Admin Users Panel Button
     */
    private Button usersBtn;

    /**
     * Admin Manager Panel Button
     */
    private Button managersBtn;
    
    /**
     * Admin Market Struct Panel Button
     */
    private Button marketStructBtn;

    /**
     * Logout Button
     */
    private Button logoutBtn;

    /**
     * Current Authentication Controller
     */
    private AuthenticationController authenticationController;

    /**
     * Current View Navigator
     */
    private Navigator currentNavigator;

    /**
     * Builds a new AdminPanelView
     * @param authenticationController AuthenticationController with the current 
     * authentication controller
     */
    public AdminPanelView(AuthenticationController authenticationController){
        super();

        this.authenticationController=authenticationController;
        this.currentNavigator= UI.getCurrent().getNavigator();

        configuration();
    }

    /**
     * Prepares all components
     */
    private void configuration() {
        configureDashboardButton();
        configureUsersBtn();
        configureManagersBtn();
        configureMarketStructBtn();
        prepareLogoutButton();
        addButtonsToComponent();

        // The default right panel components
        rightPanel.setContent(new DashboardComponent());
    }

    /**
     * Prepares Home Button
     */
    private void configureDashboardButton() {
        dashboardBtn = new Button(DASHBOARD_BTN_CAPTION, VaadinIcons.DASHBOARD);
        dashboardBtn.addClickListener((Button.ClickEvent clickEvent) -> 
            setRightPanelContents(new DashboardComponent())
        );
    }

    /**
     * Prepares Users Button
     */
    private void configureUsersBtn() {
        usersBtn = new Button(USERS_BTN_CAPTION, VaadinIcons.USERS);
        usersBtn.addClickListener((Button.ClickEvent clickEvent) -> 
            setRightPanelContents(new UserManagementComponent())
        );
    }

    /**
     * Prepares Managers Button
     */
    private void configureManagersBtn() {
        managersBtn = new Button(MANAGERS_BTN_CAPTION, VaadinIcons.USER_STAR);
        managersBtn.addClickListener((Button.ClickEvent clickEvent) -> 
            setRightPanelContents(new ManagerManagementComponent())
        );
    }

    /**
     * Prepares Market Struct Button
     */
    private void configureMarketStructBtn() {
        marketStructBtn = new Button(MARKET_STRUCT_BTN_CAPTION, VaadinIcons.TREE_TABLE);
        marketStructBtn.addClickListener((Button.ClickEvent clickEvent) -> 
            setRightPanelContents(new MarketStructureComponent())
        );
    }

    /**
     * Prepares the logout button
     */
    private void prepareLogoutButton() {
        logoutBtn = new Button(LOGOUT_BUTTON_DESCRIPITION);
        logoutBtn.setIcon(VaadinIcons.EXIT);
        logoutBtn.addClickListener((Button.ClickEvent clickEvent) -> {
            if(authenticationController.logout()){
                PopupNotification.show(SUCCESSFUL_LOGOUT_TITLE,SUCCESSFUL_LOGOUT_MESSAGE
                        , Notification.Type.ASSISTIVE_NOTIFICATION, Position.TOP_RIGHT);
                currentNavigator.navigateTo(LoginView.VIEW_NAME);
            }else{
                PopupNotification.show(INVALID_LOGOUT_TITLE,INVALID_LOGOUT_MESSAGE
                        ,Notification.Type.ERROR_MESSAGE,Position.TOP_RIGHT);
            }
        });
    }

    /**
     * Adds desired buttons to left panel
     */
    private void addButtonsToComponent() {
        addNewButtonToLeftPanel(dashboardBtn);
        addNewButtonToLeftPanel(usersBtn);
        addNewButtonToLeftPanel(managersBtn);
        addNewButtonToLeftPanel(marketStructBtn);
        addNewButtonToLeftPanel(logoutBtn);
    }

}
