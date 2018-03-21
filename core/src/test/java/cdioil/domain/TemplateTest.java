/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 * Teste da classe Template.
 *
 * @author Ana Guerra (1161191)
 */
public class TemplateTest {

    Categoria cat = new Categoria("Pai", "10");
    List<Questao> listaQuestoes = new ArrayList<>();
    List<Inquerito> listaInqueritos = new ArrayList<>();

    /**
     * Instância de EAN para testes.
     */
    Template template = new Template(listaQuestoes, listaInqueritos, cat);

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

        Template template2 = new Template(listaQuestoes, listaInqueritos, cat);

        assertEquals("Hash codes iguais", template2.hashCode(), template.hashCode());
    }

    /**
     * Teste do método equals, da classe Template.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, template);
        assertNotEquals("Instância de outra classe não é igual", new CodigoQR("1"), template);
        assertNotEquals("Instância de Template diferente", new Template(null, null, null), template);
        assertEquals("Instância de Template igual", new Template(listaQuestoes, listaInqueritos, cat), template);
    }
}
