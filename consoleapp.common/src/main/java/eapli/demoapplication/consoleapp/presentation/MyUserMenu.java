package eapli.demoapplication.consoleapp.presentation;

import eapli.demoapplication.consoleapp.presentation.authz.LoginAction;
import eapli.demoapplication.consoleapp.presentation.authz.LogoutAction;
import cdioil.actions.ReturnAction;
import cdioil.actions.ShowMessageAction;
import cdioil.presentation.console.Menu;
import cdioil.presentation.console.MenuItem;

public class MyUserMenu extends Menu {

    private static final int EXIT_OPTION = 0;

    // MY USER
    private static final int CHANGE_PASSWORD_OPTION = 1;
    private static final int LOGIN_OPTION = 2;
    private static final int LOGOUT_OPTION = 3;

    public MyUserMenu() {
        super("My account >");
        buildMyUserMenu();
    }

    private void buildMyUserMenu() {
        add(new MenuItem(CHANGE_PASSWORD_OPTION, "Change password", new ShowMessageAction("Not implemented yet")));
        add(new MenuItem(LOGIN_OPTION, "Change user (Login)", new LoginAction()));
        add(new MenuItem(LOGOUT_OPTION, "Logout", new LogoutAction()));
        add(new MenuItem(EXIT_OPTION, "Return ", new ReturnAction()));
    }
}
