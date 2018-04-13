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
     * Test of hashCode method, of class QuestionOption.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        QuestionOption instance = new BinaryQuestionOption(true);
        QuestionOption other = new BinaryQuestionOption(true);
        int expResult = other.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class QuestionOption.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        QuestionOption instance = new BinaryQuestionOption(true);
        QuestionOption same = new BinaryQuestionOption(true);
        QuestionOption other = new BinaryQuestionOption(false);
        assertNotEquals("The condition should succeed because we are comparing "
                + "an instance to a null value", instance, null);
        assertEquals("The condition should succeed because we are comparing "
                + "the same instance", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes", instance, "bananas");
        assertEquals("The condition should succeed because we are comparing "
                + "instances that have the same option value", instance, same);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances that have different option values", instance, other);
    }

    /**
     * Test of toString method, of class QuestionOption.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        QuestionOption instance = new BinaryQuestionOption(true);;
        String expResult = Boolean.toString(true);
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
