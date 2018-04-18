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
 * Test the class SKU.
 *
 * @author Ana Guerra (1161191)
 */
public class SKUTest {
    /**
     * Intance the SKU for tests.
     */
    SKU sku = new SKU("1700034560");

    /**
     * Test of empty constructor of class SKU
     */
    @Test
    public void testEmptyConstructor() {
        System.out.println("SKU()");
        SKU sku = new SKU();
    }

    /**
     * Test the method toString, of class SKU.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        SKU copy = new SKU("1700034560");

        assertEquals("Os identificadores são iguais", copy.toString(), sku.toString());
    }

    /**
     * Test the method hashCode, of class SKU.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");

        SKU copy = new SKU("1700034560");

        assertEquals("Hash codes iguais", copy.hashCode(), sku.hashCode());
    }

    /**
     * Test the method equals, of class SKU.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertEquals("Comparing same instances", sku, sku);
        assertNotEquals("Objeto null não é igual", sku, null);
        assertNotEquals("Instância de outra classe não é igual", new QRCode("1"), sku);
        assertNotEquals("Instância de EAN diferente", new SKU("60007896"), sku);
        assertEquals("Instância de EAN igual", new SKU("1700034560"), sku);
    }
    
}
