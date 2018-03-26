package cdioil.persistence.impl;

import cdioil.domain.authz.Admin;
import cdioil.persistence.AdminRepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.BaseJPARepository;
import javax.persistence.Query;

/**
 * Class that represents the implementation of the Admin repository
 * @see cdioil.persistence.AdminRepository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class AdminRepositoryImpl extends BaseJPARepository<Admin,Long> implements AdminRepository{
    /**
     * Method that returns the persistence unit name that the repository uses
     * @return String with the persistence unit name that the repository uses
     */
    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
    /**
     * Method that adds an Admin to the database
     * @param admin Admin with the administrator being added to the database
     * @return Admin if the administrator was added with success, or null if an error occured
     */
    @Override
    public Admin add(Admin admin){
        if(exists(admin))return admin;
        return super.add(admin);
    }
    /**
     * Method that checks if a certain admin exists on the database
     * @param admin Admin with administrator being checked
     * @return boolean true if the administrator exists on the database, false if not
     */
    public boolean exists(Admin admin){return find(admin.getID())!=null;}
    
    
    @Override
    public Admin findByUserID(long databaseId) {

        Query q = entityManager().createQuery("SELECT a FROM Admin a WHERE a.sysUser.id = :databaseId");

        q.setParameter("databaseId", databaseId);

        if (q.getResultList().isEmpty()) {
            return null;
        }
        return (Admin) q.getSingleResult();
    }
    
}
