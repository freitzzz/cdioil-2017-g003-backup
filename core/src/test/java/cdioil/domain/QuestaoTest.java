/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import cdioil.domain.EAN;
import cdioil.domain.Questao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 * Testes da classe Questao.
 * @author Ana Guerra (1161191)
 */
public class QuestaoTest {
    
    /**
     * Instância de Questao para testes.
     */
    Questao questao = new Questao("Teste da Questao", 0, 4, 0.5);

    /**
     * Teste do método toString, da classe Questao.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        assertEquals("Os identificadores são iguais", "Descrição da Questão: Teste da Questao\n", questao.toString());
    }

    /**
     * Teste do método hashCode, da classe Questao.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertEquals("Hash codes iguais", -937181503, questao.hashCode());
    }

    /**
     * Teste do método equals, da classe Questao.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, questao);
        assertNotEquals("Instância de outra classe não é igual", new EAN(1), questao);
        assertNotEquals("Instância de Questao diferente", new Questao("Teste1",0,4,0.5), questao);
        assertEquals("Instância de Questao igual", new Questao("Teste da Questao",0,4,0.5), questao);
    }
    
}
