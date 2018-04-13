/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.persistence.impl;

import cdioil.domain.Product;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.ProductRepository;
import java.util.Map;

/**
 *
 * @author Ana Guerra (1161191)
 */
public class ProductRepositoryImpl extends BaseJPARepository<Map<String, Product>,Long> implements ProductRepository{
    /**
     * Method that returns the persistence unit name that the repository uses
     * @return String with the persistence unit name that the repository uses
     */
    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
    
}
