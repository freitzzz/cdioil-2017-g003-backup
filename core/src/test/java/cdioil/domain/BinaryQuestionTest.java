package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for BinaryQuestion class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class BinaryQuestionTest {

    /**
     * Tests for the constructor.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor Tests");
        assertNull("The condition should succeed because the argument is invalid",
                createBinaryQuestion(null));
        assertNull("\"The condition should succeed because the argument is invalid",
                createBinaryQuestion(""));
        assertNotNull("\"The condition should succeed because the argument is valid",
                createBinaryQuestion("Translating things is fun :))))))"));
    }

    /**
     * Test of hashCode method, of class BinaryQuestion.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        String q = "IT'S REALLY FUN c:";
        BinaryQuestion instance = new BinaryQuestion(q);
        
        BinaryQuestion other = new  BinaryQuestion(q);
        
        assertEquals(instance.hashCode(), other.hashCode());
    }

    /**
     * Test of equals method, of class BinaryQuestion.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        BinaryQuestion instance = new BinaryQuestion("QuestaoTeste");
        BinaryQuestion instance2 = new BinaryQuestion("QuestaoTeste");
        BinaryQuestion instance3 = new BinaryQuestion("TesteQuestao");
        assertEquals("The condition should succeed because we are "
                + "comparing the same instances", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing"
                + " instances of different classes", instance, "bananas");
        assertNotEquals("The condition should succeed because we are "
                + "comparing the an instance with a null value", instance, null);
        assertEquals("The condition should succeed because we are comparing two"
                + " instances with the same properties", instance, instance2);
        assertNotEquals("The condition should succeed because we are comparing"
                + " instances with the same properties", instance, instance3);
    }

    /**
     * Test of content method, of class BinaryQuestion.
     */
    @Test
    public void testContent() {
        System.out.println("content");
        String q = "Do you like Translating Java Docs?";
        BinaryQuestion instance = new BinaryQuestion(q);
        String expResult = q;
        String result = instance.content();
        assertEquals(expResult, result);
    }

    /**
     * Test of type method, of class BinaryQuestion.
     */
    @Test
    public void testType() {
        System.out.println("type");
        BinaryQuestion instance = new BinaryQuestion("I'm lost in translation");
        String expResult = QuestionAnswerTypes.BINARY.toString();
        String result = instance.type();
        assertEquals(expResult, result);
    }

    /**
     * Builds a BinaryQuestion with a question
     *
     * @param question the question itself
     * @return BinaryQuestion instance
     */
    private BinaryQuestion createBinaryQuestion(String question) {
        try {
            return new BinaryQuestion(question);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
