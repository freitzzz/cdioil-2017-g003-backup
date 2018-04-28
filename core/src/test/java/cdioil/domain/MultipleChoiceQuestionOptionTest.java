package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for MultipleChoiceQuestionOption class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class MultipleChoiceQuestionOptionTest {

    /**
     * Constructor tests.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor tests");
        assertNotNull("Empty constructor test", new MultipleChoiceQuestionOption());
        assertNull("The condition should succeed because the string is"
                + " null", createMCQuestionOption(null));
        assertNull("The condition should succeed because the string is "
                + "empty", createMCQuestionOption(""));
        assertNotNull("The condition should succeed because the string is "
                + "valid", createMCQuestionOption("Option A"));
        assertNull("The condition should succeed because the question option "
                + "is null", createMCQuestionOptionCopy(null));
        assertNotNull("The condition should succeed because the argument is "
                + "valid", createMCQuestionOptionCopy(createMCQuestionOption("Option A")));
    }

    /**
     * Test of hashCode method, of class MultipleChoiceQuestionOption.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        MultipleChoiceQuestionOption instance = createMCQuestionOption("Option B");
        MultipleChoiceQuestionOption other = createMCQuestionOption("Option B");
        assertEquals(instance.hashCode(), other.hashCode());
        assertNotEquals(instance.hashCode(), "".hashCode());
    }

    /**
     * Test of equals method, of class MultipleChoiceQuestionOption.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        MultipleChoiceQuestionOption instance = createMCQuestionOption("Option B");
        MultipleChoiceQuestionOption other = createMCQuestionOption("Option B");
        assertEquals("The condition should succeed because we are comparing "
                + "the same instance", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing "
                + "an instance with a null value", instance, null);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes", instance, "bananas");
        assertEquals("The condition should succeed because both options have the "
                + "same content", instance, other);
        other = createMCQuestionOption("Option A");
        assertNotEquals("The condition should succeed because the options have "
                + "different contents", instance, other);
    }

    /**
     * Test of toString method, of class MultipleChoiceQuestionOption.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String option = "Option B";
        MultipleChoiceQuestionOption instance = createMCQuestionOption(option);
        assertEquals(instance.toString(), option);
    }

    /**
     * Test of getContent method, of class MultipleChoiceQuestionOption.
     */
    @Test
    public void testGetContent() {
        System.out.println("getContent");
        String option = "Option B";
        MultipleChoiceQuestionOption instance = createMCQuestionOption(option);
        assertEquals(instance.getContent(), option);
    }

    /**
     * Builds a MultipleChoiceQuestionOption instance with a string
     *
     * @param value option value
     * @return MultipleChoiceQuestionOption instance
     */
    private MultipleChoiceQuestionOption createMCQuestionOption(String value) {
        try {
            return new MultipleChoiceQuestionOption(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Builds a MultipleChoiceQuestionOption instance with a QuestionOption
     *
     * @param value option we want a copy of
     * @return MultipleChoiceQuestionOption instance
     */
    private MultipleChoiceQuestionOption createMCQuestionOptionCopy(QuestionOption option) {
        try {
            return new MultipleChoiceQuestionOption(option);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
