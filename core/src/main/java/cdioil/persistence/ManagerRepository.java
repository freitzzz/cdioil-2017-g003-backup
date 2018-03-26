package cdioil.persistence;

import cdioil.domain.authz.Manager;

/**
 * Interface for the Manager repository
 *
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public interface ManagerRepository {

    /**
     * Retrieves an instance of Manager with the respective SystemUser database
     * id.
     *
     * @param dataBaseId SystemUser database id
     * @return Manager with the matching SystemUser id.
     */
    Manager findByUserID(long dataBaseId);
}
