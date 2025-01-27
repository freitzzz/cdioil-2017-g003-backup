package cdioil.domain.authz;

import cdioil.framework.dto.SystemUserDTO;
import static org.junit.Assert.*;
import org.junit.Test;

public class RegisteredUserTest {

    @Test(expected = IllegalArgumentException.class)
    public void criaInstanciaInvalidaDeUserRegistadoTest() {
        new RegisteredUser(null);
    }
    
    @Test
    public void testHashCode(){
        SystemUser sysUser = new SystemUser(new Email("myPrecious@gmail.com"), 
                new Name("Gollum", "Smeagol"), new Password("Precious3"));
        
        RegisteredUser regUser = new RegisteredUser(sysUser);
        RegisteredUser otherRegUser = new RegisteredUser(sysUser);
        
        int result = regUser.hashCode();
        int otherResult = otherRegUser.hashCode();
        
        assertEquals(result, otherResult);
        assertNotEquals(result, sysUser.hashCode());
        
        //This chunk kills code mutations
        assertNotEquals("".hashCode(), result);
        int num = 41 * 5 + sysUser.hashCode();
        assertEquals(num, result);
    }

    @Test
    public void equalTest() {
        RegisteredUser ur = new RegisteredUser(new SystemUser(new Email("myPrecious@gmail.com"), new Name("Gollum", "Smeagol"), new Password("Precious3")));
        //test same instance
        assertTrue(ur.equals(ur));
        //test with null parameter
        assertFalse(ur.equals(null));
        //test with object of different class
        assertFalse(ur.equals("String"));
        //test with null instance
        String str = null;
        assertFalse(ur.equals(str));
        //test with different instances
        assertFalse(ur.equals(new RegisteredUser()));
        //test with equal instances
        assertTrue(ur.equals(new RegisteredUser(new SystemUser(new Email("myPrecious@gmail.com"), new Name("Gollum", "Smeagol"), new Password("Precious3")))));
    }
    
    /**
     * Test of getProfile method, of class RegisteredUser.
     */
    @Test
    public void testGetProfile(){
        System.out.println("getProfile");
        RegisteredUser instance =  new RegisteredUser(new SystemUser(new Email("myPrecious@gmail.com"), new Name("Gollum", "Smeagol"), new Password("Precious3")));
        Profile expResult = new Profile(instance);
        Profile result = instance.getProfile();
        assertEquals(result,expResult);
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        RegisteredUser ur = new RegisteredUser(new SystemUser(new Email("myPrecious@gmail.com"), new Name("Gollum", "Smeagol"), new Password("Precious3")));
        String expected = "Nome: Gollum Smeagol\nEmail: myprecious@gmail.com\n";
        assertEquals(expected, ur.toString());
    }

    @Test
    public void testGetId() {
        System.out.println("getID");
        SystemUser systemUserX=new SystemUser(new Email("myPrecious@gmail.com"), new Name("Gollum", "Smeagol"), new Password("Precious3"));
        RegisteredUser ur = new RegisteredUser(systemUserX);
        assertEquals(systemUserX, ur.getID());
    }
    /**
     * Test of toDTO method, of class Admin.
     */
    @Test
    public void testToDTO() {
        System.out.println("toDTO");
        SystemUser sysUser=new SystemUser(new Email("11111111@email.com"),new Name("AAAA","BBBB"),new Password("Password123"));
        RegisteredUser instance = new RegisteredUser(sysUser);
        SystemUserDTO expResult = sysUser.toDTO();
        SystemUserDTO result = instance.toDTO();
        assertEquals("Condition should be successful since both DTO's are the same",expResult, result);
    }
}
