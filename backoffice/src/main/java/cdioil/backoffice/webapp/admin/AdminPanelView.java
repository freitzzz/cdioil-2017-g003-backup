package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.webapp.DashboardLayoutView;
import cdioil.backoffice.webapp.authz.LoginView;
import cdioil.backoffice.webapp.utils.ImageUtils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

/**
 * @author <a href="https://github.com/freitzzz">freitzzz</a>
 */
public class AdminPanelView extends DashboardLayoutView implements View {

    /**
     * Constant that represents the current page view name
     */
    public static final String VIEW_NAME="Admin Panel";

    /**
     * Current Navigator
     */
    private final Navigator navigator;

    /**
     * Dashboard Button
     */
    private Button dashboardBtn;

    /**
     * Dashboard Button Caption
     */
    private static final String DASHBOARD_BTN_CAPTION =
            "Dashboard";

    /**
     * Assign Manager Button
     */
    private Button assignManagerButton;

    /**
     * Assign Manager Button Caption
     */
    private static final String ASSIGNMANAGER_BTN_CAPTION =
            "Registar Gestor";

    /**
     * Builds a new AdminPanelView
     */
    public AdminPanelView(){
        navigator= UI.getCurrent().getNavigator();
        configuration();
    }

    /**
     * Prepares all components
     */
    private void configuration() {
        configureHomeButton();
        configureAssignManagerButton();
        addButtonsToComponent();

        // The default right panel components
        setRightPanelContents(null); //TODO should display dashboard
    }

    /**
     * Prepares Home Button
     */
    private void configureHomeButton() {
        dashboardBtn = new Button(DASHBOARD_BTN_CAPTION, VaadinIcons.DASHBOARD);
        dashboardBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //TODO create dashboard view
            }
        });
    }

    /**
     * Prepares Assign Manager Button
     */
    private void configureAssignManagerButton() {
        assignManagerButton = new Button(ASSIGNMANAGER_BTN_CAPTION, VaadinIcons.USER_CHECK);
        assignManagerButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                setRightPanelContents(new RegisterManagerView());
            }
        });
    }

    /**
     * Adds desired buttons to left panel
     */
    private void addButtonsToComponent() {
        addNewButtonToLeftPanel(dashboardBtn);
        addNewButtonToLeftPanel(assignManagerButton);
    }

}
