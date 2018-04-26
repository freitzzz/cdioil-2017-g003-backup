package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.webapp.DefaultPanelView;
import cdioil.domain.dto.UserDTO;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The User Management View of the AdminPanel
 */
public class UserManagementComponent extends DefaultPanelView {

    private Grid<UserDTO> userGrid;

    /**
     * Constructs an instance of the User Management view
     */
    public UserManagementComponent() {
        super("User Management");
        prepareComponents();
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
        List<UserDTO> dummyUserList = new ArrayList<>(Arrays.asList(
                new UserDTO("Pedro", "Portela", "email1"),
                new UserDTO("Andre", "Sousa", "email2"),
                new UserDTO("Gil", "Silva", "email3"),
                new UserDTO("Joao", "Moreira", "email4")));

        userGrid = new Grid<>();
        userGrid.setItems(dummyUserList);

        userGrid.addColumn(UserDTO::getFirstName).setCaption("First Name").setResizable(false);
        userGrid.addColumn(UserDTO::getLastName).setCaption("Last Name").setResizable(false);
        userGrid.addColumn(UserDTO::getEmail).setCaption("Email").setResizable(false);

        userGrid.setSizeFull();

        addComponent(userGrid);
    }
}
