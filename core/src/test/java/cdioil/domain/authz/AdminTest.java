package cdioil.domain.authz;

import cdioil.framework.dto.SystemUserDTO;
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
        assertNotNull("Empty constructor test", new Admin());
    }

    /**
     * Test of method hashCode, of class Admin.
     */
    @Test
    public void testeHashCode() {
        System.out.println("hashCode");
        Admin a = createAdmin(sysUser);
        Admin otherAdmin = new Admin(sysUser);
        
        int expResult = otherAdmin.hashCode();
        int result = a.hashCode();
        assertEquals(expResult,result);
    
        int sysUserHashCode = sysUser.hashCode();
        assertNotEquals(sysUserHashCode, result);
        
        //This chunk kills code mutations
        assertNotEquals("".hashCode(), result);
        int num = 17 * 5 + sysUser.hashCode();
        assertEquals(num, result);
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
        Admin realAdmin = createAdmin(sysUser);
        SystemUser expResult=sysUser;
        SystemUser result = realAdmin.getID();
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


    /**
     * Test of toDTO method, of class Admin.
     */
    @Test
    public void testToDTO() {
        System.out.println("toDTO");
        Admin instance = createAdmin(sysUser);
        SystemUserDTO expResult = sysUser.toDTO();
        SystemUserDTO result = instance.toDTO();
        assertEquals("Condition should be successful since both DTO's are the same",expResult, result);
    }

}
