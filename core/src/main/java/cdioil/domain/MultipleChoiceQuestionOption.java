package cdioil.domain;

import javax.persistence.Entity;

/**
 * Represents a multiple choice question option.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "MultipleChoiceQuestionOption")
public class MultipleChoiceQuestionOption extends QuestionOption<String> {

    /**
     * The content of the option.
     */
    private String textContent;

    /**
     * Builds a multiple choice question option with a string containing the
     * option itself
     *
     * @param option string with the option itself
     */
    public MultipleChoiceQuestionOption(String option) {
        if (option == null || option.isEmpty()) {
            throw new IllegalArgumentException("A opção de uma questão de "
                    + "escolha múltipla não deve ser null ou vazia.");
        }
        this.textContent = option;
    }

    /**
     * Builds a Multiple Choice Question Option based on another Question Option
     *
     * @param option the question option we want a copy of
     */
    public MultipleChoiceQuestionOption(QuestionOption option) {
        if (option == null) {
            throw new IllegalArgumentException("A opção não pode ser null");
        }
        this.textContent = (String) option.getContent();
    }

    /**
     * Empty constructor for JPA.
     */
    protected MultipleChoiceQuestionOption() {
    }

    /**
     * MultipleChoiceQuestionOption's hash code
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return textContent.hashCode();
    }

    /**
     * Checks if two MultipleChoiceQuestionOptions are equal
     *
     * @param obj object to be compared
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
        final MultipleChoiceQuestionOption other = (MultipleChoiceQuestionOption) obj;
        return textContent.equals(other.textContent);
    }

    /**
     * Returns the content of the option
     *
     * @return content of the option
     */
    @Override
    public String toString() {
        return textContent;
    }

    /**
     * Returns the content of the option
     *
     * @return content of the option
     */
    @Override
    public String getContent() {
        return textContent;
    }
}
