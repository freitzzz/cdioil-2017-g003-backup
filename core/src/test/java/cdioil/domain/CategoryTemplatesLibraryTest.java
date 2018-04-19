package cdioil.domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for CategoryTemplatesLibrary.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class CategoryTemplatesLibraryTest {

    /**
     * Test of categoryTemplateSet method, of class CategoryTemplatesLibrary.
     */
    @Test
    public void testCategoryTemplateSet() {
        System.out.println("categoryTemplateSet");
        Category category = new Category("CategoryTest", "10DC-10UN-100CAT");
        CategoryTemplatesLibrary instance = new CategoryTemplatesLibrary();
        assertNull("The condition should succeed because there aren't any "
                + "categories added to the library", instance.categoryTemplateSet(category));
        instance.addCategory(category);
        assertNotNull("The condition should succeed because there's a category "
                + "in the library", instance.categoryTemplateSet(category));
    }

    /**
     * Test of addCategory method, of class CategoryTemplatesLibrary.
     */
    @Test
    public void testAddCategory() {
        System.out.println("addCategory");
        Category category = new Category("CategoryTest", "10DC-10UN-100CAT");
        CategoryTemplatesLibrary instance = new CategoryTemplatesLibrary();
        assertTrue("The condition should succeed because the category wasn't "
                + "added to the library", instance.addCategory(category));
        assertFalse("The condition should succeed because the category already "
                + "exists in the library", instance.addCategory(category));
    }

    /**
     * Test of removeCategory method, of class CategoryTemplatesLibrary.
     */
    @Test
    public void testRemoveCategory() {
        System.out.println("removeCategory");
        Category category = new Category("CategoryTest", "10DC-10UN-100CAT");
        CategoryTemplatesLibrary instance = new CategoryTemplatesLibrary();
        assertFalse("The condition should succeed because the category "
                + "doesn't exist in the library", instance.removeCategory(category));
        instance.addCategory(category);
        assertTrue("The condition should succeed because the category was "
                + "added and can be removed", instance.removeCategory(category));
    }

    /**
     * Test of doesCategoryExist method, of class CategoryTemplatesLibrary.
     */
    @Test
    public void testDoesCategoryExist() {
        System.out.println("doesCategoryExist");
        Category category = new Category("CategoryTest", "10DC-10UN-100CAT");
        CategoryTemplatesLibrary instance = new CategoryTemplatesLibrary();
        assertFalse("The condition should succeed because the category doesn't "
                + "exist in the library", instance.doesCategoryExist(category));
        instance.addCategory(category);
        assertTrue("The condition should succeed because the category exists "
                + "in the library", instance.doesCategoryExist(category));
    }

    /**
     * Test of addTemplate method, of class CategoryTemplatesLibrary.
     */
    @Test
    public void testAddTemplate() {
        System.out.println("addTemplate");
        Category category = new Category("CategoryTest", "10DC-10UN-100CAT");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "324"));
        Template template = new Template("template", questionGroup);
        CategoryTemplatesLibrary instance = new CategoryTemplatesLibrary();
        assertFalse("The condition should succeed because the category doesn't "
                + "exist in the library", instance.addTemplate(category, template));
        instance.addCategory(category);
        assertTrue("The condition should succeed because the category exists "
                + "in the library and therefore the template can be added",
                instance.addTemplate(category, template));
        assertFalse("The condition should succeed because the template "
                + "was already added", instance.addTemplate(category, template));
    }

    /**
     * Test of removeTemplate method, of class CategoryTemplatesLibrary.
     */
    @Test
    public void testRemoveTemplate() {
        System.out.println("removeTemplate");
        Category category = new Category("CategoryTest", "10DC-10UN-100CAT");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "324"));
        Template template = new Template("template", questionGroup);
        CategoryTemplatesLibrary instance = new CategoryTemplatesLibrary();
        assertFalse("The condition should succeed because the category "
                + "doesn't exist in the library", instance.removeTemplate(category, template));
        instance.addCategory(category);
        assertFalse("The condition should succed because the template wasn't"
                + "added yet", instance.removeTemplate(category, template));
        instance.addTemplate(category, template);
        assertTrue("The condition should succeed because the template exists "
                + "in the library", instance.removeTemplate(category, template));
    }

    /**
     * Test of doesTemplateExist method, of class CategoryTemplatesLibrary.
     */
    @Test
    public void testDoesTemplateExist() {
        System.out.println("doesTemplateExist");
        Category category = new Category("CategoryTest", "10DC-10UN-100CAT");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "324"));
        Template template = new Template("template", questionGroup);
        CategoryTemplatesLibrary instance = new CategoryTemplatesLibrary();
        assertFalse("The condition should succeed because there aren't "
                + "any categories in the library", instance.doesTemplateExist(category, template));
        instance.addCategory(category);
        assertFalse("The condition should succeed because there aren't any "
                + "templates in the library", instance.doesTemplateExist(category, template));
        instance.addTemplate(category, template);
        assertTrue("The condition should succeed because the template exists "
                + "in the library", instance.doesTemplateExist(category, template));
    }

    /**
     * Test of hashCode method, of class CategoryTemplatesLibrary.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        CategoryTemplatesLibrary instance = new CategoryTemplatesLibrary();
        CategoryTemplatesLibrary other = new CategoryTemplatesLibrary();
        int expResult = other.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class CategoryTemplatesLibrary.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        CategoryTemplatesLibrary instance = new CategoryTemplatesLibrary();
        CategoryTemplatesLibrary other = new CategoryTemplatesLibrary();
        Category category = new Category("CategoryTest", "10DC-10UN-100CAT");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "324"));
        Template template = new Template("template", questionGroup);
        assertNotEquals("The condition should succeed because we are comparing "
                + "an instance to a null value", instance, null);
        assertEquals("The condition should succeed because we are comparing "
                + "the same instance", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes", instance, "bananas");
        instance.addCategory(category);
        assertNotEquals("The condition should succeed because we are comparing "
                + "libraries that have different mappings (one has a category "
                + "the other doesn't", instance, other);
        other.addCategory(category);
        assertEquals("The condition should succeed because we are comparing "
                + " libraries that have the same mapping (both have the "
                + "same category", instance, other);
        instance.addTemplate(category, template);
        assertNotEquals("The condition should succeed because we are comparing "
                + "libraries that have different mappings (one has a template"
                + " associated with a library the other doesn't", instance, other);
        other.addTemplate(category, template);
        assertEquals("The condition should succeed because we are comparing "
                + "libraries that have the same mapping (both have the same "
                + "template for the same category", instance, other);
    }

    /**
     * Test of getID method, of class CategoryTemplatesLibrary.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        CategoryTemplatesLibrary instance = new CategoryTemplatesLibrary();
        Map<Category, TemplateGroup> expResult = new HashMap<>();
        Map<Category, TemplateGroup> result = instance.getID();
        assertEquals(expResult, result);
    }

}
