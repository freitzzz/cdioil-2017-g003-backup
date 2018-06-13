package cdioil.persistence;

import cdioil.domain.Product;
import cdioil.domain.SKU;
import java.util.List;

/**
 * Product Repository
 *
 * @author Ana Guerra (1161191)
 */
public interface ProductRepository {

    /**
     * Method used for retrieving products with a given code.<p>
     * Please note, that it is possible, however unlikely, that a code may be
     * associated with multiple products, due to the fact that one product's
     * QRCode might have the same value as another product's EAN.
     *
     * @param code product's code
     * @return list of products that have the given code
     */
    public List<Product> getProductsByCode(String code);

    /**
     * Retrieves a product with a given SKU.
     * @param sku product's stock keeping unit (SKU)
     * @return product with a matching SKU
     */
    public Product getProductBySKU(SKU sku);
}
