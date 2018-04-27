package cdioil.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
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
        t = new Template("template", questionGroup);
    }
    
    @Test
    public void ensureJPAConstructorCreatesInstance(){
        t = new Template();
        assertNotNull(t);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullQuestionGroupThrowsException(){
        t = new Template("Template", null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureEmptyQuestionGroupThrowsException(){
        t = new Template("Template", new QuestionGroup("Empty Question Group"));
    }
    
    /**
     * Test of the method hashCode, of the class Template.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "435"));
        Template t2 = new Template("template", questionGroup);

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
        assertNotEquals("Instância de Template diferente", new Template("template", questionGroup), t);
        questionGroup2.addQuestion(new BinaryQuestion("Question", "435"));
        assertEquals("Instância de Template igual", new Template("template", questionGroup2), t);
        questionGroupDiffQuestions.addQuestion(new BinaryQuestion("Question 3", "987"));
        Template tDifferentQuestions = new Template("template", questionGroupDiffQuestions);
        tDifferentQuestions.getQuestionGroup().addQuestion(new BinaryQuestion("Question", "342"));
        t.getQuestionGroup().addQuestion(new BinaryQuestion("Question 2", "2532"));
        assertFalse(t.equals(tDifferentQuestions));
    }
    
    /**
     * Test of the method toString, of the class Template.
     */
    @Test
    public void testToString(){
        System.out.println("toString");
        Question q1 = new BinaryQuestion("Do you like apples?", "A32");
        QuestionGroup questionGroup1 = new QuestionGroup("QuestionGroup 1");
        questionGroup1.addQuestion(q1);
        Template t1 = new Template("Template", questionGroup1);
        Template t2 = new Template("Template", questionGroup1);
        
        assertEquals(t1.toString(), t2.toString());
    }
}
