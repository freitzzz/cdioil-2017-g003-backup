package cdioil.backoffice.webapp.manager;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ManagerDashBoard extends Panel {

    private VerticalLayout mainLayout;

    public ManagerDashBoard() {
        prepareComponents();
    }

    private void prepareComponents() {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        this.setSizeFull();
        mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setSpacing(false);
        setContent(mainLayout);

        mainLayout.addComponent(prepareTitle());
        //mainLayout.addComponent(prepareMainPanel());
    }

    private Component prepareMainPanel() {
        return null;
    }

    private Component prepareTitle() {
        HorizontalLayout layout = new HorizontalLayout();

        layout.addStyleName("view-header");

        Label titleLabel = new Label("Dashboard");
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H1);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);

        layout.addComponent(titleLabel);
        return layout;
    }
}
