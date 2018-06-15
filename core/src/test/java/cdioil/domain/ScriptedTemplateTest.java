package cdioil.domain;

import cdioil.application.utils.Graph;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for ScriptedTemplate.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ScriptedTemplateTest {

    @Test
    public void ensureJPAConstructorBuildsInstance() {
        ScriptedTemplate template = new ScriptedTemplate();

        assertNotNull(template);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureInstanceWithNullNameIsNotConstructed() {

        ScriptedTemplate template = new ScriptedTemplate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureInstanceWithEmptyNameIsNotConstructed() {

        ScriptedTemplate template = new ScriptedTemplate("      ");
    }

    @Test
    public void ensureAddQuestionDoesNotAddDuplicate() {
        ScriptedTemplate template = new ScriptedTemplate("Super Cool And Awesome Template");
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
        ScriptedTemplate template = new ScriptedTemplate("You won't believe it's not butter!");
        assertFalse(template.addQuestion(null));

        Iterator<Question> questionIterator = template.getQuestions().iterator();
        assertFalse(questionIterator.hasNext());
    }

    @Test
    public void ensureAddQuestionAddsNewQuestion() {
        ScriptedTemplate template = new ScriptedTemplate("Third time's charm");

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
        ScriptedTemplate template = new ScriptedTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        assertFalse(template.removeQuestion(null));
        assertTrue(template.getQuestions().iterator().hasNext());
    }

    @Test
    public void ensureRemoveNotAddedQuestionReturnsFalse() {
        ScriptedTemplate template = new ScriptedTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");

        assertFalse(template.removeQuestion(q));
        assertFalse(template.getQuestions().iterator().hasNext());
    }

    @Test
    public void ensureRemoveQuestionWorks() {
        ScriptedTemplate template = new ScriptedTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);

        assertTrue(template.removeQuestion(q));

        Iterator<Question> questionIterator = template.getQuestions().iterator();
        assertTrue(questionIterator.hasNext());
        assertEquals(q2, questionIterator.next());
    }

    @Test
    public void ensureGetQuestionsWorks() {
        ScriptedTemplate template = new ScriptedTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        template.addQuestion(q);

        Iterator<Question> resultIterator = template.getQuestions().iterator();
        Iterator<Question> expectedIterator = new ArrayList<>(Arrays.asList(q, q2, q3)).iterator();

        //q1
        assertEquals(resultIterator.next(), expectedIterator.next());

        //q2
        assertEquals(resultIterator.next(), expectedIterator.next());

        //q3
        assertEquals(resultIterator.next(), expectedIterator.next());

        assertFalse(resultIterator.hasNext());
    }

    @Test
    public void ensureSetNextQuestionWorks() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        ScriptedTemplate template = new ScriptedTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        template.setNextQuestion(q, q2, q.getOptionList().get(0), 0);
        template.setNextQuestion(q, q3, q.getOptionList().get(1), 0);

        Field graphField = template.getClass().getDeclaredField("graph");
        graphField.setAccessible(true);
        Graph g = (Graph) graphField.get(template);

        Iterator<Question> adjQuestionIterator = g.adjacentQuestions(q).iterator();

        assertEquals(adjQuestionIterator.next(), q2);
        assertEquals(adjQuestionIterator.next(), q3);
        assertFalse(adjQuestionIterator.hasNext());
    }

    @Test
    public void ensureTemplatesWithEqualQuestionsGraphAndTitlesHaveSameHashCode() {
        ScriptedTemplate template = new ScriptedTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        ScriptedTemplate otherTemplate = new ScriptedTemplate("New Template");

        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q2);
        otherTemplate.addQuestion(q3);

        int result = template.hashCode();
        int otherResult = otherTemplate.hashCode();

        assertEquals(result, otherResult);
    }

    @Test
    public void ensureTemplatesWithSameTitleButDiffferentQuestionGraphHaveDifferentHashCode() {
        ScriptedTemplate template = new ScriptedTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        ScriptedTemplate otherTemplate = new ScriptedTemplate("New Template");

        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q2);
        otherTemplate.addQuestion(q3);
        otherTemplate.setNextQuestion(q, q3, q.getOptionList().get(0), 0);

        int result = template.hashCode();
        int otherResult = otherTemplate.hashCode();

        assertNotEquals(result, otherResult);
    }

    @Test
    public void ensureTemplatesWithEqualQuestionGraphsButDifferentTitleHaveDifferentHashCode() {
        ScriptedTemplate template = new ScriptedTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        ScriptedTemplate otherTemplate = new ScriptedTemplate("Other Template");

        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q2);
        otherTemplate.addQuestion(q3);

        int result = template.hashCode();
        int otherResult = otherTemplate.hashCode();

        assertNotEquals(result, otherResult);
    }

    @Test
    public void ensureTemplatesWithDifferentQuestionGraphAndNameHaveDifferentHashCodes() {
        ScriptedTemplate template = new ScriptedTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        ScriptedTemplate otherTemplate = new ScriptedTemplate("Other Template");

        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q3);
        otherTemplate.addQuestion(q2);
        otherTemplate.setNextQuestion(q2, q3, q2.getOptionList().get(0), 0);

        int result = template.hashCode();
        int otherResult = otherTemplate.hashCode();

        assertNotEquals(result, otherResult);
    }

    @Test
    public void ensureHashCodeMutationsAreKilled() {

        String title = "Template Title";

        ScriptedTemplate template = new ScriptedTemplate(title);

        int hash = 7;
        hash = 29 * hash + template.getGraph().hashCode();
        hash = 29 * hash + title.hashCode();

        int result = template.hashCode();

        assertEquals(hash, result);

        assertNotEquals("".hashCode(), result);
    }

    @Test
    public void ensureSameInstanceIsEqual() {
        ScriptedTemplate template = new ScriptedTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        template.setNextQuestion(q, q3, q.getOptionList().get(0), 0);
        template.setNextQuestion(q, q2, q.getOptionList().get(1), 0);

        assertEquals(template, template);
    }

    @Test
    public void ensureTemplatesWithEqualQuestionGraphAndTitleAreEqual() {
        ScriptedTemplate template = new ScriptedTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        ScriptedTemplate otherTemplate = new ScriptedTemplate("New Template");

        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q2);
        otherTemplate.addQuestion(q3);

        assertEquals(template, otherTemplate);
    }

    @Test
    public void ensureNullIsNotEqual() {

        ScriptedTemplate template = new ScriptedTemplate("New Template");

        ScriptedTemplate otherTemplate = null;

        assertFalse(template.equals(otherTemplate));
    }

    @Test
    public void ensureDifferentClassObjectIsNotEqual() {
        ScriptedTemplate template = new ScriptedTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");

        assertFalse(template.equals(q));
    }

    @Test
    public void ensureTemplatesWithEqualQuestionGraphButDifferentTitleAreNotEqual() {

        ScriptedTemplate template = new ScriptedTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        template.setNextQuestion(q, q3, q.getOptionList().get(0), 0);
        template.setNextQuestion(q, q2, q.getOptionList().get(1), 0);

        ScriptedTemplate otherTemplate = new ScriptedTemplate("Other template");

        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q2);
        otherTemplate.addQuestion(q3);

        otherTemplate.setNextQuestion(q, q3, q.getOptionList().get(0), 0);
        otherTemplate.setNextQuestion(q, q2, q.getOptionList().get(1), 0);

        assertNotEquals(template, otherTemplate);
    }

    @Test
    public void ensureTemplatesWithEqualTitleButDifferentQuestionsGraphAreNotEqual() {

        ScriptedTemplate template = new ScriptedTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        template.setNextQuestion(q, q3, q.getOptionList().get(0), 0);
        template.setNextQuestion(q, q2, q.getOptionList().get(1), 0);

        ScriptedTemplate otherTemplate = new ScriptedTemplate("New Template");

        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q2);
        otherTemplate.addQuestion(q3);

        otherTemplate.setNextQuestion(q, q2, q.getOptionList().get(0), 0);
        otherTemplate.setNextQuestion(q, q3, q.getOptionList().get(1), 0);

        assertNotEquals(template, otherTemplate);
    }

    @Test
    public void ensureTemplatesWithDifferentQuestionGraphAndTitlesAreNotEqual() {

        ScriptedTemplate template = new ScriptedTemplate("New Template");

        Question q = new BinaryQuestion("Do you like apples?", "B1");
        template.addQuestion(q);
        Question q2 = new BinaryQuestion("Do you like our fruit section?", "B2");
        template.addQuestion(q2);
        Question q3 = new BinaryQuestion("Do you like grapes?", "B3");
        template.addQuestion(q3);

        template.setNextQuestion(q, q3, q.getOptionList().get(0), 0);
        template.setNextQuestion(q, q2, q.getOptionList().get(1), 0);

        ScriptedTemplate otherTemplate = new ScriptedTemplate("Other Template");

        otherTemplate.addQuestion(q);
        otherTemplate.addQuestion(q2);
        otherTemplate.addQuestion(q3);

        otherTemplate.setNextQuestion(q, q2, q.getOptionList().get(0), 0);
        otherTemplate.setNextQuestion(q, q3, q.getOptionList().get(1), 0);

        assertNotEquals(template, otherTemplate);
    }
}
