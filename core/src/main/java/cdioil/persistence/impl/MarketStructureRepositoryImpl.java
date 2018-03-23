/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.persistence.impl;

import cdioil.domain.MarketStructure;
import cdioil.persistence.MarketStructureRepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.RepositorioBaseJPA;

/**
 *
 * @author António Sousa [1161371]
 */
public class MarketStructureRepositoryImpl extends RepositorioBaseJPA<MarketStructure, Long> implements MarketStructureRepository{

    @Override
    protected String nomeUnidadePersistencia() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
    
    
    
    
    
}
