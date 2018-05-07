package cdioil.frontoffice.presentation;

import cdioil.application.authz.AuthenticationController;
import cdioil.frontoffice.presentation.authz.RegisterUserUI;

/**
 * Frontoffice console.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class FrontOfficeConsole {

    /**
     * Constant that represents the message that ocures if the user does not
     * have permissions to enter frontoffice
     */
    private static final String USER_NOT_AUTHORIZED_FRONTOFFICE = "Não tem permissões para aceder ao frontoffice!";

    /**
     * FrontOfficeConsole constructor.
     */
    public FrontOfficeConsole() {
        registerUserMain();
    }

    /**
     * Starts front office.
     *
     * @param authenticationController
     */
    public static void enterFrontoffice(AuthenticationController authenticationController) {
        if (authenticationController.canAccessFrontoffice()) {
            new MainMenu(authenticationController);
        } else {
            throw new IllegalStateException(USER_NOT_AUTHORIZED_FRONTOFFICE);
        }
    }

    /**
     * US180 UI class
     */
    public void registerUserMain() {
        RegisterUserUI registerUserUI = new RegisterUserUI();
        registerUserUI.registerUser();
    }
}
