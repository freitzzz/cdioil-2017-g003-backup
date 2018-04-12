package cdioil.domain.authz;

import cdioil.domain.QRCode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the class UsersGroup.
 *
 * @author Rita Gonçalves (1160912)
 */
public class UsersGroupTest {

    /**
     * Instance of UsersGroup for test purposes.
     */
    private UsersGroup gu;

    public UsersGroupTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.gu = new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))));
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of the constructors of class UsersGroup
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureUsersGroupIsBuiltCorrectly() {
        UsersGroup uGroup = new UsersGroup();
        uGroup = new UsersGroup(null);
    }

    /**
     * Test of the method isUserValid, of the class UsersGroup.
     */
    @Test
    public void testIsUserValido() {
        System.out.println("isUserValid");
        RegisteredUser u = null;
        assertFalse("Objeto null não é válido", gu.isUserValid(u));
        u = new RegisteredUser(new SystemUser(new Email("dinaMagalhaes@gmail.com"), new Name("Zindeira",
                "da Bela"), new Password("ThisIsTheP455w0rd")));
        assertFalse("Objeto não pertence à lista", gu.isUserValid(u));
        gu.addUser(u);
        assertTrue("Objeto existente na lista é válido", gu.isUserValid(u));
    }

    /**
     * Test of the method addUser, of the class UsersGroup.
     */
    @Test
    public void testAdicionarUser() {
        System.out.println("addUser");
        assertFalse("Objeto null não é válido", gu.addUser(null));
        RegisteredUser u = new RegisteredUser(new SystemUser(new Email("dinaMagalhaes@gmail.com"), new Name("Zindeira",
                "da Bela"), new Password("ThisIsTheP455w0rd")));
        assertTrue("Objeto não pertence à lista, logo pode ser adicionado", gu.addUser(u));
        gu.addUser(u);
        assertFalse("Objeto existente na lista, logo não pode ser adicionado", gu.addUser(u));
    }

    /**
     * Test of the method removeUser, of the class UsersGroup.
     */
    @Test
    public void testRemoverUser() {
        System.out.println("removeUser");
        RegisteredUser u = null;
        assertFalse("Objeto null não é válido", gu.removeUser(u));
        u = new RegisteredUser(new SystemUser(new Email("dinaMagalhaes@gmail.com"), new Name("Zindeira",
                "da Bela"), new Password("ThisIsTheP455w0rd")));
        assertFalse("Objeto não pertence à lista, logo não pode ser removido", gu.removeUser(u));
        gu.addUser(u);
        assertTrue("Objeto existente na lista, logo pode ser removido", gu.removeUser(u));
    }

    /**
     * Test of the method toString, of the class UsersGroup.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        UsersGroup oth = new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros@gmail.com"),
                new Name("Quim", "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))));
        assertEquals("Deveriam ser iguais", oth.toString(), gu.toString());
    }

    /**
     * Test of the method hashCode, of the class UsersGroup.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        UsersGroup oth = new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))));
        assertEquals("Deveriam ser iguais", oth.hashCode(), gu.hashCode());
    }

    /**
     * Test of the method equals, of the class UsersGroup.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        //test with same isntance
        assertTrue(gu.equals(gu));
        //test with null parameter
        assertFalse(gu.equals(null));
        //test with object of different class
        assertFalse(gu.equals("String"));
        //test with null instance
        UsersGroup uGroup = null;
        assertFalse(gu.equals(uGroup));
        //test with equal instances
        assertTrue(gu.equals(new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))))));
        //test with different instances
        assertTrue(gu.equals(new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros1@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V411"))))));
    }
}
