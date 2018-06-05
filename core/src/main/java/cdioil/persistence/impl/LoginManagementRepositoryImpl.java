package cdioil.persistence.impl;

import cdioil.application.domain.authz.LoginManagement;
import cdioil.domain.authz.Email;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.LoginManagementRepository;
import cdioil.persistence.PersistenceUnitNameCore;
import javax.persistence.Query;

/**
 * LoginManagementRepositoryImplementation class
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class LoginManagementRepositoryImpl extends BaseJPARepository<LoginManagement,Email> implements LoginManagementRepository {

    /**
     * Returns the PU's name
     *
     * @return string with the persistence unit's name
     */
    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
    
}
