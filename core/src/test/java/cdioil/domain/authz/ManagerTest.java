package cdioil.domain.authz;

import cdioil.domain.Category;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Unit testing for class Manager.
 * @author 
 */
public class ManagerTest {

    /**
     * Builds invalid manager instance.
     */
    @Test(expected = IllegalArgumentException.class)
    public void criaInstanciaInvalidaDeGestorTest() {
        new Manager(null);
    }

    /**
     * Tests hashCode and equals method of class Manager.
     */
    @Test
    public void equalsHashCodeTest() {
        Manager g = new Manager(new SystemUser());

        Assert.assertNotEquals(g, "Dummy");
        Assert.assertNotEquals(g, null);
        Assert.assertNotEquals(g, 14);
        Assert.assertEquals(g, g);
    }

    /**
     * Test addCategories method, of class Manager.
     */
    @Test
    public void testeAdicionarCategorias() {
        System.out.println("adicionarCategorias");

        Gestor instance = new Gestor(new SystemUser(new Email("myPrecious@gmail.com"),
                new Name("Gollum", "Smeagol"), new Password("Precious3")), new LinkedList<>());
	//teste adiconar lista a null
        List<Category> lc = null;
        assertFalse(instance.addCategories(lc));
        //teste adicionar lista vazia
        lc = new LinkedList<>();
        assertFalse(instance.addCategories(lc));
        //teste adiconar lista com elementos
        lc.add(new Category("Fruit", "124CAT", "10DC-10UN-124CAT"));
        lc.add(new Category("Beverage", "6040SCAT", "10DC-10UN-100CAT-6040SCAT"));
        assertTrue(instance.adicionarCategorias(lc));
        //teste adicionar lista com elementos repetidos
        assertTrue(instance.addCategories(lc));
    }

    /**
     * Test removeCategories method, of class Manager.
     */
    @Test
    public void testRemoveCategories() {
        System.out.println("removerCategorias");

        Gestor instance = new Gestor(new SystemUser(new Email("myPrecious@gmail.com"),
                new Name("Gollum", "Smeagol"), new Password("Precious3")), new LinkedList<>());
        //teste remover lista a null
        List<Category> lc = null;
        assertFalse(instance.removeCategories(lc));
        //teste remover lista vazia
        lc = new LinkedList<>();
        assertFalse(instance.removeCategories(lc));
        //teste remover lista com elementos inexistentes
        //teste adiconar lista com elementos
        lc.add(new Category("Fruit", "124CAT", "10DC-10UN-124CAT"));
        lc.add(new Category("Beverage", "6040SCAT", "10DC-10UN-100CAT-6040SCAT"));
        assertFalse(instance.removerCategorias(lc));
        //teste adicionar lista com elementos existentes
        instance.addCategories(lc);
        assertTrue(instance.removeCategories(lc));
    }
}
