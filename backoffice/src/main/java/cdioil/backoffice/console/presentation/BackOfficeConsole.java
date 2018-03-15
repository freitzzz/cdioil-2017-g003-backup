package cdioil.backoffice.console.presentation;

import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Gestor;

/**
 * Classe que representa a consola de açoes do backoffice
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class BackOfficeConsole {
    /**
     * Constrói uma nova consola para as funcionalidades do backoffice do Administrador
     * @param admin Admin com o administrador que esta aceder ao backoffice
     */
    public BackOfficeConsole(Admin admin){
        MainMenu.mainLoopAdmin(admin);
    }
    /**
     * Constrói uma nova consola para as funcionalidades do backoffice do Gestor
     * @param gestor Gestor com o gestor que esta aceder ao backoffice
     */
    public BackOfficeConsole(Gestor gestor){
        MainMenu.mainLoopGestor(gestor);
    }
}
