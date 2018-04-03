package cdioil.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Unit testing class for QuantitativeQuestion.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "QuantitativeQuestion")
public class QuantitativeQuestion extends Question<String> implements Serializable {

    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Minimum value for the question.
     */
    private Double minValue;

    /**
     * Minimum value for the question.
     */
    private Double maxValue;

    /**
     * Increment value (e.g. minValue = 0, maxValue = 5, increment = 0.5
     * possible values: 0, 0.5, 1, 1.5, 2, ...)
     */
    private Double increment;

    /**
     * Builds a quantitative question with the question itself, it's id,
     * the minimum value a user can answer the question, the maximum value a
     * user can answer the question and the increment value.
     * @param question the question itself
     * @param questionID the question's ID
     * @param minValue min value a user can answer the question
     * @param maxValue max value a user can answer the question
     * @param increment increment value
     */
    public QuantitativeQuestion(String question, String questionID, Double minValue, Double maxValue, Double increment) {
        if (question == null || question.isEmpty()) {
            throw new IllegalArgumentException("A pergunta não pode ser null");
        }
        if (questionID == null || questionID.isEmpty()) {
            throw new IllegalArgumentException("O id da pergunta não pode ser "
                    + "null");
        }
        if (minValue == null || Double.isNaN(minValue) || Double.isInfinite(minValue) || minValue < 0.0) {
            throw new IllegalArgumentException("O valor mínimo tem que ter um "
                    + "valor positivo e não pode ser infinito nem null");
        }
        if (maxValue == null || Double.isNaN(maxValue) || Double.isInfinite(maxValue) || maxValue < 0.0) {
            throw new IllegalArgumentException("O valor máximo tem que ter um "
                    + "valor positivo e não pode ser infinito nem null");
        }
        if (minValue >= maxValue) {
            throw new IllegalArgumentException("O valor mínimo tem que ser"
                    + " menor do que o valor máximo");
        }
        if (increment == null || Double.isNaN(increment) || Double.isInfinite(increment)
                || increment >= maxValue || increment < 0.0) {
            throw new IllegalArgumentException("O valor de incrementação tem que"
                    + " ser positivo e menor do que o valor máximo e não pode"
                    + " ser infinito nem null");
        }
        this.content = question;
        this.questionID = questionID;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.increment = increment;
        this.type = QuestionAnswerTypes.QUANTITATIVE;
    }

    /**
     * Empty Constructor for JPA.
     */
    protected QuantitativeQuestion() {

    }

    /**
     * Returns a list with all the possible values a user can give to answer a
     * question.
     *
     * @return list of doubles
     */
    public List<Double> possibleValues() {
        LinkedList<Double> possibleValues = new LinkedList<>();
        Double i = minValue;
        while (Double.compare(i, maxValue) != 1) {
            possibleValues.add(i);
            i += increment;
        }
        return possibleValues;
    }
}
