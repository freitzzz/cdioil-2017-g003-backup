package cdioil.frontoffice.presentation;

import cdioil.domain.authz.SystemUser;
import cdioil.frontoffice.presentation.authz.ChangeUserDataUI;
import cdioil.frontoffice.presentation.authz.RegisterUserUI;

public class MainMenu {

    /**
     * Metodo que invoca a UI responsavel pela US183.
     *
     * @param loggedUser user com sessao iniciada
     */
    public void mainAlterarDados(SystemUser loggedUser) {
        ChangeUserDataUI mui = new ChangeUserDataUI(loggedUser);
        mui.changeData();
    }
    
    
    /**
     * Metodo que invoca a UI responsavel pela US180.
     */
    public void mainRegistoUser() {
        RegisterUserUI registarUtilizadorUI = new RegisterUserUI();
        registarUtilizadorUI.registerUser();
    }
}
