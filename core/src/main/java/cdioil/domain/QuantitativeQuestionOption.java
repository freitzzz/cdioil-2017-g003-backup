package cdioil.domain;

import javax.persistence.Entity;

/**
 * Represents a quantitative question option (numeric value that's within an
 * interval).
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "QuantitativeQuestionOption")
public class QuantitativeQuestionOption extends QuestionOption<Double> {

    /**
     * Builds a QuantitativeQuestionOption with a double value
     *
     * @param value value of the option
     */
    public QuantitativeQuestionOption(Double value) {
        if (value == null) {
            throw new IllegalArgumentException("O valor da opção não pode ser "
                    + "null");
        }
        if (value < 0) {
            throw new IllegalArgumentException("O valor da opção não pode ser "
                    + "negativo");
        }
        if (value.isNaN() || value.isInfinite()) {
            throw new IllegalArgumentException("O valor da opção não pode ser "
                    + "infinito e tem que ser um valor numérico");
        }
        this.content = value;
    }

    /**
     * Empty constructor for JPA.
     */
    protected QuantitativeQuestionOption() {
    }
}
