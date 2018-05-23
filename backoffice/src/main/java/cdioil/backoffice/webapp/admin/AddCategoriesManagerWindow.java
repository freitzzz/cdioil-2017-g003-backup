package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.authz.CategoryManagementController;
import com.vaadin.ui.Notification;

import java.util.List;

public class AddCategoriesManagerWindow extends CategoryManagementWindow {

    private static final String WINDOW_CAPTION =
            "Adicionar Categorias a Gestor";

    /**
     * Manager not set error
     */
    private static final String MANAGER_NOT_SET_ERROR =
            "O gestor não existe";

    private static final String NO_CATS_ADDED =
            "0 caminhos adicionados.";

    private static final String ADDED_NOTADDED_MESSAGE =
            "%d categorias adicionadas." +
                    " Categorias não adicionadas: %d\n%s";

    private CategoryManagementController controller;

    public AddCategoriesManagerWindow(String selectedEmail) {
        super(selectedEmail);
        instantiateComponents(selectedEmail);
        setCaption(WINDOW_CAPTION);
    }

    private void instantiateComponents(String selectedEmail) {
        controller = new CategoryManagementController();
        if (!controller.setManager(selectedEmail)) {
            Notification.show(MANAGER_NOT_SET_ERROR, Notification.Type.ERROR_MESSAGE);
            return;
        }
    }

    @Override
    protected void doConfirmAction(List<String> categoriesPathList) {
        int successCounter = 0;
        int errorCounter = 0;

        StringBuilder notAddedPaths = new StringBuilder();
        for (String path : categoriesPathList) {
            if (!controller.setPath(path)
                || !controller.addCategories()) {
                notAddedPaths.append("| " + path);
                errorCounter++;
            } else {
                successCounter++;
            }
        }

        if (successCounter == 0) {
            Notification.show(NO_CATS_ADDED, Notification.Type.ERROR_MESSAGE);
        } else {
            Notification.show(String.format(ADDED_NOTADDED_MESSAGE,
                    successCounter, errorCounter, notAddedPaths.toString()),
                    Notification.Type.TRAY_NOTIFICATION);
        }
    }
}
