package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.authz.CategoryManagementController;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Remove Category from Manager Window
 */
public class RemoveCategoryManagerWindow extends Window {

    /**
     * Window Caption
     */
    private static final String WINDOW_CAPTION =
            "Remover Categorias de Gestor";

    /**
     * TextField Description
     */
    private static final String TEXTFIELD_DESCRIPTION =
            "Caminho Categoria";

    /**
     * Manager not set error
     */
    private static final String MANAGER_NOT_SET_ERROR =
            "O gestor não existe";

    /**
     * No cat path selected error
     */
    private static final String NO_PATH_SELECTED_ERROR =
            "Por favor introduza o caminho da categoria";

    /**
     * Category with given path is not assigned to manager
     */
    private static final String CAT_NOT_EXISTS_ERROR =
            "Categoria não está associada ao gestor selecionado";

    /**
     * Default error message
     */
    private static final String DEFAULT_ERROR =
            "Erro a remover categoria do gestor";

    /**
     * Selected Manager Label
     */
    private static final String SELECTED_MANAGER_LBL =
            "Gestor selecionado: ";

    /**
     * Confirm Button Caption
     */
    private static final String CONFIRM_BTN_CAPTION =
            "Confirmar";

    /**
     * Controller class
     */
    private transient CategoryManagementController controller;

    /**
     * Main Window Layout
     */
    private VerticalLayout mainLayout;

    /**
     * Selected Manager Email
     */
    private String selectedManagerEmail;

    /**
     * Category Path TextField
     */
    private TextField catPathTextField;

    /**
     * Creates Window with a given manager email
     * @param selectedManagerEmail manager email
     */
    public RemoveCategoryManagerWindow(String selectedManagerEmail) {
        this.selectedManagerEmail = selectedManagerEmail;

        setProperties();
        instantiateComponents();
        prepareComponents();
    }

    /**
     * Sets window properties
     */
    private void setProperties() {
        setCaption(WINDOW_CAPTION);
        setModal(true);
        setResizable(false);
        center();
    }

    /**
     * Instantiates needed components
     */
    private void instantiateComponents() {
        mainLayout = new VerticalLayout();
        controller = new CategoryManagementController();
        catPathTextField = new TextField();
    }

    /**
     * Prepares window components
     */
    private void prepareComponents() {
        mainLayout.setSpacing(true);
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        mainLayout.setSizeUndefined();

        catPathTextField.setDescription(TEXTFIELD_DESCRIPTION);

        Button confirmBtn = new Button(CONFIRM_BTN_CAPTION);
        confirmBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (!controller.setManager(selectedManagerEmail)) {
                    Notification.show(MANAGER_NOT_SET_ERROR, Notification.Type.ERROR_MESSAGE);
                    return;
                }

                if (catPathTextField.getValue().trim().isEmpty()) {
                    Notification.show(NO_PATH_SELECTED_ERROR, Notification.Type.ERROR_MESSAGE);
                    return;
                }

                if (!controller.setPath(catPathTextField.getValue())) {
                    Notification.show(CAT_NOT_EXISTS_ERROR, Notification.Type.ERROR_MESSAGE);
                    return;
                }

                if (!controller.removeCategories()) {
                    Notification.show(DEFAULT_ERROR, Notification.Type.ERROR_MESSAGE);
                    return;
                }
            }
        });

        mainLayout.addComponents(new Label(SELECTED_MANAGER_LBL + selectedManagerEmail),
                catPathTextField, confirmBtn);

        setContent(mainLayout);
    }
}
