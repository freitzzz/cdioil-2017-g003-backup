package cdioil.domain;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Unit testing class for QuantitativeQuestion.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class QuantitativeQuestionTest {

    private String q;
    private String id;

    /**
     * Sets up variables before running tests.
     */
    @Before
    public void setUp() {
        q = "Question";
        id = "3";
    }

    /**
     * Constructor tests.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor tests");
        List<QuestionOption> list = new LinkedList<>();
        QuantitativeQuestionOption option1 = new QuantitativeQuestionOption(1.0);

        assertNull("The condition should succeed because the option list "
                + "is null", createQQuestion(q, id, null));
        assertNull("The condition should succeed because the option list "
                + "is empty", createQQuestion(q, id, list));

        list.add(option1);

        assertNull("The condition should succeed because the question is null",
                createQQuestion(null, id, list));
        assertNull("The condition should succeed because the question is empty",
                createQQuestion("", id, list));

        assertNull("The condition should succeed because the question id is "
                + "null", createQQuestion(q, null, list));
        assertNull("The condition should succeed because question id is "
                + "empty", createQQuestion(q, "", list));

        assertNotNull("The condition should succeed because all arguments are valid",
                createQQuestion(q, id, list));

        assertNotNull("Empty constructor test", new QuantitativeQuestion());

        assertEquals("The condition should succeed because the question we sent "
                + "is equal to the one we receive", createQQuestion(q, id, list),
                createQQuestionCopy(createQQuestion(q, id, list)));
    }

    /**
     * Builds a QuantitativeQuestion.
     *
     * @param question the question itself
     * @param questionID the question's ID
     * @param optionList the question's option list
     * @return QuantitativeQuestion instance
     */
    private QuantitativeQuestion createQQuestion(String question, String questionID, List<QuestionOption> optionList) {
        try {
            return new QuantitativeQuestion(question, questionID, optionList);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Builds a QuantitativeQuestion from another question
     *
     * @param question question we want to copy
     * @return QuantitativeQuestion instance
     */
    private QuantitativeQuestion createQQuestionCopy(Question question) {
        try {
            return new QuantitativeQuestion(question);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
