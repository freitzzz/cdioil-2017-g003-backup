package cdioil.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Interface that contains methods that are common to all types of Questions.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Question<T> implements Serializable, ValueObject {

    /**
     * Serialization code.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * Database id.
     */
    protected Long id;

    /**
     * The type of question.
     */
    protected QuestionAnswerTypes type;

    /**
     * The question's content.
     */
    protected T content;

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
     * Returns the text of the question
     *
     * @return the question itself
     */
    public T content() {
        return content;
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
        hash = 29 * hash + Objects.hashCode(this.content);
        return hash;
    }

    /**
     * Verifies object equality based on the Question's attributes and class type.
     *
     * @param obj object to be compared to
     * @return true if the objects are truly equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(!(obj instanceof Question<?>)){
            return false;
        }
        final Question<?> other = (Question<?>) obj;
        if (this.type != other.type) {
            return false;
        }
        return this.content.equals(other.content);
    }

}
