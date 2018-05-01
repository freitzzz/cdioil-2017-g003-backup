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
    private Product product;

    public ProductTest() {
    }

    @Before
    public void setUp() {
        this.product = new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235"));
    }

    @Test
    public void ensureEmptyConstructorCreatesInstance() {
        Product p1 = new Product();
        assertNotNull(p1);
    }

    /**
     * Test of the constructor of class Product
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureProductDoesntHaveNullName() {
        System.out.println("Product()");
        Product product = new Product(null, new SKU(), "1 L");
    }

    /**
     * Test of the constructor of class Product
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureProductDoesntHaveEmptyName() {
        System.out.println("Product()");
        Product product = new Product("       ", new SKU(), "1 L");
    }

    /**
     * Test of the constructor of class Product
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureProductDoesntHaveNullSKU() {
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
                + "are equal.", product.productName(), "ProdutoTeste");
    }

    /**
     * Test of toString method, of class Product.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        Product copia = new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235"));

        assertEquals(product.toString(), copia.toString());
        assertNotEquals(product.toString(), null);
    }

    /**
     * Test of hashCode method, of class Product.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");

        Product copia = new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235"));

        assertEquals(product.hashCode(), copia.hashCode());

        //Mutation tests
        assertNotEquals("".hashCode(), product.hashCode());
        int num = 67 * 7 + product.getID().hashCode();
        assertEquals(num, product.hashCode());

    }

    /**
     * Test of equals method, of class Product.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, product);
        assertNotEquals("Instância de outra classe não é igual", new Category("CategoriaTeste", "100DC"), product);

        assertNotEquals("Instância de Produto diferente", new Product("ProdutoTeste", new SKU("33312118"), "1 L", new QRCode("552671")), product);
        assertEquals("Instância com códigos iguais igual", new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")), product);
    }

    /**
     * Test of changeProductImage method, of class Product.
     */
    @Test
    public void testAlterarImagemProduto() {
        System.out.println("alterarImagemProduto");
        //test with same instance
        assertTrue(product.equals(product));
        //test with null parameter
        assertFalse(product.equals(null));
        //test with instances of different classes
        assertFalse(product.equals(new QRCode()));
        byte[] imagem = "Nova Imagem".getBytes();
        product.changeProductImage(imagem);
        assertTrue("A condição acertar pois a Imagem do produto foi alterada com successo", product.changeProductImage(imagem));
        assertFalse("A condição acertar pois a Imagem do produto é invalida", product.changeProductImage(null));
    }

    /**
     * Test of getID method, of class Product.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");

        Product p1 = new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235"));
        SKU expected = new SKU("544231234");

        assertEquals(p1.getID(), expected);
    }
}
