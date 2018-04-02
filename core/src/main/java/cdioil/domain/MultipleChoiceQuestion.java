package cdioil.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents a multiple choice question.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class MultipleChoiceQuestion extends Question<String> implements Serializable {

    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * List containing all the options for the question.
     */
    private List<String> options;

    /**
     * Builds a multiple choice question with a set of strings
     *
     * @param question the question itself
     * @param options set of options for the question
     */
    public MultipleChoiceQuestion(String question, LinkedList<String> options) {
        if (question == null || question.isEmpty()) {
            throw new IllegalArgumentException("A pergunta não pode ser null ou "
                    + "vazia");
        }
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("O conjunto de opções não pode"
                    + " ser vazio ou null");
        }
        this.content = question;
        this.options = options;
        this.type = QuestionAnswerTypes.MULTIPLE_CHOICE;
    }

    /**
     * Empty constructor for JPA.
     */
    protected MultipleChoiceQuestion() {

    }
}
