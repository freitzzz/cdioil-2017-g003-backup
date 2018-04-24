package cdioil.domain;

import java.util.Objects;
import javax.persistence.Entity;

/**
 * Represents a multiple choice question option.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "MultipleChoiceQuestionOption")
public class MultipleChoiceQuestionOption extends QuestionOption<String> {

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

    public MultipleChoiceQuestionOption(QuestionOption option){
        this.textContent = (String) option.getContent();
    }
    
    /**
     * Empty constructor for JPA.
     */
    protected MultipleChoiceQuestionOption() {
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.textContent);
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
        final MultipleChoiceQuestionOption other = (MultipleChoiceQuestionOption) obj;
        if (!Objects.equals(this.textContent, other.textContent)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return textContent;
    }

    @Override
    public String getContent() {
        return textContent;
    }

}
