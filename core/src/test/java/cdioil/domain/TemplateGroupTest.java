package cdioil.domain;

import java.util.HashSet;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for TemplateGroup class
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class TemplateGroupTest {

    /**
     * Constructor tests.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor");
        assertNotNull("Empty constructor test", new TemplateGroup());
        assertNull("This condition should succeed because the title is null",
                createTemplateGroup(null));
        assertNull("This condition should succeed because the title is empty",
                createTemplateGroup(""));
        assertNotNull("This condition should succeed because the title is valid",
                createTemplateGroup("Title"));
    }

    /**
     * Test of getQuestions method, of class QuestionGroup.
     */
    @Test
    public void testGetQuestions() {
        System.out.println("getQuestions");
        QuestionGroup instance = new QuestionGroup("QuestionGroup");
        HashSet<Question> expResult = new HashSet<>();
        HashSet<Question> result = (HashSet<Question>) instance.getQuestions();
        assertEquals(expResult, result);
    }

    /**
     * Test of containsTemplate method, of class TemplateGroup.
     */
    @Test
    public void testContainsTemplate() {
        System.out.println("containsTemplate");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "324"));
        Template template = new Template("Template1", questionGroup);
        TemplateGroup instance = new TemplateGroup("TemplateGroup");
        assertFalse("The condition should succeed because the question doesn't "
                + "exist in the set", instance.containsTemplate(template));
        instance.addTemplate(template);
        assertTrue("The condition should succeed because the question exists"
                + "in the set.", instance.containsTemplate(template));
    }

    /**
     * Test of TemplateQuestion method, of class TemplateGroup.
     */
    @Test
    public void testAddTemplate() {
        System.out.println("addTemplate");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "324"));
        Template template = new Template("Template2", questionGroup);
        TemplateGroup instance = createTemplateGroup("TemplateGroup");
        assertTrue("The condition should succeed because the template"
                + "can be added.", instance.addTemplate(template));
        assertFalse("The condition should succeed because the template"
                + "already exists in the set and cannot be added",
                instance.addTemplate(template));
    }

    /**
     * Test of removeTemplate method, of class TemplateGroup.
     */
    @Test
    public void testRemoveTemplate() {
        System.out.println("removeTemplate");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "324"));
        Template template = new Template("template3", questionGroup);
        TemplateGroup instance = createTemplateGroup("TemplateGroup");
        assertFalse("The condition should succeed because the template"
                + "doesn't exist.", instance.removeTemplate(template));
        instance.addTemplate(template);
        assertTrue("The condition should succeed because the template exists",
                instance.removeTemplate(template));
    }

    /**
     * Test of hashCode method, of class TemplateGroup.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        String title = "Template Group";
        TemplateGroup instance = createTemplateGroup(title);
        TemplateGroup other = createTemplateGroup(title);
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "324"));
        Template t = new Template("template4", questionGroup);
        instance.addTemplate(t);
        other.addTemplate(t);
        assertTrue(instance.hashCode() == other.hashCode());
    }

    /**
     * Test of equals method, of class TemplateGroup.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        TemplateGroup other = createTemplateGroup("Group");
        TemplateGroup instance = createTemplateGroup("Group");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "324"));
        Template template = new Template("Template5", questionGroup);
        template.getQuestionGroup().addQuestion(new BinaryQuestion("Question", "43"));
        assertEquals("The condition should succeed because we are comparing"
                + "the same instances.", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes.", instance, "bananas");
        assertNotEquals("The condition should succeed because we are comparing"
                + "an instance with a null value.", instance, null);
        assertEquals("The condition should succeed because we are comparing"
                + "instances with the same properties.", instance, other);
        instance.addTemplate(template);
        QuestionGroup anotherQuestionGroup = new QuestionGroup("QuestionGroup 2");
        anotherQuestionGroup.addQuestion(new BinaryQuestion("Question 2", "345"));
        other.addTemplate(new Template("Template5", anotherQuestionGroup));
        assertNotEquals("The condition should succeed because we are comparing"
                + "instances with different sets of templates.", instance, other);
        TemplateGroup instance2 = createTemplateGroup("Group2");
        instance2.addTemplate(template);
        assertNotEquals("The condition should succeed because we are comparing"
                + "instances with different titles.", instance, instance2);
        TemplateGroup instance3 = createTemplateGroup("Group2");
        instance3.addTemplate(template);
        assertEquals("The condition should succeed because we are comparing"
                + "instances with the same template set and title", instance2,
                instance3);
    }

    /**
     * Builds a TemplateGroup instance.
     *
     * @param title template group title
     * @return TemplateGroup instance or null if an exception ocurred
     */
    private TemplateGroup createTemplateGroup(String title) {
        try {
            return new TemplateGroup(title);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
