package cdioil.application.utils;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
        File file = new File("test_pass.csv");
        assertEquals(CSVCategoriesReader.class,
                CategoriesReaderFactory.create(file).getClass());
        assertNull(CategoriesReaderFactory.create(new File("test_failure.fail")));
    }
    
}
