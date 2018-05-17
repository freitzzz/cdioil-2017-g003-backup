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
import javax.persistence.SequenceGenerator;

/**
 * Class that represents an option that a question has. (e.g. a binary question has 2 options (Yes/No) )
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @param <T> defines the type of content of the option (e.g. boolean value on a binary option)
 */
@Entity
@SequenceGenerator(name = "questionOptionSeq", initialValue = 1, allocationSize = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class QuestionOption<T> implements Serializable, ValueObject {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionOptionSeq")
    @Column(name = "QUESTIONOPTION_ID")
    private long id;

    /**
     * Empty constructor for JPA.
     */
    protected QuestionOption() {
    }

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

    /**
     * Returns the QuestionType according to the received type.
     *
     * @param type Type of the Question
     * @param content Content of the QuestionOption
     * @return the QuestionOption
     */
    public static QuestionOption getQuestionOption(String type, String content) {
        try {
            QuestionTypes questionType = QuestionTypes.valueOf(type);

            switch (questionType) {
                case BINARY:
                    return new BinaryQuestionOption(Boolean.parseBoolean(content));
                case MULTIPLE_CHOICE:
                    return new MultipleChoiceQuestionOption(content);
                case QUANTITATIVE:
                    return new QuantitativeQuestionOption(Double.parseDouble(content));
                default:
                    return null;
            }
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    /**
     * Returns the content of the option
     *
     * @return content of type T
     */
    abstract T getContent();
}
