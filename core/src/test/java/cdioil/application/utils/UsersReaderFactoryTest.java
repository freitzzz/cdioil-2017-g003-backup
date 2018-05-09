package cdioil.application.utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for UsersReaderFactory class
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class UsersReaderFactoryTest {
    /**
     * Test of create method, of class UsersReaderFactory.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        String file = "test_pass.csv";
        assertEquals(CSVUsersReader.class, UsersReaderFactory.create(file).getClass());
        assertNull(UsersReaderFactory.create("test_fails.failure"));
    }
}
