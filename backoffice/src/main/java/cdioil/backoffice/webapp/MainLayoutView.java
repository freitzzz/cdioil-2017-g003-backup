package cdioil.backoffice.webapp;

import cdioil.backoffice.webapp.utils.ImageUtils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

/**
 * The Main Web App View
 *
 * If a new web app view is created, it should extend this class,
 * to ensure layout coherence
 */
public class MainLayoutView extends MainLayoutDesign implements View {

    /**
     * Constructor
     */
    public MainLayoutView() {
        userAvatar.setSource(ImageUtils.imagePathAsResource("/WEB-INF/users/DEFAULT_USER_IMAGE.png"));
        //TODO Temporary method
        prepareLogoutButton();
    }

    private void prepareLogoutButton() {
        Button logoutBtn = new Button("Logout");
        logoutBtn.setIcon(VaadinIcons.EXIT);
        logoutBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //TODO Implement LOGOUT
            }
        });
        addNewButtonToLeftPanel(logoutBtn);
    }

    /**
     * Adds a button to the left panel
     * @param btn desired button
     */
    protected void addNewButtonToLeftPanel(Button btn) {
        btn.setStyleName("v-button-borderless");
        btn.setWidthUndefined();
        btn.setHeightUndefined();
        btnPanel.addComponent(btn);
    }

    /**
     * Changes right panel's content
     * @param layout new layout/content
     */
    protected final void setRightPanelContents(VerticalLayout layout) {
        rightPanel.setContent(layout);
    }
}
