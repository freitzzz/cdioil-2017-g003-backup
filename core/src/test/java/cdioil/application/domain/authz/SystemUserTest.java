/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.domain.authz;

import cdioil.domain.authz.Email;
import cdioil.domain.authz.Nome;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.SystemUser;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author João
 */
public class SystemUserTest {

    /**
     * Teste do método alterarCampoInformacao, da classe SystemUser
     */
    @Test
    public void testeAlterarCampoInformacao() {
        System.out.println("alterarCampoInformacao");
        SystemUser instance = new SystemUser(new Email("blabla@gmail.com"), new Nome("Belmiro", "Azevedo"), new Password("123blaBLA123"));
        //teste alterar nome com nome sem espaços
        assertFalse(instance.alterarCampoInformacao("Belmiro", 1));
        //teste alterar nome com nome válido
        assertTrue(instance.alterarCampoInformacao("James Bond", 1));
        //teste alterar Email com email inválido
        assertFalse(instance.alterarCampoInformacao("portugal", 2));
        //teste alterar Email com email válido
        assertTrue(instance.alterarCampoInformacao("portugal@gmail.com", 2));
        //teste alterar Password com password fraca
        assertFalse(instance.alterarCampoInformacao("senhor", 3));
        //teste alterar Password com password válida
        assertTrue(instance.alterarCampoInformacao("Senhor123", 3));
    }
}
