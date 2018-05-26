package cdioil.backoffice.webapp;

import cdioil.backoffice.webapp.utils.ImageUtils;
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
     * Constant that represents the path that holds the default image of an user
     */
    private static final String DEFAULT_USER_IMAGE_WEBINF_PATH="/WEB-INF/users/DEFAULT_USER_IMAGE.png";

    /**
     * Constructor
     */
    public MainLayoutView() {
        userAvatar.setSource(ImageUtils.imagePathAsResource(DEFAULT_USER_IMAGE_WEBINF_PATH));
    }

    /**
     * Adds a button to the left panel
     * @param btn desired button
     */
    protected void addNewButtonToLeftPanel(Button btn) {
        btn.setStyleName("v-button-borderless");
        btn.setWidth(100, Unit.PERCENTAGE);
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
