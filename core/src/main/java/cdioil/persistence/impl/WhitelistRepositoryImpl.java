package cdioil.persistence.impl;

import cdioil.domain.authz.Whitelist;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.RepositorioBaseJPA;
import cdioil.persistence.WhitelistRepository;

/**
 *
 * @author António Sousa [1161371]
 */
public class WhitelistRepositoryImpl extends RepositorioBaseJPA<Whitelist, String> implements WhitelistRepository {

    @Override
    protected String nomeUnidadePersistencia() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

}
