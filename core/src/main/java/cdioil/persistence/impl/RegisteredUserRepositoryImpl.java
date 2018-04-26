package cdioil.persistence.impl;

import cdioil.application.utils.OperatorsEncryption;
import cdioil.domain.authz.Email;
import cdioil.persistence.BaseJPARepository;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.SystemUser;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.RegisteredUserRepository;
import java.util.List;
import javax.persistence.Query;

/**
 * Registered User Repository Implementation
 */
public class RegisteredUserRepositoryImpl extends BaseJPARepository<RegisteredUser, Long> implements RegisteredUserRepository {

    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

    @Override
    public RegisteredUser add(RegisteredUser user) {
        if (exists(user)) {
            return user;
        }
        return super.add(user);
    }

    public boolean exists(RegisteredUser user) {
        SystemUser sysUser=new UserRepositoryImpl().findByEmail(user.getID().getID());
        if(sysUser==null)return false;
        Query query=entityManager().createQuery("SELECT RU FROM RegisteredUser RU WHERE RU.sysUser = :sysUser").setParameter("sysUser",sysUser);
        return !query.getResultList().isEmpty();
    }
    
    /**
     * Method that finds a certain RegisteredUser with a certain user ID
     * @param dataBaseId Long with the user ID
     * @return RegisteredUser with the registered user with a certain user ID, null 
     * if no registered user was found
     */
    public RegisteredUser findByUserID(long dataBaseId) {
        Query q = entityManager().createQuery("SELECT ru FROM RegisteredUser ru WHERE ru.sysUser.id = :databaseId");
        q.setParameter("databaseId", dataBaseId);
        if (q.getResultList().isEmpty())return null;
        return (RegisteredUser)q.getSingleResult();
    }
    
    /**
     * Retrieves a list of users based on a domain
     *
     * @param domain domain used to retrieve the users
     * @return list of users
     */
    @Override
    public List<RegisteredUser> getUsersByDomain(String domain) {
        String encryptedDomain = OperatorsEncryption.removeEncryptionHeader(OperatorsEncryption.encrypt("@" + domain, Email.ENCRYPTION_CODE, Email.ENCRYPTION_VALUE));
        Query q = entityManager().createQuery("SELECT r FROM RegisteredUser r WHERE r.sysUser.email.email REGEXP :pattern");
        q.setParameter("pattern", ".*" + encryptedDomain);
        if (q.getResultList().isEmpty()) {
            return null;
        }
        return q.getResultList();
    }
    /**
     * Method that finds a certain admin by it's SystemUser
     * @param systemUser SystemUser with the admin system user
     * @return RegisteredUser with the admin that has a certain SystemUser
     */
    public RegisteredUser findBySystemUser(SystemUser systemUser){
        if(systemUser==null)return null;
        Query querySystemUser=entityManager().createQuery("SELECT RU FROM RegisteredUser RU WHERE RU.sysUser= :systemUser")
                .setParameter("systemUser",systemUser);
        return !querySystemUser.getResultList().isEmpty() ? (RegisteredUser)querySystemUser.getSingleResult() : null;
    }
    @Override
    public List<RegisteredUser> getUsersByFilters(String domain, String username, String birthYear, String location) {

        StringBuilder baseQueryStringBuilder = new StringBuilder("SELECT r FROM RegisteredUser r");

        if (domain != null && !domain.trim().isEmpty()) {
            baseQueryStringBuilder.append(" WHERE r.su.email.email REGEXP :p_domain");
        }
        if (username != null && !username.trim().isEmpty()) {
            baseQueryStringBuilder.append(getOperator(baseQueryStringBuilder));
            baseQueryStringBuilder.append("r.su.email.email REGEXP :p_username");
        }
        if (birthYear != null && !birthYear.trim().isEmpty()) {
            baseQueryStringBuilder.append(getOperator(baseQueryStringBuilder));
            baseQueryStringBuilder.append("EXTRACT (YEAR FROM r.su.birthDate.birthDate) = :p_birthyear");
        }
        if (location != null && !location.trim().isEmpty()) {
            baseQueryStringBuilder.append(getOperator(baseQueryStringBuilder));
            baseQueryStringBuilder.append("r.su.location.location = :p_location");
        }

        String queryString = baseQueryStringBuilder.toString();
        Query q = entityManager().createQuery(queryString);

        if (domain != null && !domain.trim().isEmpty()) {
            domain = ".*" + OperatorsEncryption.removeEncryptionHeader(OperatorsEncryption.encrypt("@" + domain, Email.ENCRYPTION_CODE, Email.ENCRYPTION_VALUE));
            q.setParameter("p_domain", domain);
        }
        if (username != null && !username.trim().isEmpty()) {
            username = ".*" + OperatorsEncryption.removeEncryptionHeader(OperatorsEncryption.encrypt(username, Email.ENCRYPTION_CODE, Email.ENCRYPTION_VALUE)) + ".*";
            q.setParameter("p_username", username);
        }
        if (birthYear != null && !birthYear.trim().isEmpty()) {
            q.setParameter("p_birthyear", birthYear);
        }
        if (location != null && !location.trim().isEmpty()) {
            q.setParameter("p_location", location);
        }

        List<RegisteredUser> registeredUsers = q.getResultList();
        if (registeredUsers.isEmpty()) {
            return null;
        }

        return registeredUsers;
    }

    private String getOperator(StringBuilder queryStringBuilder) {
        if (queryStringBuilder.indexOf("WHERE") == -1) {
            return " WHERE ";
        }
        return " AND ";
    }
}
