package cdioil.feedbackmonkey.restful.utils.json;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * JSON Service that represents a user that is reviewing a product (answering a survey).
 *
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 * @author <a href="1161380@isep.ipp.pt">Joana Pinheiro</a>
 * @since Version 5.0 of FeedbackMonkey
 */
public class ReviewJSONService {
    /**
     * Question Answer map of a review.
     */
    @SerializedName(value = "questionAsnwerMap", alternate = {"QUESTIONANSWERMAP", "questionanswermap"})
    private Map<String, String> questionAnswerMap;

    /**
     * Builds a new ReviewJSONService with the question answer map of the review
     * being serialized
     *
     * @param questionAnswerMap question answer map of the review being serialized
     */
    public ReviewJSONService(Map<String, String> questionAnswerMap) {
        this.questionAnswerMap = new HashMap<>();
        for (Entry<String, String> entry : questionAnswerMap.entrySet()) {
            this.questionAnswerMap.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Access method to the question answer map.
     *
     * @return map with the questions and answers of a review
     */
    public Map<String, String> getQuestionAnswerMap() {
        return questionAnswerMap;
    }
}



