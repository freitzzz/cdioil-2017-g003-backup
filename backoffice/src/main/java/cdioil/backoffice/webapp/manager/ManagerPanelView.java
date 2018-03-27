package cdioil.backoffice.webapp.manager;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.UI;

/**
 * @author <a href="https://github.com/freitzzz">freitzzz</a>
 */
public class ManagerPanelView extends ManagerPanelDesign implements View {
    /**
     * Constant that represents the current page view name
     */
    public static final String VIEW_NAME="Manager Panel";
    /**
     * Current Navigator
     */
    private final Navigator navigator;
    /**
     * Builds a new ManagerPanelView
     */
    public ManagerPanelView(){
        navigator=UI.getCurrent().getNavigator();
    }
}
