package cdioil.application.utils.services.json;

import cdioil.domain.Answer;
import com.google.gson.annotations.SerializedName;

/**
 * AnswerJSONService class that represents the serialization/deserialization of an Answer 
 * in JSON object format
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 6.0 of FeedbackMonkey
 */
public final class AnswerJSONService {
    /**
     * String with the Answer type
     */
    @SerializedName(value="answerType",alternate = {"AnswerType","ANSWERTYPE"})
    private String type;
    /**
     * String with the Answer content
     */
    @SerializedName(value="answer",alternate = {"Answer","ANSWER"})
    private String answer;
    /**
     * Builds a new AnswerJSONService with the Answer being serialized into a JSON boject
     * @param answer Answer with the answer being serialized into a JSON object
     */
    public AnswerJSONService(Answer answer){
        this.answer=answer.getContent();
        this.type=answer.getClass().getSimpleName();
    }
}
