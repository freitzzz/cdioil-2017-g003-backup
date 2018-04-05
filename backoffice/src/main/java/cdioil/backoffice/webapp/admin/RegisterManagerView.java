package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.authz.AssignManagerController;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class RegisterManagerView extends RegisterManagerDesign implements View {

    private AssignManagerController controller;

    public RegisterManagerView() {
        controller = new AssignManagerController();
        fillTables();

        confirmBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Iterator<String> selection = userTable.getSelectedItems().iterator();

                String email = null;
                try {
                    email = selection.next();
                } catch (NoSuchElementException e) {
                    Notification.show("Selecione um utilizador!", Notification.Type.ERROR_MESSAGE);
                    return;
                }

                Notification.show("Gestor registado!", Notification.Type.WARNING_MESSAGE);
                //controller.assignManager(email);
            }
        });
    }

    private void fillTables() {
        //TODO Format emails/names
        List<String> emails = controller.registeredUsers();

        userTable.setItems(emails);
        userTable.addColumn(String::toString).setCaption("Utilizador");
        userTable.getDataProvider().refreshAll();
    }
}
