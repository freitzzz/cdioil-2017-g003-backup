/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.authz;

import cdioil.domain.authz.SystemUser;
import cdioil.persistence.UserRepository;
import cdioil.persistence.impl.UserRepositoryImpl;
import cdioil.persistence.RepositorioBaseJPA;

/**
 * Controller do UC Mudar Informação do Utilizador
 *
 * @author João
 */
public class MudarInfoUtilizadorController {

    /**
     * Logged-in user
     */
    private SystemUser su;

    /**
     * Cria uma instância de MudarInfoUtilizadorController a partir de um
     * SystemUser
     *
     * @param su Logged-in user
     */
    public MudarInfoUtilizadorController(SystemUser su) {
        this.su = su;
    }

    /**
     * Altera informação do utilizador
     *
     * @param novaInfo nova informação
     * @param opcao inteiro que representa o campo de informação a alterar
     * @return true se informação for alterada com sucesso, false se nova
     * informação for inválida
     */
    public boolean alterarCampoInformacao(String novaInfo, int opcao) {
        boolean b = su.alterarCampoInformacao(novaInfo, opcao);
        if (b) {
            RepositorioBaseJPA repo = new UserRepositoryImpl();
            repo.merge(su);
        }
        return b;
    }
}
