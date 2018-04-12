package cdioil.backoffice.webapp;

import cdioil.backoffice.webapp.utils.ImageUtils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

public class DashboardLayoutView extends DashboardLayoutDesign implements View {

    public DashboardLayoutView() {
        prepareButtons();
        userAvatar.setSource(ImageUtils.imagePathAsResource("/WEB-INF/users/DEFAULT_USER_IMAGE.png"));
    }

    private void prepareButtons() {
        configureLogoutBtn();
    }

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

    protected void addNewButtonToLeftPanel(Button btn) {
        btn.setStyleName("v-button-borderless");
        btn.setWidth("100%");
        btn.setHeightUndefined();
        btnPanel.addComponent(btn);
    }

    protected void setRightPanelContents(Component component) {
        rightPanel.setContent(component);
    }
}
