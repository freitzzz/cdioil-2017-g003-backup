package cdioil.domain;

import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.Name;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.SystemUser;
import cdioil.domain.authz.UsersGroup;
import cdioil.graph.Graph;
import cdioil.time.TimePeriod;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit testing class for Questionnaire class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class QuestionnaireTest {

    private String title;
    private String description;
    private UsersGroup gu;
    private TimePeriod timePeriod;
    private Graph<Question, Answer> graph;

    @Before
    public void setUp() {
        title = "Titulo Teste";
        description = "Questionario Teste";
        gu = new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))));
        LocalDate d = LocalDate.of(2010, Month.MARCH, 2);
        LocalTime t = LocalTime.of(10, 10, 10);
        LocalDateTime dt = LocalDateTime.of(d, t);
        LocalDate d2 = LocalDate.of(2010, Month.MARCH, 4);
        LocalTime t2 = LocalTime.of(10, 10, 10);
        LocalDateTime dt2 = LocalDateTime.of(d2, t2);
        timePeriod = new TimePeriod(dt, dt2);
        graph = new Graph(true);
    }

    /**
     * Test of the constructor of the class Constest.
     */
    @Test
    public void constructorTest() {
        System.out.println("Testes Construtor");
        assertNull("The condition should succeed because the title is "
                + "null", createQuestionnaire(null, description, gu, timePeriod, graph));
        assertNull("The condition should succeed because the description is "
                + "null", createQuestionnaire(title, null, gu, timePeriod, graph));
        assertNull("The condition should succeed because the target audience is "
                + "null", createQuestionnaire(title, description, null, timePeriod, graph));
        assertNull("The condition should succeed because the time period is "
                + "null", createQuestionnaire(title, description, gu, null, graph));
        assertNotNull("The condition should succeed because the arguments are "
                + "valid", createQuestionnaire(title, description, gu, timePeriod, graph));
        assertNull("The condition should succeed because the graph is null",
                createQuestionnaire(title, description, gu, timePeriod, null));
        assertNotNull("Empty constructor test", new Questionnaire());
    }

    /**
     * Test of the hashCode method, of the class Constest.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Questionnaire instance = createQuestionnaire(title, description, gu, timePeriod, graph);
        Questionnaire other = createQuestionnaire(title, description, gu, timePeriod, graph);
        int expResult = other.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of the equals method, of the class Constest.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Questionnaire instance = createQuestionnaire(title, description, gu, timePeriod, graph);
        Questionnaire instance2 = createQuestionnaire(title, description, gu, timePeriod, graph);
        Questionnaire instance3 = createQuestionnaire("Titulo 3", "Questionario 3", gu, timePeriod, graph);
        assertEquals("A condição deve acertar pois estamos a comparar"
                + "as mesmas instancias", instance, instance);
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "instancias de classes diferentes", instance, "bananas");
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "uma instancia com outra a null", instance, null);
        assertEquals("A condição deve acertar pois estamos a comparar"
                + "duas instancias iguais", instance, instance2);
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "duas instancias diferentes", instance, instance3);
    }

    /**
     * Test of the toString method, of the class Constest.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Questionnaire instance = createQuestionnaire(title, description, gu, timePeriod, graph);
        String expResult = "Evento: Titulo Teste\n"
                + "Descricao: Questionario Teste\n"
                + "Data de Inicio: 2010-03-02 10:10:10\n"
                + "Data de Fim: 2010-03-04 10:10:10\n"
                + "Publico Alvo: GESTOR RESPONSÁVEL:\n"
                + "Nome: Quim  Barreiros\n"
                + "Email: quimBarreiros@gmail.com\n"
                + "\n"
                + "USERS:\n"
                + "";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Create a new object Constest with a description, group users, begin date
     * and end date.
     *
     * @param description
     * @param gu
     * @param beginDate
     * @param endDate
     * @return instance of the Contest
     */
    private Questionnaire createQuestionnaire(String title, String description, UsersGroup gu,
            TimePeriod timePeriod, Graph<Question, Answer> graph) {
        try {
            return new Questionnaire(title, description, gu, timePeriod, graph);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
