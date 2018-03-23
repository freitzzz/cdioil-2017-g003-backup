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

        EstruturaMercadologica struct = new EstruturaMercadologica();

        struct.adicionarCategoriaRaiz(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verificarAdicionarCategoriaNaoPermiteParametrosNulos() {

        EstruturaMercadologica struct = new EstruturaMercadologica();

        Category c = new Category("Bricolage", "109CAT");

        struct.adicionarCategoriaRaiz(c);

        struct.adicionarCategoria(c, null);
    }

    @Test
    public void verificarAdicionarCategoriaRaizAdiciona() {

        EstruturaMercadologica struct = new EstruturaMercadologica();

        assertEquals("A estrutura deverá conter apenas o node raiz", 1, struct.tamanho());

        Category c = new Category("Bens Alimentares", "109UB");

        assertTrue(struct.adicionarCategoriaRaiz(c));

        assertTrue(struct.verificaLigados(struct.getRaiz().getElemento(), c));

        assertEquals(2, struct.tamanho());

    }

    @Test
    public void verificarAdicionarCategoriaAdiciona() {

        EstruturaMercadologica struct = new EstruturaMercadologica();

        assertEquals("A estrutura deverá conter apenas o node raiz", 1, struct.tamanho());

        Category c = new Category("Bens Alimentares", "109UB");

        assertTrue(struct.adicionarCategoriaRaiz(c));

        assertEquals("A estrutura deverá conter dois nodes", 2, struct.tamanho());

        Category c2 = new Category("Bebidas", "20UN");

        assertTrue(struct.adicionarCategoria(c, c2));

        assertEquals("A estrutura deverá agora conter três nodes", 3, struct.tamanho());

        assertTrue(struct.verificaLigados(c, c2));

    }

    @Test
    public void verificarGetFolhasFunciona() {

        EstruturaMercadologica struct = new EstruturaMercadologica();

        Category c1 = new Category("Casa e Decoracao", "1009SCAT");

        Category c2 = new Category("Roupa", "20CAT");

        struct.adicionarCategoriaRaiz(c1);
        struct.adicionarCategoriaRaiz(c2);

        List<Category> expected = new LinkedList<>();

        expected.add(c1);
        expected.add(c2);

        assertEquals(struct.getFolhas(), expected);

    }

    @Test(expected = IllegalArgumentException.class)
    public void verficarVerificarLigadosNaoPermiteParametrosNulos() {

        EstruturaMercadologica struct = new EstruturaMercadologica();

        struct.verificaLigados(null, null);

    }

    @Test
    public void verificaVerificarLigadosFunciona() {

        EstruturaMercadologica struct = new EstruturaMercadologica();

        Category c = new Category("Higiene", "10CAT");

        struct.adicionarCategoriaRaiz(c);

        assertTrue(struct.verificaLigados(struct.getRaiz().getElemento(), c));
    }

    @Test(expected = IllegalArgumentException.class)
    public void verificarAdicionarProdutoNaoPermiteParametrosNulos() {

        EstruturaMercadologica struct = new EstruturaMercadologica();

        struct.adicionarProduto(null, null);
    }

    @Test
    public void verificarAdicionarProdutoApenasAdicionaACategoriasFolhas() {

        EstruturaMercadologica struct = new EstruturaMercadologica();

        Category c1 = new Category("Categoria pai", "100CAT");

        struct.adicionarCategoriaRaiz(c1);

        Category c2 = new Category("Categoria filho", "20SCAT");

        struct.adicionarCategoria(c1, c2);
        
        Produto p = new Produto("Produto", new EAN("1"));

        assertFalse("A categoria nao e uma folha", struct.adicionarProduto(p, c1));

        assertTrue(struct.adicionarProduto(p, c2));
    }

    @Test
    public void testAdicionarSubCategorias() {

        EstruturaMercadologica struct = new EstruturaMercadologica();

        Category mae = new Category("Roupa", "20UB");

        struct.adicionarCategoriaRaiz(mae);

        for (int i = 0; i < 30; i++) {

            Category pai = new Category("Alimentar", "10UB");

            struct.adicionarCategoriaRaiz(pai);

            Category filho = new Category("Bens Essenciais", "10DC");

            struct.adicionarCategoria(pai, filho);

            Category filho2 = new Category("Gorduras Liquidas", "1000CAT");

            struct.adicionarCategoria(filho, filho2);

            Category filho3 = new Category("Sub-Categoria " + i, "10" + i + "SCAT");

            struct.adicionarCategoria(filho2, filho3);
            
            Produto p = new Produto("Produto " + i, new EAN("i"), new QRCode(Integer.toString(2*i)));

            struct.adicionarProduto(p, filho3);

        }
        assertEquals(35, struct.tamanho());
    }
}
