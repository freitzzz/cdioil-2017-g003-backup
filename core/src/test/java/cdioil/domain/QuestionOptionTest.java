package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for the common methods of the subclasses of QuestionOption.
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

    /**
     * Test of the getQuestionOption method, of the class QuestionOption for a BinaryQuestionOption.
     */
    @Test
    public void testGetBinaryQuestionOption() {
        System.out.println("test of getQuestionOption for a binary question option");
        assertEquals(new BinaryQuestionOption(false), QuestionOption.getQuestionOption("BINARY", "false"));
    }

    /**
     * Test of the getQuestionOption method, of the class QuestionOption for a QuantitativeQuestionOption.
     */
    @Test
    public void testGetQuantitativeQuestionOption() {
        System.out.println("test of getQuantitativeOption for a quantitative question option");
        assertEquals(new QuantitativeQuestionOption(5.0), QuestionOption.getQuestionOption("QUANTITATIVE", "5.0"));
    }

    /**
     * Test of the getQuestionOption method, of the class QuestionOption for a MultipleChoiceQuestionOption.
     */
    @Test
    public void testGetMultipleChoiceQuestionOption() {
        System.out.println("test of getQuestionOption for a multiple choice question option");
        assertEquals(new MultipleChoiceQuestionOption("myChoice"), QuestionOption.getQuestionOption("MULTIPLE_CHOICE", "myChoice"));
    }

    /**
     * Test of the getQuestionOption method, of the class QuestionOption for an invalid QuestionOption.
     */
    @Test
    public void testGetInvalidQuestionOption() {
        System.out.println("test of getQuestionOption for an invalid question option");
        assertEquals(null, QuestionOption.getQuestionOption("lmao", "myChoice"));
    }
}
