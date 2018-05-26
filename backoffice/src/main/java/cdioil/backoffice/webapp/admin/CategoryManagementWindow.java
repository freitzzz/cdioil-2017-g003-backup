package cdioil.backoffice.webapp.admin;

import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * The default UI for adding/removing categories
 */
public abstract class CategoryManagementWindow extends Window {

    /**
     * Main Window Layout
     */
    private VerticalLayout mainLayout;

    /**
     * Selected user email
     */
    private String selectedUserEmail;

    /**
     * List of visible category rows
     */
    private List<CategoryRow> visibleRowList;

    /**
     * List of visible category rows
     */
    private List<CategoryRow> selectedCategoriesList;

    /**
     * Categories Panel Layout
     */
    private VerticalLayout categoriesLayout;

    /**
     * Add Row Button
     */
    private Button addBtn;

    /**
     * Delete Row Button
     */
    private Button delBtn;

    /**
     * Constructor
     *
     * @param selectedUserEmail selectedUser
     */
    public CategoryManagementWindow(String selectedUserEmail) {
        this.selectedUserEmail = selectedUserEmail;

        setProperties();
        instantiateComponents();
        prepareComponents();
    }

    /**
     * Sets window properties
     */
    private void setProperties() {
        setModal(true);
        setResizable(false);
        setWidth(550, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
        center();
    }

    private void instantiateComponents() {
        mainLayout = new VerticalLayout();
        categoriesLayout = new VerticalLayout();
        visibleRowList = new ArrayList<>();
        selectedCategoriesList = new ArrayList<>();
    }

    protected String getSelectedUserEmail() {
        return selectedUserEmail;
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
     *
     * @return panel
     */
    private Panel prepareCategoriesPanel() {
        categoriesLayout.setSizeFull();

        // Create 1st row
        CategoryRow firstRow = new CategoryRow(selectedCategoriesList.size());
        addCategoryRow(firstRow);

        return new Panel(categoriesLayout);
    }

    /**
     * Prepares Add, Delete and Confirm Buttons
     *
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
                addCategoryRow(newRow);
                delBtn.setEnabled(true);
            }
        });

        delBtn = new Button(VaadinIcons.MINUS);
        delBtn.setDescription("Remove ultima categoria");
        if (selectedCategoriesList.size() <= 1) {
            delBtn.setEnabled(false);
        }
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
                List<String> categoriesPathList = new ArrayList<>();
                for (CategoryRow cr : selectedCategoriesList) {
                    String inputValue = cr.getInputValue();
                    if (!inputValue.isEmpty()) {
                        categoriesPathList.add(inputValue);
                    }
                }

                if (categoriesPathList.isEmpty()) {
                    Notification.show("Introduza pelo menos uma categoria.",
                            Notification.Type.ERROR_MESSAGE);
                    return;
                }

                try {
                    doConfirmAction(categoriesPathList);
                } catch (IllegalArgumentException e) {
                    Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
                    ExceptionLogger.logException(LoggerFileNames.BACKOFFICE_LOGGER_FILE_NAME,
                            Level.SEVERE, e.getMessage());
                }
            }
        });

        hl.addComponents(addBtn, delBtn, confirmButton);
        hl.setSpacing(true);
        return hl;
    }

    protected abstract void doConfirmAction(List<String> categoriesPathList);

    protected void addCategoryRow(CategoryRow row) {
        categoriesLayout.addComponent(row);
        selectedCategoriesList.add(row);
    }
}
