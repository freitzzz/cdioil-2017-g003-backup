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
                + "negative",createQQuestionOption(negativeValue));
        assertNotNull("The condition should succed because the double has "
                + "a valid value",createQQuestionOption(positiveValue));
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
    
}
