package cdioil.domain;

import java.util.List;
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
     * @param optionList
     */
    public BinaryQuestion(String question, String questionID, List<QuestionOption> optionList) {
        super(question, questionID, optionList);
        this.type = QuestionAnswerTypes.BINARY;
    }

    /**
     * Empty Constructor for JPA.
     */
    protected BinaryQuestion() {
    }
}
