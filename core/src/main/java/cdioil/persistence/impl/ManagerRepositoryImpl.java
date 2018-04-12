package cdioil.persistence.impl;

import cdioil.domain.authz.Manager;
import cdioil.domain.authz.SystemUser;
import cdioil.persistence.ManagerRepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.BaseJPARepository;
import javax.persistence.Query;

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
    public boolean exists(Manager manager){return new UserRepositoryImpl().findByEmail(manager.getID().getID())!=null;}

    @Override
    public Manager findByUserID(long dataBaseId) {
        Query q = entityManager().createQuery("SELECT m FROM Manager m WHERE m.su.id = :databaseId");
        q.setParameter("databaseId", dataBaseId);
        if (q.getResultList().isEmpty())return null;
        return (Manager) q.getSingleResult();
    }

    /**
     * Finds a manager that is associated with a system user
     * @param sysUser system user that the manager is associated with
     * @return manager instance that's associated with the system user, null
     * if the manager doesn't exist
     */
    public Manager findBySystemUser(SystemUser sysUser) {
        Query q = entityManager().createQuery("SELECT m FROM Manager m WHERE m.su = :sysUser");
        q.setParameter("sysUser", sysUser);
        if(q.getResultList().isEmpty())return null;
        return (Manager) q.getSingleResult();
    } 
}
