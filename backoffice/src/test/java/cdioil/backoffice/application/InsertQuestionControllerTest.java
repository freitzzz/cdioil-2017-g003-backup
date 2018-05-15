/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.application;

import cdioil.domain.MultipleChoiceQuestionOption;
import cdioil.domain.QuantitativeQuestionOption;
import cdioil.domain.QuestionOption;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.Name;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.SystemUser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the class InsertQuestionController.
 *
 * @author Rita Gonçalves (1160912)
 */
public class InsertQuestionControllerTest {

    /**
     * Instance of InsertQuestionController for test purposes.
     */
    private InsertQuestionController ctrl;

    public InsertQuestionControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Manager mng = new Manager(new SystemUser(new Email("myEmail@gmail.com"),
                new Name("Zinde", "City"), new Password("Th1s1sMyC1ty")));

        ctrl = new InsertQuestionController(mng);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getQuestionTypes method, of class InsertQuestionController.
     */
    @Test
    public void testGetQuestionTypes() {
        System.out.println("getQuestionTypes");
        List<String> expected = new ArrayList<>();
        expected.add("Binária");
        expected.add("Escolha Múltipla");
        expected.add("Quantitativa");

        assertEquals(expected, ctrl.getQuestionTypes());
    }

    /**
     * Test of createQuestion method, of class InsertQuestionController (checks if the return is 1).
     */
    @Test
    public void testCreateBinaryQuestion() {
        System.out.println("createQuestion - binary");
        String questionType = "Binária";
        String questionText = "This is my content";
        String questionID = "22";

        assertEquals(1, ctrl.createQuestion(questionType, questionText, questionID, new ArrayList<>()));
    }
    
    /**
     * Test of createQuestion method, of class InsertQuestionController (checks if the return is 0).
     */
    @Test
    public void testInvalidCreateBinaryQuestion() {
        System.out.println("createQuestion - binary");
        String questionType = "Binária";
        String questionText = "";
        String questionID = "22";

        assertEquals(0, ctrl.createQuestion(questionType, questionText, questionID, new ArrayList<>()));
    }

    /**
     * Test of createQuestion method, of class InsertQuestionController (checks if the return is 1).
     */
    @Test
    public void testCreateMultipleChoiceQuestion() {
        System.out.println("createQuestion - multiple choice");
        String questionType = "Escolha Múltipla";
        String questionText = "This is my content";
        String questionID = "22";

        List<QuestionOption> options = new ArrayList<>();
        options.add(new MultipleChoiceQuestionOption("Sou uma boa opção"));
        options.add(new MultipleChoiceQuestionOption("Sou uma má opção"));

        assertEquals(1, ctrl.createQuestion(questionType, questionText, questionID, options));
    }

    
    /**
     * Test of createQuestion method, of class InsertQuestionController (checks if the return is 0).
     */
    @Test
    public void testCreateInvalidMultipleChoiceQuestion() {
        System.out.println("createQuestion - multiple choice");
        String questionType = "Escolha Múltipla";
        String questionText = "";
        String questionID = "22";

        List<QuestionOption> options = new ArrayList<>();
        options.add(new MultipleChoiceQuestionOption("Sou uma boa opção"));
        options.add(new MultipleChoiceQuestionOption("Sou uma má opção"));

        assertEquals(0, ctrl.createQuestion(questionType, questionText, questionID, options));
    }

    
    /**
     * Test of createQuestion method, of class InsertQuestionController (checks if the return is 1).
     */
    @Test
    public void testCreateQuantitativeQuestion() {
        System.out.println("createQuestion - quantitative");
        String questionType = "Quantitativa";
        String questionText = "This is my content";
        String questionID = "22";

        List<QuestionOption> scale = new ArrayList<>();
        scale.add(new QuantitativeQuestionOption(1.0));
        scale.add(new QuantitativeQuestionOption(2.0));
        scale.add(new QuantitativeQuestionOption(3.0));
        scale.add(new QuantitativeQuestionOption(4.0));

        assertEquals(1, ctrl.createQuestion(questionType, questionText, questionID, scale));
    }
    
     /**
     * Test of createQuestion method, of class InsertQuestionController (checks if the return is 0).
     */
    @Test
    public void testCreateInvalidQuantitativeQuestion() {
        System.out.println("createQuestion - quantitative");
        String questionType = "Quantitativa";
        String questionText = "";
        String questionID = "22";

        List<QuestionOption> scale = new ArrayList<>();
        scale.add(new QuantitativeQuestionOption(1.0));
        scale.add(new QuantitativeQuestionOption(2.0));
        scale.add(new QuantitativeQuestionOption(3.0));
        scale.add(new QuantitativeQuestionOption(4.0));

        assertEquals(0, ctrl.createQuestion(questionType, questionText, questionID, scale));
    }

    /**
     * Test of createQuestion method, of class InsertQuestionController (checks if the return is -1).
     */
    @Test
    public void testCreateInvalidQuestion() {
        System.out.println("createQuestion - invalid type");
        String questionType = "Another one";
        String questionText = "This is my content";
        String questionID = "22";

        assertEquals(-1, ctrl.createQuestion(questionType, questionText, questionID, new ArrayList<>()));
    }

    /**
     * Test of extractOption method, of class InsertQuestionController (checks if return is 1).
     */
    @Test
    public void testExtractBinaryOption() {
        System.out.println("extractOption - binary");

        assertEquals(1, ctrl.extractOption("Binária"));
        assertEquals(1, ctrl.extractOption("1"));
    }

    /**
     * Test of extractOption method, of class InsertQuestionController (checks if return is 2).
     */
    @Test
    public void testExtractMultipleChoiceOption() {
        System.out.println("extractOption - multiple choice");

        assertEquals(2, ctrl.extractOption("Escolha Múltipla"));
        assertEquals(2, ctrl.extractOption("2"));
    }

    /**
     * Test of extractOption method, of class InsertQuestionController (checks if return is 3).
     */
    @Test
    public void testExtractQuantitativeOption() {
        System.out.println("extractOption - quantitative");

        assertEquals(3, ctrl.extractOption("Quantitativa"));
        assertEquals(3, ctrl.extractOption("3"));
    }

    /**
     * Test of extractOption method, of class InsertQuestionController (checks if return is -1).
     */
    @Test
    public void testExtractInvalidOption() {
        System.out.println("extractOption - invalid type");

        assertEquals(-1, ctrl.extractOption("Another one"));
        assertEquals(-1, ctrl.extractOption("4"));
    }

    /**
     * Test of createNewMultipleChoiceOption method, of class InsertQuestionController.
     */
    @Test
    public void testCreateNewMultipleChoiceOption() {
        System.out.println("createNewMultipleChoiceOption");
        String content = "ESKETIT";
        MultipleChoiceQuestionOption option = new MultipleChoiceQuestionOption(content);
        assertEquals(option, ctrl.createNewMultipleChoiceOption(content));
    }

    /**
     * Test of createNewQuantitativeOption method, of class InsertQuestionController.
     */
    @Test
    public void testCreateNewQuantitativeOption() {
        System.out.println("createNewQuantitativeOption");
        double content = 4.20;
        QuantitativeQuestionOption option = new QuantitativeQuestionOption(content);
        assertEquals(option, ctrl.createNewQuantitativeQuestionOption(content));
    }
    
    /**
     * Test of checkPath method, of class InsertQuestionController.
     */
    @Test
    public void testCheckPath(){
        System.out.println("checkPath");   
        assertTrue(ctrl.checkPath("10dc"));
    }
}
