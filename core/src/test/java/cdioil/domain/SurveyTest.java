package cdioil.domain;

import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.Name;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.SystemUser;
import cdioil.domain.authz.UsersGroup;
import cdioil.framework.SurveyDTO;
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
    private Survey testGlobalSurvey;
    private Survey testTargetedSurvey;
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
        list.add(new Product("ProdutoTeste 2", new SKU("566341098"), "1 Kg", new QRCode("4563218")));
        this.testGlobalSurvey = new GlobalSurvey(list, timePeriod);
        testTargetedSurvey = new TargetedSurvey(list, timePeriod, new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41")))));
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

        assertEquals(testGlobalSurvey.hashCode(), outro.hashCode());

        //Mutation tests
        assertNotEquals("".hashCode(), testGlobalSurvey.hashCode());
        assertEquals(testGlobalSurvey.getGraphCopy().hashCode() + list.hashCode(),
                testGlobalSurvey.hashCode());
    }

    /**
     * Test of method equals, of class Survey.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, testGlobalSurvey);
        ArrayList<SurveyItem> otherList = new ArrayList<>();
        otherList.add(new Product("Other Product", new SKU("4444444444"), "1 L", new QRCode("235245246")));
        assertNotEquals("Instância de Inquerito diferente", new GlobalSurvey(otherList, timePeriod), testGlobalSurvey);
        assertEquals("Instância de Inquerito igual", new GlobalSurvey(list, timePeriod), testGlobalSurvey);
        assertEquals("Compare same instance", testGlobalSurvey, testGlobalSurvey);
        assertNotEquals("Instances of different classes", testGlobalSurvey, "bananas");
        assertNotEquals("Different type of surveys", testGlobalSurvey, testTargetedSurvey);

        //Kill mutations
        assertNotEquals(testGlobalSurvey, null);
    }

    /**
     * Test of method toString, of class Survey.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Survey s = new GlobalSurvey(list, timePeriod);
        assertEquals("A condição deve acertar pois o conteudo das Strings são iguais", testGlobalSurvey.toString(),
                s.toString());
        assertNotEquals(testGlobalSurvey.toString(), null);
    }

    /**
     * Test of method addQuestion, of class Survey.
     */
    @Test
    public void testAddQuestion() {
        System.out.println("addQuestion");
        String id = "4P";
        Question q = new BinaryQuestion("QuestaoTeste", id);
        assertTrue("Deveria ser possível adicionar", testGlobalSurvey.addQuestion(q));
        testGlobalSurvey.addQuestion(q);
        assertFalse("Questão null", testGlobalSurvey.addQuestion(null));
        assertFalse("Questão já existente", testGlobalSurvey.addQuestion(q));
    }

    /**
     * Test of method removeQuestion, of class Survey.
     */
    @Test
    public void testRemoveQuestion() {
        System.out.println("removeQuestion");
        String id = "5Q";
        Question q = new BinaryQuestion("QuestaoTeste", id);
        testGlobalSurvey.addQuestion(q);
        assertTrue("Deveria ser possível remover", testGlobalSurvey.removeQuestion(q));
        testGlobalSurvey.removeQuestion(q);
        assertFalse("Questão null", testGlobalSurvey.removeQuestion(null));
        assertFalse("Questão não existente", testGlobalSurvey.removeQuestion(q));
    }

    /**
     * Test of method isValidQuestion, of class Survey.
     */
    @Test
    public void testIsValidQuestion() {
        System.out.println("isValidQuestion");
        String id = "E8";
        Question q = new BinaryQuestion("QuestaoTeste", id);
        testGlobalSurvey.addQuestion(q);
        assertTrue("Deveria ser válida", testGlobalSurvey.isValidQuestion(q));
        testGlobalSurvey.removeQuestion(q);
        assertFalse("Questão null", testGlobalSurvey.isValidQuestion(null));
        assertFalse("Questão não existente", testGlobalSurvey.isValidQuestion(q));
    }

    /**
     * Test of method changeState, of class Survey.
     */
    @Test
    public void testChangeState() {
        System.out.println("changeState");
        SurveyState state = SurveyState.DRAFT;
        assertFalse("The condition should succeed because the new state "
                + "of the survey is equal to the past one", testGlobalSurvey.changeState(state));
        state = SurveyState.ACTIVE;
        assertTrue("The condition should succeed because the new state "
                + "of the survey is different from the past one", testGlobalSurvey.changeState(state));
        assertFalse("The condition should succeed because the new state is null",
                testGlobalSurvey.changeState(null));
    }

    /**
     * Test of method setNextQuestion, of class Survey.
     */
    @Test
    public void testSetNextQuestion() {
        System.out.println("setNextQuestion");
        assertFalse("The condition should succeed because the origin vertex is "
                + "equal to the destination vertex", testGlobalSurvey.setNextQuestion(new BinaryQuestion("Question", "QuestionID"),
                        new BinaryQuestion("Question", "QuestionID"), new BinaryQuestionOption(Boolean.FALSE), 0));
        assertTrue("The condition should succeed because the arguments are all valid",
                testGlobalSurvey.setNextQuestion(new BinaryQuestion("Question", "QuestionID"), new BinaryQuestion("Other Question", "QuestionID"),
                        new BinaryQuestionOption(Boolean.TRUE), 0));
        assertFalse("The condition should succeed because we are trying to insert "
                + "the same edge twice", testGlobalSurvey.setNextQuestion(new BinaryQuestion("Question", "QuestionID"), new BinaryQuestion("Other Question", "QuestionID"),
                        new BinaryQuestionOption(Boolean.TRUE), 0));
    }

    /**
     * Test of method getName, of class Survey.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        assertEquals("The condition should be successful since the both survey names are the same",
                testGlobalSurvey.getName(), new GlobalSurvey(list, timePeriod).getName());
    }
     /**
     * Test of method getID, of class Survey.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        assertEquals("The condition should be successful since the both survey id are the same",
                testGlobalSurvey.getID(), new GlobalSurvey(list, timePeriod).getID());
    }
    /**
     * Test of method getSurveyEndDate, of class Survey
     */
    @Test
    public void testGetSurveyEndDate(){
        System.out.println("getSurveyEndDate");
        TimePeriod timePeriodX=new TimePeriod(LocalDateTime.now(),LocalDateTime.MAX);
        TimePeriod timePeriodY=new TimePeriod(LocalDateTime.now(),LocalDateTime.MAX);
        Survey surveyX=new GlobalSurvey(new ArrayList<>(list),timePeriodX);
        Survey surveyY=new GlobalSurvey(new ArrayList<>(list),timePeriodY);
        assertEquals("The condition should be succesful since both surveys have the same end date"
                ,surveyX.getSurveyEndDate(),surveyY.getSurveyEndDate());
    }

    /**
     * Test of method getItemList, of class Survey.
     */
    public void testGetItemList() {
        System.out.println("getItemList");
        list = new LinkedList<>();
        list.add(new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")));
        list.add(new Product("ProdutoTeste 2", new SKU("566341098"), "1 Kg", new QRCode("4563218")));
        testGlobalSurvey = new GlobalSurvey(list, timePeriod);
        assertEquals("Should be the same", list, testGlobalSurvey.getItemList());
    }

    @Test
    public void testToDTO() {
        ArrayList<SurveyItem> items = new ArrayList<>();
        items.add(new Product());

        Survey s = new GlobalSurvey(items, new TimePeriod(LocalDateTime.MIN,
                LocalDateTime.now()));

        SurveyDTO expected = new SurveyDTO("survey", s.getName(),
                s.getSurveyEndDate(), SurveyState.DRAFT.toString());

        assertEquals(expected, s.toDTO());
    }
}
