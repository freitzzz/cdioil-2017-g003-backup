package cdioil.persistence.impl;

import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.SystemUser;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.RepositorioBaseJPA;

/**
 * Classe que implementa e permite a persistencia do repositório de gestores
 * na base de dados
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class GestorRepositoryImpl extends RepositorioBaseJPA<Manager,Long>{
    /**
     * Método que devolve o nome da unidade de persistência usada no modulo em 
     * que está a ser feita a implementação
     * @return String com o nome da unidade de persistencia
     */
    @Override
    protected String nomeUnidadePersistencia() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
    @Override
    public Manager add(Manager gestor){
        if(exists(gestor))return gestor;
        return super.add(gestor);
    }
    public boolean exists(Manager gestor){return find(gestor.getID())!=null;}
    
}
