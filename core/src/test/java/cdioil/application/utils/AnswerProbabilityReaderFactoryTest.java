package cdioil.application.utils;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for AnswerProbabilityReaderFactory class
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class AnswerProbabilityReaderFactoryTest {
    /**
     * Test of create method, of class AnswerProbabilityReaderFactory.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        File file = new File("test_file.csv");
        assertEquals(CSVAnswerProbabilityReader.class,
                AnswerProbabilityReaderFactory.create(file).getClass());
        assertNull(AnswerProbabilityReaderFactory.create(new File("test_fail.failure")));
    }
}
