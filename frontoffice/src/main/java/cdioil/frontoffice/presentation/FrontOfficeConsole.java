package cdioil.frontoffice.presentation;

import cdioil.application.authz.AuthenticationController;
import cdioil.frontoffice.presentation.authz.RegisterUserUI;

/**
 * Classe que representa a consola de açoes do backoffice.
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class FrontOfficeConsole {
    /**
     * Constant that represents the message that ocures if the user does not have have permissions to enter frontoffice
     */
    private static final String USER_NOT_AUTHORIZED_FRONTOFFICE="Não têm permissões para aceder ao frontoffice!";
    public static void enterFrontoffice(AuthenticationController authenticationController){
        if(authenticationController.canAccessFrontoffice()){
            new MainMenu(authenticationController);
        }else{
            throw new IllegalStateException(USER_NOT_AUTHORIZED_FRONTOFFICE);
        }
    }
    
    
    public FrontOfficeConsole(){
        mainRegistoUser();
    }

    /**
     * Metodo que invoca a UI responsavel pela US180.
     */
    public void mainRegistoUser() {
        RegisterUserUI registarUtilizadorUI = new RegisterUserUI();
        registarUtilizadorUI.registerUser();
    }
}
