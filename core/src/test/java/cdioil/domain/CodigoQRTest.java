/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Testes da classe CodigoQR.
 *
 * @author Rita Gonçalves (1160912)
 */
public class CodigoQRTest {

    /**
     * Instância de CodigoQR para testes.
     */
    CodigoQR c;

    public CodigoQRTest() {
    }

    @Before
    public void setUp() {
        c = new CodigoQR("73534325");
    }

    /**
     * Teste do método toString, da classe CodigoQR.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        Code copia = new CodigoQR("73534325");

        assertEquals("Objetos com atributos iguais deverão ter descrições iguais", copia.toString(), c.toString());

        Code outro = new CodigoQR("4237484234");

        assertNotEquals("Objetos com atributos diferentes deverão ter descrições diferentes", outro.toString(), c.toString());

        Code codBarras = new EAN("73534325");

        assertNotEquals("Apesar de ambos serem códigos com o mesmo valor as descrições deverão ser diferentes", codBarras.toString(), c.toString());

    }

    /**
     * Teste do método hashCode, da classe CodigoQR.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");

        Code copia = new CodigoQR("73534325");

        assertEquals("Objetos com atributos iguais deverão ter hashcodes iguais", copia.toString(), c.toString());

        Code outro = new CodigoQR("4237484234");

        assertNotEquals("Objetos com atributos diferentes deverão ter hashcodes diferentes", outro.toString(), c.toString());

        Code codBarras = new EAN("73534325");

        assertNotEquals("Apesar de ambos serem códigos com o mesmo valor os hashcodes deverão ser diferentes", codBarras.hashCode(), c.hashCode());

    }

    /**
     * Teste do método equals, da classe CodigoQR.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, c);
        assertNotEquals("Instância de outra classe não é igual", new EAN("4324"), c);
        assertNotEquals("Instância de CodigoQR diferente", new CodigoQR("6032"), c);
        assertEquals("Instância de CodigoQR igual", new CodigoQR("73534325"), c);
    }
}
