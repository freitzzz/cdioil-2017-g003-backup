package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for QuantitativeQuestionOption class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class QuantitativeQuestionOptionTest {

    /**
     * Constructor tests.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor tests");
        assertNotNull("Empty constructor test", new QuantitativeQuestionOption());
        double negativeValue = -5.0;
        double positiveValue = 5.0;
        assertNull("The condition should succeed because the double is"
                + " null", createQQuestionOption(null));
        assertNull("The condition should succeed because the double is "
                + "NaN", createQQuestionOption(Double.NaN));
        assertNull("The condition should succeed because the double is "
                + "infinity", createQQuestionOption(Double.POSITIVE_INFINITY));
        assertNull("The condition should succeed because the double is "
                + "negative", createQQuestionOption(negativeValue));
        assertNotNull("The condition should succed because the double has "
                + "a valid value", createQQuestionOption(positiveValue));
        assertNull("The condition should succeed because we are sending a null "
                + "value", createQQuestionOptionCopy(null));
        assertNotNull("The condition should succeed because the argument "
                + "is valid", createQQuestionOptionCopy(createQQuestionOption(positiveValue)));
    }

    /**
     * Test of hashCode method, of class QuantitativeQuestionOption.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        double value = 5.0;
        QuantitativeQuestionOption instance = createQQuestionOption(value);
        QuantitativeQuestionOption other = createQQuestionOption(value);
        assertEquals(instance.hashCode(), other.hashCode());
        assertNotEquals("".hashCode(),instance.hashCode());
    }

    /**
     * Test of equals method, of class QuantitativeQuestionOption.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        double value = 5.0;
        QuantitativeQuestionOption instance = createQQuestionOption(value);
        QuantitativeQuestionOption other = createQQuestionOption(value);
        assertEquals("The condition should succeed because we are comparing the"
                + " same instance", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing "
                + "an instance to a null value", instance, null);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes", instance, "bananas");
        assertEquals("The condition should succeed because both options "
                + "have the same content", instance, other);
        double otherValue = 6.0;
        other = createQQuestionOption(otherValue);
        assertNotEquals("The condition should succeed because the options "
                + "have different contents", instance, other);
        otherValue = 4.0;
        other = createQQuestionOption(otherValue);
        assertNotEquals("The condition should succeed because the options "
                + "have different contents", instance, other);
    }

    /**
     * Test of toString method, of class QuantitativeQuestionOption.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        double value = 5.0;
        assertEquals(createQQuestionOption(value).toString(), Double.toString(value));
    }

    /**
     * Test of getContent method, of class QuantitativeQuestionOption.
     */
    @Test
    public void testGetContent() {
        System.out.println("getContent");
        Double value = 5.0;
        QuantitativeQuestionOption option = createQQuestionOption(value);
        assertEquals(option.getContent(),value);
    }

    /**
     * Builds a QuantitativeQuestionOption instance with a double
     *
     * @param value option value
     * @return QuantitativeQuestionOption instance
     */
    private QuantitativeQuestionOption createQQuestionOption(Double value) {
        try {
            return new QuantitativeQuestionOption(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Builds a QuantitativeQuestionOption with a question option instance
     *
     * @param option question option that we want a copy of
     * @return copy of received option
     */
    private QuantitativeQuestionOption createQQuestionOptionCopy(QuestionOption option) {
        try {
            return new QuantitativeQuestionOption(option);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
