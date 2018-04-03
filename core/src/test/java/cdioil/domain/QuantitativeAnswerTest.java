package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for QuantitativeAnswer.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class QuantitativeAnswerTest {

    /**
     * Tests for the constructor.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor");
        assertNull("The condition should succeed because the argument is null",
                createQAnswer(null));
        assertNull("The condition should succeed because the argument is NaN",
                createQAnswer(Double.NaN));
        assertNull("The condition should succeed because the argument is infinity",
                createQAnswer(Double.POSITIVE_INFINITY));
        assertNull("The condition should succeed because the argument has a "
                + "negative value", createQAnswer(-1.0));
        assertNotNull("The condition should succeed because the argument is valid",
                createQAnswer(2.0));
        assertNotNull("Empty constructor test", new QuantitativeAnswer());
    }

    /**
     * Test of hashCode method, of class QuantitativeAnswer.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        QuantitativeAnswer instance = createQAnswer(5.0);
        QuantitativeAnswer other = createQAnswer(5.0);

        assertEquals(instance.hashCode(), other.hashCode());
    }

    /**
     * Test of equals method, of class QuantitativeAnswer.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        QuantitativeAnswer instance = createQAnswer(5.0);
        QuantitativeAnswer other = createQAnswer(5.0);
        QuantitativeAnswer another = createQAnswer(3.0);
        BinaryAnswer outsider = new BinaryAnswer(true);

        assertEquals("The condition should succeed because we are comparing "
                + "the same instance", instance, instance);
        assertEquals("The condition should succeed because we are comparing "
                + "answers with the same value", instance, other);
        assertNotEquals("The condition should succeed because we are comparing "
                + "an instance with a null value", instance, null);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes", instance, "bananas");
        assertNotEquals("The condition should succeed because we are comparing "
                + "answers with different values", instance, another);
        assertNotEquals("The condition should succeed because we are comparing "
                + "answers of different types", instance, outsider);
    }

    /**
     * Test of content method, of class QuantitativeAnswer.
     */
    @Test
    public void testContent() {
        System.out.println("content");
        QuantitativeAnswer instance = createQAnswer(5.0);
        Double expResult = 5.0;
        Double result = instance.content();
        assertEquals(expResult, result);
    }

    /**
     * Test of type method, of class QuantitativeAnswer.
     */
    @Test
    public void testType() {
        System.out.println("type");
        QuantitativeAnswer instance = createQAnswer(5.0);
        String expResult = QuestionAnswerTypes.QUANTITATIVE.toString();
        String result = instance.type();
        assertEquals(expResult, result);
    }

    /**
     * Builds a quantitative answer with a double value
     *
     * @param answer the answer itself
     * @return QuantitativeAnswer instance
     */
    private QuantitativeAnswer createQAnswer(Double answer) {
        try {
            return new QuantitativeAnswer(answer);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
