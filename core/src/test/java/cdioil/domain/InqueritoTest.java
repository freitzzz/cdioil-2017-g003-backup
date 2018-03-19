/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import cdioil.domain.authz.Email;
import cdioil.domain.authz.Gestor;
import cdioil.domain.authz.GrupoUtilizadores;
import cdioil.domain.authz.Nome;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.SystemUser;
import java.util.ArrayList;
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes da classe Inquerito.
 *
 * @author Rita Gonçalves (1160912)
 */
public class InqueritoTest {

    /**
     * Instância de Inquérito para testes.
     */
    Inquerito i;
    GrupoUtilizadores gu;
    Calendar data;

    public InqueritoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        gu = new GrupoUtilizadores((new Gestor(new SystemUser(new Email("quimBarreiros@gmail.com"), new Nome("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41")))));
        data = Calendar.getInstance();
        this.i = new Inquerito(new Produto("UmProduto", new EAN(73292)), data, gu);

    }

    @After
    public void tearDown() {
    }

    /**
     * Teste do método hashCode, da classe Inquerito.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertEquals("Deveriam ser iguais", 74343, i.hashCode());
    }

    /**
     * Teste do método equals, da classe Inquerito.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, i);
        assertNotEquals("Instância de outra classe não é igual", new Categoria("CategoriaTeste", "100FC"), i);
        assertNotEquals("Instância de Inquerito diferente", new Inquerito(new Produto("OutroProduto", new EAN(123)), data, gu), i);
        assertEquals("Instância de Inquerito igual", new Inquerito(new Produto("UmProduto", new EAN(73292)), data, gu), i);
    }

    /**
     * Teste do método toString, da classe Inquerito.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        System.out.println(i.toString());
        assertEquals("A condição deve acertar pois o conteudo das Strings são iguais", i.toString(),
                 "Inquerito sobre o produto:\n" + new Produto("UmProduto", new EAN(73292))
                + "\nData:\n" + data);
    }

    /**
     * Teste do método adicionarQuestao, da classe Inquerito.
     */
    @Test
    public void testAdicionarQuestao() {
        System.out.println("adicionarQuestao");
        Questao q = new Questao("QuestaoTeste", 0, 4, 0.5);
        assertTrue("Deveria ser possível adicionar", i.adicionarQuestao(q));
        i.adicionarQuestao(q);
        assertFalse("Questão null", i.adicionarQuestao(null));
        assertFalse("Questão já existente", i.adicionarQuestao(q));
    }

    /**
     * Teste do método removerQuestao, da classe Inquerito.
     */
    @Test
    public void testarRemoverQuestao() {
        System.out.println("removerQuestao");
        Questao q = new Questao("QuestaoTeste", 0, 4, 0.5);
        i.adicionarQuestao(q);
        assertTrue("Deveria ser possível remover", i.removerQuestao(q));
        i.removerQuestao(q);
        assertFalse("Questão null", i.removerQuestao(null));
        assertFalse("Questão não existente", i.removerQuestao(q));
    }

    /**
     * Teste do método isQuestaoValida, da classe Inquerito.
     */
    @Test
    public void testarIsQuestaoValida() {
        System.out.println("isQuestaoValida");
        Questao q = new Questao("QuestaoTeste", 0, 4, 0.5);
        i.adicionarQuestao(q);
        assertTrue("Deveria ser válida", i.isQuestaoValida(q));
        i.removerQuestao(q);
        assertFalse("Questão null", i.isQuestaoValida(null));
        assertFalse("Questão não existente", i.isQuestaoValida(q));
    }

    /**
     * Test do metodo info, da classe Concurso.
     */
    @Test
    public void testInfo() {
        System.out.println("info");
        Inquerito inquerito = new Inquerito(new Produto("Teste", new EAN(123456789)), data, gu);
        String expResult = "Inquerito sobre o produto:\n" + (new Produto("Teste", new EAN(123456789))).toString()
                + "\nData:\n" + data;
        String result = inquerito.info();
        assertEquals(expResult, result);
    }

    /**
     * Test of publicoAlvo method, of class Inquerito.
     */
    @Test
    public void testPublicoAlvo() {
        System.out.println("publicoAlvo");
        Inquerito inquerito = new Inquerito(new Produto("Teste", new EAN(123456789)), data, gu);
        GrupoUtilizadores expResult = gu;
        GrupoUtilizadores result = inquerito.publicoAlvo();
        assertEquals(expResult, result);
    }

}
