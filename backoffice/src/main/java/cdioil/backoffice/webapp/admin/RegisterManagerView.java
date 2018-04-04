package cdioil.backoffice.webapp.admin;

import cdioil.application.authz.AssignManagerController;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;

import java.util.Iterator;
import java.util.List;

public class RegisterManagerView extends RegisterManagerDesign implements View {

    private AssignManagerController controller;

    public RegisterManagerView() {
        controller = new AssignManagerController();
        fillTables();

        confirmBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Iterator<String> selection = userTable.getSelectedItems().iterator();

                String email = selection.next();

                if (email == null) {
                    //TODO display error
                } else {
                    controller.assignManager(email);
                    //TODO Mensagem de sucesso
                }
            }
        });
    }

    private void fillTables() {
        userTable.setSelectionMode(Grid.SelectionMode.SINGLE);

        List<String> emails = controller.registeredUsers();

        userTable.setItems(emails);

        userTable.getDataProvider().refreshAll();
    }
}
