package cdioil.persistence.impl;

import cdioil.domain.MarketStructure;
import cdioil.persistence.MarketStructureRepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.BaseJPARepository;

/**
 *
 * @author António Sousa [1161371]
 */
public class MarketStructureRepositoryImpl extends BaseJPARepository<MarketStructure, Long> implements MarketStructureRepository {

    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }

}
