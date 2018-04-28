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
        assertNull("The condition should succeed because the question option is null",
                createBinaryQuestionOptionCopy(null));
        assertNotNull("The condition should succeed because the question option is "
                + "valid", createBinaryQuestionOptionCopy(createBinaryQuestionOption(Boolean.FALSE)));
    }

    /**
     * Test of hashCode method, of class BinaryQuestionOption.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        BinaryQuestionOption instance = createBinaryQuestionOption(Boolean.FALSE);
        BinaryQuestionOption other = createBinaryQuestionOption(Boolean.FALSE);
        assertEquals(instance.hashCode(), other.hashCode());
    }

    /**
     * Test of equals method, of class BinaryQuestionOption.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        BinaryQuestionOption instance = createBinaryQuestionOption(Boolean.FALSE);
        BinaryQuestionOption other = createBinaryQuestionOption(Boolean.FALSE);
        assertEquals("The condition should succeed because we are comparing "
                + "the same instance", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing "
                + "an instance with a null value", instance, null);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes", instance, "bananas");
        assertEquals("The condition should succeed because we are comparing "
                + "options with the same content", instance, other);
        other = createBinaryQuestionOption(Boolean.TRUE);
        assertNotEquals("The condition should succeed because we are comparing "
                + "options with different contents", instance, other);
    }

    /**
     * Test of toString method, of class BinaryQuestionOption.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        BinaryQuestionOption instance = createBinaryQuestionOption(Boolean.FALSE);
        assertEquals(instance.toString(), Boolean.toString(Boolean.FALSE));
    }

    /**
     * Test of getContent method, of class BinaryQuestionOption.
     */
    @Test
    public void testGetContent() {
        System.out.println("getContent");
        BinaryQuestionOption instance = createBinaryQuestionOption(Boolean.FALSE);
        assertEquals(instance.getContent(), Boolean.FALSE);
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

    /**
     * Builds a BinaryQuestionOption instance from another question option
     *
     * @param option the option we want to copy
     * @return BinaryQuestionOption instance
     */
    private BinaryQuestionOption createBinaryQuestionOptionCopy(QuestionOption option) {
        try {
            return new BinaryQuestionOption(option);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
