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
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Ana Guerra (1161191)
 */
public class ProductRepositoryImpl extends BaseJPARepository<Product,Long> implements ProductRepository{
    /**
     * Method that returns the persistence unit name that the repository uses
     * @return String with the persistence unit name that the repository uses
     */
    @Override
    protected String persistenceUnitName() {
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
    
    @Override
    public List<Product> getProductsByCode(String code) {
        
        Query q = entityManager().createNativeQuery("SELECT DISTINCT pc.PRODUCT_ID FROM PRODUCT_CODE pc WHERE pc.CODES_ID IN "
                + "(SELECT q.ID FROM QR_CODE q WHERE q.PRODUCTCODE = ?"
                + " UNION "
                + "SELECT e.ID FROM EAN e WHERE e.PRODUCTCODE = ?)");
        q.setParameter(1, code);
        q.setParameter(2, code);
        List<Long> productIDs = (List<Long>) q.getResultList();

        q = entityManager().createQuery("SELECT p FROM Product p WHERE p.id IN :productIDs");
        q.setParameter("productIDs", productIDs);
        List<Product> p = (List<Product>) q.getResultList();

        return p.isEmpty() ? null : p;
    }
}
