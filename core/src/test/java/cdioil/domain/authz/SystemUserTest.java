package cdioil.domain.authz;

import java.time.LocalDate;
import java.time.Month;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author João
 */
public class SystemUserTest {

    SystemUser instance;
    SystemUser otherInstance;

    @Before
    public void setUp() {
        instance = new SystemUser(new Email("myPrecious@gmail.com"), new Name("Gollum", "Smeagol"), new Password("Precious3"));
        otherInstance = new SystemUser(new Email("myPrecious@gmail.com"), new Name("Gollum", "Smeagol"), new Password("Precious3"),
                new PhoneNumber("939999999"), new Location("Lil Pump's Mansion"), new BirthDate(LocalDate.of(1222, Month.MARCH, 23)));
    }

    /**
     * Test of empty constructor of class SystemUser
     */
    @Test
    public void testEmptyConstructor() {
        System.out.println("SystemUser()");
        SystemUser sysUser = new SystemUser();
    }

    /**
     * Teste do método changeUserDatafield, da classe SystemUser
     */
    @Test
    public void testChangeUserDataField() {
        System.out.println("alterarCampoInformacao");
        //teste alterar nome com nome sem espaços
        assertFalse(instance.changeUserDatafield("Belmiro", 1));
        //teste alterar nome com nome válido
        assertTrue(instance.changeUserDatafield("James Bond", 1));
        //teste alterar Email com email inválido
        assertFalse(instance.changeUserDatafield("madameTusseau", 2));
        //teste alterar Email com email válido
        new Email("tragedyOfDarthPlagueisTheWise@gmail.com");
        assertTrue(instance.changeUserDatafield("tragedyOfDarthPlagueisTheWise@gmail.com", 2));
        //teste alterar Password com password fraca
        assertFalse(instance.changeUserDatafield("deathsticks", 3));
        //teste alterar Password com password válida
        assertTrue(instance.changeUserDatafield("WannaBuysomedeathsticks123", 3));
        //change phone number test
        assertTrue(instance.changeUserDatafield("919999999", 4));
        //change location test
        assertTrue(instance.changeUserDatafield("ESKEETIT", 5));
        //change birth date test
        assertTrue(instance.changeUserDatafield("1000-03-23", 6));
        //test with invalid option
        assertTrue(instance.changeUserDatafield("deathsticks", 7));
    }

    /**
     * Teste do método equals, da classe SystemUser
     */
    @Test
    public void testeEquals() {
        System.out.println("equals");
        //teste com instâncias iguais
        assertTrue(instance.equals(new SystemUser(new Email("myPrecious@gmail.com"), new Name("Gollum", "Smeagol"), new Password("Precious3"))));
        //teste com instâncias diferentes com email igual
        assertTrue(instance.equals(new SystemUser(new Email("myPrecious@gmail.com"), new Name("Smeagol", "Gollum"), new Password("1RingIsMine"))));
        //teste com instâncias com emails diferentes
        assertFalse(instance.equals(new SystemUser(new Email("precious@gmail.com"), new Name("Smeagol", "Gollum"), new Password("1RingIsMine"))));
        //teste com objetos que não são instâncias de SystemUser
        assertFalse(instance.equals(new Email("americaWantsYou@gmail.com")));
        //teste com mesma instância
        assertTrue(instance.equals(instance));
    }

    /**
     * Teste do método hashCode, da classe SystemUser
     */
    @Test
    public void testeHashCode() {
        System.out.println("hashCode");
        int expResult = new Email("myPrecious@gmail.com").hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Teste do método samePassword, da classe SystemUser
     */
    @Test
    public void testePasswordIgual() {
        System.out.println("passwordIgual");
        //teste com passwords iguais
        assertTrue(instance.samePassword("Precious3"));
        //teste com passwords diferentes
        assertFalse(instance.samePassword("Precious4"));
        //teste com password inválida
        assertFalse(instance.samePassword(""));
    }

    /**
     * Teste do método toString, da classe SystemUser
     */
    @Test
    public void testeToString() {
        System.out.println("toString");
        SystemUser other = new SystemUser(new Email("myPrecious@gmail.com"),
                new Name("Gollum", "Smeagol"), new Password("Precious3"));
        assertTrue(instance.toString().equals(other.toString()));
    }

    /**
     * Test of getID method, of class SystemUser.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        Email expResult = new Email("myPrecious@gmail.com");
        Email result = instance.getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of activateAccount method, of class SystemUser.
     */
    @Test
    public void testActivateAccount() {
        System.out.println("activateAccount");
        instance.activateAccount();
    }

    /**
     * Test of getActivationCode method, of class SystemUser.
     */
    @Test
    public void testGetActivationCode() {
        System.out.println("getActivationCode");
        assertEquals(instance.getActivationCode(), instance.getActivationCode());
    }
    
    
}
