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

    /**
     * Retrieves a list of registered users filtered by the given attributes.
     *
     * @param domain filter applied to the users' email address domain
     * @param username filter applied to the users' username
     * @param birthYear filter applied to the users' birth year
     * @param location filter applied to the users' location
     * @return list of users matching the filters.
     */
    public abstract List<RegisteredUser> getUsersByFilters(String domain, String username, String birthYear, String location);
}
