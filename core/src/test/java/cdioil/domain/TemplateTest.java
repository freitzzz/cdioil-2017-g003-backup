/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this t file, choose Tools | Templates
 * and open the t in the editor.
 */
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
     * List of Questions for test purposes.
     */
    List<Inquerito> surveys;

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
        c = new Category("Pai", "10DC","10DC");
        date = Calendar.getInstance();
        questions = new LinkedList<>();
        surveys = new LinkedList<>();
        t = new Template(c);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of the method addQuestion, of the class Template.
     */
    @Test
    public void testAddQuestion() {
        System.out.println("addQuestion");
        Question q = new BinaryQuestion("QuestaoTeste");
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
        Question q = new BinaryQuestion("QuestaoTeste");
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
        Question q = new BinaryQuestion("QuestaoTeste");
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
        assertNotEquals("Objeto null não é igual", null, t);
        assertNotEquals("Instância de outra classe não é igual", new QRCode("1"), t);
        assertNotEquals("Instância de outra classe não é igual", new QRCode("1"), t);
        assertNotEquals("Instância de Template diferente", new Template(null), t);
        assertEquals("Instância de Template igual", new Template(c), t);
    }
}
