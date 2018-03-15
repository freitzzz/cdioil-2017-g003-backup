package cdioil.application.utils;

import cdioil.domain.authz.SystemUser;
import java.util.List;

/**
 * Interface que representa a leitura de utilizadores através de ficheiros
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public interface UtilizadoresReader {
    /**
     * Método que através de um determinado ficheiro lê uma determinada lista 
     * de utilizadores
     * @return List com todos os SystemUser contidos no ficheiro ou null caso o 
     * ficheiro não seja válido
     */
    public abstract List<SystemUser> read();
}
