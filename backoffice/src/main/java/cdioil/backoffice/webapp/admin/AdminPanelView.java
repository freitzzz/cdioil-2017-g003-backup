package cdioil.backoffice.webapp.admin;

import cdioil.application.authz.AuthenticationController;
import cdioil.backoffice.webapp.MainLayoutView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
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
     * Current Navigator
     */
    private final Navigator navigator;
    
    /**
     * Current Authentication controller
     */
    private final AuthenticationController authenticationController;

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
     * Builds a new AdminPanelView
     * @param authenticationController AuthenticationController with the current 
     * authentication controller
     */
    public AdminPanelView(AuthenticationController authenticationController){
        navigator= UI.getCurrent().getNavigator();
        this.authenticationController=authenticationController;
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
        addButtonsToComponent();

        // The default right panel components
        rightPanel.setContent(new DashboardComponent());
    }

    /**
     * Prepares Home Button
     */
    private void configureDashboardButton() {
        dashboardBtn = new Button(DASHBOARD_BTN_CAPTION, VaadinIcons.DASHBOARD);
        dashboardBtn.addClickListener((Button.ClickEvent clickEvent) -> {
            setRightPanelContents(new DashboardComponent());
        });
    }

    /**
     * Prepares Users Button
     */
    private void configureUsersBtn() {
        usersBtn = new Button(USERS_BTN_CAPTION, VaadinIcons.USERS);
        usersBtn.addClickListener((Button.ClickEvent clickEvent) -> {
            setRightPanelContents(new UserManagementComponent());
        });
    }

    /**
     * Prepares Managers Button
     */
    private void configureManagersBtn() {
        managersBtn = new Button(MANAGERS_BTN_CAPTION, VaadinIcons.USER_STAR);
        managersBtn.addClickListener((Button.ClickEvent clickEvent) -> {
            setRightPanelContents(new ManagerManagementComponent());
        });
    }

    /**
     * Prepares Market Struct Button
     */
    private void configureMarketStructBtn() {
        marketStructBtn = new Button(MARKET_STRUCT_BTN_CAPTION, VaadinIcons.TREE_TABLE);
        marketStructBtn.addClickListener((Button.ClickEvent clickEvent) -> {
            setRightPanelContents(new MarketStructureComponent());
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
    }

}
