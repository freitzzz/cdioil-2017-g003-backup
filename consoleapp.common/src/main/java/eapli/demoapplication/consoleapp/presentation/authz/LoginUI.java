package eapli.demoapplication.consoleapp.presentation.authz;

import eapli.demoapplication.application.authz.LoginController;
import eapli.demoapplication.domain.authz.UnableToAuthenticateException;
import cdioil.application.Controller;
import cdioil.presentation.console.AbstractUI;
import eapli.util.Console;

/**
 * UI for user login action. Created by nuno on 21/03/16.
 */
public class LoginUI extends AbstractUI {

    private final LoginController theController = new LoginController();

    protected Controller controller() {
        return this.theController;
    }

    @Override
    protected boolean doShow() {
        final String userName = Console.readLine("Username:");
        final String password = Console.readLine("Password:");

        try {
            this.theController.login(userName, password);
            System.out.println("Authentication Successful");
            return true;
        } catch (final UnableToAuthenticateException e) {
            System.out.println("Invalid authentication");
            return false;
        }
    }

    @Override
    public String headline() {
        return "Login";
    }
}
