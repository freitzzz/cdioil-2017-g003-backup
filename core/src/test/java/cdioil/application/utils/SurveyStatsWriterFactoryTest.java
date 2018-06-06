package cdioil.application.utils;

import cdioil.domain.Question;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for SurveyStatsWriterFactory class
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class SurveyStatsWriterFactoryTest {

    /**
     * Test of create method for a CSV file, of class SurveyStatsWriterFactory.
     */
    @Test
    public void testCreateCSV() {
        System.out.println("create CSV file");
        String filename = "test_passes.csv";
        long id = (long) 23.4;
        String title = "Test Passes";
        Map<Question, Integer> totalBinary = new HashMap<>();
        Map<Question, Integer> totalQuantitative = new HashMap<>();
        Map<Question, Double> binaryMean = new HashMap<>();
        Map<Question, Double> quantitativeMean = new HashMap<>();
        Map<Question, Double> binaryMeanDeviation = new HashMap<>();
        Map<Question, Double> quantitativeMeanDeviation = new HashMap<>();
        assertEquals(CSVSurveyStatsWriter.class,
                SurveyStatsWriterFactory.create(filename, id, title, totalBinary, totalQuantitative,
                        binaryMean, quantitativeMean, binaryMeanDeviation, quantitativeMeanDeviation).getClass());
    }

    /**
     * Test of create method, of class SurveyStatsWriterFactory.
     */
    @Test
    public void testCreateFailsForInvalidExtension() {
        System.out.println("create invalid file");
        String filename = "test_fails.failure";
        long id = (long) 23.4;
        String title = "Test Passes";
        Map<Question, Integer> totalBinary = new HashMap<>();
        Map<Question, Integer> totalQuantitative = new HashMap<>();
        Map<Question, Double> binaryMean = new HashMap<>();
        Map<Question, Double> quantitativeMean = new HashMap<>();
        Map<Question, Double> binaryMeanDeviation = new HashMap<>();
        Map<Question, Double> quantitativeMeanDeviation = new HashMap<>();
        assertNull(SurveyStatsWriterFactory.create(filename, id, title, totalBinary, totalQuantitative,
                binaryMean, quantitativeMean, binaryMeanDeviation, quantitativeMeanDeviation));
    }
    
     /**
     * Test of create method for a XML file, of class SurveyStatsWriterFactory.
     */
    @Test
    public void testCreateXML() {
        System.out.println("create XML file");
        String filename = "test_passes.xml";
        long id = (long) 23.4;
        String title = "Test Passes";
        Map<Question, Integer> totalBinary = new HashMap<>();
        Map<Question, Integer> totalQuantitative = new HashMap<>();
        Map<Question, Double> binaryMean = new HashMap<>();
        Map<Question, Double> quantitativeMean = new HashMap<>();
        Map<Question, Double> binaryMeanDeviation = new HashMap<>();
        Map<Question, Double> quantitativeMeanDeviation = new HashMap<>();
        assertEquals(XMLSurveyStatsWriter.class,
                SurveyStatsWriterFactory.create(filename, id, title, totalBinary, totalQuantitative,
                        binaryMean, quantitativeMean, binaryMeanDeviation, quantitativeMeanDeviation).getClass());
    }
}
