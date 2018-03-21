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
 * Testes da classe Categoria.
 *
 * @author Rita Gonçalves (1160912)
 */
public class CategoriaTest {

    /**
     * Instância de Categoria para testes.
     */
    Categoria c;

    public CategoriaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        c = new Categoria("CategoriaTeste", "100CAT");
    }

    @After
    public void tearDown() {
    }

    /**
     * Teste do método adicionarProduto, da classe Categoria.
     */
    @Test
    public void testAdicionarProduto() {
        System.out.println("adicionarProduto");
        Produto p = new Produto("ProdutoTeste", new EAN("5434"));
        assertTrue("Produto pode ser adicionado", c.adicionarProduto(p));
        c.adicionarProduto(p);
        assertFalse("Produto já existente no Set", c.adicionarProduto(p));
    }

    /**
     * Teste do método toString, da classe Categoria.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        assertEquals("As descrições são iguais", "Nome: CategoriaTeste\nDescritor: 100CAT\n", c.toString());
    }

    /**
     * Teste do método hashCode, da classe Categoria.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertEquals("Hash codes iguais", 1448654062, c.hashCode());
    }

    /**
     * Teste do método equals, da classe Categoria.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, c);
        assertNotEquals("Instância de outra classe não é igual", new CodigoQR("12"), c);
        assertNotEquals("Instância de Categoria diferente", new Categoria("OutraCategoria", "102SCAT"), c);
        assertEquals("Instância de Categoria igual", new Categoria("NomeDiferenteMesmoDescritor", "100CAT"), c);
    }

}
