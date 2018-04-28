package cdioil.domain;

import cdioil.framework.domain.ddd.ValueObject;
import java.io.Serializable;
import javax.persistence.Column;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class QuestionOption<T> implements Serializable, ValueObject {

    /**
     * Returns an exact copy of the given QuestionOption.
     *
     * @param option option being copied
     * @return copied option
     */
    public static QuestionOption copyQuestionOption(QuestionOption option) {
        if (option instanceof BinaryQuestionOption) {
            return new BinaryQuestionOption(option);
        }
        if (option instanceof MultipleChoiceQuestionOption) {
            return new MultipleChoiceQuestionOption(option);
        }
        if (option instanceof QuantitativeQuestionOption) {
            return new QuantitativeQuestionOption(option);
        }
        return null;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "QUESTIONOPTION_ID")
    private long id;

    /**
     * Empty constructor for JPA.
     */
    protected QuestionOption() {
    }

    abstract T getContent();

}
