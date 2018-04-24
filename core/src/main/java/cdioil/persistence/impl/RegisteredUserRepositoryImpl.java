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
        Query query=entityManager().createQuery("SELECT RU FROM RegisteredUser RU WHERE RU.su = :sysUser").setParameter("sysUser",sysUser);
        return !query.getResultList().isEmpty();
    }
    
    /**
     * Method that finds a certain RegisteredUser with a certain user ID
     * @param dataBaseId Long with the user ID
     * @return RegisteredUser with the registered user with a certain user ID, null 
     * if no registered user was found
     */
    public RegisteredUser findByUserID(long dataBaseId) {
        Query q = entityManager().createQuery("SELECT ru FROM RegisteredUser ru WHERE ru.su.id = :databaseId");
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
        Query q = entityManager().createQuery("SELECT r FROM RegisteredUser r WHERE r.su.email.email REGEXP :pattern");
        q.setParameter("pattern", ".*" + encryptedDomain);
        if (q.getResultList().isEmpty()) {
            return null;
        }
        return q.getResultList();
    }

    @Override
    public List<RegisteredUser> getUsersByFilters(String domain, String username, String birthYear, String location) {

        StringBuilder baseQueryStringBuilder = new StringBuilder("SELECT r FROM RegisteredUser r");

        if (domain != null && !domain.trim().isEmpty()) {
            domain = ".*" + OperatorsEncryption.removeEncryptionHeader(OperatorsEncryption.encrypt("@" + domain, Email.ENCRYPTION_CODE, Email.ENCRYPTION_VALUE));
            baseQueryStringBuilder.append(" WHERE r.su.email.email REGEXP '");
            baseQueryStringBuilder.append(domain);
            baseQueryStringBuilder.append("'");
        }

        if (username != null && !username.trim().isEmpty()) {
            baseQueryStringBuilder.append(getOperator(baseQueryStringBuilder));
            username = ".*" + OperatorsEncryption.removeEncryptionHeader(OperatorsEncryption.encrypt(username, Email.ENCRYPTION_CODE, Email.ENCRYPTION_VALUE)) + ".*";
            baseQueryStringBuilder.append("r.su.email.email REGEXP '");
            baseQueryStringBuilder.append(username);
            baseQueryStringBuilder.append("'");
        }

        if (birthYear != null && !birthYear.trim().isEmpty()) {
            baseQueryStringBuilder.append(getOperator(baseQueryStringBuilder));
            baseQueryStringBuilder.append("r.su.birthDate.birthDate REGEXP '");
            baseQueryStringBuilder.append(birthYear);
            baseQueryStringBuilder.append(".*");
        }

        if (location != null && !location.trim().isEmpty()) {
            baseQueryStringBuilder.append(getOperator(baseQueryStringBuilder));
            baseQueryStringBuilder.append("r.su.location.location = '");
            baseQueryStringBuilder.append(location);
            baseQueryStringBuilder.append("'");
        }

        String queryString = baseQueryStringBuilder.toString();
        Query q = entityManager().createQuery(queryString);

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
