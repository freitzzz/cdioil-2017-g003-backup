/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import cdioil.domain.Categoria;
import cdioil.domain.EstruturaMercadologica;
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

        Categoria c = new Categoria("Bricolage", "1");

        struct.adicionarCategoriaRaiz(c);

        struct.adicionarCategoria(c, null);
    }

    @Test
    public void verificarAdicionarCategoriaRaizAdiciona() {

        EstruturaMercadologica struct = new EstruturaMercadologica();

        assertEquals("A estrutura deverá conter apenas o node raiz", 1, struct.tamanho());

        Categoria c = new Categoria("Bens Alimentares", "1");

        assertTrue(struct.adicionarCategoriaRaiz(c));

        assertTrue(struct.verificaLigados(struct.getRaiz().getElemento(), c));

        assertEquals(2, struct.tamanho());

    }

    @Test
    public void verificarAdicionarCategoriaAdiciona() {

        EstruturaMercadologica struct = new EstruturaMercadologica();

        assertEquals("A estrutura deverá conter apenas o node raiz", 1, struct.tamanho());

        Categoria c = new Categoria("Bens Alimentares", "1");

        assertTrue(struct.adicionarCategoriaRaiz(c));

        assertEquals("A estrutura deverá conter dois nodes", 2, struct.tamanho());

        Categoria c2 = new Categoria("Bebidas", "2");

        assertTrue(struct.adicionarCategoria(c, c2));

        assertEquals("A estrutura deverá agora conter três nodes", 3, struct.tamanho());

        assertTrue(struct.verificaLigados(c, c2));

    }

    @Test
    public void verificarGetFolhasFunciona() {

        EstruturaMercadologica struct = new EstruturaMercadologica();

        Categoria c1 = new Categoria("Casa e Decoracao", "1");

        Categoria c2 = new Categoria("Roupa", "2");

        struct.adicionarCategoriaRaiz(c1);
        struct.adicionarCategoriaRaiz(c2);

        List<Categoria> expected = new LinkedList<>();

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

        Categoria c = new Categoria("Higiene", "1");

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

        Categoria c1 = new Categoria("Categoria pai", "1");

        struct.adicionarCategoriaRaiz(c1);

        Categoria c2 = new Categoria("Categoria filho", "2");

        struct.adicionarCategoria(c1, c2);

        Produto p = new Produto("Produto", new Preco("0.32€"));

        assertFalse("A categoria nao e uma folha", struct.adicionarProduto(p, c1));

        assertTrue(struct.adicionarProduto(p, c2));
    }

       @Test
    public void testAdicionarSubCategorias() {

        EstruturaMercadologica struct = new EstruturaMercadologica();

        Categoria mae = new Categoria("Roupa", "DC20");

        struct.adicionarCategoriaRaiz(mae);

        for (int i = 0; i < 30; i++) {

            Categoria pai = new Categoria("Alimentar", "DC10");

            struct.adicionarCategoriaRaiz(pai);

            Categoria filho = new Categoria("Bens Essenciais", "UN10");

            struct.adicionarCategoria(pai, filho);

            Categoria filho2 = new Categoria("Gorduras Liquidas", "CAT1000");

            struct.adicionarCategoria(filho, filho2);

            Categoria filho3 = new Categoria("Sub-Categoria " + i, "SCAT0" + i);

            struct.adicionarCategoria(filho2, filho3);

            Produto p = new Produto("Produto " + i, new Preco("1.40€"));

            struct.adicionarProduto(p, filho3);

        }

        assertEquals(35, struct.tamanho());

    }

}
