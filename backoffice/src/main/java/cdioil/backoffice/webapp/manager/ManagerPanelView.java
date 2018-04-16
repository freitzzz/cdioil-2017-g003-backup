package cdioil.backoffice.webapp.manager;

import cdioil.backoffice.webapp.DashboardLayoutView;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;

import com.vaadin.navigator.View;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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
        configureAccordion();
        //configureHomeButton();
        //configureImportButton();
        //configureExportButton();
    }

    private void configureAccordion() {
        VerticalLayout vl = new VerticalLayout();
        vl.setHeight(-1, Unit.PIXELS);
        vl.setWidth(100.0f, Unit.PERCENTAGE);
        Button b1 = createButton("Importar");
        Button b2 = createButton("Exportar");

        Button b3 = createButton("Criar Inquerito");
        Button b4 = createButton("Eliminar Inquerito");
        Button b5 = createButton("Ver Inqueritos");

        vl.addComponentsAndExpand(b1, b2);
        addTabToAccordion("Importar", vl, VaadinIcons.DASHBOARD);

        vl = new VerticalLayout();
        vl.setHeight(-1, Unit.PIXELS);
        vl.setWidth(100.0f, Unit.PERCENTAGE);
        vl.addComponentsAndExpand(b3, b4, b5);
        addTabToAccordion("Inqueritos", vl, VaadinIcons.BAR_CHART);
    }

    /**
     * Prepares Home Button
     */
    private void configureHomeButton() {
        dashboardBtn = new Button(DASHBOARD_BTN_CAPTION, VaadinIcons.DASHBOARD);
        dashboardBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //TODO Dashboard/Home View
            }
        });
    }

    /**
     * Prepares Import button
     */
    private void configureImportButton(){
        importBtn = new Button(IMPORT_BTN_CAPTION, VaadinIcons.SIGN_IN);
        importBtn.addClickListener(clickEvent -> {
            setRightPanelContents(new ManagerImportView());
        });
        addNewButtonToLeftPanel(importBtn);
    }

    /**
     * Prepares Export button
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
