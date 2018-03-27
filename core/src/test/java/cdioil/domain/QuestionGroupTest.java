package cdioil.domain;

import java.util.HashSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for class QuestionGroup.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class QuestionGroupTest {

    /**
     * Test of getQuestions method, of class QuestionGroup.
     */
    @Test
    public void testGetQuestions() {
        System.out.println("getQuestions");
        QuestionGroup instance = new QuestionGroup("QuestionGroup");
        HashSet<Question> expResult = new HashSet<>();
        HashSet<Question> result = instance.getQuestions();
        assertEquals(expResult, result);
    }

    /**
     * Test of containsQuestion method, of class QuestionGroup.
     */
    @Test
    public void testContainsQuestion() {
        System.out.println("containsQuestion");
        Question question = new BinaryQuestion("QuestaoTeste");
        QuestionGroup instance = new QuestionGroup("QuestionGroup");
        instance.addQuestion(question);
        assertTrue("The condition should succeed because the question exists"
                + "in the set.", instance.containsQuestion(question));
    }

    /**
     * Test of addQuestion method, of class QuestionGroup.
     */
    @Test
    public void testAddQuestion() {
        System.out.println("addQuestion");
        Question question = new BinaryQuestion("QuestaoTeste");
        QuestionGroup instance = new QuestionGroup("QuestionGroup");
        assertTrue("The condition should succeed because the question"
                + "can be added.", instance.addQuestion(question));
        assertFalse("The condition should succeed because the question"
                + "already exists in the set and cannot be added",
                instance.addQuestion(question));
    }

    /**
     * Test of removeQuestion method, of class QuestionGroup.
     */
    @Test
    public void testRemoveQuestion() {
        System.out.println("removeQuestion");
        Question question = new BinaryQuestion("QuestaoTeste");
        QuestionGroup instance = new QuestionGroup("QuestionGroup");
        assertFalse("The condition should succeed because the question"
                + "doesn't exist.", instance.removeQuestion(question));
        instance.addQuestion(question);
        assertTrue("The condition should succeed because the question exists",
                instance.removeQuestion(question));
    }

    /**
     * Test of hashCode method, of class QuestionGroup.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        QuestionGroup instance = new QuestionGroup("QuestionGroup");
        QuestionGroup other = new QuestionGroup("QuestionGroup");
        int expResult = other.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class QuestionGroup.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        QuestionGroup other = new QuestionGroup("Group");
        QuestionGroup instance = new QuestionGroup("Group");
        Question q = new BinaryQuestion("QuestaoTeste");
        assertEquals("The condition should succeed because we are comparing"
                + "the same instances.", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes.", instance, "bananas");
        assertNotEquals("The condition should succeed because we are comparing"
                + "an instance with a null value.", instance, null);
        assertEquals("The condition should succeed because we are comparing"
                + "instances with the same properties.", instance, other);
        instance.addQuestion(q);
        other.addQuestion(new BinaryQuestion("QuestaoTeste2"));
        assertNotEquals("The condition should succeed because we are comparing"
                + "instances with different sets of questions.", instance, other);
        QuestionGroup instance2 = new QuestionGroup("Group2");
        instance2.addQuestion(q);
        assertNotEquals("The condition should succeed because we are comparing"
                + "instances with different titles.", instance, instance2);
        QuestionGroup instance3 = new QuestionGroup("Group2");
        instance3.addQuestion(q);
        assertEquals("The condition should succeed because we are comparing"
                + "instances with the same question set and title", instance2,
                instance3);
    }

}
