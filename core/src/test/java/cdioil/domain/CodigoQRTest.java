/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import cdioil.domain.CodigoQR;
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

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        c = new CodigoQR(52);
    }

    @After
    public void tearDown() {
    }

    /**
     * Teste do método toString, da classe CodigoQR.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        assertEquals("As descrições são iguais", "ID: 52\n", c.toString());
    }

    /**
     * Teste do método hashCode, da classe CodigoQR.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertEquals("Hash codes iguais", 255, c.hashCode());
    }

    /**
     * Teste do método equals, da classe CodigoQR.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, c);
        assertNotEquals("Instância de outra classe não é igual", "Hello", c);
        assertNotEquals("Instância de CodigoQR diferente", new CodigoQR(60), c);
        assertEquals("Instância de CodigoQR igual", new CodigoQR(52), c);
    }
}
