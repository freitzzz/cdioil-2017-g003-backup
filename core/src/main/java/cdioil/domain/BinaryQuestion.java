package cdioil.domain;

import java.util.ArrayList;
import java.util.Arrays;
import javax.persistence.Entity;

/**
 * Represents a binary question.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "BinaryQuestion")
public class BinaryQuestion extends Question {

    /**
     * Builds an instance of BinaryQuestion receiving a question.
     *
     * @param question text of the question
     * @param questionID question's ID
     */
    public BinaryQuestion(String question, String questionID) {
        super(question, questionID, new ArrayList<BinaryQuestionOption>
        (Arrays.asList(new BinaryQuestionOption(Boolean.TRUE), new BinaryQuestionOption(Boolean.FALSE))));
    }

    /**
     * Empty Constructor for JPA.
     */
    protected BinaryQuestion() {
    }
}
