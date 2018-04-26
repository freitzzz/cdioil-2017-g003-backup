package cdioil.backoffice.webapp;

import cdioil.backoffice.webapp.admin.AdminPanelView;
import cdioil.backoffice.webapp.authz.LoginView;
import cdioil.backoffice.webapp.manager.ManagerExportView;
import cdioil.backoffice.webapp.manager.ManagerImportView;
import cdioil.backoffice.webapp.manager.ManagerPanelView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
    /**
     * Current Navigator
     */
    private Navigator navigator;
    /**
     * Initites Vaadin Web Application
     * @param vaadinRequest Vaadin Request
     */
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        navigator=new Navigator(this,this);
        registerViews();
    }

    /**
     * Registers WebApp views to the current navigator
     */
    private void registerViews(){
        navigator.addView(LoginView.VIEW_NAME,LoginView.class);
        navigator.addView(AdminPanelView.VIEW_NAME,AdminPanelView.class);
        navigator.addView(ManagerPanelView.VIEW_NAME,ManagerPanelView.class);
        navigator.navigateTo(LoginView.VIEW_NAME);
    }
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
