package cdioil.persistence.impl;

import cdioil.application.utils.OperatorsEncryption;
import cdioil.domain.authz.Email;
import cdioil.persistence.BaseJPARepository;
import cdioil.domain.authz.RegisteredUser;
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
        return new UserRepositoryImpl().findByEmail(user.getID().getID()) != null;
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
}
