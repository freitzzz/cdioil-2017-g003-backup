package cdioil.domain;

import cdioil.application.utils.Graph;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for ScriptedTemplate.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ScriptedTemplateTest {

    /**
     * Test to ensure addQuestion method, of class ScriptedTemplate, adds a
     * valid question
     */
    @Test
    public void ensureAddQuestionAddsValidQuestion() {
        System.out.println("ensureAddQuestionAddsValidQuestion");
        Question question = new BinaryQuestion("Question", "ID");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(question);
        ScriptedTemplate instance = new ScriptedTemplate("Title", questionGroup);
        assertTrue(instance.addQuestion(question));
    }

    /**
     * Test to ensure addQuestion method, of class ScriptedTemplate, detects
     * null question.
     */
    @Test
    public void ensureAddQuestionDetectsNullQuestion() {
        System.out.println("ensureAddQuestionDetectsNullQuestion");
        Question question = null;
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "ID"));
        ScriptedTemplate instance = new ScriptedTemplate("Title", questionGroup);
        assertFalse(instance.addQuestion(question));
    }

    /**
     * Test to ensure removeQuestion method, of class ScriptedTemplate, removes
     * a question.
     */
    @Test
    public void ensureRemoveQuestionRemovesQuestion() {
        System.out.println("ensureRemoveQuestionRemovesQuestion");
        Question question = new BinaryQuestion("Title", "ID");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(question);
        ScriptedTemplate instance = new ScriptedTemplate("Title", questionGroup);
        instance.addQuestion(question);
        assertTrue(instance.removeQuestion(question));
    }

    /**
     * Test to ensure removeQuestion method, of class ScriptedTemplate, doesn't
     * remove a null value.
     */
    @Test
    public void ensureRemoveQuestionDoesntRemoveNull() {
        System.out.println("ensureRemoveQuestionDoesntRemoveNull");
        Question question = null;
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Question", "ID"));
        ScriptedTemplate instance = new ScriptedTemplate("Title", questionGroup);
        assertFalse(instance.removeQuestion(question));
    }

    /**
     * Test to ensure removeQuestion method, of class ScriptedTemplate, doesn't
     * remove a non existent question.
     */
    @Test
    public void ensureRemoveQuestionDoesntRemoveInvalidQuestion() {
        System.out.println("ensureRemoveQuestionDoesntRemoveInvalidQuestion");
        Question question = new BinaryQuestion("Title", "ID");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(question);
        ScriptedTemplate instance = new ScriptedTemplate("Title", questionGroup);
        assertFalse(instance.removeQuestion(question));
    }

    /**
     * Test to ensure setNextQuestion method, of class ScriptedTemplate, creates
     * an edge for an existing question in the graph's question list.
     */
    @Test
    public void ensureSetNextQuestionWorks() {
        System.out.println("ensureSetNextQuestionWorks");
        Question origin = new BinaryQuestion("Question", "ID");
        Question destination = new BinaryQuestion("Question2", "ID2");
        QuestionOption option = new BinaryQuestionOption(Boolean.FALSE);
        double weight = 0.0;
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(origin);
        ScriptedTemplate instance = new ScriptedTemplate("Title", questionGroup);
        instance.addQuestion(origin);
        instance.addQuestion(destination);
        assertTrue(instance.setNextQuestion(origin, destination, option, weight));
    }

    /**
     * Test to ensure isValidQuestion method, of class ScriptedTemplate, returns
     * true when a question is truly valid (exists in the graph and the question
     * group).
     */
    @Test
    public void ensureThatQuestionExistsInGraphAndInQuestionGroup() {
        System.out.println("ensureThatQuestionExistsInGraphAndInQuestionGroup");
        Question question = new BinaryQuestion("Question", "ID");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(question);
        ScriptedTemplate instance = new ScriptedTemplate("Title", questionGroup);
        instance.addQuestion(question);
        assertTrue(instance.isValidQuestion(question));
    }

    /**
     * Test to ensure isValidQuestion method, of class ScriptedTemplate, returns
     * false when a question only exists in the question group of the template.
     */
    @Test
    public void ensureThatQuestionIsInvalid() {
        System.out.println("ensureThatQuestionIsInvalid");
        Question question = new BinaryQuestion("Question", "ID");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(question);
        ScriptedTemplate instance = new ScriptedTemplate("Title", questionGroup);
        assertFalse(instance.isValidQuestion(question));
    }

    /**
     * Test of hashCode method, of class ScriptedTemplate.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(new BinaryQuestion("Title", "ID"));
        ScriptedTemplate instance = new ScriptedTemplate("Title", questionGroup);
        ScriptedTemplate other = new ScriptedTemplate("Title", questionGroup);
        instance.addQuestion(new BinaryQuestion("Title", "ID"));
        other.addQuestion(new BinaryQuestion("Title", "ID"));
        assertTrue(instance.hashCode() == other.hashCode());
        Graph graph = new Graph();
        graph.insertVertex(new BinaryQuestion("Title", "ID"));

        //Kill mutations
        assertNotEquals("".hashCode(), instance.hashCode());
        int number = 29 * 7 + graph.hashCode();
        assertEquals(number, instance.hashCode());
    }

    /**
     * Test of equals method, of class ScriptedTemplate.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Question questionQG = new BinaryQuestion("Question", "ID");
        QuestionGroup questionGroup = new QuestionGroup("QuestionGroup");
        questionGroup.addQuestion(questionQG);
        ScriptedTemplate instance = new ScriptedTemplate("Title", questionGroup);
        ScriptedTemplate other = new ScriptedTemplate("Title", questionGroup);
        ScriptedTemplate another = new ScriptedTemplate("Title", questionGroup);
        BinaryQuestion question = new BinaryQuestion("Question", "ID");
        BinaryQuestion question2 = new BinaryQuestion("Question2", "ID2");
        BinaryQuestionOption option = new BinaryQuestionOption(Boolean.FALSE);
        instance.addQuestion(question);
        instance.addQuestion(question2);
        other.addQuestion(question);
        other.addQuestion(question2);
        another.addQuestion(question);
        another.addQuestion(question2);
        instance.setNextQuestion(question, question2, option, 0);
        other.setNextQuestion(question, question2, option, 0);
        another.setNextQuestion(question2, question, option, 0);
        assertNotEquals("The condition should succeed because we are comparing an instance"
                + "to a null value", instance, null);
        assertEquals("The condition should succeed because we are comparing the same "
                + "instance", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing instances"
                + " of different classes", instance, new Template("Title", questionGroup));
        assertEquals("The condition should succeed because we are comparing instances"
                + "that have the same graph", instance, other);
        assertNotEquals("The condition should succeed because we are comparing instances "
                + "that have a different graph", instance, another);
    }
    
    /**
     * Misc tests
     */
    @Test
    public void testMisc(){
        assertNotNull("Empty constructor test",new ScriptedTemplate());
    }

}
