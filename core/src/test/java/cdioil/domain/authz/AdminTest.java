package cdioil.domain.authz;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Unit testing for class Admin.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class AdminTest {

    private SystemUser sysUser;

    @Before
    public void setUp() {
        Email email = new Email("lilpump@guccigang.com");
        Password pwd = new Password("Bananas19");
        Name nome = new Name("Lil", "Pump");
        sysUser = new SystemUser(email, nome, pwd);
    }

    /**
     * Tests for the constructor.
     */
    @Test
    public void testConstructor() {
        System.out.println("Test Constructor");
        assertNull("The condition should succeed because the argument is invalid",
                createAdmin(null));
        assertNotNull("The condition should succeed because the argument is valid",
                createAdmin(sysUser));
    }

    /**
     * Test of method hashCode, of class Admin.
     */
    @Test
    public void testeHashCode() {
        System.out.println("hashCode");
        Admin a = createAdmin(sysUser);
        int expResult = sysUser.hashCode();
        int result = a.hashCode();
        assertEquals(expResult,result);
    }

    /**
     * Test of equals method, of class Admin.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Email email = new Email("bananas@bananeira.com");
        Password pwd = new Password("Bananas19");
        Name nome = new Name("Lil", "Pump");
        SystemUser sysUser2 = new SystemUser(email, nome, pwd);
        Admin a = createAdmin(sysUser);
        Admin a2 = createAdmin(sysUser2);
        Admin a3 = createAdmin(sysUser);

        assertEquals("A condição deve acertar pois estamos a comparar"
                + "as mesmas instancias", a, a);
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "instancias de classes diferentes", a, "bananas");
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "uma instancia com outra a null", a, null);
        assertEquals("A condição deve acertar pois estamos a comparar"
                + "duas instancias iguais", a, a3);
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "duas instancias diferentes", a, a2);
    }

    /**
     * Test of method toString of class Admin.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Admin a = createAdmin(sysUser);
        String expResult = sysUser.toString();
        String result = a.toString();
        assertEquals("A condição deve acertar pois as strings são iguais",
                 expResult, result);
    }
    
    /**
     * Test of method getID, of class Admin.
     */
    @Test
    public void testGetID(){
        System.out.println("getID");
        Admin a = createAdmin(sysUser);
        Email expResult = new Email("lilpump@guccigang.com");
        Email result = a.getID();
        assertEquals(expResult,result);
    }

    /**
     * Builds an Admin instance of a SystemUser
     *
     * @param sysUser system user to be associated with the admin
     * @return Admin instance
     */
    private Admin createAdmin(SystemUser sysUser) {
        try {
            return new Admin(sysUser);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
