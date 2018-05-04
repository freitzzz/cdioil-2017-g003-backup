package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.authz.AddWhitelistController;
import com.vaadin.event.selection.MultiSelectionEvent;
import com.vaadin.event.selection.MultiSelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;

public class WhitelistManagementWindow extends Window {

    private VerticalLayout mainLayout;

    private AddWhitelistController controller;

    private ListSelect<String> entries;

    private Button editButton;

    private Button deleteButton;

    public WhitelistManagementWindow() {
        instantiateComponents();
        prepareComponents();
    }

    private void instantiateComponents() {
        mainLayout = new VerticalLayout();
        controller = new AddWhitelistController();
        entries = new ListSelect<>();
    }

    private void prepareComponents() {
        setDraggable(true);
        setModal(false);
        setResizable(false);
        center();

        prepareMainLayout();

        setContent(mainLayout);
    }

    private void prepareMainLayout() {
        mainLayout.setSizeUndefined();
        mainLayout.setSpacing(true);
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        mainLayout.addComponents(prepareHeader(), prepareEntriesList(), prepareButtons());
    }

    private Label prepareHeader() {
        Label titleLabel = new Label("Whitelist");
        titleLabel.addStyleName(ValoTheme.LABEL_H2);
        return titleLabel;
    }

    private ListSelect prepareEntriesList() {
        entries.setSizeFull();

        // Gets list of entries and converts it to Collection
        List<String> entriesStringList = new ArrayList<>();
        controller.getExistingEntries().forEach(entriesStringList::add);

        entries.addSelectionListener(new MultiSelectionListener<String>() {
            @Override
            public void selectionChange(MultiSelectionEvent<String> multiSelectionEvent) {
                if (multiSelectionEvent.getAllSelectedItems().isEmpty()) {
                    editButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                } else {
                    editButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                }
            }
        });

        entries.setItems(entriesStringList);

        return entries;
    }

    private HorizontalLayout prepareButtons() {
        HorizontalLayout botLayout = new HorizontalLayout();
        botLayout.setSizeUndefined();
        botLayout.setSpacing(true);

        Button addButton = new Button(VaadinIcons.PLUS);
        addButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //TODO ADD Entry
            }
        });

        deleteButton = new Button(VaadinIcons.MINUS);
        deleteButton.setEnabled(false);
        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //TODO DELETE ENTRY
            }
        });

        editButton = new Button("Edit");
        editButton.setEnabled(false);
        editButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

            }
        });

        botLayout.addComponents(addButton, deleteButton, editButton);

        return botLayout;
    }
}
