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
        instance = new SystemUser(new Email("myPrecious@gmail.com"), new Nome("Gollum", "Smeagol"), new Password("Precious3"));
    }

    /**
     * Teste do método alterarCampoInformacao, da classe SystemUser
     */
    @Test
    public void testeAlterarCampoInformacao() {
        System.out.println("alterarCampoInformacao");
        //teste alterar nome com nome sem espaços
        assertFalse(instance.alterarCampoInformacao("Belmiro", 1));
        //teste alterar nome com nome válido
        assertTrue(instance.alterarCampoInformacao("James Bond", 1));
        //teste alterar Email com email inválido
        assertFalse(instance.alterarCampoInformacao("madameTusseau", 2));
        //teste alterar Email com email válido
        new Email("tragedyOfDarthPlagueisTheWise@gmail.com");
        assertTrue(instance.alterarCampoInformacao("tragedyOfDarthPlagueisTheWise@gmail.com", 2));
        //teste alterar Password com password fraca
        assertFalse(instance.alterarCampoInformacao("deathsticks", 3));
        //teste alterar Password com password válida
        assertTrue(instance.alterarCampoInformacao("WannaBuysomedeathsticks123", 3));
    }

    /**
     * Teste do método equals, da classe SystemUser
     */
    @Test
    public void testeEquals() {
        System.out.println("equals");
        //teste com instâncias iguais
        assertTrue(instance.equals(new SystemUser(new Email("myPrecious@gmail.com"), new Nome("Gollum", "Smeagol"), new Password("Precious3"))));
        //teste com instâncias diferentes com email igual
        assertTrue(instance.equals(new SystemUser(new Email("myPrecious@gmail.com"), new Nome("Smeagol", "Gollum"), new Password("1RingIsMine"))));
        //teste com instâncias com emails diferentes
        assertFalse(instance.equals(new SystemUser(new Email("precious@gmail.com"), new Nome("Smeagol", "Gollum"), new Password("1RingIsMine"))));
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
     * Teste do método passwordIgual, da classe SystemUser
     */
    @Test
    public void testePasswordIgual() {
        System.out.println("passwordIgual");
        //teste com passwords iguais
        assertTrue(instance.passwordIgual("Precious3"));
        //teste com passwords diferentes
        assertFalse(instance.passwordIgual("Precious4"));
        //teste com password inválida
        assertFalse(instance.passwordIgual(""));
    }

    /**
     * Teste do método toString, da classe SystemUser
     */
    @Test
    public void testeToString() {
        System.out.println("toString");
        String expected = "Nome: Gollum  Smeagol\nEmail: myPrecious@gmail.com\n";
        assertEquals(expected, instance.toString());
    }
}
