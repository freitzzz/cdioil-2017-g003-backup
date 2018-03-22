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
     * Teste a comprovar que a criação de uma Categoria com descritor sem número falha.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDescritivoSemNumerosFalha() {
        c = new Categoria("CategoriaInvalida", "DC");
    }

    /**
     * Teste a comprovar que a criação de uma Categoria com descritor sem sufixo falha.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDescritivoSemSufixoFalha() {
        c = new Categoria("CategoriaInvalida", "10");
    }

    /**
     * Teste a comprovar que a criação de uma Categoria com descritor nulo falha.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDescritivoNullFalha() {
        c = new Categoria("CategoriaInvalida", null);
    }

    /**
     * Teste a comprovar que a criação de uma Categoria com designação vazia falha.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDesignacaoVaziaFalha() {
        c = new Categoria("    ", "10DC");
    }

    /**
     * Teste a comprovar que a criação de uma Categoria com designação nulo falha.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDesignacaoNullFalha() {
        c = new Categoria(null, "10DC");
    }

    /**
     * Teste do método toString, da classe Categoria.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        
        Categoria outra = new Categoria("CategoriaTeste", "100CAT");
        
        assertEquals("As descrições são iguais", outra.toString(), c.toString());
    }

    /**
     * Teste do método hashCode, da classe Categoria.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        
        Categoria outra = new Categoria("CategoriaTeste", "100CAT");
        
        assertEquals("Hash codes iguais", outra.hashCode(), c.hashCode());
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
        assertNotEquals("Instância de Categoria igual", new Categoria("NomeDiferenteMesmoDescritor", "100CAT"), c);
        assertNotEquals("Instância de Categoria igual", new Categoria("CategoriaTeste", "101CAT"), c);
        assertEquals("Instância de Categoria igual", new Categoria("CategoriaTeste", "100CAT"), c);
    }

}
