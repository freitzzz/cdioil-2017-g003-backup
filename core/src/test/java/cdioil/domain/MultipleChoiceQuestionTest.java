package cdioil.domain;

import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for MultipleChoiceQuestion.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class MultipleChoiceQuestionTest {

    /**
     * Tests for the constructor.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor Tests");
        LinkedList<String> list = new LinkedList<>();
        String s = "test";
        list.add(s);
        String id = "5";
        assertNull("The condition should succeed because the question is null",
                createMCQuestion(null, id, list));
        assertNull("\"The condition should succeed because the question is empty",
                createMCQuestion("", id, list));
        assertNull("The condition should succeed because the question id is null",
                createMCQuestion("Question", null, list));
        assertNull("The condition should succeed because the question id is empty",
                createMCQuestion("Question", "", list));
        assertNotNull("\"The condition should succeed because the arguments are valid",
                createMCQuestion("Question", id, list));
        assertNull("The condition should succeed because the set is null",
                createMCQuestion("Question", id, null));
        list.remove(s);
        assertNull("The condition should succeed because the set is empty",
                createMCQuestion("Question", id, list));
        assertNotNull("Empty constructor test", new MultipleChoiceQuestion());
    }

    /**
     * Test of hashCode method, of class MultipleChoiceQuestion.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        String q = "Question";
        LinkedList<String> list = new LinkedList<>();
        String s = "test";
        list.add(s);
        String id = "5";
        MultipleChoiceQuestion instance = createMCQuestion(q, id, list);

        MultipleChoiceQuestion other = createMCQuestion(q, id, list);

        assertEquals(instance.hashCode(), other.hashCode());
    }

    /**
     * Test of equals method, of class MultipleChoiceQuestion.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        LinkedList<String> list = new LinkedList<>();
        String s = "test";
        list.add(s);
        String mcID = "5";
        String otherMCId = "6L";
        MultipleChoiceQuestion instance = createMCQuestion("Question", mcID, list);
        MultipleChoiceQuestion instance2 = createMCQuestion("Question", mcID, list);
        MultipleChoiceQuestion instance3 = createMCQuestion("Question3", otherMCId, list);
        MultipleChoiceQuestion instance5 = createMCQuestion("Question", otherMCId, list);
        String id = "3";
        BinaryQuestion instance4 = new BinaryQuestion("Question", id);
        assertEquals("The condition should succeed because we are "
                + "comparing the same instances", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing"
                + " instances of different classes", instance, "bananas");
        assertNotEquals("The condition should succeed because we are "
                + "comparing the an instance with a null value", instance, null);
        assertEquals("The condition should succeed because we are comparing two"
                + " instances with the same properties", instance, instance2);
        assertNotEquals("The condition should succeed because we are comparing "
                + "questions with different IDs", instance, instance5);
        assertNotEquals("The condition should succeed because we are comparing"
                + " instances with different properties", instance, instance3);
        assertNotEquals("The condition should succeed because we are comparing "
                + "questions of different types", instance, instance4);
    }

    /**
     * Test of content method, of class MultipleChoiceQuestion.
     */
    @Test
    public void testContent() {
        System.out.println("content");
        String q = "Question";
        LinkedList<String> list = new LinkedList<>();
        String s = "test";
        list.add(s);
        String id = "4";
        MultipleChoiceQuestion instance = new MultipleChoiceQuestion(q, id, list);
        String expResult = q;
        String result = instance.content();
        assertEquals(expResult, result);
    }

    /**
     * Test of type method, of class MultipleChoiceQuestion.
     */
    @Test
    public void testType() {
        System.out.println("type");
        LinkedList<String> list = new LinkedList<>();
        String s = "test";
        list.add(s);
        String q = "Question";
        String id = "4T";
        MultipleChoiceQuestion instance = new MultipleChoiceQuestion(q, id, list);
        String expResult = QuestionAnswerTypes.MULTIPLE_CHOICE.toString();
        String result = instance.type();
        assertEquals(expResult, result);
    }

    /**
     * Builds a MultipleChoiceQuestion with a question and a set of options.
     *
     * @param question the question itself
     * @param questionID the question's ID
     * @param options set with the options
     * @return MultipleChoiceQuestion instance
     */
    private MultipleChoiceQuestion createMCQuestion(String question, String questionID, LinkedList<String> options) {
        try {
            return new MultipleChoiceQuestion(question, questionID, options);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
