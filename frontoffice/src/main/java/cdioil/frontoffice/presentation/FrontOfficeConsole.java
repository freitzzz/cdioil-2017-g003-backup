package cdioil.frontoffice.presentation;

import cdioil.frontoffice.presentation.authz.RegisterUserUI;

/**
 * Classe que representa a consola de açoes do backoffice.
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class FrontOfficeConsole {
    
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
