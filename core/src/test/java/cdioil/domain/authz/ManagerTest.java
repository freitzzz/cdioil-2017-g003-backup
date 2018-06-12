package cdioil.domain.authz;

import cdioil.domain.Category;
import cdioil.framework.dto.SystemUserDTO;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit testing for class Manager.
 *
 * @author
 */
public class ManagerTest {

    private SystemUser sysUser;

    @Before
    public void setUp() {
        Email email = new Email("lilpump@guccigang.com");
        Password pwd = new Password("Bananas19");
        Name nome = new Name("Lil", "Pump");
        sysUser = new SystemUser(email, nome, pwd);
    }

    /**
     * Builds invalid manager instance.
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureConstructorThrowsExceptionWithNullValue() {
        new Manager(null);
    }

    /**
     * Tests empty constructor.
     */
    @Test
    public void testEmptyConstructor() {
        System.out.println("Empty constructor should not return null");
        assertNotNull(new Manager());
    }

    /**
     * Test of hashCode method of class Manager.
     */
    @Test
    public void hashCodeTest() {
        System.out.println("hashCode");
        Manager g = new Manager(sysUser);
        Manager otherManager = new Manager(sysUser);
        int expResult = otherManager.hashCode();
        int result = g.hashCode();
        assertEquals(expResult, result);
        
        int sysUserHashCode = sysUser.hashCode();
        assertNotEquals(sysUserHashCode, result);
        
        //This chunk kills code mutations
        assertNotEquals("".hashCode(), result);
        int num = 43 * 9 + sysUser.hashCode();
        assertEquals(num, result);
    }

    /**
     * Test of equals method of class Manager.
     */
    @Test
    public void equalsTest() {
        System.out.println("equals");

        Manager instance = new Manager(sysUser);
        assertNotEquals("The condition should succeed because we are comparing"
                + "a manager instance to a null value", instance, null);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes", instance, "bananas");
        assertEquals("The condition should succeed because we are comparing "
                + "the same instance", instance, instance);

        Email email = new Email("lilxan@guccigang.com");
        Password pwd = new Password("Bananas19");
        Name nome = new Name("Lil", "Xan");
        SystemUser sysUser2 = new SystemUser(email, nome, pwd);

        Manager other = new Manager(sysUser2);
        Manager that = new Manager(sysUser);

        assertNotEquals("The condition should succeed because we are comparing "
                + "managers that have different system users", instance, other);
        assertEquals("The condition should succeed because we are comparing "
                + "managers that have the same system user", instance, that);
    }

    /**
     * Test of toString method, of class Manager.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Manager instance = new Manager(sysUser);
        Manager other = new Manager(sysUser);
        assertEquals(instance.toString(), other.toString());

        //Mutation test
        assertNotEquals(null, instance.toString());
    }

    /**
     * Test addCategories method, of class Manager.
     */
    @Test
    public void testeAddCategories() {
        System.out.println("addCategories");

        Manager instance = new Manager(new SystemUser(new Email("myPrecious@gmail.com"),
                new Name("Gollum", "Smeagol"), new Password("Precious3")), new LinkedList<>());
        //teste adiconar lista a null
        assertFalse(instance.addCategories(null));
        //teste adicionar lista vazia
        List<Category> lc = new LinkedList<>();
        assertFalse(instance.addCategories(lc));
        //teste adiconar lista com elementos
        lc.add(new Category("Fruit", "10DC-10UN-124CAT"));
        lc.add(new Category("Beverage", "10DC-10UN-100CAT-6040SCAT"));
        assertTrue(instance.addCategories(lc));
        //teste adicionar lista com elementos repetidos
        assertTrue(instance.addCategories(lc));
    }

    /**
     * Test removeCategories method, of class Manager.
     */
    @Test
    public void testRemoveCategories() {
        System.out.println("removeCategories");

        Manager instance = new Manager(new SystemUser(new Email("myPrecious@gmail.com"),
                new Name("Gollum", "Smeagol"), new Password("Precious3")), new LinkedList<>());
        //teste remover lista a null
        List<Category> lc = null;
        assertFalse(instance.removeCategories(lc));
        //teste remover lista vazia
        lc = new LinkedList<>();
        assertFalse(instance.removeCategories(lc));
        //teste remover lista com elementos inexistentes
        lc.add(new Category("Fruit", "10DC-10UN-124CAT"));
        lc.add(new Category("Beverage", "10DC-10UN-100CAT-6040SCAT"));
        assertFalse(instance.removeCategories(lc));
        //teste adicionar lista com elementos existentes
        instance.addCategories(lc);
        assertTrue(instance.removeCategories(lc));
    }

    /**
     * Test of categoriesFromManager method, of class Manager.
     */
    @Test
    public void testCategoriesFromManager() {
        System.out.println("categoriesFromManager");
        Manager instance = new Manager(new SystemUser(new Email("myPrecious@gmail.com"),
                new Name("Gollum", "Smeagol"), new Password("Precious3")), new LinkedList<>());
        LinkedList<Category> catList = new LinkedList<>();
        catList.add(new Category("Fruit", "10DC-10UN-124CAT"));
        catList.add(new Category("Beverage", "10DC-10UN-100CAT-6040SCAT"));
        instance.addCategories(catList);
        assertTrue("The condition should succeed because the lists are equal",
                instance.categoriesFromManager().equals(catList));
    }

    /**
     * Test of getID method, of class Manager.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        Manager instance = new Manager(sysUser);
        SystemUser expResult = sysUser;
        SystemUser result = instance.getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of isAssociatedWithCategory method, of class Manager
     */
    @Test
    public void testIsAssociatedWithCategory() {
        System.out.println("isAssociatedWithCategory");
        List<Category> lc = new LinkedList<>();
        Manager instance = new Manager(sysUser, lc);
        Category c1 = new Category("Fruit", "10DC-10UN-124CAT");
        //test with empty list of categories
        assertFalse(instance.isAssociatedWithCategory(c1));
        //test when list does not contain category
        lc.add(new Category("Beverage", "10DC-10UN-100CAT-6040SCAT"));
        instance.addCategories(lc);
        assertFalse(instance.isAssociatedWithCategory(c1));
        //test when list does contain category
        lc.add(c1);
        instance.addCategories(lc);
        assertTrue(instance.isAssociatedWithCategory(c1));
    }
    /**
     * Test of toDTO method, of class Manager.
     */
    @Test
    public void testToDTO() {
        System.out.println("toDTO");
        Manager instance = new Manager(sysUser);
        SystemUserDTO expResult = sysUser.toDTO();
        SystemUserDTO result = instance.toDTO();
        assertEquals("Condition should be successful since both DTO's are the same",expResult, result);
    }
}
