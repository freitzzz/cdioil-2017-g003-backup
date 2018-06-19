package cdioil.domain;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author António Sousa [1161371]
 */
public class IndependentTemplatesLibraryTest {

    public IndependentTemplatesLibraryTest() {
    }

    @Test
    public void ensureDoesTemplateExistReturnsFalseIfTemplateHasNotBeenAdded() {
        Template template = new SimpleTemplate("Simple Template");

        IndependentTemplatesLibrary library = new IndependentTemplatesLibrary();

        assertFalse(library.doesTemplateExist(template));

        assertEquals(library.doesTemplateExist(template), library.getID().contains(template));
    }

    @Test
    public void ensureDoesTemplateExistReturnsTrueIfTemplateHasBeenAdded() {
        Template template = new SimpleTemplate("Simple Template");

        IndependentTemplatesLibrary library = new IndependentTemplatesLibrary();

        library.addTemplate(template);

        assertTrue(library.doesTemplateExist(template));

        assertEquals(library.doesTemplateExist(template), library.getID().contains(template));
    }

    @Test
    public void ensureAddTemplateAddsNewTemplate() {

        Template template = new SimpleTemplate("Simple Template");

        IndependentTemplatesLibrary library = new IndependentTemplatesLibrary();

        assertTrue(library.addTemplate(template));

        assertTrue(library.getID().contains(template));
    }

    @Test
    public void ensureAddTemplateDoesNotAddDuplicateTemplate() {

        Template template = new SimpleTemplate("Simple Template");

        IndependentTemplatesLibrary library = new IndependentTemplatesLibrary();

        library.addTemplate(template);

        assertFalse(library.addTemplate(template));

        assertEquals(library.getID().size(), 1);

        assertTrue(library.getID().contains(template));
    }

    @Test
    public void ensureRemoveTemplateRemovesExistingTemplate() {

        Template template = new SimpleTemplate("Simple Template");

        IndependentTemplatesLibrary library = new IndependentTemplatesLibrary();

        library.addTemplate(template);

        assertTrue(library.removeTemplate(template));

        assertEquals(library.getID().size(), 0);

        assertFalse(library.doesTemplateExist(template));
    }

    @Test
    public void ensureRemoveTemplateDoesNotRemoveNotAddedTemplate() {

        Template template = new SimpleTemplate("Simple Template");

        Template template2 = new SimpleTemplate("Other template");

        IndependentTemplatesLibrary library = new IndependentTemplatesLibrary();

        library.addTemplate(template);

        assertFalse(library.removeTemplate(template2));
    }

    @Test
    public void ensureHashCodeIsEqualIfLibrariesHaveTheSameTemplates() {

        IndependentTemplatesLibrary library = new IndependentTemplatesLibrary();
        IndependentTemplatesLibrary library2 = new IndependentTemplatesLibrary();

        Template template = new SimpleTemplate("Simple Template");
        Template template2 = new SimpleTemplate("Other template");

        library.addTemplate(template);
        library.addTemplate(template2);

        library2.addTemplate(template);
        library2.addTemplate(template2);

        assertEquals(library.hashCode(), library2.hashCode());
    }

    @Test
    public void ensureHashCodeIsDifferentIfLibrariesHaveDifferentTemplates() {

        IndependentTemplatesLibrary library = new IndependentTemplatesLibrary();
        IndependentTemplatesLibrary library2 = new IndependentTemplatesLibrary();

        Template template = new SimpleTemplate("Simple Template");
        Template template2 = new SimpleTemplate("Other template");
        Template template3 = new SimpleTemplate("A third template");

        library.addTemplate(template);
        library.addTemplate(template2);

        library2.addTemplate(template);
        library2.addTemplate(template3);

        assertNotEquals(library.hashCode(), library2.hashCode());
    }

    @Test
    public void ensureHashCodeMutationsAreKilled() {

        IndependentTemplatesLibrary library = new IndependentTemplatesLibrary();

        int hashCode = 3;
        hashCode = 47 * hashCode + library.getID().hashCode();

        assertEquals(hashCode, library.hashCode());

        assertNotEquals("".hashCode(), library.hashCode());
    }

    @Test
    public void ensureLibrariesAreEqualIfTheyHoldEqualTemplates() {

        IndependentTemplatesLibrary library = new IndependentTemplatesLibrary();
        IndependentTemplatesLibrary otherLibrary = new IndependentTemplatesLibrary();

        Template template = new SimpleTemplate("Simple Template");
        Template template2 = new SimpleTemplate("Other template");
        Template template3 = new SimpleTemplate("A third template");

        library.addTemplate(template);
        library.addTemplate(template2);
        library.addTemplate(template3);

        otherLibrary.addTemplate(template);
        otherLibrary.addTemplate(template2);
        otherLibrary.addTemplate(template3);

        assertEquals(library, otherLibrary);
    }

    @Test
    public void ensureLibrariesAreNotEqualIfTheyDoNotHoldEqualTemplates() {

        IndependentTemplatesLibrary library = new IndependentTemplatesLibrary();
        IndependentTemplatesLibrary otherLibrary = new IndependentTemplatesLibrary();

        Template template = new SimpleTemplate("Simple Template");
        Template template2 = new SimpleTemplate("Other template");
        Template template3 = new SimpleTemplate("A third template");

        library.addTemplate(template);
        library.addTemplate(template2);
        library.addTemplate(template3);

        otherLibrary.addTemplate(template2);
        otherLibrary.addTemplate(template3);

        assertNotEquals(library, otherLibrary);
    }

    @Test
    public void ensureGetIDWorks() {

        IndependentTemplatesLibrary library = new IndependentTemplatesLibrary();

        Set<Template> expected = new HashSet<>();

        Template template = new SimpleTemplate("Simple Template");
        Template template2 = new SimpleTemplate("Other template");
        Template template3 = new SimpleTemplate("A third template");

        library.addTemplate(template);
        library.addTemplate(template2);
        library.addTemplate(template3);

        expected.add(template);
        expected.add(template2);
        expected.add(template3);

        assertEquals(expected, library.getID());
    }
}
