package cdioil.domain;

import javax.persistence.Entity;

/**
 * Represents a binary question option (yes or no values).
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "BinaryQuestionOption")
public class BinaryQuestionOption extends QuestionOption<Boolean> {

    /**
     * Content of the option.
     */
    private boolean booleanContent;

    /**
     * Builds a BinaryQuestionOption with a boolean value
     *
     * @param value value of the option
     */
    public BinaryQuestionOption(Boolean value) {
        if (value == null) {
            throw new IllegalArgumentException("O valor da opção não pode ser "
                    + "null");
        }
        this.booleanContent = value;
    }

    /**
     * Builds a BinaryQuestionOption from another question option
     *
     * @param questionOption question option we want to copy
     */
    public BinaryQuestionOption(QuestionOption questionOption) {
        if (questionOption == null) {
            throw new IllegalArgumentException("A opção não pode ser null");
        }
        this.booleanContent = (boolean) questionOption.getContent();
    }

    /**
     * Empty constructor for JPA.
     */
    protected BinaryQuestionOption() {
    }

    /**
     * BinaryQuestionOption's hash code
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Boolean.hashCode(booleanContent);
    }

    /**
     * Checks if two BinaryQuestionOption objects are equal
     *
     * @param obj object to be compared
     * @return true if they're the same, false if otherwise
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
        final BinaryQuestionOption other = (BinaryQuestionOption) obj;
        if (this.booleanContent != other.booleanContent) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string description of the option's content
     *
     * @return string with the value of the content
     */
    @Override
    public String toString() {
        return Boolean.toString(booleanContent);
    }

    /**
     * Returns the content of the question option
     *
     * @return Boolean value
     */
    @Override
    public Boolean getContent() {
        return booleanContent;
    }
}
