/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.Question;
import cdioil.domain.QuestionGroup;
import cdioil.domain.Template;
import java.util.HashMap;
import java.util.Map;
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
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "435"));
        Template template = new Template("template", questionGroup);
        assertNull(TemplateWriterFactory.create(filename, template));
    }

    /**
     * Test of create method for a XML file, of class TemplateWriterFactory.
     */
    @Test
    public void testCreateXML() {
        System.out.println("create XML file");
        String filename = "test_passes.xml";
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "435"));
        Template template = new Template("template", questionGroup);
        assertEquals(XMLTemplateWriter.class,
                TemplateWriterFactory.create(filename, template));
    }
}
