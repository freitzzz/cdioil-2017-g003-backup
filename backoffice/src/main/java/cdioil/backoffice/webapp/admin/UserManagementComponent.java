package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.authz.UserManagementController;
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
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

/**
 * The User Management View of the AdminPanel
 */
public class UserManagementComponent extends DefaultPanelView {

    /**
     * User Management Controller
     */
    private UserManagementController userManagementController;

    /**
     * User list/grid
     */
    private Grid<SystemUserDTO> userGrid;

    /**
     * Data for the the user grid
     */
    private transient List<SystemUserDTO> userGridData;

    /**
     * Constructs an instance of the User Management view
     */
    public UserManagementComponent() {
        super("User Management");
        instantiateViewComponents();
        prepareComponents();
    }

    /**
     * Instantiates all needed components
     */
    private void instantiateViewComponents() {
        userManagementController = new UserManagementController();
        userGrid = new Grid<>();
    }

    /**
     * Prepares required components
     */
    private void prepareComponents() {
        prepareHeader();
        prepareUserTable();

        setExpandRatio(headerLayout, 0.10f);
        setExpandRatio(userGrid, 0.90f);
    }

    /**
     * Prepare Header
     */
    private void prepareHeader() {
        Responsive.makeResponsive(headerLayout);

        HorizontalLayout toolsLayout = new HorizontalLayout();
        toolsLayout.addStyleName("toolbar");

        // Create search/filter
        toolsLayout.addComponents(createSearchField(), createOptionsDropDown());

        headerLayout.addComponent(toolsLayout);
        headerLayout.setComponentAlignment(toolsLayout, Alignment.MIDDLE_RIGHT);
    }

    /**
     * Prepare Search Field
     * @return search field component
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
                        userManagementController.findFilteredSystemUsersDTO(input);

                userGridData.clear();
                userGridData.addAll(filteredUsers);
                userGrid.getDataProvider().refreshAll();
            }
        });

        return searchTextField;
    }

    /**
     * Create Options DropDown Menu
     * @return
     */
    private Component createOptionsDropDown() {
        MenuBar settingsMenuBar = new MenuBar();
        MenuItem menuItem = settingsMenuBar.addItem("", null);

        menuItem.addItem("Gerir Whitelist",
                VaadinIcons.BAN, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuItem menuItem) {
                UI.getCurrent().addWindow(new WhitelistManagementWindow());
            }
        });

        menuItem.addItem("Importar Utilizadores", VaadinIcons.USERS, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuItem menuItem) {
                UI.getCurrent().addWindow(new ImportUsersWindow());
            }
        });

        return settingsMenuBar;
    }

    /**
     * Prepare User Grid/Table
     */
    private void prepareUserTable() {
        userGridData = userManagementController.findAllSystemUsersDTO();

        userGrid.setItems(userGridData);
        userGrid.addColumn(SystemUserDTO::getFirstName).setCaption("First Name").setResizable(false);
        userGrid.addColumn(SystemUserDTO::getLastName).setCaption("Last Name").setResizable(false);
        userGrid.addColumn(SystemUserDTO::getEmail).setCaption("Email").setResizable(false);

        userGrid.setSizeFull();
        addComponent(userGrid);
    }
}
