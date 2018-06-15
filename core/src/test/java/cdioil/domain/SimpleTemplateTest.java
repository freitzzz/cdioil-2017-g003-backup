package cdioil.domain;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author António Sousa [1161371]
 */
public class SimpleTemplateTest {

    @Test
    public void ensureJPAConstructorBuildsInstance() {
        SimpleTemplate template = new SimpleTemplate();

        assertNotNull(template);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureInstanceWithNullNameIsNotConstructed() {

        SimpleTemplate template = new SimpleTemplate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureInstanceWithEmptyNameIsNotConstructed() {

        SimpleTemplate template = new SimpleTemplate("      ");
    }

    @Test
    public void ensureAddQuestionDoesNotAddDuplicate() {
        SimpleTemplate template = new SimpleTemplate("Super Cool And Awesome Template");
        Question q = new BinaryQuestion("Do you like apples?", "B1");
        assertTrue(template.addQuestion(q));

        Question copyQuestion = new BinaryQuestion("Do you like apples?", "B1");
        assertFalse(template.addQuestion(copyQuestion));

        Iterator<Question> questionIterator = template.getQuestions().iterator();

        assertEquals(q, questionIterator.next());
        //since only one question was added, the iterator should not have any more left
        assertFalse(questionIterator.hasNext());
    }

    @Test
    public void ensureAddQuestionDoesNotAddNull() {
        SimpleTemplate template = new SimpleTemplate("You won't believe it's not butter!");
        assertFalse(template.addQuestion(null));

        Iterator<Question> questionIterator = template.getQuestions().iterator();
        assertFalse(questionIterator.hasNext());
    }

    @Test
    public void testAddQuestionAddsNewQuestion() {
        SimpleTemplate template = new SimpleTemplate("Third time's charm");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        assertTrue(template.addQuestion(q));

        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        assertTrue(template.addQuestion(q2));

        Iterator<Question> questionIterator = template.getQuestions().iterator();
        assertEquals(q, questionIterator.next());
        assertEquals(q2, questionIterator.next());
    }

    @Test
    public void ensureRemoveNullReturnsFalse() {
        SimpleTemplate template = new SimpleTemplate("New Template");

        assertFalse(template.removeQuestion(null));
    }

    @Test
    public void ensureRemoveNotAddedQuestionReturnsFalse() {
        SimpleTemplate template = new SimpleTemplate("Simple Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        assertFalse(template.removeQuestion(q));
    }

    @Test
    public void ensureRemoveQuestionWorks() {
        SimpleTemplate template = new SimpleTemplate("Simple Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);

        assertTrue(template.removeQuestion(q));
        assertTrue(template.removeQuestion(q2));

        assertFalse(template.getQuestions().iterator().hasNext());
    }

    @Test
    public void ensureGetQuestionsWorks() {
        SimpleTemplate template = new SimpleTemplate("Simple Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        Iterator<Question> iterator = template.getQuestions().iterator();
        assertEquals(q, iterator.next());
        assertEquals(q2, iterator.next());
        assertEquals(q3, iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void ensureTemplatesWithEqualQuestionsListsAndTitlesHaveSameHashCode() {
        SimpleTemplate template = new SimpleTemplate("Simple Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        SimpleTemplate otherTemplate = new SimpleTemplate("Simple Template");
        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q2);
        otherTemplate.addQuestion(q3);

        int templateHashcode = template.hashCode();
        int otherTemplateHashcode = otherTemplate.hashCode();

        assertEquals(templateHashcode, otherTemplateHashcode);
    }

    @Test
    public void ensureTemplatesWithSameTitleButDiffferentQuestionListsHaveDifferentHashCode() {

        SimpleTemplate template = new SimpleTemplate("Simple Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        SimpleTemplate otherTemplate = new SimpleTemplate("Simple Template");
        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q3);
        otherTemplate.addQuestion(q2);

        int templateHashcode = template.hashCode();
        int otherTemplateHashcode = otherTemplate.hashCode();

        assertNotEquals(templateHashcode, otherTemplateHashcode);

    }

    @Test
    public void ensureTemplatesWithEqualQuestionListsButDifferentTitleHaveDifferentHashCode() {

        SimpleTemplate template = new SimpleTemplate("Simple Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        SimpleTemplate otherTemplate = new SimpleTemplate("Other Template");
        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q2);
        otherTemplate.addQuestion(q3);

        int templateHashcode = template.hashCode();
        int otherTemplateHashcode = otherTemplate.hashCode();

        assertNotEquals(templateHashcode, otherTemplateHashcode);
    }

    @Test
    public void ensureTemplatesWithDifferentQuestionListsAndNameHaveDifferentHashCodes() {

        SimpleTemplate template = new SimpleTemplate("Simple Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        SimpleTemplate otherTemplate = new SimpleTemplate("Other Template");
        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q3);
        otherTemplate.addQuestion(q2);

        int templateHashcode = template.hashCode();
        int otherTemplateHashcode = otherTemplate.hashCode();

        assertNotEquals(templateHashcode, otherTemplateHashcode);
    }

    @Test
    public void ensureHashCodeMutationsAreKilled() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

        String title = "Template Title";

        SimpleTemplate template = new SimpleTemplate(title);

        int hash = 5;
        hash = 71 * hash + template.getQuestions().hashCode();
        hash = 71 * hash + title.hashCode();

        int result = template.hashCode();

        assertEquals(hash, result);

        assertNotEquals("".hashCode(), result);
    }

    @Test
    public void ensureSameInstanceIsEqual() {

        SimpleTemplate template = new SimpleTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        assertEquals(template, template);
    }

    @Test
    public void ensureTemplatesWithEqualQuestionListsAndTitleAreEqual() {

        SimpleTemplate template = new SimpleTemplate("Simple Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        SimpleTemplate otherTemplate = new SimpleTemplate("Simple Template");
        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q2);
        otherTemplate.addQuestion(q3);

        assertEquals(template, otherTemplate);
    }

    @Test
    public void ensureNullIsNotEqual() {
        SimpleTemplate template = new SimpleTemplate("New Template");

        SimpleTemplate otherTemplate = null;

        assertFalse(template.equals(otherTemplate));
    }

    @Test
    public void ensureDifferentClassObjectIsNotEqual() {

        SimpleTemplate template = new SimpleTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");

        assertFalse(template.equals(q));
    }

    @Test
    public void ensureTemplatesWithEqualQuestionListsButDifferentTitleAreNotEqual() {
        SimpleTemplate template = new SimpleTemplate("Simple Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        SimpleTemplate otherTemplate = new SimpleTemplate("Other Template");
        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q2);
        otherTemplate.addQuestion(q3);

        assertNotEquals(template, otherTemplate);
    }

    @Test
    public void ensureTemplatesWithEqualTitleButDifferentQuestionsListAreNotEqual() {
        SimpleTemplate template = new SimpleTemplate("Simple Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        SimpleTemplate otherTemplate = new SimpleTemplate("Simple Template");
        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q3);
        otherTemplate.addQuestion(q2);

        assertNotEquals(template, otherTemplate);
    }

    @Test
    public void ensureTemplatesWithDifferentQuestionListsAndTitlesAreNotEqual() {
        SimpleTemplate template = new SimpleTemplate("Simple Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        SimpleTemplate otherTemplate = new SimpleTemplate("Other Template");
        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q3);
        otherTemplate.addQuestion(q2);

        assertNotEquals(template, otherTemplate);
    }
}
