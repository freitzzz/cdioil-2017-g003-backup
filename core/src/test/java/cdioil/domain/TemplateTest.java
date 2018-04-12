package cdioil.domain;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests of the class Template.
 *
 * @author Ana Guerra (1161191)
 */
public class TemplateTest {

    Category cat = new Category("Pai", "10DC");
    Calendar data = Calendar.getInstance();
    List<Question> listaQuestoes = new LinkedList<>();
    List<Survey> listaInqueritos = new LinkedList<>();
    /**
     * Instance of Category for test purposes.
     */
    Category c;

    /**
     * Instance of Template for test purposes.
     */
    Template t;

    /**
     * Instance of Calendar for test purposes.
     */
    Calendar date;

    /**
     * List of Questions for test purposes.
     */
    List<Question> questions;

    /**
     * List of Surveys for test purposes.
     */
    List<Survey> surveys;

    public TemplateTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        c = new Category("Pai", "10DC");
        date = Calendar.getInstance();
        questions = new LinkedList<>();
        surveys = new LinkedList<>();
        t = new Template(c);
    }

    @After
    public void tearDown() {
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
     * Test of the method addQuestion, of the class Template.
     */
    @Test
    public void testAddQuestion() {
        System.out.println("addQuestion");
        String id = "O4";
        Question q = new BinaryQuestion("QuestaoTeste", id);
        assertTrue("Deveria ser possível adicionar", t.addQuestion(q));
        assertFalse("Questão null", t.addQuestion(null));
        t.addQuestion(q);
        assertFalse("Questão já existente", t.addQuestion(q));
    }

    /**
     * Test of the method removeQuestion, of the class Template.
     */
    @Test
    public void testRemoveQuestion() {
        System.out.println("removeQuestion");
        String id = "F3";
        Question q = new BinaryQuestion("QuestaoTeste", id);
        t.addQuestion(q);
        assertTrue("Deveria ser possível remover", t.removeQuestion(q));
        assertFalse("Questão null", t.removeQuestion(null));
        t.removeQuestion(q);
        assertFalse("Questão não existente", t.removeQuestion(q));
    }

    /**
     * Test of the method isQuestionValid, of the class Template.
     */
    @Test
    public void testIsQuestionValid() {
        System.out.println("isQuestaoValida");
        String id = "W2";
        Question q = new BinaryQuestion("QuestaoTeste", id);
        t.addQuestion(q);
        assertTrue("Deveria ser válida", t.isQuestionValid(q));
        assertFalse("Questão null", t.isQuestionValid(null));
        t.removeQuestion(q);
        assertFalse("Questão não existente", t.isQuestionValid(q));
    }

    /**
     * Test of the method toString, of the class Template.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Template t2 = new Template(c);

        assertEquals("Descrições iguais", t2.toString(), t.toString());
    }

    /**
     * Test of the method hashCode, of the class Template.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Template t2 = new Template(c);

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
        assertFalse(t.equals(cat));
        //test with null instance
        Template tNull = null;
        assertFalse(t.equals(tNull));
        assertNotEquals("Instância de Template diferente", new Template(new Category("Outro", "11DC")), t);
        assertEquals("Instância de Template igual", new Template(c), t);
        Template tDifferentQuestions = new Template(new Category("Outro", "11DC"));
        tDifferentQuestions.addQuestion(new MultipleChoiceQuestion());
        assertFalse(t.equals(tDifferentQuestions));
    }
}
