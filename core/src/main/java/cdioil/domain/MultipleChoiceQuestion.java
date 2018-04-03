package cdioil.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

/**
 * Represents a multiple choice question.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "MultipleChoiceQuestion")
public class MultipleChoiceQuestion extends Question<String> implements Serializable {

    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 1L;

    /**
     * List containing all the options for the question.
     */
    @ElementCollection
    private List<String> options;

    /**
     * Builds a multiple choice question with a set of strings
     *
     * @param question the question itself
     * @param questionID the question's ID
     * @param options set of options for the question
     */
    public MultipleChoiceQuestion(String question, String questionID, LinkedList<String> options) {
        if (question == null || question.isEmpty()) {
            throw new IllegalArgumentException("A pergunta não pode ser null ou "
                    + "vazia");
        }
        if (questionID == null || questionID.isEmpty()) {
            throw new IllegalArgumentException("O id da pergunta não pode ser "
                    + "null");
        }
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("O conjunto de opções não pode"
                    + " ser vazio ou null");
        }
        this.content = question;
        this.questionID = questionID;
        this.options = options;
        this.type = QuestionAnswerTypes.MULTIPLE_CHOICE;
    }

    /**
     * Empty constructor for JPA.
     */
    protected MultipleChoiceQuestion() {

    }
}
