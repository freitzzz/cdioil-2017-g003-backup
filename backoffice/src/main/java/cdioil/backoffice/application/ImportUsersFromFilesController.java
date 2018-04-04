package cdioil.backoffice.application;

import cdioil.domain.authz.SystemUser;
import cdioil.application.utils.UsersReaderFactory;
import cdioil.persistence.impl.UserRepositoryImpl;
import java.util.List;
import cdioil.application.utils.UsersReader;
import cdioil.persistence.impl.WhitelistRepositoryImpl;
import java.util.ArrayList;

/**
 * Controller for the <i>Importar Listas de Utilizadores através de ficheiros</i> use case <b>(US-111)</b>
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class ImportUsersFromFilesController {
    /**
     * Regular expression used to identify and replace the local part of an email address
     */
    private static final String REGEX_REPLACE_EMAIL_LOCAL_PART=".+@";
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
        UsersReader usersReader=UsersReaderFactory.create(file);
        if(usersReader==null)return null;
        usersLidos=usersReader.read();
        removeUsersFromBlackList();
        return usersLidos;
    }

    /**
     * Method that persists on the database a certain list of users that were previously imported
     * @return boolean true if the persistence of the users was successful, false if not
     */
    public boolean saveUsers(){
        return new UserRepositoryImpl().saveAll(usersLidos);
    }
    /**
     * Method that removes all previously imported users, which email domains are black listed
     */
    private void removeUsersFromBlackList(){
        if(usersLidos==null||usersLidos.isEmpty())return;
        List<String> currentWhiteList=new ArrayList<>(new WhitelistRepositoryImpl().allWhitelistInString());
        for(int i=0;i<usersLidos.size();i++){
            if(!currentWhiteList.contains(usersLidos.get(i).getID().toString()
                    .replaceAll(REGEX_REPLACE_EMAIL_LOCAL_PART,"").toLowerCase())){
                usersLidos.remove(i--);
            }
        }
    }
}
