package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.authz.ListUsersController;
import cdioil.backoffice.application.authz.UserManagementController;
import cdioil.backoffice.webapp.DefaultPanelView;
import cdioil.framework.dto.SystemUserDTO;
import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;


/**
 * The User Management View of the AdminPanel
 */
public class UserManagementComponent extends DefaultPanelView {

    private UserManagementController userManagementController;

    private ListUsersController listUsersController;

    private Grid<SystemUserDTO> userGrid;

    /**
     * Constructs an instance of the User Management view
     */
    public UserManagementComponent() {
        super("User Management");
        instantiateComponents();
        prepareComponents();
    }

    private void instantiateComponents() {
        userManagementController = new UserManagementController();
        listUsersController = new ListUsersController();
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

    private void prepareHeader() {
        Responsive.makeResponsive(headerLayout);

        HorizontalLayout toolsLayout = new HorizontalLayout();
        toolsLayout.addStyleName("toolbar");

        // Create search/filter
        toolsLayout.addComponents(createSearchField(), createFilterButton(),
                createOptionsDropDown());

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
                //TODO Filters user table
            }
        });

        return searchTextField;
    }

    private Component createFilterButton() {
        Button filterButton = new Button("Filters");
        filterButton.setSizeUndefined();

        return filterButton;
    }

    private Component createOptionsDropDown() {
        Button optionsButton = new Button(VaadinIcons.ANGLE_DOWN);
        optionsButton.setSizeUndefined();

        return optionsButton;
    }


    private void prepareUserTable() {
        List<SystemUserDTO> allUsers = userManagementController.findAllSystemUsersDTO();

        userGrid.setItems(allUsers);
        userGrid.addColumn(SystemUserDTO::getFirstName).setCaption("First Name").setResizable(false);
        userGrid.addColumn(SystemUserDTO::getLastName).setCaption("Last Name").setResizable(false);
        userGrid.addColumn(SystemUserDTO::getEmail).setCaption("Email").setResizable(false);

        userGrid.setSizeFull();
        addComponent(userGrid);
    }
}
