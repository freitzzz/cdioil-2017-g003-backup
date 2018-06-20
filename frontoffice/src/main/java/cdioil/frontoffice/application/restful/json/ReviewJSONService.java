package cdioil.frontoffice.application.restful.json;

import cdioil.application.utils.services.json.AnswerJSONService;
import cdioil.application.utils.services.json.QuestionJSONService;
import cdioil.domain.Answer;
import cdioil.domain.Question;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

/**
 * ReviewJSONService class that represents the serialization/deserialization of a Review 
 * in JSON object format
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 6.0 of FeedbackMonkey
 */
public final class ReviewJSONService {
    /**
     * Map with all the review answers in Question-Answers pair value format
     */
    @SerializedName(value="reviewAnswers",alternate = {"ReviewAnswers","REVIEWANSWERS"})
    private Map<QuestionJSONService,AnswerJSONService> reviewAnswers;
    /**
     * String with the Review suggestion
     */
    @SerializedName(value="suggestion",alternate = {"Suggestion","SUGGESTION"})
    private String suggestion;
    /**
     * Builds a new ReviewJSONService with the review answers in Question-Answer 
     * pair format being serialiazed into a JSON object
     * @param reviewAnswers Map with the reviews answers in Question-Answers pair 
     * format being serialized into a JSON object
     * @param suggestion String with the review suggestion
     */
    public ReviewJSONService(Map<Question,Answer> reviewAnswers,String suggestion){
        this.reviewAnswers=toJSONServiceMap(reviewAnswers);
        this.suggestion=suggestion;
    }
    /**
     * Method that converts a Map of Question-Answer pair values into a Map of QuestionJSONService-AnswerJSONService
     * @param reviewAnswers Map with the review answers in Question-Answer pair value format being converted 
     * to QuestionJSONService-AnswerJSONService pair value format
     * @return Map with the review answers converted into a QuestionJSONService-AnswerJSONService pair value 
     * format
     */
    private Map<QuestionJSONService,AnswerJSONService> toJSONServiceMap(Map<Question,Answer> reviewAnswers){
        Map<QuestionJSONService,AnswerJSONService> reviewAnswersJSONService=new HashMap<>();
        reviewAnswers.forEach((question,answer)->{
            reviewAnswersJSONService.put(new QuestionJSONService(question),new AnswerJSONService(answer));
        });
        return reviewAnswersJSONService;
    }
    /**
     * Builds a Map<String,String> from a Map<QuestionJSONService,AnswerJSONService>
     * 
     * @return question answer map of a user's review
     */
    public Map<String,String> getQuestionAnswerMap(){
        Map<String,String> questionAnswerStringMap = new HashMap<>();
        reviewAnswers.entrySet().forEach((entry) -> {
            questionAnswerStringMap.put(entry.getKey().getQuestionText(),
                    entry.getValue().getAnswer());
        });
        return questionAnswerStringMap;
    }
}
