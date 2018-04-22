package cdioil.frontoffice.presentation.authz;

import cdioil.frontoffice.application.authz.ChangeUserDataController;
import cdioil.domain.authz.SystemUser;
import cdioil.console.Console;

/**
 * UI of US Change User's Data
 *
 * @author João
 */
public class ChangeUserDataUI {

    /**
     * Controller of US Change User's Data
     */
    private ChangeUserDataController ctrl;

    /**
     * UI's constructor
     *
     * @param su logged-in user
     */
    public ChangeUserDataUI(SystemUser su) {
        ctrl = new ChangeUserDataController(su);
    }

    /**
     * Changes user's data
     */
    public void changeData() {
        int option = Console.readInteger("Que campo de informação deseja alterar?\n1.Nome\n2.Email\n3.Password\n");
        String newData = Console.readLine("Introduza a nova informação:\n");
        boolean b = ctrl.changeDataField(newData, option);
        if (b) {
            System.out.println("Informação alterada com sucesso.\n");
        } else {
            System.out.println("Dados inválidos!");
        }
    }
}
