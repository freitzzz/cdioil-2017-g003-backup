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
 * Generic abstract class to be used for concrete types of Answer. 
*
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @param <T> type of data stored in the Answer
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Answer<T> implements Serializable, ValueObject {

    /**
     * Serialization code.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    /**
     * The content of the answer.
     */
    protected T content;

    /**
     * The type of answer.
     */
    protected QuestionAnswerTypes type;

    /**
     * Empty constructor JPA.
     */
    protected Answer() {

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
     * Returns the text of the answer
     *
     * @return the answer itself
     */
    public T content() {
        return content;
    }

    /**
     * Returns an hash value based on the attributes and class type.
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
     * Verifies object equality based on the Answer's attributes and class type.
     * @param obj object to be compared to
     * @return true if the objects are truly equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(!(obj instanceof Answer<?>)){
            return false;
        }
        final Answer<?> other = (Answer<?>) obj;
        if (this.type != other.type) {
            return false;
        }
        return this.content.equals(other.content);
    }
}
