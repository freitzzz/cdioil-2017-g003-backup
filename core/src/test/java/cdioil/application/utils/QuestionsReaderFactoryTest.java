package cdioil.application.utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for QuestionsReaderFactory class
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class QuestionsReaderFactoryTest {
    /**
     * Test of create method, of class QuestionsReaderFactory.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        String filename = "test_passes.csv";
        assertEquals(CSVQuestionsReader.class, QuestionsReaderFactory.create(filename).getClass());
        assertNull(QuestionsReaderFactory.create("test_fails.failure"));
    }
    
}
