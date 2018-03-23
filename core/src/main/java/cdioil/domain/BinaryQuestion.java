package cdioil.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Represents a binary question.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "QUESTION"))
public class BinaryQuestion extends Question<String> {
    
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
