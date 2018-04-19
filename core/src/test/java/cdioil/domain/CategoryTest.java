/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import java.util.HashSet;
import java.util.Iterator;
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
        c = new Category("CategoriaTeste", "10DC-10UN-100CAT");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of categoryName method, of class Category.
     */
    @Test
    public void testCategoryName() {
        System.out.println("categoryName");
        assertEquals("The condition should succeed because the names are"
                + "the same", c.categoryName(), "CategoriaTeste");
    }

    /**
     * Test of the method addProduct, of the class Category.
     */
    @Test
    public void testAddProduct() {
        System.out.println("addProduct");
        //test with null parameter
        assertFalse(c.addProduct(null));
        Product p = new Product("ProdutoTeste", new EAN("5434"), "1 L");
        assertTrue("Produto pode ser adicionado", c.addProduct(p));
        c.addProduct(p);
        assertFalse("Produto já existente no Set", c.addProduct(p));
    }

    /**
     * Test to validate the root name and identifier, of the class Category.
     */
    public void testRootIdentifierAndNameWork() {
        System.out.println("validate identifier regex and name - root");
        c = new Category("CategoriaValida", "RAIZ");
        assertNotNull(c);
    }

    /**
     * Test to validate that an empty name fails, of the class Category.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyNameFails() {
        System.out.println("validate empty name fails");
        c = new Category("    ", "10DC");
    }

    /**
     * Test to validate that a null name fails, of the class Category.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullNameFails() {
        System.out.println("validate null name fails");
        c = new Category(null, "10DC");
    }

    /**
     * Test to validate that a null path fails, of the class Category.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullPathFails() {
        System.out.println("validate null path fails");
        c = new Category("CategoriaInvalida", null);
    }

    /**
     * Test to validate that an empty path fails, of the class Category.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyPathFails() {
        System.out.println("validate empty path fails");
        c = new Category("CategoriaInvalida", "  ");
    }

    /**
     * Test of getProductSetIterator method, of class Category
     */
    @Test
    public void testGetProductSetIterator() {
        System.out.println("getProductSetIterator");
        Iterator<Product> ip = c.getProductSetIterator();
        //collection is empty
        assertFalse(ip.hasNext());
        c.addProduct(new Product("nome yey", new QRCode(), "1 L"));
        ip = c.getProductSetIterator();
        //collection has one item
        assertTrue(ip.hasNext());
    }

    /**
     * Test of the method toString, of the class Category.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        Category oth = new Category("CategoriaTeste", "10DC-10UN-100CAT");

        assertEquals("As descrições são iguais", oth.toString(), c.toString());
    }

    /**
     * Test of the method hashCode, of the class Category.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");

        Category oth = new Category("CategoriaTeste", "10DC-10UN-100CAT");

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
        assertNotEquals("Instância de Categoria diferente", new Category("OutraCategoria", "10DC-10UN-100CAT-102SCAT"), c);
        assertEquals("Instância de Categoria igual", new Category("CategoriaTeste", "10DC-10UN-100CAT"), c);
        Category cNUll = null;
        assertFalse(c.equals(cNUll));
    }
    
    @Test
    public void testSuffixesValues(){
        
        Category.Sufixes expected[] = {Category.Sufixes.SUFIX_DC, Category.Sufixes.SUFIX_UN,
            Category.Sufixes.SUFIX_CAT, Category.Sufixes.SUFIX_SCAT, Category.Sufixes.SUFIX_UB};
        
        assertArrayEquals(expected,Category.Sufixes.values());
    }
    
    @Test
    public void testSuffixesValueOf(){
        
        Category.Sufixes expected = Category.Sufixes.SUFIX_CAT;
        
        Category.Sufixes actual = Category.Sufixes.valueOf("SUFIX_CAT");
        
        assertEquals(actual, expected);
    }
}
