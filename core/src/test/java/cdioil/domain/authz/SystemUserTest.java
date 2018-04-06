/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain.authz;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author João
 */
public class SystemUserTest {

    SystemUser instance;

    @Before
    public void setUp() {
        instance = new SystemUser(new Email("myPrecious@gmail.com"), new Name("Gollum", "Smeagol"), new Password("Precious3"));
    }

    /**
     * Teste do método changeUserDatafield, da classe SystemUser
     */
    @Test
    public void testeAlterarCampoInformacao() {
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
    public void testGetID(){
        System.out.println("getID");
        Email expResult = new Email("myPrecious@gmail.com");
        Email result = instance.getID();
        assertEquals(expResult,result);
    }
}
