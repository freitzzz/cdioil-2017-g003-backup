package cdioil.backoffice.console.presentation;

import cdioil.application.authz.AuthenticationController;

/**
 * Class that represents the backoffice functionalities in console
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class BackOfficeConsole {
    /**
     * Hides default constructor
     */
    private BackOfficeConsole(){}
    /**
     * Constant that represents the message that ocures if a user tries to access backoffice 
     * which doesn't have permissions for it
     */
    private static final String ILLEGAL_BACKOFFICE_ACCESS="O utilizador não têm permissões suficientes "
            + "para entrar no backoffice da aplicação";
    public static void enterBackoffice(AuthenticationController authenticationController){
        if(authenticationController.canAccessAdminBackoffice()){
            new MainMenu().mainLoopAdmin(authenticationController);
        }else if(authenticationController.canAccessManagerBackoffice()){
            new MainMenu().mainLoopManager(authenticationController);
        }else{
            throw new IllegalStateException(ILLEGAL_BACKOFFICE_ACCESS);
        }
    }
}
