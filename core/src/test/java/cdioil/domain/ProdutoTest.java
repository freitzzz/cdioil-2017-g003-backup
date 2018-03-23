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
 * Testes da classe Product.
 *
 * @author Rita Gonçalves (1160912)
 */
public class ProdutoTest {

    /**
     * Instância de produto para testes.
     */
    private Product p;

    public ProdutoTest() {
    }

    @Before
    public void setUp() {
        this.p = new Product("ProdutoTeste", new EAN("544231234"), new CodigoQR("4324235"));
    }

    /**
     * Testa o método toString, da classe Product.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        System.out.println(p.toString());

        Product copia = new Product("ProdutoTeste", new EAN("544231234"), new CodigoQR("4324235"));

        assertEquals(p.toString(), copia.toString());
    }

    /**
     * Testa o método hashCode, da classe Product.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");

        Product copia = new Product("ProdutoTeste", new EAN("544231234"), new CodigoQR("4324235"));

        assertEquals(p.hashCode(), copia.hashCode());

    }

    /**
     * Testa o método equals, da classe Product.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, p);
        assertNotEquals("Instância de outra classe não é igual", new Category("CategoriaTeste", "100DC"), p);
        assertNotEquals("Instância de Produto diferente", new Product("ProdutoTeste", new EAN("33312118"), new CodigoQR("552671")), p);
        assertEquals("Instância com códigos iguais igual", new Product("ProdutoTeste", new EAN("544231234"), new CodigoQR("4324235")), p);
    }

    /**
     * Test of changeProductImage method, of class Product.
     */
    @Test
    public void testAlterarImagemProduto() {
        System.out.println("alterarImagemProduto");
        byte[] imagem = "Nova Imagem".getBytes();
        p.changeProductImage(imagem);
        boolean expResult = true;
        assertTrue("A condição acertar pois a Imagem do produto foi alterada com successo",p.changeProductImage(imagem));
        assertFalse("A condição acertar pois a Imagem do produto é invalida",p.changeProductImage(null));
    }
}
