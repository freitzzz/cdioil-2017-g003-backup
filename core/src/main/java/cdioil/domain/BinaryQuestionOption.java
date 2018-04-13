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
     * Builds a BinaryQuestionOption with a boolean value
     *
     * @param value value of the option
     */
    public BinaryQuestionOption(Boolean value) {
        if (value == null) {
            throw new IllegalArgumentException("O valor da opção não pode ser "
                    + "null");
        }
        this.content = value;
    }

    /**
     * Empty constructor for JPA.
     */
    protected BinaryQuestionOption() {
    }
}
