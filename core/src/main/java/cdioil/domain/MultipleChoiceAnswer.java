package cdioil.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents a multiple choice answer.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class MultipleChoiceAnswer extends Answer<List<Boolean>> implements Serializable {

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
     * Builds a multiple choice answer with a set of booleans.
     *
     * @param answer set of booleans containing the answer to a multiple choice
     * question (the boolean that is set to true is the chosen option)
     */
    public MultipleChoiceAnswer(LinkedList<Boolean> answer) {
        if (answer == null || answer.isEmpty()) {
            throw new IllegalArgumentException("A resposta não pode ser null"
                    + " ou vazia");
        }
        this.content = answer;
        this.type = QuestionAnswerTypes.MULTIPLE_CHOICE;
    }

    /**
     * Empty Constructor for JPA.
     */
    protected MultipleChoiceAnswer() {

    }

}
