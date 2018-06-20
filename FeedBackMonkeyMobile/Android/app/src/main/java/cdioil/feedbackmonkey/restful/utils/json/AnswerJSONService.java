package cdioil.feedbackmonkey.restful.utils.json;

import com.google.gson.annotations.SerializedName;

/**
 * AnswerJSONService class for the deserialization of a ReviewJSONService
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
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
     * Returns the answer's content
     * @return String with the answer's content
     */
    public String getAnswer(){
        return answer;
    }
}
