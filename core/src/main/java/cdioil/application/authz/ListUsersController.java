package cdioil.application.authz;

import cdioil.domain.authz.SystemUser;
import cdioil.persistence.impl.UserRepositoryImpl;

/**
 * Controller class of US130 - Listar Todos Os Utilizadores
 * Do Sistema.
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ListUsersController {
    
    public Iterable<SystemUser> listAllUsers(){
        UserRepositoryImpl repo = new UserRepositoryImpl();
        Iterable<SystemUser> usersList = repo.findAll();
        return usersList;
    }
    
}
