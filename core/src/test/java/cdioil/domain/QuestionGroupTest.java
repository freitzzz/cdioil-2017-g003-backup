package cdioil.domain;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for class QuestionGroup.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class QuestionGroupTest {

    /**
     * Constructor tests.
     */
    @Test
    public void testConstructor() {
        System.out.println("constructor");
        assertNotNull("Empty constructor test", new QuestionGroup());
        assertNull("This condition should succeed because the title is null",
                createQuestionGroup(null));
        assertNull("This condition should succeed because the title is empty",
                createQuestionGroup(""));
        assertNotNull("This condition should succeed because the title is valid",
                createQuestionGroup("Title"));
    }

    /**
     * Test of getQuestions method, of class QuestionGroup.
     */
    @Test
    public void testGetQuestions() {
        System.out.println("getQuestions");
        QuestionGroup instance = new QuestionGroup("QuestionGroup");
        Set<Question> expResult = new HashSet<>();
        Set<Question> result = instance.getQuestions();
        assertEquals(expResult, result);
    }

    /**
     * Test of containsQuestion method, of class QuestionGroup.
     */
    @Test
    public void testContainsQuestion() {
        System.out.println("containsQuestion");
        String id = "4";
        Question question = new BinaryQuestion("QuestaoTeste", id);
        QuestionGroup instance = new QuestionGroup("QuestionGroup");
        assertFalse("The condition should succeed because the question doesn't"
                + " exist in the set", instance.containsQuestion(question));
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
        String id = "4";
        Question question = new BinaryQuestion("QuestaoTeste", id);
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
        String id = "4T";
        Question question = new BinaryQuestion("QuestaoTeste", id);
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

        //Mutation tests
        assertNotEquals("".hashCode(), result);
        assertEquals(new HashSet<>().hashCode() + "QuestionGroup".hashCode(), result);
    }

    /**
     * Test of equals method, of class QuestionGroup.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        QuestionGroup other = new QuestionGroup("Group");
        QuestionGroup instance = new QuestionGroup("Group");
        String id = "4";
        String otherID = "5";
        Question q = new BinaryQuestion("QuestaoTeste", id);
        assertEquals("The condition should succeed because we are comparing"
                + "the same instances.", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes.", instance, "bananas");
        assertNotEquals("The condition should succeed because we are comparing"
                + "an instance with a null value.", instance, null);
        assertEquals("The condition should succeed because we are comparing"
                + "instances with the same properties.", instance, other);
        instance.addQuestion(q);
        other.addQuestion(new BinaryQuestion("QuestaoTeste2", otherID));
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
    
    /**
     * Test of toString method, of class QuestionGroup.
     */
    @Test
    public void testToString(){
        System.out.println("toString");
        String title = "title";
        QuestionGroup instance = createQuestionGroup(title);
        QuestionGroup other = createQuestionGroup(title);
        
        assertEquals(instance.toString(),other.toString());
        assertNotEquals(instance.toString(),null);
    }

    /**
     * Builds a QuestionGroup instance.
     *
     * @param title question group title
     * @return QuestionGroup instance or null if an exception ocurred
     */
    private QuestionGroup createQuestionGroup(String title) {
        try {
            return new QuestionGroup(title);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
