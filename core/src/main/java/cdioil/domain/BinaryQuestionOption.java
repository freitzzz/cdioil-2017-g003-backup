package cdioil.domain;

import javax.persistence.Entity;

/**
 * Represents a binary question option (yes or no values).
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "BinaryQuestionOption")
public class BinaryQuestionOption extends QuestionOption<Boolean> {

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
    
    public BinaryQuestionOption(QuestionOption questionOption){
        this.booleanContent = (boolean) questionOption.getContent();
    }

    /**
     * Empty constructor for JPA.
     */
    protected BinaryQuestionOption() {
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.booleanContent ? 1 : 0);
        return hash;
    }

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

    @Override
    public String toString() {
        return Boolean.toString(booleanContent);
    }

    @Override
    public Boolean getContent() {
        return booleanContent;
    }
}
