package cdioil.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents a quantitative answer.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class QuantitativeAnswer extends Answer<Double> implements Serializable {

    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Builds a quantitative answer with a double value
     *
     * @param answer the answer itself
     */
    public QuantitativeAnswer(Double answer) {
        if (answer == null) {
            throw new IllegalArgumentException("A resposta não pode ser null");
        }
        if (Double.isNaN(answer)) {
            throw new IllegalArgumentException("A resposta tem que ser um valor "
                    + "numérico");
        }
        if (Double.isInfinite(answer)) {
            throw new IllegalArgumentException("A resposta não pode ser infinito");
        }
        if (answer < 0.0) {
            throw new IllegalArgumentException("A resposta tem que ser um valor "
                    + "positivo");
        }
        this.content = answer;
        this.type = QuestionAnswerTypes.QUANTITATIVE;
    }

    /**
     * Empty constructor for JPA.
     */
    protected QuantitativeAnswer() {

    }
}
