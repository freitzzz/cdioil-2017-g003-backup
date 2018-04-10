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
        String id = "4";
        assertNull("The condition should succeed because the argument is invalid",
                createBinaryQuestion(null, id));
        assertNull("\"The condition should succeed because the argument is invalid",
                createBinaryQuestion("", id));
        assertNull("The condition should succeed because the id is null",
                createBinaryQuestion("Question", null));
        assertNull("The condition should succeed because the id is empty",
                createBinaryQuestion(id, ""));
        assertNotNull("\"The condition should succeed because the arguments are valid",
                createBinaryQuestion("Translating things is fun :))))))", id));
        assertNotNull("Empty constructor test", new BinaryQuestion());
    }

    /**
     * Test of hashCode method, of class BinaryQuestion.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        String q = "IT'S REALLY FUN c:";
        String id = "34";
        BinaryQuestion instance = new BinaryQuestion(q, id);

        BinaryQuestion other = new BinaryQuestion(q, id);

        assertEquals(instance.hashCode(), other.hashCode());
    }

    /**
     * Test of equals method, of class BinaryQuestion.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        String id = "5L";
        String otherID = "4L";
        BinaryQuestion instance = new BinaryQuestion("QuestaoTeste", id);
        BinaryQuestion instance2 = new BinaryQuestion("QuestaoTeste", id);
        BinaryQuestion instance3 = new BinaryQuestion("TesteQuestao", otherID);
        BinaryQuestion instance4 = new BinaryQuestion("QuestaoTeste", otherID);
        String outsiderID = "L3";
        QuantitativeQuestion outsider = new QuantitativeQuestion("QuestaoTeste",
                outsiderID, 0.0, 5.0);
        assertEquals("The condition should succeed because we are "
                + "comparing the same instances", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing"
                + " instances of different classes", instance, "bananas");
        assertNotEquals("The condition should succeed because we are "
                + "comparing the an instance with a null value", instance, null);
        assertEquals("The condition should succeed because we are comparing two"
                + " instances with the same properties", instance, instance2);
        assertNotEquals("The condition should succeed because we are comparing"
                + " questions with different ids", instance, instance4);
        assertNotEquals("The condition should succeed because we are comparing"
                + " instances with different questions and ids", instance, instance3);
        assertNotEquals("The condition should succeed because we are comparing "
                + "questions of different types", instance, outsider);
    }

    /**
     * Test of content method, of class BinaryQuestion.
     */
    @Test
    public void testContent() {
        System.out.println("content");
        String q = "Do you like Translating Java Docs?";
        String id = "A5";
        BinaryQuestion instance = new BinaryQuestion(q, id);
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
        String id = "5";
        BinaryQuestion instance = new BinaryQuestion("I'm lost in translation", id);
        String expResult = QuestionAnswerTypes.BINARY.toString();
        String result = instance.type();
        assertEquals(expResult, result);
    }

    /**
     * Builds a BinaryQuestion with a question and an id.
     *
     * @param question the question itself
     * @param questionID the question's ID
     * @return BinaryQuestion instance
     */
    private BinaryQuestion createBinaryQuestion(String question, String questionID) {
        try {
            return new BinaryQuestion(question, questionID);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
