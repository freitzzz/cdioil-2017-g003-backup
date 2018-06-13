package cdioil.domain;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests of the class Template.
 *
 * @author Ana Guerra (1161191)
 */
public class TemplateTest {

    @Test
    public void ensureGetTitleWorks() {
        String title1 = "Super Cool And Awesome Template";
        Template template = new SimpleTemplate(title1);
        assertEquals(template.getTitle(), title1);

        String title2 = "And this one is cool too, I guess";
        template = new ScriptedTemplate(title2);
        assertEquals(template.getTitle(), title2);
    }

    /**
     * Test of the method toString, of the class Template.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Question question = new BinaryQuestion("Do you like apples?", "A32");
        Template other = new SimpleTemplate("Template");
        other.addQuestion(question);
        Template another = new SimpleTemplate("Template");
        another.addQuestion(question);

        assertEquals(other.toString(), another.toString());

        //Mutation test
        assertNotEquals(other.toString(), null);
    }
}
