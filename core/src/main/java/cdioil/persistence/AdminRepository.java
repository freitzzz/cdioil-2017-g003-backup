package cdioil.persistence;

import cdioil.domain.authz.Admin;

/**
 * Interface for the Admin repository
 *
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public interface AdminRepository {

    /**
     * Retrieves an instance of Admin with the respective SystemUser database id.
     * @param dataBaseId SystemUser database id
     * @return Admin with the matching SystemUser id.
     */
    Admin findByUserID(long dataBaseId);
}
