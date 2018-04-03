package cdioil.domain;

import javax.persistence.Entity;

/**
 * Represents a binary question.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "BinaryQuestion")
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
     * @param questionID question's ID
     */
    public BinaryQuestion(String question, String questionID) {
        if (question == null || question.isEmpty()) {
            throw new IllegalArgumentException("O conteúdo da questão não "
                    + "pode ser vazio.");
        }
        if (questionID == null || questionID.isEmpty()) {
            throw new IllegalArgumentException("O id da questão não pode "
                    + "ser vazio.");
        }

        this.content = question;
        this.questionID = questionID;
        this.type = QuestionAnswerTypes.BINARY;
    }
}
