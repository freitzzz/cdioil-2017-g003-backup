/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.authz;

import cdioil.domain.authz.*;
import cdioil.persistence.impl.UserRepositoryImpl;
import java.util.List;

/**
 * Classe Controller da US136 - Pesquisar utilizadores
 * do sistema pelo seu email
 * @author Ana Guerra (1161191)
 */
public class PesquisarUtilizadoresController {

    UserRepositoryImpl userRepos;
    
    /**
     * Procura um SystemUser através de um dado email
     * @param email email dado
     * @return system user encontrado. No caso de não encontrar nenhum system user,
     * retorna null
     */
    public List<SystemUser> utilizadoresPorEmail(String email){
        userRepos = new UserRepositoryImpl();
        return userRepos.utilizadoresPorFiltracao(email);
    }
    
}
