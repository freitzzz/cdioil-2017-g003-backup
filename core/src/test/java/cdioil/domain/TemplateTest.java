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
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Teste da classe Template.
 *
 * @author Ana Guerra (1161191)
 */
public class TemplateTest {

    Categoria cat = new Categoria("Pai", "10");
    Calendar data = Calendar.getInstance();
    List<Questao> listaQuestoes = new LinkedList<>();
    List<Inquerito> listaInqueritos = new LinkedList<>();

    /**
     * Instância de Template para testes.
     */
    Template template = new Template(cat);

    /**
     * Teste do método adicionarQuestao, da classe Template.
     */
    @Test
    public void testAdicionarQuestao() {
        System.out.println("adicionarQuestao");
        Questao q = new Questao("QuestaoTeste", 0, 4, 0.5);
        assertTrue("Deveria ser possível adicionar", template.adicionarQuestao(q));
        template.adicionarQuestao(q);
        assertFalse("Questão null", template.adicionarQuestao(null));
        assertFalse("Questão já existente", template.adicionarQuestao(q));
    }

    /**
     * Teste do método adicionarInquerito, da classe Template.
     */
    @Test
    public void testAdicionarInquerito() {
        System.out.println("adicionarInquerito");
        Inquerito i = new Inquerito(new Produto("Produto", new EAN(123456789)), data,
                new GrupoUtilizadores((new Gestor(new SystemUser(new Email("quimBarreiros@gmail.com"),
                        new Nome("Quim", "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))))));
        assertTrue("Deveria ser possível adicionar", template.adicionarInquerito(i));
        template.adicionarInquerito(i);
        assertFalse("Inquerito null", template.adicionarInquerito(null));
        assertFalse("Inquerito já existente", template.adicionarInquerito(i));
    }

    /**
     * Teste do método removerQuestao, da classe Template.
     */
    @Test
    public void testarRemoverQuestao() {
        System.out.println("removerQuestao");
        Questao q = new Questao("QuestaoTeste", 0, 4, 0.5);
        template.adicionarQuestao(q);
        assertTrue("Deveria ser possível remover", template.removerQuestao(q));
        template.removerQuestao(q);
        assertFalse("Questão null", template.removerQuestao(null));
        assertFalse("Questão não existente", template.removerQuestao(q));
    }

    /**
     * Teste do método removerInquerito, da classe Template.
     */
    @Test
    public void testarRemoverInquerito() {
        System.out.println("removerInquerito");
        Inquerito i = new Inquerito(new Produto("Produto", new EAN(123456789)), data,
                new GrupoUtilizadores((new Gestor(new SystemUser(new Email("quimBarreiros@gmail.com"),
                        new Nome("Quim", "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))))));
        template.adicionarInquerito(i);
        assertTrue("Deveria ser possível remover", template.removerInquerito(i));
        template.removerInquerito(i);
        assertFalse("Inquerito null", template.removerInquerito(null));
        assertFalse("Inquerito não existente", template.removerInquerito(i));
    }

    /**
     * Teste do método isQuestaoValida, da classe Template.
     */
    @Test
    public void testarIsQuestaoValida() {
        System.out.println("isQuestaoValida");
        Questao q = new Questao("QuestaoTeste", 0, 4, 0.5);
        template.adicionarQuestao(q);
        assertTrue("Deveria ser válida", template.isQuestaoValida(q));
        template.removerQuestao(q);
        assertFalse("Questão null", template.isQuestaoValida(null));
        assertFalse("Questão não existente", template.isQuestaoValida(q));
    }

    /**
     * Teste do método isInqueritoValido, da classe Template.
     */
    @Test
    public void testarIsInqueritoValido() {
        System.out.println("isInqueritoValido");
        Inquerito i = new Inquerito(new Produto("Produto", new EAN(123456789)), data,
                new GrupoUtilizadores((new Gestor(new SystemUser(new Email("quimBarreiros@gmail.com"),
                        new Nome("Quim", "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))))));
        template.adicionarInquerito(i);
        assertTrue("Deveria ser válida", template.isInqueritoValido(i));
        template.removerInquerito(i);
        assertFalse("Inquerito null", template.isInqueritoValido(null));
        assertFalse("Inquerito não existente", template.isInqueritoValido(i));
    }

    /**
     * Teste do método toString, da classe Template.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        assertEquals("Os identificadores são iguais",
                "\nCategoria: Nome: Pai\nDescritor: 10\n\nLista de Questoes: []", template.toString());
    }

    /**
     * Teste do método hashCode, da classe Template.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");

        Template template2 = new Template(cat);

        assertEquals("Hash codes iguais", template2.hashCode(), template.hashCode());
    }

    /**
     * Teste do método equals, da classe Template.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, template);
        assertNotEquals("Instância de outra classe não é igual", new CodigoQR(1), template);
        assertNotEquals("Instância de Template diferente", new Template(null), template);
        assertEquals("Instância de Template igual", new Template(cat), template);
    }
}
