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
     * Test of create method, of class SurveyStatsWriterFactory.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        String filename = "test_passes.csv";
        Map<Question, Integer> totalBinary = new HashMap<>();
        Map<Question, Integer> totalQuantitative = new HashMap<>();
        Map<Question, Double> binaryMean = new HashMap<>();
        Map<Question, Double> quantitativeMean = new HashMap<>();
        Map<Question, Double> binaryMeanDeviation = new HashMap<>();
        Map<Question, Double> quantitativeMeanDeviation = new HashMap<>();
        assertEquals(CSVSurveyStatsWriter.class,
                SurveyStatsWriterFactory.create(filename, totalBinary, totalQuantitative,
                        binaryMean, quantitativeMean, binaryMeanDeviation, quantitativeMeanDeviation).getClass());
        assertNull(SurveyStatsWriterFactory.create("test_fails.failure", totalBinary, totalQuantitative,
                binaryMean, quantitativeMean, binaryMeanDeviation, quantitativeMeanDeviation));
    }
}
