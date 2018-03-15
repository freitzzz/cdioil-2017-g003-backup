package cdioil.application.authz;

import cdioil.domain.authz.SystemUser;
import cdioil.persistence.impl.UserRepositoryImpl;

/**
 * Classe Controller da US130 - Listar Todos Os Utilizadores
 * Do Sistema
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ListarUsersController {
    
    public Iterable<SystemUser> listarUtilizadores(){
        UserRepositoryImpl repo = new UserRepositoryImpl();
        Iterable<SystemUser> listaUsers = repo.findAll();
        return listaUsers;
    }
    
}
