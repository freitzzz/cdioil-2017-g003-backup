package cdioil.domain.authz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class RegisteredUserTest {

    @Test(expected = IllegalArgumentException.class)
    public void criaInstanciaInvalidaDeUserRegistadoTest() {
        new RegisteredUser(null);
    }

    @Test
    public void equalsHashCodeTest() {
        RegisteredUser ur = new RegisteredUser(new SystemUser(new Email("myPrecious@gmail.com"), new Name("Gollum", "Smeagol"), new Password("Precious3")));
        //test hashCode
        assertEquals(new Email("myPrecious@gmail.com").hashCode(), ur.hashCode());
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

    @Test
    public void testToString() {
        System.out.println("toString");
        RegisteredUser ur = new RegisteredUser(new SystemUser(new Email("myPrecious@gmail.com"), new Name("Gollum", "Smeagol"), new Password("Precious3")));
        String expected = "Nome: Gollum  Smeagol\nEmail: myprecious@gmail.com\n";
        assertEquals(expected, ur.toString());
    }

    @Test
    public void testGetId() {
        System.out.println("getID");
        RegisteredUser ur = new RegisteredUser(new SystemUser(new Email("myPrecious@gmail.com"), new Name("Gollum", "Smeagol"), new Password("Precious3")));
        assertEquals(new Email("myPrecious@gmail.com"), ur.getID());
    }
}
