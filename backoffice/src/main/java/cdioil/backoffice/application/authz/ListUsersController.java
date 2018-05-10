package cdioil.backoffice.application.authz;

import cdioil.domain.authz.SystemUser;
import cdioil.persistence.impl.UserRepositoryImpl;
import java.io.Serializable;

/**
 * Controller class of US130 - Listar Todos Os Utilizadores
 * Do Sistema.
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ListUsersController implements Serializable{
    
    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 13L;
    
    /**
     * Fetches all SystemUsers from the database and returns an iterable of 
     * them
     * @return iterable of SystemUser 
     */
    public Iterable<SystemUser> listAllUsers(){
        UserRepositoryImpl repo = new UserRepositoryImpl();
        return repo.findAll();
    }    
}
