package cdioil.application;

import cdioil.domain.authz.SystemUser;
import cdioil.application.utils.UtilizadoresReader;
import cdioil.application.utils.UtilizadoresReaderFactory;
import cdioil.persistence.impl.UserRepositoryImpl;
import java.util.List;

/**
 * Controlador relativo ao caso de uso Importar Listas de Utilizadores através 
 * de ficheiros (US-111)
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class ImportarUtilizadoresFicheirosController {

    /**
     * List com todos os utilizadores lidos
     */
    private List<SystemUser> usersLidos;

    /**
     * Método que importa uma lista de utilizadores diante um determinado ficheiro 
     * @param ficheiro String com o caminho do ficheiro com a lista de utilizadores
     * @return List com todos os utilizadores lidos e importados válidos, ou null 
     * caso o ficheiro não seja válido
     */
    public List<SystemUser> readUtilizadores(String ficheiro){
        UtilizadoresReader usersReader=UtilizadoresReaderFactory.create(ficheiro);
        return usersReader!=null?usersLidos=UtilizadoresReaderFactory.create(ficheiro).read() : null;
    }

    /**
     * Método que persiste na base de dados todos os utilizadores importados 
     * previamente pelos ficheiros
     * @return boolean true caso persistencia de todos os utilizadores tenha ocurrido 
     * com sucesso, false caso contrario
     */
    public boolean gravarUtilizadores(){
        return new UserRepositoryImpl().saveAll(usersLidos);
    }
}
