package org.grupo3.cdioil.isep.feedback_monkey.domain;

import org.junit.Assert;
import org.junit.Test;

public class GestorTest {

    @Test(expected = IllegalArgumentException.class)
    public void criaInstanciaInvalidaDeGestorTest() {
        new Gestor(null);
    }

    @Test
    public void equalsHashCodeTest() {
        Gestor g = new Gestor(new SystemUser());

        Assert.assertNotEquals(g, "Dummy");
        Assert.assertNotEquals(g, null);
        Assert.assertNotEquals(g, 14);
        Assert.assertEquals(g, g);
    }
}
