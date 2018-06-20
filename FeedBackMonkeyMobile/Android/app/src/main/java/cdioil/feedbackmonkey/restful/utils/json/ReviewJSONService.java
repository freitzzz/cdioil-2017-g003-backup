package cdioil.feedbackmonkey.restful.utils.json;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * ReviewJSONService class for the deserialization of the object.
 *
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 5.0 of FeedbackMonkey
 */
public class ReviewJSONService {
    /**
     * Map with all the review answers in Question-Answers pair value format
     */
    @SerializedName(value = "reviewAnswers", alternate = {"ReviewAnswers", "REVIEWANSWERS"})
    private Map<QuestionJSONService, AnswerJSONService> reviewAnswers;
    /**
     * String with the Review suggestion
     */
    @SerializedName(value = "suggestion", alternate = {"Suggestion", "SUGGESTION"})
    private String suggestion;
    /**
     * Builds a Map<String,String> from a Map<QuestionJSONService,AnswerJSONService>
     *
     * @return question answer map of a user's review
     */
    public Map<String, String> getQuestionAnswerMap() {
        Map<String, String> questionAnswerStringMap = new HashMap<>();
        for (Entry<QuestionJSONService, AnswerJSONService> entry : reviewAnswers.entrySet()) {
            questionAnswerStringMap.put(entry.getKey().getQuestionText(), entry.getValue().getAnswer());
        }
        return questionAnswerStringMap;
    }
}



