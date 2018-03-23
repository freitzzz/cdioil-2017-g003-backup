package cdioil.application;

import cdioil.domain.authz.SystemUser;
import cdioil.application.utils.UsersReaderFactory;
import cdioil.persistence.impl.UserRepositoryImpl;
import java.util.List;
import cdioil.application.utils.UsersReader;

/**
 * Controller for the <i>Importar Listas de Utilizadores através de ficheiros use case </i><b>(US-111)</b>
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class ImportUsersFromFilesController {

    /**
     * List with all users read from the file
     */
    private List<SystemUser> usersLidos;

    /**
     * Method that imports a list of users contained on a certain file
     * @param file String with the file path that contains the users being imported
     * @return List with all valid users that were read from the file, ou null if the 
     * file is not valid
     */
    public List<SystemUser> readUsers(String file){
        //NAO ESQUECER IR BUSCAR A WHITE LIST AGORA
        UsersReader usersReader=UsersReaderFactory.create(file);
        return usersReader!=null?usersLidos=UsersReaderFactory.create(file).read() : null;
    }

    /**
     * Method that persists on the database a certain list of users that were previously imported
     * @return boolean true if the persistence of the users was successful, false if not
     */
    public boolean saveUsers(){
        return new UserRepositoryImpl().saveAll(usersLidos);
    }
}
