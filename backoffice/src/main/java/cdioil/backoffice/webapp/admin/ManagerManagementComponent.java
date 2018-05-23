package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.authz.ManagerManagementController;
import cdioil.backoffice.webapp.DefaultPanelView;
import cdioil.framework.dto.SystemUserDTO;
import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

/**
 * Manager Management view
 */
public class ManagerManagementComponent extends DefaultPanelView {

    /**
     * Controller class
     */
    private transient ManagerManagementController controller;

    /**
     * User list/grid
     */
    private Grid<SystemUserDTO> managerGrid;

    /**
     * Data for the the user grid
     */
    private transient List<SystemUserDTO> managerGridData;

    /**
     * Constructs an instance of the Manager Management view
     */
    public ManagerManagementComponent() {
        super("Gestores");
        createComponents();
        prepareComponents();
    }

    /**
     * Instantiates needed components
     */
    private void createComponents() {
        controller = new ManagerManagementController();
        managerGrid = new Grid<>();
    }

    /**
     * Prepares components
     */
    private void prepareComponents() {
        prepareHeader();
        prepareGrid();

        setExpandRatio(headerLayout, 0.10f);
        setExpandRatio(managerGrid, 0.90f);
    }

    /**
     * Creates Window Header
     */
    private void prepareHeader() {
        Responsive.makeResponsive(headerLayout);

        HorizontalLayout toolsLayout = new HorizontalLayout();
        toolsLayout.addStyleName("toolbar");

        Component searchField = createSearchField();
        Component optionsDropdown = createOptionsDropDown();

        // Create search/filter
        toolsLayout.addComponents(searchField, optionsDropdown);

        headerLayout.addComponent(toolsLayout);
        headerLayout.setComponentAlignment(toolsLayout, Alignment.MIDDLE_RIGHT);
    }

    /**
     * Creates search field
     *
     * @return component
     */
    private Component createSearchField() {
        TextField searchTextField = new TextField();
        searchTextField.setPlaceholder("Search");
        searchTextField.setIcon(VaadinIcons.SEARCH);
        searchTextField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        searchTextField.addValueChangeListener(new HasValue.ValueChangeListener<String>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<String> valueChangeEvent) {
                final String input = searchTextField.getValue().toLowerCase();

                List<SystemUserDTO> filteredUsers =
                        controller.findFilteredSystemUserDTO(input);

                managerGridData.clear();
                managerGridData.addAll(filteredUsers);
                managerGrid.getDataProvider().refreshAll();
            }
        });

        return searchTextField;

    }

    /**
     * Creates drop down menu
     *
     * @return options drop down
     */
    private Component createOptionsDropDown() {
        MenuBar settingsMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem = settingsMenuBar.addItem("", null);

        menuItem.addItem("Adicionar Categorias a Gestor",
                VaadinIcons.PLUS_CIRCLE, new MenuBar.Command() {
                    @Override
                    public void menuSelected(MenuBar.MenuItem menuItem) {
                        final String selectedManagerEmail = getSelectedManagerEmail();
                        UI.getCurrent().addWindow(
                                new RemoveCategoryManagerWindow(selectedManagerEmail));
                    }
                });

        menuItem.addItem("Remover Categorias a Gestor",
                VaadinIcons.MINUS_CIRCLE, new MenuBar.Command() {
                    @Override
                    public void menuSelected(MenuBar.MenuItem menuItem) {
                        final String selectedManagerEmail = getSelectedManagerEmail();
                        UI.getCurrent().addWindow(
                                new RemoveCategoryManagerWindow(selectedManagerEmail));
                    }
                });

        menuItem.addItem("Categorias Sem Gestor",
                VaadinIcons.INFO_CIRCLE, new MenuBar.Command() {
                    @Override
                    public void menuSelected(MenuBar.MenuItem menuItem) {
                        //TODO Categorias sem gestor
                    }
                });

        return settingsMenuBar;
    }

    /**
     * Creates Manager Grid
     */
    private void prepareGrid() {
        managerGridData = controller.findAllSystemUserDTO();

        managerGrid.setItems(managerGridData);
        managerGrid.addColumn(SystemUserDTO::getFirstName).setCaption("Nome").setResizable(false);
        managerGrid.addColumn(SystemUserDTO::getLastName).setCaption("Sobrenome").setResizable(false);
        managerGrid.addColumn(SystemUserDTO::getEmail).setCaption("Email").setResizable(false);

        managerGrid.setSizeFull();
        addComponent(managerGrid);
    }

    /**
     * Returns the first selected Manager's email from the managerGrid
     * Even if multiple Managers are selected, only the first is returned
     *
     * @return selected Manager's email
     */
    private String getSelectedManagerEmail() {
        if (managerGrid.getSelectedItems().isEmpty()) {
            return null;
        }

        return managerGrid.getSelectedItems().iterator().next().getEmail();
    }
}
