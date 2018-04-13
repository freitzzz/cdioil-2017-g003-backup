package cdioil.domain;

import java.util.LinkedList;
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
        t = new Template("QuestionGroup");
    }

    /**
     * Test of constructor of class Template
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureTemplateIsBuiltCorrectly() {
        System.out.println("Template()");
        t = new Template();
        t = new Template(null);
    }

    /**
     * Test of the method hashCode, of the class Template.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Template t2 = new Template("QuestionGroup");

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
        assertNotEquals("Instância de Template diferente", new Template("QuestionGroup 2"), t);
        assertEquals("Instância de Template igual", new Template("QuestionGroup"), t);
        Template tDifferentQuestions = new Template("Different Questions");
        BinaryQuestionOption option1 = new BinaryQuestionOption(Boolean.FALSE);
        BinaryQuestionOption option2 = new BinaryQuestionOption(Boolean.TRUE);
        LinkedList<QuestionOption> list = new LinkedList<>();
        list.add(option1);
        list.add(option2);
        tDifferentQuestions.getQuestionGroup().addQuestion(new BinaryQuestion("Question", "342", list));
        t.getQuestionGroup().addQuestion(new BinaryQuestion("Question 2", "2532", list));
        assertFalse(t.equals(tDifferentQuestions));
    }
}
