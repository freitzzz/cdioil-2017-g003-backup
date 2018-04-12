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

    private Button dashboardBtn;

    private static final String DASHBOARD_BTN_CAPTION =
            "Dashboard";

    private Button assignManagerButton;

    private static final String ASSIGNMANAGER_BTN_CAPTION =
            "Registar Gestor";

    /**
     * Builds a new AdminPanelView
     */
    public AdminPanelView(){
        navigator= UI.getCurrent().getNavigator();
        configuration();
        loadImages();
        prepareButtons();
    }

    private void configuration() {
        configureHomeButton();
        configureAssignManagerButton();

        // The default right panel components
        setRightPanelContents(null); //TODO should display dashboard
    }

    private void configureHomeButton() {
        dashboardBtn = new Button(DASHBOARD_BTN_CAPTION, VaadinIcons.DASHBOARD);
        dashboardBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //TODO create dashboard view
            }
        });
    }

    private void configureAssignManagerButton() {
        assignManagerButton = new Button(ASSIGNMANAGER_BTN_CAPTION, VaadinIcons.USER_CHECK);
        assignManagerButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                setRightPanelContents(new RegisterManagerView());
            }
        });
    }

    private void loadImages() {
    }

    private void prepareButtons() {
        initializeButtons();
        setButtonCaptions();
        setButtonIcons();
        setClickListeners();
        addButtonsToComponent();
    }

    private void initializeButtons() {
        assignManagerButton = new Button();
        assignManagerButton.setStyleName("valo-me");
    }

    private void setButtonCaptions() {
        assignManagerButton.setCaption("Registar Gestor");
    }

    private void setButtonIcons() {
        assignManagerButton.setIcon(VaadinIcons.DASHBOARD);
        btnLogout.setIcon(VaadinIcons.CLOSE_CIRCLE);
    }

    private void setClickListeners() {
        assignManagerButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                rightPanel.setContent(new RegisterManagerView());
            }
        });

        btnLogout.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                navigator.navigateTo(LoginView.VIEW_NAME);
            }
        });
    }

    private void addButtonsToComponent() {
        addNewButtonToLeftPanel(assignManagerButton);
    }

}
