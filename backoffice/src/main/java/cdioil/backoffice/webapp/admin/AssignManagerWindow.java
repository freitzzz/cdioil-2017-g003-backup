package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.authz.AssignManagerController;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * The Assign Manager Popup Window
 */
public class AssignManagerWindow extends Window {

    /**
     * Controller Class
     */
    private transient AssignManagerController controller;

    /**
     * Window's Main Layout
     */
    private VerticalLayout mainLayout;

    /**
     * List of visible category rows
     */
    private List<CategoryRow> selectedCategoriesList;

    /**
     * Categories Panel Layout
     */
    private VerticalLayout categoriesLayout;

    /**
     * Selected user email
     */
    private String selectedUserEmail;

    /**
     * Add Row Button
     */
    private Button addBtn;

    /**
     * Delete Row Button
     */
    private Button delBtn;

    /**
     * Creates a new Assign Manager Window
     * @param selectedUserEmail selected user email from the user grid
     */
    public AssignManagerWindow(String selectedUserEmail) {
        this.selectedUserEmail = selectedUserEmail;
        instantiateComponents();

        checkIfUserIsNotAdminOrManager();

        setProperties();
        prepareComponents();
    }

    /**
     * Instantiates all needed components
     */
    private void instantiateComponents() {
        controller = new AssignManagerController();
        mainLayout = new VerticalLayout();
        selectedCategoriesList = new ArrayList<>();
    }

    /**
     * Checks if user is already admin or manager
     */
    private void checkIfUserIsNotAdminOrManager() {
        if (controller.isAdmin(selectedUserEmail)
                || controller.isManager(selectedUserEmail)) {
            throw new IllegalArgumentException("User is already Manager/Admin");
        }
    }

    /**
     * Sets window properties
     */
    private void setProperties() {
        setModal(true);
        setResizable(false);
        setCaption("Registar Gestor");
        setWidth(550, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
        center();
    }

    /**
     * Prepares Window Components
     */
    private void prepareComponents() {
        Label selectedUserLabel = new Label("Utilizador Selecionado: " + selectedUserEmail);

        Component botButtons = prepareBotButtons();
        mainLayout.addComponents(selectedUserLabel, prepareCategoriesPanel(), botButtons);
        mainLayout.setComponentAlignment(botButtons, Alignment.MIDDLE_RIGHT);

        setContent(mainLayout);
    }

    /**
     * Prepares the categories Panel
     * @return
     */
    private Panel prepareCategoriesPanel() {
        categoriesLayout = new VerticalLayout();
        categoriesLayout.setSizeFull();

        // Create 1st row
        CategoryRow firstRow = new CategoryRow(selectedCategoriesList.size());
        categoriesLayout.addComponent(firstRow);
        selectedCategoriesList.add(firstRow);

        return new Panel(categoriesLayout);
    }

    /**
     * Prepares Add, Delete and Confirm Buttons
     * @return Horizontal layout containing all buttons
     */
    private HorizontalLayout prepareBotButtons() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setSizeUndefined();
        hl.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);

        addBtn = new Button(VaadinIcons.PLUS);
        addBtn.setDescription("Adiciona Categoria");
        addBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                final int idx = selectedCategoriesList.size();
                CategoryRow newRow = new CategoryRow(idx);
                categoriesLayout.addComponentsAndExpand(newRow);
                selectedCategoriesList.add(newRow);
                delBtn.setEnabled(true);
            }
        });

        delBtn = new Button(VaadinIcons.MINUS);
        delBtn.setDescription("Remove ultima categoria");
        delBtn.setEnabled(false);
        delBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                final int idx = selectedCategoriesList.size() - 1;
                CategoryRow lastRow = selectedCategoriesList.get(idx);
                categoriesLayout.removeComponent(lastRow);
                selectedCategoriesList.remove(lastRow);
                if (idx == 0) {
                    delBtn.setEnabled(false);
                }
            }
        });

        Button confirmButton = new Button("Confirmar");
        confirmButton.setDescription("Regista Gestor com categorias definidas");
        confirmButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                List<String> categoriesPath = new ArrayList<>();
                for (CategoryRow cr : selectedCategoriesList) {
                    String inputValue = cr.getInputValue();
                    if (!inputValue.isEmpty()) {
                        categoriesPath.add(inputValue);
                    }
                }

                if (categoriesPath.isEmpty()) {
                    Notification.show("Introduza pelo menos uma categoria.",
                            Notification.Type.ERROR_MESSAGE);
                    return;
                }

                try {
                    controller.assignManager(selectedUserEmail, categoriesPath);
                } catch (IllegalArgumentException e) {
                    Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
                    e.printStackTrace();
                }

                Notification.show("Gestor registado com sucesso",
                        Notification.Type.TRAY_NOTIFICATION);
            }
        });

        hl.addComponents(addBtn, delBtn, confirmButton);
        hl.setSpacing(true);
        return hl;
    }
}

/**
 * Represents a Category Row (Label + TextField)
 */
class CategoryRow extends HorizontalLayout {

    /**
     * Category Label
     */
    private Label categoryLbl;

    /**
     * Row Index
     */
    private int idx;

    /**
     * Category TextField
     */
    private TextField categoryInput;

    /**
     * Creates a new Category Row with a given index
     * @param idx index
     */
    protected CategoryRow(int idx) {
        categoryLbl = new Label();
        this.idx = idx;
        categoryInput = new TextField();
        categoryInput.setPlaceholder("Caminho da Categoria");

        prepareLayout();
    }

    /**
     * Prepares CategoryRow Layout
     */
    private void prepareLayout() {
        setWidth(100, Sizeable.Unit.PERCENTAGE);
        categoryLbl.setValue("Categoria " + (idx + 1));

        categoryInput.setWidth(100, Unit.PERCENTAGE);
        addComponents(categoryLbl, categoryInput);
    }

    /**
     * Gets TextField Value
     * @return textfield value
     */
    protected String getInputValue() {
        return categoryInput.getValue();
    }
}
