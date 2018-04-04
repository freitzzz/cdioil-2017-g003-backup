package cdioil.domain;

import cdioil.time.TimePeriod;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.UsersGroup;
import cdioil.domain.authz.Name;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.SystemUser;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test of the class Constest.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ContestTest {

    private String title;
    private String description;
    private UsersGroup gu;
    private TimePeriod timePeriod;

    @Before
    public void setUp() {
        title = "Titulo Teste";
        description = "Concurso Teste";
        gu = new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))));
        LocalDate d = LocalDate.of(2010, Month.MARCH, 2);
        LocalTime t = LocalTime.of(10, 10, 10);
        LocalDateTime dt = LocalDateTime.of(d, t);
        LocalDate d2 = LocalDate.of(2010, Month.MARCH, 4);
        LocalTime t2 = LocalTime.of(10, 10, 10);
        LocalDateTime dt2 = LocalDateTime.of(d2, t2);
        timePeriod = new TimePeriod(dt, dt2);
    }

    /**
     * Test of the constructor of the class Constest.
     */
    @Test
    public void constructorTest() {
        System.out.println("Testes Construtor");
        assertNull("The condition should succeed because the arguments are "
                + "invalid", createContest(null, description, gu, timePeriod));
        assertNull("The condition should succeed because the arguments are "
                + "invalid", createContest(title, null, gu, timePeriod));
        assertNull("The condition should succeed because the arguments are "
                + "invalid", createContest(title, description, null, timePeriod));
        assertNull("The condition should succeed because the arguments are "
                + "invalid", createContest(title, description, gu, null));
        assertNotNull("The condition should succeed because the arguments are "
                + "valid", createContest(title, description, gu, timePeriod));
        assertNotNull("Empty constructor test", new Contest());
    }

    /**
     * Test of the hashCode method, of the class Constest.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Contest instance = createContest(title, description, gu, timePeriod);
        Contest other = createContest(title, description, gu, timePeriod);
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
        Contest instance = createContest(title, description, gu, timePeriod);
        Contest instance2 = createContest(title, description, gu, timePeriod);
        Contest instance3 = createContest("Titulo 3", "Concurso 3", gu, timePeriod);
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
        Contest instance = createContest(title, description, gu, timePeriod);
        String expResult = "Evento: Titulo Teste\n"
                + "Descricao: Concurso Teste\n"
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
     * Test of addUsersToGroup method, of class Event
     */
    @Test
    public void testAddUsersToGroup() {
        System.out.println("addUsersToGroup");
        Contest c = createContest(title, description, gu, timePeriod);
        assertFalse(c.addUsersToGroup(null));
        List<RegisteredUser> lru = new LinkedList<>();
        lru.add(new RegisteredUser(new SystemUser(new Email("myPrecious@gmail.com"), new Name("Gollum", "Smeagol"), new Password("Precious3"))));
        assertTrue(c.addUsersToGroup(lru));
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
    private Contest createContest(String title, String description, UsersGroup gu,
            TimePeriod timePeriod) {
        try {
            return new Contest(title, description, gu, timePeriod);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
