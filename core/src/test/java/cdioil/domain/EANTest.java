/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 * Test the class EAN.
 *
 * @author Ana Guerra (1161191)
 */
public class EANTest {

    /**
     * Intance the EAN for tests.
     */
    EAN ean = new EAN("1700034560");

    /**
     * Test the method toString, of class EAN.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        
        EAN copy = new EAN("1700034560");

        assertEquals("Os identificadores são iguais", copy.toString(), ean.toString());
    }

    /**
     * Test the method hashCode, of class EAN.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");

        EAN copy = new EAN("1700034560");

        assertEquals("Hash codes iguais", copy.hashCode(), ean.hashCode());
    }

    /**
     * Test the method equals, of class EAN.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertEquals("Comparing same instances",ean,ean);
        assertNotEquals("Objeto null não é igual", ean, null);
        assertNotEquals("Instância de outra classe não é igual", new QRCode("1"), ean);
        assertNotEquals("Instância de EAN diferente", new EAN("60007896"), ean);
        assertEquals("Instância de EAN igual", new EAN("1700034560"), ean);
    }

}
