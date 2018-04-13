package cdioil.domain;

import cdioil.framework.domain.ddd.AggregateRoot;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 * Abstract class that represents a Question.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @param <T> defines the type of questionText of the question
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Question<T> implements Serializable {

    /**
     * Serialization code.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * Database id.
     */
    protected Long databaseID;

    /**
     * The type of question.
     */
    protected QuestionAnswerTypes type;

    /**
     * The question itself.
     */
    private String questionText;

    /**
     * The question's ID.
     */
    private String questionID;

    /**
     * List of options that the question has.
     */
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<QuestionOption> optionList;

    public Question(String questionText, String questionID, List<QuestionOption> optionList) {
        if (questionText == null || questionText.isEmpty()) {
            throw new IllegalArgumentException("O texto da questão não "
                    + "pode ser null ou vazio.");
        }
        if (questionID == null || questionID.isEmpty()) {
            throw new IllegalArgumentException("O id da questão não pode "
                    + "ser null ou vazio.");
        }
        if (optionList == null || optionList.isEmpty()) {
            throw new IllegalArgumentException("A lista de opções não pode "
                    + "ser null ou vazia");
        }
        this.questionText = questionText;
        this.questionID = questionID;
        this.optionList = optionList;
    }

    /**
     * Empty Constructor for JPA.
     */
    protected Question() {
    }

    /**
     * Return the question's type.
     *
     * @return question's type enum value
     */
    public String type() {
        return type.toString();
    }

    /**
     * Returns an hash value based on the attributes and class type.
     *
     * @return hash value
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.getClass());
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + Objects.hashCode(this.questionText);
        hash = 29 * hash + Objects.hash(this.questionID);
        return hash;
    }

    /**
     * Verifies object equality based on the Question's attributes and class
     * type.
     *
     * @param obj object to be compared to
     * @return true if the objects are truly equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Question<?>)) {
            return false;
        }
        final Question<?> other = (Question<?>) obj;
        if (this.type != other.type) {
            return false;
        }
        if (!this.questionID.equals(other.questionID)) {
            return false;
        }
        return this.questionText.equals(other.questionText);
    }

    /**
     * Returns the text of the question
     *
     * @return string containg the question
     */
    @Override
    public String toString() {
        return questionText;
    }
}
