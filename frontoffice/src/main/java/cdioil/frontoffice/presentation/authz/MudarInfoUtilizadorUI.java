/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.frontoffice.presentation.authz;

import cdioil.application.authz.MudarInfoUtilizadorController;
import cdioil.domain.authz.SystemUser;
import cdioil.frontoffice.utils.Console;

/**
 * UI do UC Mudar Informação Utilizador
 *
 * @author João
 */
public class MudarInfoUtilizadorUI {

    /**
     * Controller do UC Mudar Informação Utilizador
     */
    private MudarInfoUtilizadorController ctrl;

    /**
     * Construtor da UI
     *
     * @param su logged-in user
     */
    public MudarInfoUtilizadorUI(SystemUser su) {
        ctrl = new MudarInfoUtilizadorController(su);
    }

    /**
     * Altera os dados do utilizador
     */
    public void mudarInformacao() {
        int opcao = Console.readInteger("Que campo de informação deseja alterar?\n1.Nome\n2.Email\n3.Password\n");
        String novaInfo = Console.readLine("Introduza a nova informação:\n");
        ctrl.alterarCampoInformacao(novaInfo, opcao);
    }
}
