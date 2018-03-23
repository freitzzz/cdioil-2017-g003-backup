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
 * Tests of the class Category.
 *
 * @author Rita Gonçalves (1160912)
 */
public class CategoryTest {

    /**
     * Instance of Category for test purposes.
     */
    Category c;

    public CategoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        c = new Category("CategoriaTeste", "100CAT", "10DC-10UN-100CAT");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of the method addProduct, of the class Category.
     */
    @Test
    public void testAdicionarProduto() {
        System.out.println("addProduct");
        Product p = new Product("ProdutoTeste", new EAN("5434"));
        assertTrue("Produto pode ser adicionado", c.addProduct(p));
        c.addProduct(p);
        assertFalse("Produto já existente no Set", c.addProduct(p));
    }

    /**
     * Test of the regular expression to validate the identifier (no numbers), of the class Category.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDescritivoSemNumerosFalha() {
        c = new Category("CategoriaInvalida", "DC", "10DC");
    }

    /**
     * Test of the regular expression to validate the identifier (no sufix), of the class Category.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDescritivoSemSufixoFalha() {
        c = new Category("CategoriaInvalida", "10", "10DC");
    }

    /**
     * Test of the regular expression to validate the identifier (null identifier), of the class Category.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDescritivoNullFalha() {
        c = new Category("CategoriaInvalida", null, "10DC");
    }

    /**
     * Test of the regular expression to validate the name (no name), of the class Category.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDesignacaoVaziaFalha() {
        c = new Category("    ", "10DC", "10DC");
    }

    /**
     * Test of the regular expression to validate the name (null name), of the class Category.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDesignacaoNullFalha() {
        c = new Category(null, "10DC", "10DC");
    }

    /**
     * Test of the method toString, of the class Category.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        Category oth = new Category("CategoriaTeste", "100CAT", "10DC-10UN-100CAT");

        assertEquals("As descrições são iguais", oth.toString(), c.toString());
    }

    /**
     * Test of the method hashCode, of the class Category.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");

        Category oth = new Category("CategoriaTeste", "100CAT", "10DC-10UN-100CAT");

        assertEquals("Hash codes iguais", oth.hashCode(), c.hashCode());
    }

    /**
     * Test of the method equals, of the class Category.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, c);
        assertNotEquals("Instância de outra classe não é igual", new QRCode("12"), c);
        assertNotEquals("Instância de Categoria diferente", new Category("OutraCategoria", "102SCAT", "10DC-10UN-100CAT-102SCAT"), c);
        assertEquals("Instância de Categoria igual", new Category("CategoriaTeste", "100CAT", "10DC-10UN-100CAT"), c);
    }
}
