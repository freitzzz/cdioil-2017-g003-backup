package cdioil.backoffice.console.presentation;

import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Manager;

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
        new MainMenu().mainLoopAdmin(admin);
    }
    /**
     * Constrói uma nova consola para as funcionalidades do backoffice do Manager
     * @param gestor Manager com o gestor que esta aceder ao backoffice
     */
    public BackOfficeConsole(Manager gestor){
        new MainMenu().mainLoopGestor(gestor);
    }
}
