package cdioil.backoffice.webapp.manager;

import cdioil.backoffice.webapp.DefaultPanelView;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * The Dashboard view of the Manager Panel
 */
public class DashboardComponent extends DefaultPanelView {

    /**
     * Constructs an instance of the Manager's Dashboard
     */
    public DashboardComponent() {
        super("Dashboard");
        prepareComponents();
    }

    /**
     * Prepares components
     */
    private void prepareComponents() {
    }
}
