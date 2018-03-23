/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.GrupoUtilizadores;
import cdioil.domain.authz.Name;
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

    Category cat = new Category("Pai", "10DC");
    Calendar data = Calendar.getInstance();
    List<Question> listaQuestoes = new LinkedList<>();
    List<Survey> listaInqueritos = new LinkedList<>();

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
        Question q = new BinaryQuestion("QuestaoTeste");
        assertTrue("Deveria ser possível adicionar", template.adicionarQuestao(q));
        template.adicionarQuestao(q);
        assertFalse("Questão null", template.adicionarQuestao(null));
        assertFalse("Questão já existente", template.adicionarQuestao(q));
    }
    /**
     * Teste do método removerQuestao, da classe Template.
     */
    @Test
    public void testarRemoverQuestao() {
        System.out.println("removerQuestao");
        Question q = new BinaryQuestion("QuestaoTeste");
        template.adicionarQuestao(q);
        assertTrue("Deveria ser possível remover", template.removerQuestao(q));
        template.removerQuestao(q);
        assertFalse("Questão null", template.removerQuestao(null));
        assertFalse("Questão não existente", template.removerQuestao(q));
    }
    /**
     * Teste do método isQuestaoValida, da classe Template.
     */
    @Test
    public void testarIsQuestaoValida() {
        System.out.println("isQuestaoValida");
        Question q = new BinaryQuestion("QuestaoTeste");
        template.adicionarQuestao(q);
        assertTrue("Deveria ser válida", template.isQuestaoValida(q));
        template.removerQuestao(q);
        assertFalse("Questão null", template.isQuestaoValida(null));
        assertFalse("Questão não existente", template.isQuestaoValida(q));
    }
    /**
     * Teste do método toString, da classe Template.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        assertEquals("Os identificadores são iguais",
                "\nCategoria: Nome: Pai\nDescritivo: 10DC\n\nLista de Questoes: []", template.toString());
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
        assertNotEquals("Instância de outra classe não é igual", new QRCode("1"), template);
        assertNotEquals("Instância de outra classe não é igual", new QRCode("1"), template);
        assertNotEquals("Instância de Template diferente", new Template(null), template);
        assertEquals("Instância de Template igual", new Template(cat), template);
    }
}
