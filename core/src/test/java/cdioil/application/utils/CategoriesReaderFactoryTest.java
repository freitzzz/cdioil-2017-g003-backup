package cdioil.application.utils;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for CategoriesReaderFactory class
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class CategoriesReaderFactoryTest {
    /**
     * Test of create method, of class CategoriesReaderFactory.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        assertEquals(CSVCategoriesReader.class,
                CategoriesReaderFactory.create("test_pass.csv").getClass());
        assertNull(CategoriesReaderFactory.create("test_failure.fail"));
    }
    
}
