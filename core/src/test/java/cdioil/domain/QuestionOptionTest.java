package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for the common methods of the subclasses of
 * QuestionOption.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class QuestionOptionTest {

    /**
     * Test of the copyQuestionOption method, of the class QuestionOption.
     */
    @Test
    public void testCopyQuestionOption() {
        System.out.println("copyQuestionOption");
        assertNull("The condition should succeed because the value is null ",
                QuestionOption.copyQuestionOption(null));
        BinaryQuestionOption binQuestionOption = new BinaryQuestionOption(Boolean.TRUE);
        assertEquals("The condition should succeed because the copy we receive "
                + "is equal to the instance", binQuestionOption,
                QuestionOption.copyQuestionOption(binQuestionOption));
        MultipleChoiceQuestionOption multChoiceQuestionOption = new MultipleChoiceQuestionOption("Option A");
        assertEquals("The condition should succeed because the copy we receive "
                + "is equal to the instance", multChoiceQuestionOption,
                QuestionOption.copyQuestionOption(multChoiceQuestionOption));
        QuantitativeQuestionOption quantQuestionOption = new QuantitativeQuestionOption(Double.MAX_VALUE);
        assertEquals("The condition should succeed because the copy we receive "
                + "is equal to the instance", quantQuestionOption,
                QuestionOption.copyQuestionOption(quantQuestionOption));
    }
}
