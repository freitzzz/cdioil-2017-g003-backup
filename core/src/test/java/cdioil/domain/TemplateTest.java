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
    private Template instance;

    @Before
    public void setUp() {
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "435"));
        instance = new Template("template", questionGroup);
    }
    
    @Test
    public void ensureJPAConstructorCreatesInstance(){
        instance = new Template();
        assertNotNull(instance);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullQuestionGroupThrowsException(){
        instance = new Template("Template", null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureEmptyQuestionGroupThrowsException(){
        instance = new Template("Template", new QuestionGroup("Empty Question Group"));
    }
   
    @Test
    public void testGetTitle(){
        assertEquals("template", instance.getTitle());    
    }
    /**
     * Test of the method hashCode, of the class Template.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "435"));
        Template other = new Template("template", questionGroup);
        assertEquals("Hash codes iguais", other.hashCode(), instance.hashCode());
        
        //Mutation tests
        assertNotEquals("".hashCode(),instance.hashCode());
        int num = 43 * 7 + questionGroup.hashCode();
        assertEquals(num,instance.hashCode());
    }

    /**
     * Test of the method equals, of the class Template.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        //test with same instance
        assertTrue(instance.equals(instance));
        //test with null parameter
        assertFalse(instance.equals(null));
        //test with instance of other class
        assertFalse(instance.equals("bananas"));
        //test with null instance
        Template tNull = null;
        assertFalse(instance.equals(tNull));
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup 2");
        questionGroup.addQuestion(new BinaryQuestion("Question", "435"));
        QuestionGroup questionGroup2 = new QuestionGroup("QuestionGroup");
        QuestionGroup questionGroupDiffQuestions = new QuestionGroup("QuestionGroup Different Questions");
        assertNotEquals("Instância de Template diferente", new Template("template", questionGroup), instance);
        questionGroup2.addQuestion(new BinaryQuestion("Question", "435"));
        assertEquals("Instância de Template igual", new Template("template", questionGroup2), instance);
        questionGroupDiffQuestions.addQuestion(new BinaryQuestion("Question 3", "987"));
        Template tDifferentQuestions = new Template("template", questionGroupDiffQuestions);
        tDifferentQuestions.getQuestionGroup().addQuestion(new BinaryQuestion("Question", "342"));
        instance.getQuestionGroup().addQuestion(new BinaryQuestion("Question 2", "2532"));
        assertFalse(instance.equals(tDifferentQuestions));
    }
    
    /**
     * Test of the method toString, of the class Template.
     */
    @Test
    public void testToString(){
        System.out.println("toString");
        Question question = new BinaryQuestion("Do you like apples?", "A32");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup 1");
        questionGroup.addQuestion(question);
        Template other = new Template("Template", questionGroup);
        Template another = new Template("Template", questionGroup);
        
        assertEquals(other.toString(), another.toString());
        
        //Mutation test
        assertNotEquals(other.toString(),null);
    }
}
