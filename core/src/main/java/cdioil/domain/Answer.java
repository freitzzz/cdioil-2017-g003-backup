package cdioil.domain;

import cdioil.framework.domain.ddd.ValueObject;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * Represents an answer (option that a user chose)
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
@SequenceGenerator(name = "answerSeq",initialValue = 1,allocationSize = 1)
public class Answer implements Serializable, ValueObject {

    /**
     * Serialization code.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The content of the chosen option.
     */
    private String content;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "answerSeq")
    private long id;

    public Answer(QuestionOption chosenOption) {
        if (chosenOption == null) {
            throw new IllegalArgumentException("A opção escolhida não pode "
                    + "ser null");
        }
        content = chosenOption.toString();
    }

    /**
     * Empty constructor JPA.
     */
    protected Answer() {
    }

    /**
     * Access method to the content of the question.
     *
     * @return the content of the question
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Answer's hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return content.hashCode();
    }

    /**
     * Checks if two answers are equal.
     *
     * @param obj object to be compared
     * @return true if they're equal, false if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Answer)) {
            return false;
        }
        final Answer other = (Answer) obj;
        return this.content.equals(other.content);
    }

    /**
     * Returns the content of the answer.
     *
     * @return string with the answer's content
     */
    @Override
    public String toString() {
        return content;
    }
}
