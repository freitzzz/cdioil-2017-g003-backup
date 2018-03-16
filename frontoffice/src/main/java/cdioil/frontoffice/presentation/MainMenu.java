package cdioil.frontoffice.presentation;

import cdioil.domain.authz.SystemUser;
import cdioil.frontoffice.presentation.authz.MudarInfoUtilizadorUI;
import cdioil.frontoffice.presentation.authz.RegistarUtilizadorUI;

public class MainMenu {

    /**
     * Metodo que invoca a UI responsavel pela US183.
     *
     * @param loggedUser user com sessao iniciada
     */
    public void mainAlterarDados(SystemUser loggedUser) {
        MudarInfoUtilizadorUI mui = new MudarInfoUtilizadorUI(loggedUser);
        mui.mudarInformacao();
    }
    
    
    /**
     * Metodo que invoca a UI responsavel pela US180.
     */
    public void mainRegistoUser() {
        RegistarUtilizadorUI registarUtilizadorUI = new RegistarUtilizadorUI();
        registarUtilizadorUI.registarUtilizadorUI();
    }
}
