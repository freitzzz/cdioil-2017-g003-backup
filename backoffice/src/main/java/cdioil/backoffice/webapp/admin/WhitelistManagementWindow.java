package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.authz.AddWhitelistController;
import com.vaadin.event.selection.MultiSelectionEvent;
import com.vaadin.event.selection.MultiSelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WhitelistManagementWindow extends Window {

    private VerticalLayout mainLayout;

    private AddWhitelistController controller;

    private ListSelect<String> entries;

    private List<String> entriesDataProvider;

    private Button editButton;

    private Button deleteButton;

    private TextField addEditTextField;

    public WhitelistManagementWindow() {
        instantiateComponents();
        prepareComponents();
    }

    private void instantiateComponents() {
        mainLayout = new VerticalLayout();
        controller = new AddWhitelistController();
        entries = new ListSelect<>();
        entriesDataProvider = new ArrayList<>();
        addEditTextField = new TextField();
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
        controller.getExistingEntries().forEach(entriesDataProvider::add);

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

        entries.setItems(entriesDataProvider);

        return entries;
    }

    private HorizontalLayout prepareButtons() {
        HorizontalLayout botLayout = new HorizontalLayout();
        botLayout.setSizeUndefined();
        botLayout.setSpacing(true);
        addEditTextField.setWidth("100%");
        addEditTextField.setPlaceholder("New entry");

        Button addButton = new Button(VaadinIcons.PLUS);
        addButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Window addEntryWindow = new Window("Add Entry");
                addEntryWindow.setResizable(false);
                addEntryWindow.setModal(true);
                addEntryWindow.center();

                VerticalLayout vl = new VerticalLayout();
                vl.setSizeUndefined();
                vl.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
                addEditTextField.clear();
                vl.addComponent(addEditTextField);

                Button confirmBtn = new Button("Ok");
                confirmBtn.setSizeUndefined();
                confirmBtn.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        try {
                            final String newValue = addEditTextField.getValue();
                            if (entriesDataProvider.contains(newValue)) {
                                Notification n = new Notification("Already exists",
                                        Notification.Type.ERROR_MESSAGE);
                                n.setDelayMsec(1000);
                                n.show(UI.getCurrent().getPage());
                                addEntryWindow.close();
                                return;
                            }
                            controller.addAuthorizedDomain(addEditTextField.getValue());
                            entriesDataProvider.add(newValue);
                            entries.getDataProvider().refreshAll();
                            entries.deselectAll();
                            Notification n = new Notification("Whitelist entry added with success",
                                    Notification.Type.TRAY_NOTIFICATION);
                            n.setDelayMsec(1000);
                            n.show(UI.getCurrent().getPage());
                        } catch (Exception e) {
                            //TODO log exception
                            Notification n = new Notification("Error creating whitelist entry",
                                    Notification.Type.ERROR_MESSAGE);
                            n.setDelayMsec(1000);
                            n.show(UI.getCurrent().getPage());
                            e.printStackTrace();
                        } finally {
                            addEntryWindow.close();
                        }
                    }
                });
                vl.addComponent(confirmBtn);
                addEntryWindow.setContent(vl);
                UI.getCurrent().addWindow(addEntryWindow);
            }
        });

        deleteButton = new Button(VaadinIcons.MINUS);
        deleteButton.setEnabled(false);
        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                // Get selected domain
                String selected = getSelectedString(); //TODO catch exception

                // Remove from database
                try {
                    System.out.println(selected);
                    controller.removeAuthorizedDomain(selected);
                    // Remove from entries Data provider
                    entriesDataProvider.remove(selected);

                    entries.getDataProvider().refreshAll();
                    entries.deselectAll();
                    Notification n = new Notification("Whitelist entry deleted with success",
                            Notification.Type.TRAY_NOTIFICATION);
                    n.setDelayMsec(1000);
                    n.show(UI.getCurrent().getPage());
                } catch (Exception e) {
                    //TODO Log exception
                    e.printStackTrace();
                    Notification n = new Notification("Error deleting whitelist entry",
                            Notification.Type.ERROR_MESSAGE);
                    n.setDelayMsec(1000);
                    n.show(UI.getCurrent().getPage());
                }
            }
        });

        editButton = new Button("Edit");
        editButton.setEnabled(false);
        editButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                // Popup with textfield
                Window editEntryWindow = new Window("Edit Whitelist Entry");
                editEntryWindow.setResizable(false);
                editEntryWindow.setModal(true);
                editEntryWindow.center();

                VerticalLayout vl = new VerticalLayout();
                vl.setSizeUndefined();
                vl.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

                addEditTextField.clear();
                vl.addComponent(addEditTextField);

                Button confirmBtn = new Button("Ok");
                confirmBtn.setSizeUndefined();
                confirmBtn.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        try {
                            // Get selected entry
                            final String selected = getSelectedString();
                            final String newValue = addEditTextField.getValue();

                            if (entriesDataProvider.contains(newValue)) {
                                Notification n = new Notification("Entry already exists",
                                        Notification.Type.ERROR_MESSAGE);
                                n.setDelayMsec(1000);
                                n.show(UI.getCurrent().getPage());
                                editEntryWindow.close();
                                return;
                            }

                            controller.removeAuthorizedDomain(selected);
                            controller.addAuthorizedDomain(newValue);
                            entriesDataProvider.remove(selected);
                            entriesDataProvider.add(newValue);
                            entries.getDataProvider().refreshAll();
                            entries.deselectAll();
                            Notification n = new Notification("Whitelist entry added with success",
                                    Notification.Type.TRAY_NOTIFICATION);
                            n.setDelayMsec(1000);
                            n.show(UI.getCurrent().getPage());
                        } catch (Exception e) {
                            //TODO Log exception
                            e.printStackTrace();
                            Notification n = new Notification("Error modifying whitelist entry",
                                    Notification.Type.ERROR_MESSAGE);
                            n.setDelayMsec(1000);
                            n.show(UI.getCurrent().getPage());
                        } finally {
                            editEntryWindow.close();
                        }
                    }
                });
                vl.addComponent(confirmBtn);

                editEntryWindow.setContent(vl);

                UI.getCurrent().addWindow(editEntryWindow);
            }
        });

        botLayout.addComponents(addButton, deleteButton, editButton);

        return botLayout;
    }

    /**
     * Since Single Selection is not natively supported by ListSelect,
     * if the user selects more than one entry, only the first is
     * handled
     *
     * @return user selection
     */
    private String getSelectedString() {
        Set<String> selection = entries.getSelectedItems();

        if (selection.isEmpty()) {
            throw new IllegalArgumentException("Expecting at least one selected element. Got 0");
        }

        return selection.iterator().next();
    }
}
