package cdioil.domain;

import cdioil.domain.authz.Email;
import cdioil.domain.authz.Gestor;
import cdioil.domain.authz.UsersGroup;
import cdioil.domain.authz.Name;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.SystemUser;
import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test of the class Constest.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ContestTest {

    private String description;
    private UsersGroup gu;
    private Calendar beginDate;
    private Calendar endDate;

    @Before
    public void setUp() {
        description = "Concurso Teste";
        gu = new UsersGroup(new Gestor(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))));
        beginDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
    }

    /**
     * Test of the constructor of the class Constest.
     */
    @Test
    public void constructorTest() {
        System.out.println("Testes Construtor");
        assertNull("A condição deve acertar pois os argumentos são inválidos",
                createContest(null, gu, beginDate, endDate));
        assertNull("A condição deve acertar pois os argumentos são inválidos",
                createContest(description, gu, null, endDate));
        assertNull("A condição deve acertar pois os argumentos são inválidos",
                createContest(description, gu, beginDate, null));
        assertNotNull("A condição deve acertar pois os argumentos são válidos",
                createContest(description, gu, beginDate, endDate));
    }

    /**
     * Test of the hashCode method, of the class Constest.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Contest instance = createContest(description, gu, beginDate, endDate);
        int expResult = description.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of the equals method, of the class Constest.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Contest instance = createContest(description, gu, beginDate, endDate);
        Contest instance2 = createContest(description, gu, beginDate, endDate);
        Contest instance3 = createContest("Concurso 3", gu, beginDate, endDate);
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
        Contest instance = createContest(description, gu, beginDate, endDate);
        String expResult = "Concurso Teste";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of the info method, of the class Constest.
     */
    @Test
    public void testInfo() {
        System.out.println("info");
        Contest instance = createContest(description, gu, beginDate, endDate);
        String expResult = "Concurso Teste";
        String result = instance.info();
        assertEquals(expResult, result);
    }

    /**
     * Test of publicoAlvo method, of class Constest.
     */
    @Test
    public void testPublicoAlvo() {
        System.out.println("publicoAlvo");
        Contest instance = createContest(description, gu, beginDate, endDate);
        UsersGroup expResult = gu;
        UsersGroup result = instance.publicoAlvo();
        assertEquals(expResult, result);
    }

    /**
     * Create a new object Constest with a description, group users, begin date and end date.
     *
     * @param description
     * @param gu
     * @param beginDate
     * @param endDate
     * @return instance of the Contest
     */
    private Contest createContest(String description, UsersGroup gu,
            Calendar beginDate, Calendar endDate) {
        try {
            return new Contest(description, gu, beginDate, endDate);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
