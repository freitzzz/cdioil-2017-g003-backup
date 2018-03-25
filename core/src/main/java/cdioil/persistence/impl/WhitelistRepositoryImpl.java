package cdioil.persistence.impl;

import cdioil.domain.authz.Whitelist;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.WhitelistRepository;
import java.util.List;

/**
 *
 * @author António Sousa [1161371]
 */
public class WhitelistRepositoryImpl extends BaseJPARepository<Whitelist, String> implements WhitelistRepository {

    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
    public List<String> allWhitelistInString(){
        return (List<String>)entityManager().createNativeQuery("SELECT * FROM WHITELIST").getResultList();
    }

}
