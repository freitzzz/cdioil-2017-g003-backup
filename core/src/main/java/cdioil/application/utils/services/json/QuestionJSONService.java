package cdioil.application.utils.services.json;

import cdioil.domain.Question;
import com.google.gson.annotations.SerializedName;

/**
 * QuestionJSONService class that represents the serialization/deserialization of a Question 
 * in JSON object format
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 6.0 of FeedbackMonkey
 */
public final class QuestionJSONService {
    /**
     * String with the question type
     */
    @SerializedName(value="questionType",alternate = {"QuestionType","QUESTIONTYPE"})
    private String type;
    /**
     * String with the question content
     */
    @SerializedName(value="question",alternate = {"Question","QUESTION"})
    private String question;
    /**
     * Builds a new QuestionJSONService with the Question being serialized into a 
     * JSON object
     * @param question Question with the question being serialized into a JSON object 
     */
    public QuestionJSONService(Question question){
        this.question=question.getQuestionText();
        this.type=question.getType().name();
    }
}
