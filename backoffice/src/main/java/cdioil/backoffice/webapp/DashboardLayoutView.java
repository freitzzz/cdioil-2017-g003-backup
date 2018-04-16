package cdioil.backoffice.webapp;

import cdioil.backoffice.webapp.utils.ImageUtils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 * The Main Web App View
 */
public class DashboardLayoutView extends DashboardLayoutDesign implements View {

    /**
     * Holds all buttons
     */
    private Accordion accordion;

    /**
     * Constructor
     */
    public DashboardLayoutView() {
        prepareAccordion();
        prepareButtons();
        userAvatar.setSource(ImageUtils.imagePathAsResource("/WEB-INF/users/DEFAULT_USER_IMAGE.png"));
    }

    /**
     * Configures Left Panel Accordion
     */
    private void prepareAccordion() {
        btnPanel.setSizeFull();
        accordion = new Accordion();
        accordion.setHeight("-1px");
        accordion.setWidth("100%");
        btnPanel.addComponent(accordion);
    }

    /**
     * Prepares all buttons
     */
    private void prepareButtons() {
        configureLogoutBtn();
    }

    /**
     * Prepares Logout Button
     */
    private void configureLogoutBtn() {
        btnLogout.setStyleName("v-button-borderless");
        btnLogout.setIcon(VaadinIcons.EXIT);
        btnLogout.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //TODO Implement logout
            }
        });
    }

    /**
     * Adds a button to the left panel
     * @param btn desired button
     */
    protected void addNewButtonToLeftPanel(Button btn) {
        btn.setStyleName("v-button-borderless");
        btn.setWidth("100%");
        btn.setHeightUndefined();
        btnPanel.addComponent(btn);
    }

    /**
     * Adds a new Tab to the Left Panel Accordion
     * @param tabCaption tab caption
     * @param layout set of buttons inside a vertical layout
     * @param icon icon
     */
    protected void addTabToAccordion(String tabCaption, VerticalLayout layout,
                                     Resource icon) {
        accordion.addTab(layout, tabCaption, icon);

    }

    /**
     * Changes right panel's content
     * @param component new component
     */
    protected void setRightPanelContents(Component component) {
        rightPanel.setContent(component);
    }

    /**
     * Creates a button with a specific style that matches the web app
     * layout and components
     * This method should be used to instantiate all buttons that belong to the
     * left panel
     *
     * @param btnCaption Button Caption
     * @return new button
     */
    protected Button createButton(String btnCaption) {
        Button btn = new Button(btnCaption);
        btn.setStyleName("v-button-borderless");
        btn.setWidth("100%");
        btn.setHeightUndefined();
        return btn;
    }
}
