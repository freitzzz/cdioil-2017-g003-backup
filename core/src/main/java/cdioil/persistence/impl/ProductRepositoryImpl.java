package cdioil.persistence.impl;

import cdioil.domain.Product;
import cdioil.domain.SKU;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.PersistenceUnitNameCore;
import cdioil.persistence.ProductRepository;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Ana Guerra (1161191)
 */
public class ProductRepositoryImpl extends BaseJPARepository<Product, Long> implements ProductRepository {

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

    @Override
    public Product getProductBySKU(SKU sku) {
        Query q = entityManager().createQuery("SELECT p FROM Product p WHERE p.sku = :p_sku");
        q.setParameter("p_sku", sku);
        
        List<Product> p = (List<Product>)q.getResultList();
        
        return p.isEmpty() ? null : p.get(0);
    }
}
