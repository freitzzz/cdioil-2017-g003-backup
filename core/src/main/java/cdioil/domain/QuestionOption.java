package cdioil.domain;

import cdioil.framework.domain.ddd.ValueObject;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Class that represents an option that a question has. (e.g. a binary question
 * has 2 options (Yes/No) )
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @param <T> defines the type of content of the option (e.g. boolean value on a
 * binary option)
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class QuestionOption<T> implements Serializable, ValueObject {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Content of the option.
     */
    protected T content;

    /**
     * Empty constructor for JPA.
     */
    protected QuestionOption() {
    }

    /**
     * QuestionOption's hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return content.hashCode();
    }

    /**
     * Checks whether two QuestionOptions are equal
     *
     * @param obj object to be compared
     * @return true if they're equal, false if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof QuestionOption)) {
            return false;
        }
        final QuestionOption<?> other = (QuestionOption<?>) obj;
        return this.content.equals(other.content);
    }

    /**
     * Returns a description of the content of the option
     *
     * @return string with the content of the option
     */
    @Override
    public String toString() {
        return content.toString();
    }
}
