package cdioil.application.utils;

import cdioil.domain.Category;
import cdioil.domain.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for ProductsReaderFactory class
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ProductsReaderFactoryTest {

    /**
     * Test of create method, of class ProductsReaderFactory.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Map<Category, List<Product>> existsProducts = new HashMap<>();
        assertEquals(CSVProductsReader.class,
                ProductsReaderFactory.create("test_pass.csv", existsProducts).getClass());
        assertNull(ProductsReaderFactory.create("test_will_fail.failure", existsProducts));
    }
}
