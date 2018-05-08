package cdioil.backoffice.webapp.manager;

import cdioil.application.authz.AuthenticationController;
import cdioil.backoffice.webapp.MainLayoutView;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;

import com.vaadin.navigator.View;
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
     * Constant that represents the title of the notification that pops up when an error occures
     * while opening the Export Tab Page
     */
    private static final String ERROR_OPENING_EXPORT_TAB_TITLE="Ocorreu um erro ao abrir a pagina de exportação!";
    
    /**
     * Dashboard Button Caption
     */
    private static final String DASHBOARD_BTN_CAPTION =
            "Dashboard";

    /**
     * Import Button Caption
     */
    private static final String IMPORT_BTN_CAPTION =
            "Importar";

    /**
     * Export Button Caption
     */
    private static final String EXPORT_BTN_CAPTION =
            "Exportar";
    
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
     * Import Button
     */
    private Button importBtn;

    /**
     * Export Button
     */
    private Button exportBtn;

    /**
     * Builds a new ManagerPanelView
     * @param authenticationController AuthenticationController with the current 
     * authentication controller
     */
    public ManagerPanelView(AuthenticationController authenticationController){
        navigator=UI.getCurrent().getNavigator();
        this.authenticationController=authenticationController;
        configuration();
        setRightPanelContents(new DashboardComponent());
    }

    /**
     * Configures current page
     */
    private void configuration(){
        //configureHomeButton();
        //configureImportButton();
        //configureExportButton();
    }

    /**
     * Prepares Home Button
     */
    private void configureHomeButton() {
        dashboardBtn = new Button(DASHBOARD_BTN_CAPTION, VaadinIcons.DASHBOARD);
        dashboardBtn.addClickListener((Button.ClickEvent clickEvent) -> 
            setRightPanelContents(new DashboardComponent())
        );
        addNewButtonToLeftPanel(dashboardBtn);
    }

    /**
     * Prepares Import button
     */
    private void configureImportButton(){
        importBtn = new Button(IMPORT_BTN_CAPTION, VaadinIcons.SIGN_IN);
        importBtn.addClickListener(clickEvent -> 
            setRightPanelContents(null)
        );
        addNewButtonToLeftPanel(importBtn);
    }

    /**
     * Prepares Export button
     */
    private void configureExportButton(){
        exportBtn = new Button(EXPORT_BTN_CAPTION, VaadinIcons.SIGN_OUT);
        exportBtn.addClickListener(listener -> {
            try {
                setRightPanelContents(null);
            } catch (IllegalStateException e) {
                Notification.show(ERROR_OPENING_EXPORT_TAB_TITLE, Notification.Type.ERROR_MESSAGE);
            }
        });
        addNewButtonToLeftPanel(exportBtn);
    }

}
