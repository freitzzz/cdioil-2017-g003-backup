package cdioil.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;

/**
 * Represents a binary question.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity(name = "BinaryQuestion")
public class BinaryQuestion extends Question {

    /**
     * Builds an instance of BinaryQuestion receiving a question.
     *
     * @param question text of the question
     * @param questionID question's ID
     */
    public BinaryQuestion(String question, String questionID) {
        super(question, questionID, optionList());
        super.type = QuestionTypes.BINARY;
    }
    
    public BinaryQuestion(Question question){
        super(question.getQuestionText(),question.getQuestionID(),question.getOptionList());
        super.type = QuestionTypes.BINARY;
    }

    /**
     * Builds the option list for a Binary Question.
     *
     * @return list with binary question options
     */
    private static List<QuestionOption> optionList() {
        List<QuestionOption> list = new ArrayList<>();
        list.add(new BinaryQuestionOption(true));
        list.add(new BinaryQuestionOption(false));
        return list;
    }

    /**
     * Empty Constructor for JPA.
     */
    protected BinaryQuestion() {
    }
}
