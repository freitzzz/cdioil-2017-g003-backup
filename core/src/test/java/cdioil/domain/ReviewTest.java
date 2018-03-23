/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author João
 */
public class ReviewTest {

    Review instance;

    @Before
    public void setUp() {
        instance = new Review("ding dong your opinion is wrong", new Product());
    }

    /**
     * Teste do método equals, da classe Avaliacao
     */
    /*@Test
    public void testeEquals() {
        System.out.println("equals");
        //teste com objetos que não são instâncias deAvaliacao
        assertFalse(instance.equals(new Survey()));
        //teste com null
        assertFalse(instance.equals(null));
        //teste com a mesma instância
        assertTrue(instance.equals(instance));
        //teste com instâncias diferentes com a mesma informação mas ID diferente
        Review av = new Review("ding dong your opinion is wrong", new Survey());
        assertFalse(instance.equals(av));

    }*/

    /**
     * Test of toString method, of class Review
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expected = "Avaliação:\nOpinião: ding dong your opinion is wrong";
        assertEquals(expected, instance.toString());
    }
}
