package cdioil.domain;

import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Unit testing class for methods that are common to all subclasses of abstract
 * class Question.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class QuestionTest {

    private Question instance;

    @Before
    public void setUp() {
        String question = "Questao";
        String id = "54";
        instance = new BinaryQuestion(question, id);
    }

    /**
     * Test of hashCode method, of class Question.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        String question = "Questao";
        String id = "54";
        Question other = new BinaryQuestion(question, id);
        int expResult = other.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Question.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        LinkedList<QuestionOption> outsiderList = new LinkedList<>();
        MultipleChoiceQuestionOption outsiderOption1 = new MultipleChoiceQuestionOption("Option 1");
        outsiderList.add(outsiderOption1);
        String question = "Questao";
        String otherQuestion = "Questao 2";
        String id = "54";
        String otherID = "34";
        Question other = new BinaryQuestion(question, id);
        assertNotEquals("The condition should succeed because we are comparing "
                + "an instance with a null value", instance, null);
        assertEquals("The condition should succeed because we are comparing "
                + "the same instance", instance, instance);
        assertEquals("The condition should succeed because we are comparing "
                + "instances that have the same properties", instance, other);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes", instance, "bananas");
        Question outsider = new MultipleChoiceQuestion(question, id, outsiderList);
        other = new BinaryQuestion(otherQuestion, id);
        assertNotEquals("The condition should succeed because we are comparing "
                + "questions that have different contents", instance, other);
        other = new BinaryQuestion(question, otherID);
        assertNotEquals("The condition should succeed because we are comparing "
                + "questions that have different ids", instance, other);
    }

    /**
     * Test of toString method, of class Question.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "Questao";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
