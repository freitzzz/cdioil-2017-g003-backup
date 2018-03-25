package cdioil.persistence.impl;

import cdioil.domain.authz.Manager;
import cdioil.persistence.ManagerRepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.BaseJPARepository;

/**
 * Class that represents the implementation of the Manager repository
 * @see cdioil.persistence.ManagerRepository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class ManagerRepositoryImpl extends BaseJPARepository<Manager,Long> implements ManagerRepository{
    /**
     * Method that returns the persistence unit name that the repository uses
     * @return String with the persistence unit name that the repository uses
     */
    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
    /**
     * Method that adds an Manager to the database
     * @param manager Manager with the manager being added to the database
     * @return Manager if the manager was added with success, or null if an error occured
     */
    @Override
    public Manager add(Manager manager){
        if(exists(manager))return manager;
        return super.add(manager);
    }
    /**
     * Method that checks if a certain manager exists on the database
     * @param manager Manager with manager being checked
     * @return boolean true if the manager exists on the database, false if not
     */
    public boolean exists(Manager manager){return find(manager.getID())!=null;}
    
}
