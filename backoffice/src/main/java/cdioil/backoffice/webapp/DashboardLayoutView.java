package cdioil.backoffice.webapp;

import cdioil.backoffice.webapp.utils.ImageUtils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

/**
 * The Main Web App View
 */
public class DashboardLayoutView extends DashboardLayoutDesign implements View {

    /**
     * Constructor
     */
    public DashboardLayoutView() {
        prepareButtons();
        userAvatar.setSource(ImageUtils.imagePathAsResource("/WEB-INF/users/DEFAULT_USER_IMAGE.png"));
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
     * Changes right panel's content
     * @param component new component
     */
    protected void setRightPanelContents(Component component) {
        rightPanel.setContent(component);
    }
}
