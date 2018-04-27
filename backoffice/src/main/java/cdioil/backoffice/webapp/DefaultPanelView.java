package cdioil.backoffice.webapp;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * The default class for creating a right panel view
 *
 * All views that are to be displayed inside the right panel should extend
 * this class, to ensure all views are coherent with each other
 */
public abstract class DefaultPanelView extends VerticalLayout {

    /**
     * The top layout
     *
     * Contains the header and other possible items
     */
    protected HorizontalLayout headerLayout;

    /**
     * The title label
     */
    private Label headerTitleLabel;

    /**
     * Given a title, constructs an instance of this class
     * @param headerLabel title
     */
    public DefaultPanelView(String headerLabel) {
        prepareComponents(headerLabel);
    }

    /**
     * Prepares all components
     * @param headerLabel header title
     */
    private void prepareComponents(String headerLabel) {
        instantiateComponents();
        prepareHeader(headerLabel);
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSpacing(true);
        setSizeFull();
    }

    /**
     * Instantiates all needed components
     */
    private void instantiateComponents() {
        headerLayout = new HorizontalLayout();
    }

    /**
     * Prepares the header component
     * @param headerLabel header title
     */
    private void prepareHeader(String headerLabel) {
        headerLayout.setWidth("100%");
        headerLayout.addStyleName("viewheader");

        headerTitleLabel = new Label(headerLabel);
        headerTitleLabel.setSizeUndefined();
        headerTitleLabel.addStyleName(ValoTheme.LABEL_H1);
        headerTitleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);

        headerLayout.addComponent(headerTitleLabel);
        addComponent(headerLayout);
    }
}
