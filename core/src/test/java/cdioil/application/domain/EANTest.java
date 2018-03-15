/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.domain;

import cdioil.domain.CodigoQR;
import cdioil.domain.EAN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 * Teste da classe EAN.
 * @author Ana Guerra (1161191)
 */
public class EANTest {
    
    /**
     * Instância de EAN para testes.
     */
    EAN ean = new EAN(1700034560);

    /**
     * Teste do método toString, da classe EAN.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        assertEquals("Os identificadores são iguais", "ID: 1700034560\n", ean.toString());
    }

    /**
     * Teste do método hashCode, da classe EAN.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertEquals("Hash codes iguais", 1700035141, ean.hashCode());
    }

    /**
     * Teste do método equals, da classe EAN.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, ean);
        assertNotEquals("Instância de outra classe não é igual", new CodigoQR(1), ean);
        assertNotEquals("Instância de EAN diferente", new EAN(60007896), ean);
        assertEquals("Instância de EAN igual", new EAN(1700034560), ean);
    }
    
}
