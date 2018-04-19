/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of Product class
 *
 * @author Rita Gonçalves (1160912)
 */
public class ProductTest {

    /**
     * Instance of Product for testing purposes.
     */
    private Product p;

    public ProductTest() {
    }

    @Before
    public void setUp() {
        this.p = new Product("ProdutoTeste", new EAN("544231234"), "1 L", new QRCode("4324235"));
    }

    /**
     * Test of the constructor of class Product
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureProductDoesntHaveNullName() {
        System.out.println("Product()");
        Product product = new Product(null, new QRCode(), "1 L");
    }

    /**
     * Test of the constructor of class Product
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureProductDoesntHaveEmptyName() {
        System.out.println("Product()");
        Product product = new Product("       ", new QRCode(), "1 L");
    }

    /**
     * Test of the constructor of class Product
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureProductDoesntHaveNullCode() {
        System.out.println("Product()");
        Product product = new Product("Nome yey", null, "1 L");
    }

    /**
     * Test of productName method, of class Product.
     */
    @Test
    public void testProductName() {
        System.out.println("productName");
        assertEquals("The condition should succeed because the names"
                + "are equal.", p.productName(), "ProdutoTeste");
    }

    /**
     * Test of toString method, of class Product.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        System.out.println(p.toString());

        Product copia = new Product("ProdutoTeste", new EAN("544231234"), "1 L", new QRCode("4324235"));

        assertEquals(p.toString(), copia.toString());
    }

    /**
     * Test of hashCode method, of class Product.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");

        Product copia = new Product("ProdutoTeste", new EAN("544231234"), "1 L", new QRCode("4324235"));

        assertEquals(p.hashCode(), copia.hashCode());

    }

    /**
     * Test of equals method, of class Product.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, p);
        assertNotEquals("Instância de outra classe não é igual", new Category("CategoriaTeste", "100DC"), p);

        assertNotEquals("Instância de Produto diferente", new Product("ProdutoTeste", new EAN("33312118"), "1 L", new QRCode("552671")), p);
        assertEquals("Instância com códigos iguais igual", new Product("ProdutoTeste", new EAN("544231234"), "1 L", new QRCode("4324235")), p);
    }

    /**
     * Test of changeProductImage method, of class Product.
     */
    @Test
    public void testAlterarImagemProduto() {
        System.out.println("alterarImagemProduto");
        //test with same instance
        assertTrue(p.equals(p));
        //test with null parameter
        assertFalse(p.equals(null));
        //test with instances of different classes
        assertFalse(p.equals(new QRCode()));
        byte[] imagem = "Nova Imagem".getBytes();
        p.changeProductImage(imagem);
        boolean expResult = true;
        assertTrue("A condição acertar pois a Imagem do produto foi alterada com successo", p.changeProductImage(imagem));
        assertFalse("A condição acertar pois a Imagem do produto é invalida", p.changeProductImage(null));
    }
}
