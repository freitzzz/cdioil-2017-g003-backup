/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes da classe Produto.
 *
 * @author Rita Gonçalves (1160912)
 */
public class ProdutoTest {

    /**
     * Instância de produto para testes.
     */
    private Produto p;

    public ProdutoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.p = new Produto("ProdutoTeste", new Preco("7€"), new EAN(33312113));
    }

    @After
    public void tearDown() {
    }

    /**
     * Testa o método toString, da classe Produto.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        System.out.println(p.toString());
        assertEquals("Deveriam ser iguais", "Nome: ProdutoTeste\nPreço: 7.0 EUR\nCódigo de Barras: \nID: 33312113\nCódigo QR: \nSem código QR\n", p.toString());
    }

    /**
     * Testa o método hashCode, da classe Produto.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertEquals("Deveriam ser iguais", 33313163, p.hashCode());
    }

    /**
     * Testa o método equals, da classe Produto.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, p);
        assertNotEquals("Instância de outra classe não é igual", new Categoria("CategoriaTeste", "100FC"), p);
        assertNotEquals("Instância de Produto diferente", new Produto("ProdutoTeste", new Preco("7€"), new EAN(33312118), new CodigoQR(552671)), p);
        assertEquals("Instância de CodigoQR igual", new Produto("ProdutoTeste", new Preco("7€"), new EAN(33312113)), p);
    }
}
