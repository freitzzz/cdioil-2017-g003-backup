package cdioil.persistence;

import cdioil.domain.authz.Email;
import cdioil.domain.authz.SystemUser;
import java.util.List;

/**
 * Interface respetiva ao repositorio de utilizadores
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public interface UserRepository {
    /**
     * Método que diante uma determinada lista de utilizadores, persiste-os na 
     * base de dados
     * @param users List com todos os SystemUsers a serem persistidos na base de dados
     * @return boolean true se operação ocorreu com successo, false caso contrário
     */
    public abstract boolean saveAll(List<SystemUser> users);

    /**
     * Procura um SystemUser através de um dado email
     * @param email email dado
     * @return system user encontrado. No caso de não encontrar nenhum system user,
     * retorna null
     */
    SystemUser findByEmail(Email email);
    
     /**
     * Procura uma lista de SystemUser através de um dado email
     * @param email email dado
     * @return lista de system user encontrados. No caso de não encontrar nenhum system user,
     * retorna null
     */
    List<SystemUser> utilizadoresPorEmail(String email);
}
