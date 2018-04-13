package cdioil.domain;

import java.util.LinkedList;
import java.util.List;
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
        BinaryQuestionOption option1 = new BinaryQuestionOption(Boolean.FALSE);
        BinaryQuestionOption option2 = new BinaryQuestionOption(Boolean.TRUE);
        LinkedList<QuestionOption> list = new LinkedList<>();
        assertNull("The condition should succeed because the list is null",
                createBinaryQuestion("Question", id, null));
        assertNull("The condition should succeed because the list is empty",
                createBinaryQuestion("Question", id, list));
        list.add(option1);
        list.add(option2);
        assertNull("The condition should succeed because the question text"
                + " is null", createBinaryQuestion(null, id, list));
        assertNull("The condition should succeed because the question text is "
                + "empty", createBinaryQuestion("", id, list));
        assertNull("The condition should succeed because the id is null",
                createBinaryQuestion("Question", null, list));
        assertNull("The condition should succeed because the id is empty",
                createBinaryQuestion(id, "", list));
        assertNotNull("The condition should succeed because the arguments are valid",
                createBinaryQuestion("Translating things is fun :))))))", id, list));
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
        BinaryQuestionOption option1 = new BinaryQuestionOption(Boolean.FALSE);
        BinaryQuestionOption option2 = new BinaryQuestionOption(Boolean.TRUE);
        LinkedList<QuestionOption> list = new LinkedList<>();
        list.add(option1);
        list.add(option2);
        BinaryQuestion instance = new BinaryQuestion(q, id, list);

        BinaryQuestion other = new BinaryQuestion(q, id, list);

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
        BinaryQuestionOption option1 = new BinaryQuestionOption(Boolean.FALSE);
        BinaryQuestionOption option2 = new BinaryQuestionOption(Boolean.TRUE);
        QuantitativeQuestionOption option3 = new QuantitativeQuestionOption(3.0);
        LinkedList<QuestionOption> list = new LinkedList<>();
        LinkedList<QuestionOption> outsiderList = new LinkedList<>();
        list.add(option1);
        list.add(option2);
        outsiderList.add(option3);
        BinaryQuestion instance = new BinaryQuestion("QuestaoTeste", id, list);
        BinaryQuestion instance2 = new BinaryQuestion("QuestaoTeste", id, list);
        BinaryQuestion instance3 = new BinaryQuestion("TesteQuestao", otherID, list);
        BinaryQuestion instance4 = new BinaryQuestion("QuestaoTeste", otherID, list);
        String outsiderID = "L3";
        QuantitativeQuestion outsider = new QuantitativeQuestion("QuestaoTeste",
                outsiderID, outsiderList);
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
     * Test of type method, of class BinaryQuestion.
     */
    @Test
    public void testType() {
        System.out.println("type");
        String id = "5";
        BinaryQuestionOption option1 = new BinaryQuestionOption(Boolean.FALSE);
        BinaryQuestionOption option2 = new BinaryQuestionOption(Boolean.TRUE);
        LinkedList<QuestionOption> list = new LinkedList<>();
        list.add(option1);
        list.add(option2);
        BinaryQuestion instance = new BinaryQuestion("I'm lost in translation", id, list);
        String expResult = QuestionAnswerTypes.BINARY.toString();
        String result = instance.type();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class BinaryQuestion.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        BinaryQuestionOption option1 = new BinaryQuestionOption(Boolean.FALSE);
        BinaryQuestionOption option2 = new BinaryQuestionOption(Boolean.TRUE);
        LinkedList<QuestionOption> list = new LinkedList<>();
        list.add(option1);
        list.add(option2);
        BinaryQuestion instance = createBinaryQuestion("Question", "98", list);
        BinaryQuestion other = createBinaryQuestion("Question", "98", list);
        assertTrue(instance.toString().equals(other.toString()));
    }

    /**
     * Builds a BinaryQuestion with a question and an id.
     *
     * @param question the question itself
     * @param questionID the question's ID
     * @param optionList the question's option list
     * @return BinaryQuestion instance
     */
    private BinaryQuestion createBinaryQuestion(String question, String questionID, List<QuestionOption> optionList) {
        try {
            return new BinaryQuestion(question, questionID, optionList);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
