package cdioil.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Represents a binary question.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "QUESTION"))
public class BinaryQuestion implements Serializable, Question {

    /**
     * Serialization code.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * Database id.
     */
    private Long id;

    /**
     * Question text.
     */
    private String question;

    /**
     * Question type.
     */
    private final QuestionAnswerTypes type = QuestionAnswerTypes.BINARY;

    /**
     * Builds an instance of BinaryQuestion receiving a question.
     * @param question text of the question
     */
    public BinaryQuestion(String question) {
        if (question == null || question.isEmpty()) {
            throw new IllegalArgumentException("O conteúdo da questão não "
                    + "pode ser vazio.");
        }
        this.question = question;

    }

    protected BinaryQuestion() {
        //For ORM
    }

    /**
     * Hash code of BinaryQuestion.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return question.hashCode();
    }

    /**
     * Verifies if two instances of BinaryQuestion are the same.
     *
     * @param obj instance to be compared
     * @return true if they have the same question, false if they don't.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BinaryQuestion)) {
            return false;
        }
        final BinaryQuestion other = (BinaryQuestion) obj;
        return this.question.equals(other.question);
    }

    /**
     * Returns the content of the question
     *
     * @return string with the text of the question
     */
    @Override
    public String content() {
        return question;
    }

    /**
     * Returns the type of the question
     *
     * @return string with the question type
     */
    @Override
    public String type() {
        return type.toString();
    }

}
