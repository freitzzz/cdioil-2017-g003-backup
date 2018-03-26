package cdioil.persistence.impl;

import cdioil.domain.authz.Email;
import cdioil.domain.authz.Password;
import cdioil.persistence.BaseJPARepository;
import cdioil.domain.authz.SystemUser;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.UserRepository;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import javax.persistence.NoResultException;

/**
 * Class that represents the implementation of the SystemUser repository
 * @see cdioil.persistence.UserRepository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class UserRepositoryImpl extends BaseJPARepository<SystemUser, Integer> implements UserRepository {

    /**
     * Method that returns the persistence unit name that the repository uses
     * @return String with the persistence unit name that the repository uses
     */
    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

    /**
     * Method that persists a certain list of valid users in the database
     * @param users List with all valid users to be saved on the database
     * @return boolean true if the operation was successful, false if not
     */
    @Override
    public boolean saveAll(List<SystemUser> users) {
        if (users == null || users.isEmpty()) {
            return false;
        }
        int usersAdded = users.size();
        for (int i = 0; i < users.size(); i++) {
            if (add(users.get(i)) == null) {
                usersAdded--;
            }
        }
        return usersAdded != 0;
    }
    /**
     * Method that adds an user to the database
     * @param user SystemUser with the user being added to the database
     * @return SystemUser if the user was added with success, or null if an error occured
     */
    @Override
    public SystemUser add(SystemUser user) {
        if (exists(user)) {
            return user;
        }
        return super.add(user);
    }

    /**
     * Method that finds a certain SystemUser by his email
     * @param email Email with the email being searched
     * @return SystemUser with the user that has the email that was being searched
     */
    @Override
    public SystemUser findByEmail(Email email) {
        EntityManager em = entityManager();
        Query q =
                em.createQuery("SELECT u from SystemUser u where lower(u.email.email) = :email");
        q.setParameter("email", email.toString().toLowerCase());
        try{
            return (SystemUser) q.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
    
    public long login(Email email, String passwordString) {

        EntityManager em = entityManager();

        Query q = em.createQuery("SELECT u.password FROM SystemUser u WHERE LOWER(u.email.email) = :email");

        q.setParameter("email", email.toString().toLowerCase());

        Password password = (Password) q.getSingleResult();

        if (password.verifyPassword(passwordString)) {

            q = em.createQuery("SELECT u.id FROM SystemUser u WHERE LOWER(u.email.email) = :email");
            
            q.setParameter("email", email.toString().toLowerCase());
            
            return (long) q.getSingleResult();
        } else {
            return -1;
        }
    }

    /**
     * Method that finds all users with a certain email pattern 
     * <br>Uses Native Queries since JPQL doesn't allow the use of regex functions in queries
     * @param emailPattern String with the email pattern that is being searched
     * @return List with all users found with a certain email pattern, or null if an 
     * error occured
     */
    @Override
    public List<SystemUser> usersByPattern(String emailPattern) {
        EntityManager em = entityManager();
        Query queryRegexed=em.createNativeQuery("select * from SYSTEMUSER u where lower(u.email) regexp '"+emailPattern+"'",SystemUser.class);
        return (List<SystemUser>) queryRegexed.getResultList();
    }
    /**
     * Method that checks if a certain user exists on the database
     * @param user SystemUser with the user being checked
     * @return boolean true if the user exists on the database, false if not
     */
    public boolean exists(SystemUser user) {
        return findByEmail(user.getID()) != null;
    }
}
