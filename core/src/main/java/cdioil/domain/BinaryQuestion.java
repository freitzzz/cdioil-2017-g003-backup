package cdioil.domain;

import javax.persistence.Entity;

/**
 * Represents a binary question.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class BinaryQuestion extends Question<String> {

    /**
     * Empty Constructor for JPA.
     */
    protected BinaryQuestion() {
    }
    
    /**
     * Builds an instance of BinaryQuestion receiving a question.
     *
     * @param question text of the question
     */
    public BinaryQuestion(String question) {
        if (question == null || question.isEmpty()) {
            throw new IllegalArgumentException("O conteúdo da questão não "
                    + "pode ser vazio.");
        }

        this.content = question;
        this.type = QuestionAnswerTypes.BINARY;
    }
}
