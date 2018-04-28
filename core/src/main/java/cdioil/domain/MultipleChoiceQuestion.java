package cdioil.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;

/**
 * Represents a multiple choice question.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "MultipleChoiceQuestion")
public class MultipleChoiceQuestion extends Question implements Serializable {

    /**
     * Builds a multiple choice question with the question itself, it's ID and
     * the list of options
     *
     * @param questionText the question itself
     * @param questionID the question's ID
     * @param options set of options for the question
     */
    public MultipleChoiceQuestion(String questionText, String questionID, List<QuestionOption> options) {
        super(questionText, questionID, options);
        super.type = QuestionTypes.MULTIPLE_CHOICE;
    }

    /**
     * Builds a multiple choice question from another question
     *
     * @param question question we want to copy
     */
    public MultipleChoiceQuestion(Question question) {
        super(question.getQuestionText(), question.getQuestionID(), question.getOptionList());
        super.type = QuestionTypes.MULTIPLE_CHOICE;
    }

    /**
     * Empty constructor for JPA.
     */
    protected MultipleChoiceQuestion() {
    }
}
