package cdioil.application.utils;

import cdioil.domain.Review;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for SurveyAnswersWriterFactory class
 *  
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class SurveyAnswersWriterFactoryTest {
    /**
     * Test of create method, of class SurveyAnswersWriterFactory.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        String filename = "test_passes.csv";
        List<Review> surveyReviews = new LinkedList<>();
        assertEquals(CSVSurveyAnswersWriter.class, 
                SurveyAnswersWriterFactory.create(filename, surveyReviews).getClass());
        assertNull(SurveyAnswersWriterFactory.create("test_fails.failure",surveyReviews));
    }
}
