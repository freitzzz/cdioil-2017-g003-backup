package cdioil.domain;

import cdioil.time.TimePeriod;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedList;
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
    private Survey i;
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
        list.add(new Product("ProdutoTeste", new EAN("544231234"), "1 L", new QRCode("4324235")));
        this.i = new GlobalSurvey(list, timePeriod);
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

        assertEquals(i.hashCode(), outro.hashCode());
    }

    /**
     * Test of method equals, of class Survey.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, i);
        ArrayList<SurveyItem> otherList = new ArrayList<>();
        otherList.add(new Product("Other Product", new EAN("4444444444"), "1 L", new QRCode("235245246")));
        assertNotEquals("Instância de Inquerito diferente", new GlobalSurvey(otherList, timePeriod), i);
        assertEquals("Instância de Inquerito igual", new GlobalSurvey(list, timePeriod), i);
        assertEquals("Compare same instance", i, i);
        assertNotEquals("Instances of different classes", i, "bananas");
    }

    /**
     * Test of method toString, of class Survey.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Survey s = new GlobalSurvey(list, timePeriod);
        assertEquals("A condição deve acertar pois o conteudo das Strings são iguais", i.toString(),
                s.toString());

    }

    /**
     * Test of method addQuestion, of class Survey.
     */
    @Test
    public void testAddQuestion() {
        System.out.println("addQuestion");
        String id = "4P";
        Question q = new BinaryQuestion("QuestaoTeste", id);
        assertTrue("Deveria ser possível adicionar", i.addQuestion(q));
        i.addQuestion(q);
        assertFalse("Questão null", i.addQuestion(null));
        assertFalse("Questão já existente", i.addQuestion(q));
    }

    /**
     * Test of method removeQuestion, of class Survey.
     */
    @Test
    public void testRemoveQuestion() {
        System.out.println("removeQuestion");
        String id = "5Q";
        Question q = new BinaryQuestion("QuestaoTeste", id);
        i.addQuestion(q);
        assertTrue("Deveria ser possível remover", i.removeQuestion(q));
        i.removeQuestion(q);
        assertFalse("Questão null", i.removeQuestion(null));
        assertFalse("Questão não existente", i.removeQuestion(q));
    }

    /**
     * Test of method isValidQuestion, of class Survey.
     */
    @Test
    public void testIsValidQuestion() {
        System.out.println("isValidQuestion");
        String id = "E8";
        Question q = new BinaryQuestion("QuestaoTeste", id);
        i.addQuestion(q);
        assertTrue("Deveria ser válida", i.isValidQuestion(q));
        i.removeQuestion(q);
        assertFalse("Questão null", i.isValidQuestion(null));
        assertFalse("Questão não existente", i.isValidQuestion(q));
    }

    /**
     * Test of method changeState, of class Survey.
     */
    @Test
    public void testChangeState() {
        System.out.println("changeState");
        SurveyState state = SurveyState.DRAFT;
        assertFalse("The condition should succeed because the new state "
                + "of the survey is equal to the past one", i.changeState(state));
        state = SurveyState.ACTIVE;
        assertTrue("The condition should succeed because the new state "
                + "of the survey is different from the past one", i.changeState(state));
        assertFalse("The condition should succeed because the new state is null",
                i.changeState(null));
    }

    /**
     * Test of method setNextQuestion, of class Survey.
     */
    @Test
    public void testSetNextQuestion() {
        System.out.println("setNextQuestion");
        assertFalse("The condition should succeed because the origin vertex is "
                + "equal to the destination vertex", i.setNextQuestion(new BinaryQuestion("Question", "QuestionID"),
                        new BinaryQuestion("Question", "QuestionID"), new BinaryQuestionOption(Boolean.FALSE), 0));
        assertTrue("The condition should succeed because the arguments are all valid",
                i.setNextQuestion(new BinaryQuestion("Question", "QuestionID"), new BinaryQuestion("Other Question", "QuestionID"),
                        new BinaryQuestionOption(Boolean.TRUE), 0));
        assertFalse("The condition should succeed because we are trying to insert "
                + "the same edge twice", i.setNextQuestion(new BinaryQuestion("Question", "QuestionID"), new BinaryQuestion("Other Question", "QuestionID"),
                        new BinaryQuestionOption(Boolean.TRUE), 0));
    }
}
