package cdioil.persistence;

import cdioil.domain.authz.RegisteredUser;
import java.util.List;

/**
 * Registered User Repository
 */
public interface RegisteredUserRepository {

    /**
     * Retrieves a list of users based on a domain
     *
     * @param domain domain used to retrieve the users
     * @return list of users
     */
    public abstract List<RegisteredUser> getUsersByDomain(String domain);
}
