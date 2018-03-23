package cdioil.domain.authz;

import cdioil.domain.Category;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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

    /**
     * Teste do método adicionarCategorias, da classe Gestor
     */
    @Test
    public void testeAdicionarCategorias() {
        System.out.println("adicionarCategorias");
        Gestor instance = new Gestor(new SystemUser(new Email("myPrecious@gmail.com"),
                new Name("Gollum", "Smeagol"), new Password("Precious3")), new LinkedList<>());
        //teste adiconar lista a null
        List<Category> lc = null;
        assertFalse(instance.adicionarCategorias(lc));
        //teste adicionar lista vazia
        lc = new LinkedList<>();
        assertFalse(instance.adicionarCategorias(lc));
        //teste adiconar lista com elementos
        lc.add(new Category("Fruit", "124CAT", "10DC-10UN-124CAT"));
        lc.add(new Category("Beverage", "6040SCAT", "10DC-10UN-100CAT-6040SCAT"));
        assertTrue(instance.adicionarCategorias(lc));
        //teste adicionar lista com elementos repetidos
        assertTrue(instance.adicionarCategorias(lc));
    }

    /**
     * Teste do método removerCategorias, da classe Gestor
     */
    @Test
    public void testeRemoverCategorias() {
        System.out.println("removerCategorias");
        Gestor instance = new Gestor(new SystemUser(new Email("myPrecious@gmail.com"),
                new Name("Gollum", "Smeagol"), new Password("Precious3")), new LinkedList<>());
        //teste remover lista a null
        List<Category> lc = null;
        assertFalse(instance.removerCategorias(lc));
        //teste remover lista vazia
        lc = new LinkedList<>();
        assertFalse(instance.removerCategorias(lc));
        //teste remover lista com elementos inexistentes
        //teste adiconar lista com elementos
        lc.add(new Category("Fruit", "124CAT", "10DC-10UN-124CAT"));
        lc.add(new Category("Beverage", "6040SCAT", "10DC-10UN-100CAT-6040SCAT"));
        assertFalse(instance.removerCategorias(lc));
        //teste adicionar lista com elementos existentes
        instance.adicionarCategorias(lc);
        assertTrue(instance.removerCategorias(lc));
    }
}
