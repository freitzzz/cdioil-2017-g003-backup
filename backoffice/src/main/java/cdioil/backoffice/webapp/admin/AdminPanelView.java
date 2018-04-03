package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.webapp.utils.ImageUtils;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

/**
 * @author <a href="https://github.com/freitzzz">freitzzz</a>
 */
public class AdminPanelView extends AdminPanelDesign implements View{
    /**
     * Constant that represents the current page view name
     */
    public static final String VIEW_NAME="Admin Panel";
    /**
     * Current Navigator
     */
    private final Navigator navigator;
    /**
     * Builds a new AdminPanelView
     */
    public AdminPanelView(){
        navigator= UI.getCurrent().getNavigator();

        loadImages();

        leftPanelActionListeners();
    }

    private void loadImages() {
        logoHomeImg.setSource(ImageUtils.imagePathAsResource("/WEB-INF/logos/Feedback_Monkey_Full.png"));
    }

    private void leftPanelActionListeners() {
        btnAddManager.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                rightPanel.setContent(new RegisterManagerView());
            }
        });
    }
}
