package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.webapp.authz.LoginView;
import cdioil.backoffice.webapp.utils.ImageUtils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

/**
 * @author <a href="https://github.com/freitzzz">freitzzz</a>
 */
public class AdminPanelView extends AdminPanelDesign implements View {

    /**
     * Constant that represents the current page view name
     */
    public static final String VIEW_NAME="Admin Panel";

    /**
     * Current Navigator
     */
    private final Navigator navigator;

    private GridLayout wipComponent;

    /**
     * Builds a new AdminPanelView
     */
    public AdminPanelView(){
        navigator= UI.getCurrent().getNavigator();

        loadImages();

        setButtonIcons();

        leftPanelActionListeners();

        wipComponent = wipView;
    }

    private void setButtonIcons() {
        btnDashboard.setIcon(VaadinIcons.DASHBOARD);
        btnAddManager.setIcon(VaadinIcons.CLIPBOARD_USER);
        btn3.setIcon(VaadinIcons.USERS);
        btn4.setIcon(VaadinIcons.BAR_CHART);
        btn5.setIcon(VaadinIcons.COG);
        btnConta.setIcon(VaadinIcons.USER_CARD);
        btnLogout.setIcon(VaadinIcons.CLOSE_CIRCLE);
    }

    private void loadImages() {
        wipImage.setSource(ImageUtils.imagePathAsResource("/WEB-INF/backgrounds/WIP.png"));
        imgManager.setSource(ImageUtils.imagePathAsResource("/WEB-INF/users/DEFAULT_USER_IMAGE.png"));
    }

    private void leftPanelActionListeners() {
        btnDashboard.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                rightPanel.setContent(wipComponent);
            }
        });
        btnAddManager.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                rightPanel.setContent(new RegisterManagerView());
            }
        });
        btn3.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                rightPanel.setContent(wipComponent);
            }
        });
        btn4.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                rightPanel.setContent(wipComponent);
            }
        });
        btn5.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                rightPanel.setContent(wipComponent);
            }
        });
        btnLogout.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                navigator.navigateTo(LoginView.VIEW_NAME);
            }
        });
    }
}
