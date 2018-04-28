package cdioil.domain;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for MultipleChoiceQuestion.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class MultipleChoiceQuestionTest {

    /**
     * Tests for the constructor.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor Tests");
        LinkedList<QuestionOption> list = new LinkedList<>();
        MultipleChoiceQuestionOption option1 = new MultipleChoiceQuestionOption("Option 1");
        String id = "5";
        assertNull("The condition should succeed because the option list is null",
                createMCQuestion("Question", id, null));
        assertNull("The condition should succeed because the option list is empty",
                createMCQuestion("Question", id, list));
        list.add(option1);
        assertNull("The condition should succeed because the question is null",
                createMCQuestion(null, id, list));
        assertNull("The condition should succeed because the question is empty",
                createMCQuestion("", id, list));
        assertNull("The condition should succeed because the question id is null",
                createMCQuestion("Question", null, list));
        assertNull("The condition should succeed because the question id is empty",
                createMCQuestion("Question", "", list));
        assertNotNull("The condition should succeed because the arguments are valid",
                createMCQuestion("Question", id, list));
        assertNull("The condition should succeed because the set is null",
                createMCQuestion("Question", id, null));
        assertNotNull("Empty constructor test", new MultipleChoiceQuestion());
        assertEquals("The condition should succeed because the question we received "
                + "is equal to the one we sent",
                createMCQuestionCopy(createMCQuestion("Question", id, list)),
                createMCQuestion("Question", id, list));
    }

    /**
     * Builds a MultipleChoiceQuestion with a question and a set of options.
     *
     * @param question the question itself
     * @param questionID the question's ID
     * @param options set with the options
     * @return MultipleChoiceQuestion instance
     */
    private MultipleChoiceQuestion createMCQuestion(String question, String questionID, List<QuestionOption> optionList) {
        try {
            return new MultipleChoiceQuestion(question, questionID, optionList);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Builds a MultipleChoiceQuestion from another Question.
     *
     * @param question Question instance
     * @return MultipleChoiceQuestion instance
     */
    private MultipleChoiceQuestion createMCQuestionCopy(Question question) {
        try {
            return new MultipleChoiceQuestion(question);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
