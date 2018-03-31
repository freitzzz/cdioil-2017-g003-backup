package cdioil.backoffice.webapp.manager;

import cdioil.backoffice.webapp.authz.LoginView;

import cdioil.backoffice.webapp.utils.ImageUtils;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import static com.vaadin.ui.TabSheet.Tab;
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
     * Constant that represents the default name used for the Manager name
     */
    private static final String DEFAULT_MANAGER_NAME ="Manager";
    /**
     * Constant that represents the default image resource for the Manager photo
     */
    private static final String DEFAULT_IMAGE_RESOURCE="/WEB-INF/users/DEFAULT_USER_IMAGE.png";
    /**
     * Current Navigator
     */
    private final Navigator navigator;
    /**
     * Current Tab for the "Importar Button"
     */
    private Tab currentImportarTab;
    /**
     * Builds a new ManagerPanelView
     */
    public ManagerPanelView(){
        navigator=UI.getCurrent().getNavigator();
        configuration();
    }

    /**
     * Configures current page
     */
    private void configuration(){
        configureLogoutButton();
        configureManagerImage();
        configureManagerName();
        configureImportButton();
    }

    /**
     * Configures Manager Image
     */
    private void configureManagerImage(){
        imgManager.setSource(ImageUtils.imagePathAsResource(DEFAULT_IMAGE_RESOURCE));
    }

    /**
     * Configures Manager Name
     */
    private void configureManagerName(){
        lblName.setValue(DEFAULT_MANAGER_NAME);
    }

    /**
     * Configures Logout button
     */
    private void configureLogoutButton(){
        btnLogout.addClickListener((clickEvent) ->{
            navigator.navigateTo(LoginView.VIEW_NAME);
        });
    }

    /**
     * Configures Import button
     */
    private void configureImportButton(){
        btnImportar.addClickListener(clickEvent -> {
            if(currentImportarTab==null||tabSheetOptions.getTabPosition(currentImportarTab)<0){
                currentImportarTab=tabSheetOptions.addTab(new ManagerImportView(),btnImportar.getCaption());
                currentImportarTab.setClosable(true);
                tabSheetOptions.setSelectedTab(currentImportarTab);
            }
        });
    }

}
