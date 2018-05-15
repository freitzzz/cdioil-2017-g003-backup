package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.authz.ManagerManagementController;
import cdioil.backoffice.webapp.DefaultPanelView;
import cdioil.framework.dto.ManagerDTO;
import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

/**
 * Manager Management view
 */
public class ManagerManagementComponent extends DefaultPanelView {

    private ManagerManagementController controller;

    /**
     * User list/grid
     */
    private Grid<ManagerDTO> managerGrid;

    /**
     * Data for the the user grid
     */
    private transient List<ManagerDTO> managerGridData;

    /**
     * Constructs an instance of the Manager Management view
     */
    public ManagerManagementComponent() {
        super("Gestores");
        instantiateComponents();
        prepareComponents();
    }

    private void instantiateComponents() {
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

    private Component createSearchField() {
        TextField searchTextField = new TextField();
        searchTextField.setPlaceholder("Search");
        searchTextField.setIcon(VaadinIcons.SEARCH);
        searchTextField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        searchTextField.addValueChangeListener(new HasValue.ValueChangeListener<String>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<String> valueChangeEvent) {
                final String input = searchTextField.getValue().toLowerCase();

                List<ManagerDTO> filteredUsers =
                        controller.findFilteredManagerDTO(input);

                managerGridData.clear();
                managerGridData.addAll(filteredUsers);
                managerGrid.getDataProvider().refreshAll();
            }
        });

        return searchTextField;

    }

    private Component createOptionsDropDown() {
        MenuBar settingsMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem = settingsMenuBar.addItem("", null);

        menuItem.addItem("Novo Gestor",
                VaadinIcons.PLUS, new MenuBar.Command() {
                    @Override
                    public void menuSelected(MenuBar.MenuItem menuItem) {
                        //TODO Novo gestor
                    }
                });
        menuItem.addItem("Associar Categorias",
                VaadinIcons.PLUS_CIRCLE, new MenuBar.Command() {
                    @Override
                    public void menuSelected(MenuBar.MenuItem menuItem) {
                        //TODO Associar Categorias
                    }
                });
        menuItem.addItem("Remover Categoria de Gestor",
                VaadinIcons.MINUS_CIRCLE, new MenuBar.Command() {
                    @Override
                    public void menuSelected(MenuBar.MenuItem menuItem) {
                        //TODO Remover Categoria de Gestor
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

    private void prepareGrid() {
        managerGridData = controller.findAllManagerDTO();

        managerGrid.setItems(managerGridData);
        managerGrid.addColumn(ManagerDTO::getFirstName).setCaption("Nome").setResizable(false);
        managerGrid.addColumn(ManagerDTO::getLastName).setCaption("Sobrenome").setResizable(false);
        managerGrid.addColumn(ManagerDTO::getEmail).setCaption("Email").setResizable(false);

        managerGrid.setSizeFull();
        addComponent(managerGrid);
    }

}
