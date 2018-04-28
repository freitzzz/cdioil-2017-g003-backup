package cdioil.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;

/**
 * Unit testing class for QuantitativeQuestion.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "QuantitativeQuestion")
public class QuantitativeQuestion extends Question implements Serializable {

    /**
     * Builds a quantitative question with the question itself, it's id and the
     * list of options
     *
     * @param question the question itself
     * @param questionID the question's ID
     * @param optionList option list containing all the values that can be used
     * to answer the question
     */
    public QuantitativeQuestion(String question, String questionID, List<QuestionOption> optionList) {
        super(question, questionID, optionList);
        super.type = QuestionTypes.QUANTITATIVE;
    }

    /**
     * Builds a quantitative question from another question
     *
     * @param question question we want to copy
     */
    public QuantitativeQuestion(Question question) {
        super(question.getQuestionText(), question.getQuestionID(), question.getOptionList());
        super.type = QuestionTypes.MULTIPLE_CHOICE;
    }

    /**
     * Empty Constructor for JPA.
     */
    protected QuantitativeQuestion() {
    }
}
