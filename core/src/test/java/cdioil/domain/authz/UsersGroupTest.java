package cdioil.domain.authz;

import java.util.LinkedList;
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
    private UsersGroup usersGroup;

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
        this.usersGroup = new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
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
        assertFalse("Objeto null não é válido", usersGroup.isUserValid(u));
        u = new RegisteredUser(new SystemUser(new Email("dinaMagalhaes@gmail.com"), new Name("Zindeira",
                "da Bela"), new Password("ThisIsTheP455w0rd")));
        assertFalse("Objeto não pertence à lista", usersGroup.isUserValid(u));
        usersGroup.addUser(u);
        assertTrue("Objeto existente na lista é válido", usersGroup.isUserValid(u));
    }

    /**
     * Test of the method addUser, of the class UsersGroup.
     */
    @Test
    public void testAdicionarUser() {
        System.out.println("addUser");
        assertFalse("Objeto null não é válido", usersGroup.addUser(null));
        RegisteredUser u = new RegisteredUser(new SystemUser(new Email("dinaMagalhaes@gmail.com"), new Name("Zindeira",
                "da Bela"), new Password("ThisIsTheP455w0rd")));
        assertTrue("Objeto não pertence à lista, logo pode ser adicionado", usersGroup.addUser(u));
        usersGroup.addUser(u);
        assertFalse("Objeto existente na lista, logo não pode ser adicionado", usersGroup.addUser(u));
    }

    /**
     * Test of the method removeUser, of the class UsersGroup.
     */
    @Test
    public void testRemoverUser() {
        System.out.println("removeUser");
        RegisteredUser u = null;
        assertFalse("Objeto null não é válido", usersGroup.removeUser(u));
        u = new RegisteredUser(new SystemUser(new Email("dinaMagalhaes@gmail.com"), new Name("Zindeira",
                "da Bela"), new Password("ThisIsTheP455w0rd")));
        assertFalse("Objeto não pertence à lista, logo não pode ser removido", usersGroup.removeUser(u));
        usersGroup.addUser(u);
        assertTrue("Objeto existente na lista, logo pode ser removido", usersGroup.removeUser(u));
    }

    /**
     * Test of the method toString, of the class UsersGroup.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        UsersGroup oth = new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros@gmail.com"),
                new Name("Quim", "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))));
        assertEquals("Deveriam ser iguais", oth.toString(), usersGroup.toString());

        //Mutation test
        assertNotEquals(null, oth.toString());
    }

    /**
     * Test of the method hashCode, of the class UsersGroup.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        UsersGroup oth = new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))));
        assertEquals("Deveriam ser iguais", oth.hashCode(), usersGroup.hashCode());
        RegisteredUser user = new RegisteredUser(new SystemUser(new Email("myPrecious@gmail.com"), new Name("Gollum", "Smeagol"), new Password("Precious3")));

        //Mutation tests
        assertNotEquals("".hashCode(), usersGroup.hashCode());
        oth.addUser(user);
        LinkedList<RegisteredUser> list = new LinkedList<>();
        list.add(user);
        int num = 47 * 7 + list.hashCode();
        assertEquals(num, oth.hashCode());
    }

    /**
     * Test of the method equals, of the class UsersGroup.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        //test with same isntance
        assertTrue(usersGroup.equals(usersGroup));
        //test with null parameter
        assertFalse(usersGroup.equals(null));
        //test with object of different class
        assertFalse(usersGroup.equals("String"));
        //test with null instance
        UsersGroup uGroup = null;
        assertFalse(usersGroup.equals(uGroup));
        //test with equal instances
        assertTrue(usersGroup.equals(new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))))));
        //test with different instances
        assertTrue(usersGroup.equals(new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros1@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V411"))))));
    }
}
