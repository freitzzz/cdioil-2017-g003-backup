package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for BinaryQuestionOption class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class BinaryQuestionOptionTest {

    /**
     * Constructor tests.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor tests");
        assertNotNull("Empty constructor test", new BinaryQuestionOption());
        assertNull("The condition should succeed because the boolean value is"
                + " null", createBinaryQuestionOption(null));
        assertNotNull("The condition should succeed because the boolean value is "
                + "a valid one", createBinaryQuestionOption(Boolean.FALSE));
    }

    /**
     * Builds a BinaryQuestionOption instance with a boolean
     *
     * @param value boolean value
     * @return BinaryQuestionOption instance
     */
    private BinaryQuestionOption createBinaryQuestionOption(Boolean value) {
        try {
            return new BinaryQuestionOption(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
