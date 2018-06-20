package cdioil.feedbackmonkey.restful.utils.json;

import com.google.gson.annotations.SerializedName;

/**
 * QuestionJSONService class for the deserialization of a ReviewJSONService
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
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
     * Returns the question text
     * @return String with the question text
     */
    public String getQuestionText(){
        return question;
    }
}
