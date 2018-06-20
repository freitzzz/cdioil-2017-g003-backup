package cdioil.application.utils;

import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for ProductsReaderFactory class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 */
public class ProductsReaderFactoryTest {

    /**
     * Test of create method for CSV files, of class ProductsReaderFactory.
     */
    @Test
    public void testCreateCSVFileWorks() {
        System.out.println("create CSV file");
        assertEquals(CSVProductsReader.class,
                ProductsReaderFactory.create("test_pass.csv", new HashMap<>()).getClass());
    }

    /**
     * Test of create method for XML files, of class ProductsReaderFactory.
     */
    @Test
    public void testCreateXMLFileWorks() {
        System.out.println("create XML file");
        assertEquals(XMLProductsReader.class,
                ProductsReaderFactory.create("test_pass.xml", new HashMap<>()).getClass());
    }

    /**
     * Test of create method for JSON files, of class ProductsReaderFactory.
     */
    @Test
    public void testCreateJSONFileWorks() {
        System.out.println("create JSON file");
        assertEquals(XMLProductsReader.class,
                ProductsReaderFactory.create("test_pass.json", new HashMap<>()).getClass());
    }

    /**
     * Test of create method for invalid files, of class ProductsReaderFactory.
     */
    @Test
    public void testCreateInvalidExtensionFileFails() {
        System.out.println("create invalid file");
        assertNull(ProductsReaderFactory.create("test_will_fail.failure", new HashMap<>()));
    }
}
