package cdioil.domain;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Unit testing class for QuantitativeQuestion.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class QuantitativeQuestionTest {

    String q;
    Double minValue;
    Double maxValue;
    Double increment;

    /**
     * Sets up variables before running tests.
     */
    @Before
    public void setUp() {
        q = "Question";
        minValue = 0.0;
        maxValue = 5.0;
        increment = 0.5;
    }

    /**
     * Constructor tests.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor tests");
        assertNull("The condition should succeed because the question is null",
                createQQuestion(null, minValue, maxValue, increment));
        assertNull("The condition should succeed because the question is empty",
                createQQuestion("", minValue, maxValue, increment));

        assertNull("The condition should succeed because the min value "
                + "is null", createQQuestion(q, null, maxValue, increment));
        assertNull("The condition should succeed because the max value "
                + "is null", createQQuestion(q, minValue, null, increment));
        assertNull("The condition should succeed because the increment value "
                + "is null", createQQuestion(q, minValue, maxValue, null));

        assertNull("The condition should succeed because the min value "
                + "isn't a number", createQQuestion(q, Double.NaN, maxValue, increment));
        assertNull("The condition should succeed because the max value "
                + "isn't a number", createQQuestion(q, minValue, Double.NaN, increment));
        assertNull("The condition should succeed because the increment value "
                + "isn't a number", createQQuestion(q, minValue, maxValue, Double.NaN));

        assertNull("The condition should succeed because the min value "
                + "is infinity", createQQuestion(q, Double.POSITIVE_INFINITY, maxValue, increment));
        assertNull("The condition should succeed because the max value "
                + "is infinity", createQQuestion(q, minValue, Double.POSITIVE_INFINITY, increment));
        assertNull("The condition should succeed because the increment value "
                + "is infinity", createQQuestion(q, minValue, maxValue, Double.POSITIVE_INFINITY));

        assertNull("The condition should succeed because the min value is negative",
                createQQuestion(q, -1.0, maxValue, increment));
        assertNull("The condition should succeed because the max value is negative",
                createQQuestion(q, minValue, -1.0, increment));
        assertNull("The condition should succeed because the increment value is negative",
                createQQuestion(q, minValue, maxValue, -1.0));

        assertNull("The condition should succeed because the min value is bigger "
                + "than the max value", createQQuestion(q, maxValue + 1, maxValue, increment));
        assertNull("The condition should succeed because the min value is equal "
                + "to the max value", createQQuestion(q, maxValue, maxValue, increment));
        assertNull("The condition should succeed because the increment value is "
                + " bigger than the max value", createQQuestion(q, minValue, maxValue, maxValue + 1));
        assertNull("The condition should succeed because the increment value is "
                + " equal to the max value", createQQuestion(q, minValue, maxValue, maxValue));

        assertNotNull("The condition should succeed becaue all arguments are valid",
                createQQuestion(q, minValue, maxValue, increment));

    }

    /**
     * Test of hashCode method, of class QuantitativeQuestion.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        QuantitativeQuestion instance = createQQuestion(q, minValue, maxValue, increment);
        QuantitativeQuestion other = createQQuestion(q, minValue, maxValue, increment);

        assertEquals(instance.hashCode(), other.hashCode());
    }

    /**
     * Test of equals method, of class QuantitativeQuestion.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        QuantitativeQuestion instance = createQQuestion(q, minValue, maxValue, increment);
        QuantitativeQuestion other = createQQuestion(q, minValue, maxValue, increment);
        QuantitativeQuestion another = createQQuestion("QuestionT", minValue, maxValue, increment);
        BinaryQuestion outsider = new BinaryQuestion("Question");
        assertEquals("The condition should succeed because we are comparing the "
                + "same instance", instance, instance);
        assertEquals("The condition should succeed because we are comparing instances "
                + "with the same properties", instance, other);
        assertNotEquals("The condition should succeed because we are comparing "
                + "an instance with a null value", instance, null);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes", instance, "bananas");
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances with different questions", instance, another);
        assertNotEquals("The condition should succeed because we are comparing "
                + "questions of different types", instance, outsider);
    }

    /**
     * Test of content method, of class QuantitativeQuestion.
     */
    @Test
    public void testContent() {
        System.out.println("content");
        QuantitativeQuestion instance = createQQuestion(q, minValue, maxValue, increment);
        String expResult = q;
        String result = instance.content();
        assertEquals(expResult, result);
    }

    /**
     * Test of type method, of class QuantitativeQuestion.
     */
    @Test
    public void testType() {
        System.out.println("type");
        QuantitativeQuestion instance = createQQuestion(q, minValue, maxValue, increment);
        String expResult = QuestionAnswerTypes.QUANTITATIVE.toString();
        String result = instance.type();
        assertEquals(expResult, result);
    }

    /**
     * Test of possibleValues method, of class QuantitativeQuestion.
     */
    @Test
    public void testPossibleValues() {
        System.out.println("possibleValues");
        QuantitativeQuestion instance = createQQuestion(q, minValue, maxValue, increment);
        List<Double> expResult = new LinkedList<>();
        expResult.add(0.0);
        expResult.add(0.5);
        expResult.add(1.0);
        expResult.add(1.5);
        expResult.add(2.0);
        expResult.add(2.5);
        expResult.add(3.0);
        expResult.add(3.5);
        expResult.add(4.0);
        expResult.add(4.5);
        expResult.add(5.0);
        List<Double> result = instance.possibleValues();
        assertEquals(expResult, instance.possibleValues());
    }

    /**
     * Builds a QuantitativeQuestion.
     *
     * @param question the question itself
     * @param minValue min value for the question
     * @param maxValue max value for the question
     * @param increment increment value for the question
     * @return QuantitativeQuestion instance
     */
    private QuantitativeQuestion createQQuestion(String question, Double minValue, Double maxValue, Double increment) {
        try {
            return new QuantitativeQuestion(question, minValue, maxValue, increment);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
