/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        this.gu = new UsersGroup(new Gestor(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))));
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of the method isUserValid, of the class UsersGroup.
     */
    @Test
    public void testIsUserValido() {
        System.out.println("isUserValid");
        UserRegistado u = null;
        assertFalse("Objeto null não é válido", gu.isUserValid(u));
        u = new UserRegistado(new SystemUser(new Email("dinaMagalhaes@gmail.com"), new Name("Zindeira",
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
        UserRegistado u = new UserRegistado(new SystemUser(new Email("dinaMagalhaes@gmail.com"), new Name("Zindeira",
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
        UserRegistado u = null;
        assertFalse("Objeto null não é válido", gu.removeUser(u));
        u = new UserRegistado(new SystemUser(new Email("dinaMagalhaes@gmail.com"), new Name("Zindeira",
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
        UsersGroup oth = new UsersGroup(new Gestor(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))));
        assertEquals("Deveriam ser iguais", oth.toString(), gu.toString());
    }

    /**
     * Test of the method hashCode, of the class UsersGroup.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        UsersGroup oth = new UsersGroup(new Gestor(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))));
        assertEquals("Deveriam ser iguais", oth.hashCode(), gu.hashCode());
    }

    /**
     * Test of the method equals, of the class UsersGroup.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, gu);
        assertNotEquals("Instância de outra classe não é igual", new QRCode("40"), gu);
        assertEquals("Instância de GrupoUtilizadores igual", gu, gu);
    }
}
