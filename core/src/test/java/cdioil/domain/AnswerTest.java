package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for class Answer.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class AnswerTest {

    /**
     * Constructor tests.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor tests");
        assertNull("The condition should succeed because the argument is null",
                createAnswer(null));
        assertNotNull("Empty constructor test", new Answer());
        assertNotNull("The condition should succeed because the argument is "
                + "valid", createAnswer(new BinaryQuestionOption(Boolean.FALSE)));
    }

    /**
     * Test of hashCode method, of class Answer.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Answer instance = new Answer(new BinaryQuestionOption(false));
        Answer other = new Answer(new BinaryQuestionOption(false));
        int expResult = other.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
        assertNotEquals("".hashCode(),result);
    }

    /**
     * Test of equals method, of class Answer.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Answer instance = new Answer(new BinaryQuestionOption(false));
        Answer other = new Answer(new BinaryQuestionOption(false));
        Answer another = new Answer(new BinaryQuestionOption(true));
        assertNotEquals("The condition should succeed because we are comparing an"
                + " instance with a null value", instance, null);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes", instance, "bananas");
        assertEquals("The condition should succeed because we are comparing "
                + "the same instance", instance, instance);
        assertEquals("The condition should succeed because we are comparing "
                + "instances with the same property", instance, other);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances with different properties", instance, another);
    }

    /**
     * Test of toString method, of class Answer.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Answer instance = new Answer(new QuantitativeQuestionOption(4.0));
        String expResult = Double.toString(4.0);
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getContent method, of class Answer.
     */
    @Test
    public void testGetContent(){
        System.out.println("getContent");
        assertEquals("4.0", createAnswer(new QuantitativeQuestionOption(4.0)).getContent());
    }
    
    /**
     * Builds an answer with a given option
     *
     * @param option instance of QuestionOption
     * @return Answer instance or null if an exception ocurred
     */
    private Answer createAnswer(QuestionOption option) {
        try {
            return new Answer(option);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
