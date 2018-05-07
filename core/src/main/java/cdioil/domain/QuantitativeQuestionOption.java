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
     * The content of the option.
     */
    private double numericContent;

    /**
     * Builds a QuantitativeQuestionOption with a double value
     *
     * @param value value of the option
     */
    public QuantitativeQuestionOption(Double value) {
        if (value == null) {
            throw new IllegalArgumentException("O valor da opcao não pode ser "
                    + "null");
        }
        if (value < 0) {
            throw new IllegalArgumentException("O valor da opção não pode ser "
                    + "negativo");
        }
        if (value.isNaN() || value.isInfinite()) {
            throw new IllegalArgumentException("O valor da opção nao pode ser "
                    + "infinito e tem que ser um valor numérico");
        }
        this.numericContent = value;
    }

    /**
     * Builds a quantitative question option based on another question option.
     *
     * @param option question option that we want to copy
     */
    public QuantitativeQuestionOption(QuestionOption option) {
        if (option == null) {
            throw new IllegalArgumentException("A opção não pode ser null");
        }
        this.numericContent = (double) option.getContent();
    }

    /**
     * Empty constructor for JPA.
     */
    protected QuantitativeQuestionOption() {
    }

    /**
     * QuantitativeQuestionOption's hash code
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Double.hashCode(numericContent);
    }

    /**
     * Checks if two QuantitativeQuestionOptions are equal
     *
     * @param obj question option to be compared
     * @return true if they're equal, false if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QuantitativeQuestionOption other = (QuantitativeQuestionOption) obj;
        return Double.compare(this.numericContent, other.numericContent) == 0;
    }

    /**
     * Returns the content of the option in a String
     *
     * @return String with the numeric content
     */
    @Override
    public String toString() {
        return Double.toString(numericContent);
    }

    /**
     * Returns the content of the question option
     *
     * @return double value
     */
    @Override
    public Double getContent() {
        return numericContent;
    }
}
