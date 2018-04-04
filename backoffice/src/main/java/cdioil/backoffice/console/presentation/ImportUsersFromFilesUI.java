package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.ImportUsersFromFilesController;
import cdioil.backoffice.utils.Console;
import cdioil.domain.authz.SystemUser;
import java.util.List;

/**
 * User Interface for the <i>Importar Listas de Utilizadores através de ficheiros</i> use case <b>(US-111)</b>
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class ImportUsersFromFilesUI {
    /**
     * Constant that represents the exit code from the User Interface
     */
    private static final String EXIT_CODE="Sair";
    /**
     * Constant that represents the code that allows the system to know if the 
     * users that were imported should be persisted on the database or not
     */
    private static final String PERSIST_BASE_DADOS_CODE="Sim";
    /**
     * Constant that represents the message that asks the user to type the path of the 
     * file to be imported
     */
    private static final String FILE_PATH_MESSAGE="Por favor indique o caminho do "
            + "ficheiro que pretende importar";
    /**
     * Constant that represents the error message when a file or file path is invalid
     */
    private static final String FILE_PATH_NOT_FOUND_MESSAGE="Caminho de ficheiro "
            + "não encontrado!\nPretendo continuar? Digite \""+EXIT_CODE+"\" para sair "
            + "ou qualquer outra mensagem para continuar";
    /**
     * Constant that represents the message when no valid users were imported from the file
     */
    private static final String NO_USERS_IMPORTED_MESSAGE="Nenhum utilizador válido foi importado!";
    /**
     * Constant that represents the console header messages when users were imported with success
     */
    private static final String[] IMPORTED_USERS_MESSAGE={"#####Utilizadores Importados#####"
            ,"#####                       #####"};
    
    /**
     * Constant that represents the message asked to the user if he wants to persist the users 
     * imported to the database
     */
    private static final String PERSIST_USERS_DATABASE_MESSAGE="Pretende guardar os utilizadores importados na base de dados?"
                + "\nDigite \""+PERSIST_BASE_DADOS_CODE+"\" caso pretenda guardar ou qualquer outra mensagem para não guardar";
    /**
     * Constant that represents the message when the persist of the users imported were persisted
     * with sucess to the database
     */
    private static final String PERSISTED_USERS_SUCCESS_MESSAGE="Os utilizadores foram persistidos com sucesso!";
    /**
     * Constant that represents the message when the persist of the users imported failed to persist in the database
     */
    private static final String PERSISTED_USERS_FAILURE_MESSAGE="Ocorreu um erro a quando a persistencia dos utilizadores";
    /**
     * Constant that represents the exit message from the user interface
     */
    private static final String EXIT_MESSAGE="A qualquer momento digite \""+EXIT_CODE+"\" para sair";
    /**
     * Controller that allows the import of the users from files
     */
    private final ImportUsersFromFilesController iufCtrl;
    /**
     * Builds a new ImportUsersFromFilesUI that allows the communication of the user 
     * with the system for the <i>Importar Listas de Utilizadores através de ficheiros</i> use case <b>(US-111)</b>
     */
    public ImportUsersFromFilesUI(){
        iufCtrl=new ImportUsersFromFilesController();
        importUsers();
    }
    /**
     * Method that treats the interaction of the administrator and the import of the users
     */
    private void importUsers(){
        System.out.println(EXIT_MESSAGE);
        boolean catched=false;
        while(!catched){
            String pathFicheiro=Console.readLine(FILE_PATH_MESSAGE);
            if(pathFicheiro.equalsIgnoreCase(EXIT_CODE))return;
            List<SystemUser> usersImportados=iufCtrl.readUsers(pathFicheiro);
            if(usersImportados==null){
                String decisao=Console.readLine(FILE_PATH_NOT_FOUND_MESSAGE);
                if(decisao.equalsIgnoreCase(EXIT_CODE))return;
            }else{
                if(usersImportados.isEmpty()){
                    System.out.println(NO_USERS_IMPORTED_MESSAGE);
                }else{
                    System.out.println(IMPORTED_USERS_MESSAGE[0]);
                    usersImportados.forEach((t)->{
                        System.out.println(t);
                    });
                    System.out.println(IMPORTED_USERS_MESSAGE[1]);
                    catched=true;
                }
            }
        }
        persistUsers();
    }
    /**
     * Method that treats the persistence of users to the database
     */
    private void persistUsers(){
        String persistir=Console.readLine(PERSIST_USERS_DATABASE_MESSAGE);
        if(persistir.equalsIgnoreCase(PERSIST_BASE_DADOS_CODE)){
            if(iufCtrl.saveUsers()){
                System.out.println(PERSISTED_USERS_SUCCESS_MESSAGE);
            }else{
                System.out.println(PERSISTED_USERS_FAILURE_MESSAGE);
            }
        }
    }
}
