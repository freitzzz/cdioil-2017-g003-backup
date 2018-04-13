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

}
