package cdioil.domain;

import cdioil.time.TimePeriod;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing for class Survey.
 *
 * @author Rita Gonçalves (1160912)
 */
public class SurveyTest {

    /**
     * Data for testing
     */
    private Survey testSurvey;
    private TimePeriod timePeriod;
    private LinkedList<SurveyItem> list;

    public SurveyTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        timePeriod = new TimePeriod(LocalDateTime.of(1, Month.MARCH, 1, 1, 1),
                LocalDateTime.of(2, Month.MARCH, 2, 2, 2));
        list = new LinkedList<>();
        list.add(new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")));
        this.testSurvey = new GlobalSurvey(list, timePeriod);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of empty constructor of class Survey
     */
    @Test
    public void testEmptyConstructor() {
        System.out.println("Survey()");
        Survey s = new GlobalSurvey();
    }

    /**
     * Ensure that an exception is thrown when the item list is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullItemListThrowsException() {
        new GlobalSurvey(null, timePeriod);
    }

    /**
     * Ensure that an exception is thrown when the data is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullDateThrowsException() {
        new GlobalSurvey(new ArrayList<>(), null);
    }

    /**
     * Test of method hashCode, of class Survey.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Survey outro = new GlobalSurvey(list, timePeriod);

        assertEquals(testSurvey.hashCode(), outro.hashCode());

        //Mutation tests
        assertNotEquals("".hashCode(), testSurvey.hashCode());
        assertEquals(testSurvey.getGraphCopy().hashCode() + list.hashCode(),
                testSurvey.hashCode());
    }

    /**
     * Test of method equals, of class Survey.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, testSurvey);
        ArrayList<SurveyItem> otherList = new ArrayList<>();
        otherList.add(new Product("Other Product", new SKU("4444444444"), "1 L", new QRCode("235245246")));
        assertNotEquals("Instância de Inquerito diferente", new GlobalSurvey(otherList, timePeriod), testSurvey);
        assertEquals("Instância de Inquerito igual", new GlobalSurvey(list, timePeriod), testSurvey);
        assertEquals("Compare same instance", testSurvey, testSurvey);
        assertNotEquals("Instances of different classes", testSurvey, "bananas");
    }

    /**
     * Test of method toString, of class Survey.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Survey s = new GlobalSurvey(list, timePeriod);
        assertEquals("A condição deve acertar pois o conteudo das Strings são iguais", testSurvey.toString(),
                s.toString());
        assertNotEquals(testSurvey.toString(), null);
    }

    /**
     * Test of method addQuestion, of class Survey.
     */
    @Test
    public void testAddQuestion() {
        System.out.println("addQuestion");
        String id = "4P";
        Question q = new BinaryQuestion("QuestaoTeste", id);
        assertTrue("Deveria ser possível adicionar", testSurvey.addQuestion(q));
        testSurvey.addQuestion(q);
        assertFalse("Questão null", testSurvey.addQuestion(null));
        assertFalse("Questão já existente", testSurvey.addQuestion(q));
    }

    /**
     * Test of method removeQuestion, of class Survey.
     */
    @Test
    public void testRemoveQuestion() {
        System.out.println("removeQuestion");
        String id = "5Q";
        Question q = new BinaryQuestion("QuestaoTeste", id);
        testSurvey.addQuestion(q);
        assertTrue("Deveria ser possível remover", testSurvey.removeQuestion(q));
        testSurvey.removeQuestion(q);
        assertFalse("Questão null", testSurvey.removeQuestion(null));
        assertFalse("Questão não existente", testSurvey.removeQuestion(q));
    }

    /**
     * Test of method isValidQuestion, of class Survey.
     */
    @Test
    public void testIsValidQuestion() {
        System.out.println("isValidQuestion");
        String id = "E8";
        Question q = new BinaryQuestion("QuestaoTeste", id);
        testSurvey.addQuestion(q);
        assertTrue("Deveria ser válida", testSurvey.isValidQuestion(q));
        testSurvey.removeQuestion(q);
        assertFalse("Questão null", testSurvey.isValidQuestion(null));
        assertFalse("Questão não existente", testSurvey.isValidQuestion(q));
    }

    /**
     * Test of method changeState, of class Survey.
     */
    @Test
    public void testChangeState() {
        System.out.println("changeState");
        SurveyState state = SurveyState.DRAFT;
        assertFalse("The condition should succeed because the new state "
                + "of the survey is equal to the past one", testSurvey.changeState(state));
        state = SurveyState.ACTIVE;
        assertTrue("The condition should succeed because the new state "
                + "of the survey is different from the past one", testSurvey.changeState(state));
        assertFalse("The condition should succeed because the new state is null",
                testSurvey.changeState(null));
    }

    /**
     * Test of method setNextQuestion, of class Survey.
     */
    @Test
    public void testSetNextQuestion() {
        System.out.println("setNextQuestion");
        assertFalse("The condition should succeed because the origin vertex is "
                + "equal to the destination vertex", testSurvey.setNextQuestion(new BinaryQuestion("Question", "QuestionID"),
                        new BinaryQuestion("Question", "QuestionID"), new BinaryQuestionOption(Boolean.FALSE), 0));
        assertTrue("The condition should succeed because the arguments are all valid",
                testSurvey.setNextQuestion(new BinaryQuestion("Question", "QuestionID"), new BinaryQuestion("Other Question", "QuestionID"),
                        new BinaryQuestionOption(Boolean.TRUE), 0));
        assertFalse("The condition should succeed because we are trying to insert "
                + "the same edge twice", testSurvey.setNextQuestion(new BinaryQuestion("Question", "QuestionID"), new BinaryQuestion("Other Question", "QuestionID"),
                        new BinaryQuestionOption(Boolean.TRUE), 0));
    }

    /**
     * Test of getProductSurveys method, of class Survey
     */
    @Test
    public void testGetProductSurveys() {
        System.out.println("getProductSurveys");
        //test method with both parameters null or an empty list
        assertEquals(null, Survey.getProductSurveys(null, null));
        assertEquals(null, Survey.getProductSurveys(new ArrayList<>(), null));
        //test method with 1 paramater as null or empty list
        assertEquals(null, Survey.getProductSurveys(null, new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235"))));
        assertEquals(null, Survey.getProductSurveys(new ArrayList<>(), new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235"))));
        List<Survey> surveys = new ArrayList<>();
        surveys.add(testSurvey);
        assertEquals(null, Survey.getProductSurveys(surveys, null));
        //test working method
        assertEquals(surveys, Survey.getProductSurveys(surveys, new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235"))));
    }
}
