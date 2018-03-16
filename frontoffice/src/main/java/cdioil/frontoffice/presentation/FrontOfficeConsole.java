package cdioil.frontoffice.presentation;

import cdioil.domain.authz.SystemUser;

/**
 * Classe que representa a consola de açoes do backoffice.
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class FrontOfficeConsole {
    
    public FrontOfficeConsole(SystemUser loggedUser){
        new MainMenu().main(loggedUser);
    }
    
}
