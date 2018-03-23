/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author António Sousa [1161371]
 */
public class EstruturaMercadologicaTest {

    public EstruturaMercadologicaTest() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void verificarAdicionarCategoriaRaizNaoPermiteParametrosNulos() {

        MarketStructure struct = new MarketStructure();

        struct.addRootCategory(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verificarAdicionarCategoriaNaoPermiteParametrosNulos() {

        MarketStructure struct = new MarketStructure();

        Category c = new Category("Bricolage", "100CAT", "10DC-10UN-100CAT");

        struct.addRootCategory(c);

        struct.addCategory(c, null);
    }

    @Test
    public void verificarAdicionarCategoriaRaizAdiciona() {

        MarketStructure struct = new MarketStructure();

        assertEquals("A estrutura deverá conter apenas o node raiz", 1, struct.size());

        Category c = new Category("Bens Alimentares", "109UB", "10DC-10UN-100CAT-102SCAT-109UB");

        assertTrue(struct.addRootCategory(c));

        assertTrue(struct.checkDirectlyConnected(struct.getRoot().getElement(), c));

        assertEquals(2, struct.size());

    }

    @Test
    public void verificarAdicionarCategoriaAdiciona() {

        MarketStructure struct = new MarketStructure();

        assertEquals("A estrutura deverá conter apenas o node raiz", 1, struct.size());

        Category c = new Category("Bens Alimentares", "109UB", "10DC-10UN-100CAT-102SCAT-109UB");

        assertTrue(struct.addRootCategory(c));

        assertEquals("A estrutura deverá conter dois nodes", 2, struct.size());

        Category c2 = new Category("Bebidas", "20UN", "10DC-20UN");

        assertTrue(struct.addCategory(c, c2));

        assertEquals("A estrutura deverá agora conter três nodes", 3, struct.size());

        assertTrue(struct.checkDirectlyConnected(c, c2));

    }

    @Test
    public void verificarGetFolhasFunciona() {

        MarketStructure struct = new MarketStructure();

        Category c1 = new Category("Casa e Decoracao", "1009SCAT", "10DC-10UN-100CAT-1009SCAT");

        Category c2 = new Category("Roupa", "20CAT", "10DC-10UN-20CAT");

        struct.addRootCategory(c1);
        struct.addRootCategory(c2);

        List<Category> expected = new LinkedList<>();

        expected.add(c1);
        expected.add(c2);

        assertEquals(struct.getLeaves(), expected);

    }

    @Test(expected = IllegalArgumentException.class)
    public void verficarVerificarLigadosNaoPermiteParametrosNulos() {

        MarketStructure struct = new MarketStructure();

        struct.checkDirectlyConnected(null, null);

    }

    @Test
    public void verificaVerificarLigadosFunciona() {

        MarketStructure struct = new MarketStructure();

        Category c = new Category("Higiene", "10CAT", "10DC-10UN-10CAT");

        struct.addRootCategory(c);

        assertTrue(struct.checkDirectlyConnected(struct.getRoot().getElement(), c));
    }

    @Test(expected = IllegalArgumentException.class)
    public void verificarAdicionarProdutoNaoPermiteParametrosNulos() {

        MarketStructure struct = new MarketStructure();

        struct.addProduct(null, null);
    }

    @Test
    public void verificarAdicionarProdutoApenasAdicionaACategoriasFolhas() {

        MarketStructure struct = new MarketStructure();

        Category c1 = new Category("Categoria pai", "100CAT", "10DC-10UN-100CAT");

        struct.addRootCategory(c1);

        Category c2 = new Category("Categoria filho", "20SCAT", "10DC-10UN-100CAT-20SCAT");

        struct.addCategory(c1, c2);

        Product p = new Product("Produto", new EAN("1"));

        assertFalse("A categoria nao e uma folha", struct.addProduct(p, c1));

        assertTrue(struct.addProduct(p, c2));
    }

    @Test
    public void testAdicionarSubCategorias() {

        MarketStructure struct = new MarketStructure();

        Category mae = new Category("Roupa", "20UB", "10DC-10UN-100CAT-102SCAT-20UB");

        struct.addRootCategory(mae);

        for (int i = 0; i < 30; i++) {

            Category pai = new Category("Alimentar", "10UB", "10DC-10UN-100CAT-102SCAT-10UB");

            struct.addRootCategory(pai);

            Category filho = new Category("Bens Essenciais", "20DC", "20DC");

            struct.addCategory(pai, filho);

            Category filho2 = new Category("Gorduras Liquidas", "1000CAT", "10DC-10UN-1000CAT");

            struct.addCategory(filho, filho2);

            Category filho3 = new Category("Sub-Categoria " + i, "10" + i + "SCAT", "10DC-10UN-100CAT-10" + i + "SCAT");

            struct.addCategory(filho2, filho3);
            
            Product p = new Product("Produto " + i, new EAN("i"), new QRCode(Integer.toString(2*i)));

            struct.addProduct(p, filho3);

        }
        assertEquals(35, struct.size());
    }
}
