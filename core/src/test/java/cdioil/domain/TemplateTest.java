package cdioil.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the class Template.
 *
 * @author Ana Guerra (1161191)
 */
public class TemplateTest {

    /**
     * Template for test purposes.
     */
    private Template t;

    @Before
    public void setUp() {
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "435"));
        t = new Template(questionGroup);
    }

    /**
     * Test of constructor of class Template
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureTemplateIsBuiltCorrectly() {
        System.out.println("Template()");
        t = new Template();
        t = new Template(null);
        t = new Template(new QuestionGroup("QuestionGroup"));
    }

    /**
     * Test of the method hashCode, of the class Template.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "435"));
        Template t2 = new Template(questionGroup);

        assertEquals("Hash codes iguais", t2.hashCode(), t.hashCode());
    }

    /**
     * Test of the method equals, of the class Template.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        //test with same instance
        assertTrue(t.equals(t));
        //test with null parameter
        assertFalse(t.equals(null));
        //test with instance of other class
        assertFalse(t.equals("bananas"));
        //test with null instance
        Template tNull = null;
        assertFalse(t.equals(tNull));
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup 2");
        questionGroup.addQuestion(new BinaryQuestion("Question", "435"));
        QuestionGroup questionGroup2 = new QuestionGroup("QuestionGroup");
        QuestionGroup questionGroupDiffQuestions = new QuestionGroup("QuestionGroup Different Questions");
        assertNotEquals("Instância de Template diferente", new Template(questionGroup), t);
        questionGroup2.addQuestion(new BinaryQuestion("Question", "435"));
        assertEquals("Instância de Template igual", new Template(questionGroup2), t);
        questionGroupDiffQuestions.addQuestion(new BinaryQuestion("Question 3", "987"));
        Template tDifferentQuestions = new Template(questionGroupDiffQuestions);
        tDifferentQuestions.getQuestionGroup().addQuestion(new BinaryQuestion("Question", "342"));
        t.getQuestionGroup().addQuestion(new BinaryQuestion("Question 2", "2532"));
        assertFalse(t.equals(tDifferentQuestions));
    }
}
