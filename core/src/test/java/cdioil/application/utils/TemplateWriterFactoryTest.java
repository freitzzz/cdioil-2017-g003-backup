package cdioil.application.utils;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.SimpleTemplate;
import cdioil.domain.Template;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 * Tests of the class TemplateWriterFactory.
 *
 * @author Ana Guerra (1161191)
 */
public class TemplateWriterFactoryTest {

    /**
     * Test of create method, of class TemplateWriterFactory.
     */
    @Test
    public void testCreateFailsForInvalidExtension() {
        System.out.println("create invalid file");
        String filename = "test_fails.failure";
        Template template = new SimpleTemplate("template");
        template.addQuestion(new BinaryQuestion("Question", "435"));
        assertNull(TemplateWriterFactory.create(filename, template));
    }

    /**
     * Test of create method for a XML file, of class TemplateWriterFactory.
     */
    @Test
    public void testCreateXML() {
        System.out.println("create XML file");
        String filename = "test_passes.xml";
        Template template = new SimpleTemplate("template");
        template.addQuestion(new BinaryQuestion("Question", "435"));
        assertEquals(XMLTemplateWriter.class,
                TemplateWriterFactory.create(filename,template).getClass());
    }
}
