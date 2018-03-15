package cdioil.persistence.impl;

import cdioil.domain.authz.Email;
import cdioil.persistence.RepositorioBaseJPA;
import cdioil.domain.authz.SystemUser;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.UserRepository;
import java.util.List;

/**
 * Classe que implementa e permite a persistencia do repositório de utilizadores 
 * na base de dados
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class UserRepositoryImpl extends RepositorioBaseJPA<SystemUser,Email> implements UserRepository{
    /**
     * Método que devolve o nome da unidade de persistência usada no modulo em 
     * que está a ser feita a implementação
     * @return String com o nome da unidade de persistencia
     */
    @Override
    protected String nomeUnidadePersistencia() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
    /**
     * Método que diante uma determinada lista de utilizadores, persiste-os na 
     * base de dados
     * @param users List com todos os SystemUsers a serem persistidos na base de dados
     * @return boolean true se operação ocorreu com successo, false caso contrário
     */
    @Override
    public boolean saveAll(List<SystemUser> users) {
        if(users==null||users.isEmpty())return false;
        for(int i=0;i<users.size();i++){
            if(add(users.get(i))==null)return false;
        }
        return true;
    }
}
