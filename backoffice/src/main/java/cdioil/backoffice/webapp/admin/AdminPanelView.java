package cdioil.backoffice.webapp.admin;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
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
    }
}
