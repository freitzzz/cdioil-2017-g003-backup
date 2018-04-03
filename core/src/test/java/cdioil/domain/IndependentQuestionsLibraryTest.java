package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for IndependentQuestionsLibrary class.
 *
 * TODO After all types of questions are implemented, update tests to consider
 * all types of questions in the library
 *
 * @author @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class IndependentQuestionsLibraryTest {

    /**
     * Test of hashCode method, of class IndependentQuestionsLibrary.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        IndependentQuestionsLibrary instance = new IndependentQuestionsLibrary();
        IndependentQuestionsLibrary other = new IndependentQuestionsLibrary();
        int expResult = other.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class IndependentQuestionsLibrary.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        IndependentQuestionsLibrary instance = new IndependentQuestionsLibrary();
        IndependentQuestionsLibrary instance2 = new IndependentQuestionsLibrary();
        IndependentQuestionsLibrary instance3 = new IndependentQuestionsLibrary();
        assertNotEquals("The condition should succeed because we are comparing"
                + "an instance with a null value", instance, null);
        assertNotEquals("The condition should succeed because we are comparing"
                + "instances of different classes", instance, "bananas");
        assertEquals("The condition should succeed because we are comparing"
                + "the same instance", instance, instance);
        assertEquals("The condition should succeed because we are comparing"
                + "instances with the same properties", instance, instance2);
        String id = "5A";
        String otherID = "6B";
        instance.addQuestion(new BinaryQuestion("QuestaoTeste", id));
        assertNotEquals("The condition should succeed because we are comparing"
                + " a library with one question to a library"
                + " with no questions", instance, instance2);
        instance2.addQuestion(new BinaryQuestion("QuestaoTeste", id));
        assertEquals("The condition should succeed because we are comparing"
                + " instances with the same properties", instance, instance2);
        instance3.addQuestion(new BinaryQuestion("TesteQuestao", otherID));
        assertNotEquals("The condition should succeed because we are comparing"
                + "libraries that have different questions", instance, instance3);
    }

    /**
     * Test of addQuestion method, of class IndependentQuestionsLibrary.
     */
    @Test
    public void testAddQuestion() {
        System.out.println("addQuestion");
        String id = "F4";
        Question question = new BinaryQuestion("QuestaoTeste", id);
        IndependentQuestionsLibrary instance = new IndependentQuestionsLibrary();
        assertTrue("The condition should be true because this question hasn't"
                + "been added to the library.", instance.addQuestion(question));
        assertFalse("The condition should be false because the question has "
                + "already been added to the library", instance.addQuestion(question));
    }

    /**
     * Test of removeQuestion method, of class IndependentQuestionsLibrary.
     */
    @Test
    public void testRemoveQuestion() {
        System.out.println("removeQuestion");
        String id = "H4";
        Question question = new BinaryQuestion("QuestaoTeste", id);
        IndependentQuestionsLibrary instance = new IndependentQuestionsLibrary();
        assertFalse("The condition should be false because the question doesn't"
                + "exist in the library and therefore cannot be removed",
                instance.removeQuestion(question));
        instance.addQuestion(question);
        assertTrue("The condition should be true because the question exists in "
                + "the library and therefore can be removed", instance.removeQuestion(question));
    }

    /**
     * Test of doesQuestionExist method, of class IndependentQuestionsLibrary.
     */
    @Test
    public void testDoesQuestionExist() {
        System.out.println("doesQuestionExist");
        String id = "R5";
        Question question = new BinaryQuestion("QuestaoTeste", id);
        IndependentQuestionsLibrary instance = new IndependentQuestionsLibrary();
        assertFalse("The condition should be false because the question doesn't"
                + "exist in the library", instance.doesQuestionExist(question));
        instance.addQuestion(question);
        assertTrue("The condition should be true because the question was added"
                + "to the library", instance.doesQuestionExist(question));
    }

}
