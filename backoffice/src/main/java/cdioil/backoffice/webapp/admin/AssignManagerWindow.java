package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.authz.AssignManagerController;
import com.vaadin.ui.Notification;

import java.util.List;

/**
 * The Assign Manager Popup Window
 */
public class AssignManagerWindow extends CategoryManagementWindow {

    /**
     * Controller Class
     */
    private transient AssignManagerController controller;

    /**
     * Creates a new Assign Manager Window
     *
     * @param selectedUserEmail selected user email from the user grid
     */
    public AssignManagerWindow(String selectedUserEmail) {
        super(selectedUserEmail);
        setCaption("Registar Gestor");

        instantiateWindowComponents();

        try {
            checkIfUserIsNotAdminOrManager();
        } catch (IllegalArgumentException e) {
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
            close();
            return;
        }
    }

    /**
     * Instantiates all needed components
     */
    private void instantiateWindowComponents() {
        controller = new AssignManagerController();
    }

    /**
     * Checks if user is already admin or manager
     */
    private void checkIfUserIsNotAdminOrManager() {
        if (controller.isAdmin(getSelectedUserEmail())
                || controller.isManager(getSelectedUserEmail())) {
            throw new IllegalArgumentException("User is already Manager/Admin");
        }
    }

    /**
     * Calls the controller class and assigns manager
     * @param categoriesPathList list of category paths
     */
    @Override
    protected void doConfirmAction(List<String> categoriesPathList) {
        try {
            controller.assignManager(getSelectedUserEmail(), categoriesPathList);
            Notification.show("Gestor registado com sucesso",
                    Notification.Type.TRAY_NOTIFICATION);
        } catch (IllegalArgumentException e) {
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }
}

