package cdioil.frontoffice.presentation;

import cdioil.domain.authz.SystemUser;
import cdioil.frontoffice.presentation.authz.ChangeUserDataUI;
import cdioil.frontoffice.presentation.authz.RegisterUserUI;
import cdioil.frontoffice.utils.Console;

/**
 * FrontOffice Main Menu
 */
public class MainMenu {

    /**
     * Logged User
     */
    private SystemUser systemUser;

    /**
     * Line Separator for console UI
     */
    private static final String LINE_SEPARATOR =
            "======================";

    /**
     * Constructs an instance of the the front office login
     * @param systemUser logged system user
     */
    public MainMenu(SystemUser systemUser) {
        this.systemUser = systemUser;
        showMenu();
    }

    /**
     * Shows application Main Menu
     */
    private void showMenu() {
        while (true) {
            System.out.println(LINE_SEPARATOR);
            System.out.println("      Main Menu");
            System.out.println(LINE_SEPARATOR);

            System.out.println("1 - Alterar Dados");
            System.out.println("2 - Responder Inquérito");
            System.out.println("0 - Sair");

            int option = Console.readInteger("Opção");

            switch (option) {
                case 0:
                    return;
                case 1:
                    ChangeUserDataUI mui = new ChangeUserDataUI(systemUser);
                    mui.changeData();
                    break;
                case 2:
                    new AnswerSurveyUI();
                    break;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }
    }
}
