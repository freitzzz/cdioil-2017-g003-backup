/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests of class QRCode.
 *
 * @author Rita Gonçalves (1160912)
 */
public class QRCodeTest {

    /**
     * Instance of QRCode for testing purposes.
     */
    QRCode c;

    public QRCodeTest() {
    }

    @Before
    public void setUp() {
        c = new QRCode("73534325");
    }

    /**
     * Test of toString method, of class QRCode.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        Code copia = new QRCode("73534325");

        assertEquals("Objetos com atributos iguais deverão ter descrições iguais", copia.toString(), c.toString());

        Code outro = new QRCode("4237484234");

        assertNotEquals("Objetos com atributos diferentes deverão ter descrições diferentes", outro.toString(), c.toString());

        Code codBarras = new EAN("73534325");

        assertNotEquals("Apesar de ambos serem códigos com o mesmo valor as descrições deverão ser diferentes", codBarras.toString(), c.toString());

    }

    /**
     * Test of hashCode method, of class QRCode
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");

        Code copia = new QRCode("73534325");

        assertEquals("Objetos com atributos iguais deverão ter hashcodes iguais", copia.toString(), c.toString());

        Code outro = new QRCode("4237484234");

        assertNotEquals("Objetos com atributos diferentes deverão ter hashcodes diferentes", outro.toString(), c.toString());

        Code codBarras = new EAN("73534325");

        assertNotEquals("Apesar de ambos serem códigos com o mesmo valor os hashcodes deverão ser diferentes", codBarras.hashCode(), c.hashCode());

    }

    /**
     * Test of equals method, of class QRCode
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertEquals("Comparing same instances", c, c);
        assertNotEquals("Objeto null não é igual", c, null);
        assertNotEquals("Instância de outra classe não é igual", new EAN("4324"), c);
        assertNotEquals("Instância de CodigoQR diferente", new QRCode("6032"), c);
        assertEquals("Instância de CodigoQR igual", new QRCode("73534325"), c);
    }
}
