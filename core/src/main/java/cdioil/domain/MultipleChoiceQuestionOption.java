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
        this.content = option;
    }

    /**
     * Empty constructor for JPA.
     */
    protected MultipleChoiceQuestionOption() {
    }
}
