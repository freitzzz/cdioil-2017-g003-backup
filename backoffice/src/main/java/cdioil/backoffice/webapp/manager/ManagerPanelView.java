package cdioil.backoffice.webapp.manager;

import cdioil.backoffice.webapp.DashboardLayoutView;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * @author <a href="https://github.com/freitzzz">freitzzz</a>
 */
public class ManagerPanelView extends DashboardLayoutView implements View {
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
     * Current Navigator
     */
    private final Navigator navigator;

    private Button dashboardBtn;

    private Button importBtn;

    private Button exportBtn;

    private static final String HOME_BTN_CAPTION =
            "Dashboard";

    private static final String IMPORT_BTN_CAPTION =
            "Importar";

    private static final String EXPORT_BTN_CAPTION =
            "Exportar";
    /**
     * Builds a new ManagerPanelView
     */
    public ManagerPanelView(){
        navigator=UI.getCurrent().getNavigator();
        configuration();
    }

    /**
     * Configures current page
     */
    private void configuration(){
        configureHomeButton();
        configureImportButton();
        configureExportButton();
    }

    private void configureHomeButton() {
        dashboardBtn = new Button(HOME_BTN_CAPTION, VaadinIcons.DASHBOARD);
        dashboardBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //TODO Dashboard/Home View
            }
        });
    }


    /**
     * Configures Import button
     */
    private void configureImportButton(){
        importBtn = new Button(IMPORT_BTN_CAPTION, VaadinIcons.SIGN_IN);
        importBtn.addClickListener(clickEvent -> {
            setRightPanelContents(new ManagerImportView());
        });
        addNewButtonToLeftPanel(importBtn);
    }

    /**
     * Configures Export button
     */
    private void configureExportButton(){
        exportBtn = new Button(EXPORT_BTN_CAPTION, VaadinIcons.SIGN_OUT);
        exportBtn.addClickListener(listener -> {
            try {
                setRightPanelContents(new ManagerExportView());
            } catch (IllegalStateException e) {
                Notification.show(ERROR_OPENING_EXPORT_TAB_TITLE, Notification.Type.ERROR_MESSAGE);
            }
        });
        addNewButtonToLeftPanel(exportBtn);
    }

}
