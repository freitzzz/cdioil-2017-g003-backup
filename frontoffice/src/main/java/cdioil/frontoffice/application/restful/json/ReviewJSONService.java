/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.frontoffice.application.restful.json;

import cdioil.domain.Answer;
import cdioil.domain.Question;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * JSON Service that represents a user that is reviewing a product (answering a survey).
 *
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 * @author <a href="1161380@isep.ipp.pt">Joana Pinheiro</a>
 *
 * @since Version 5.0 of FeedbackMonkey
 */
public class ReviewJSONService {
    /**
     * Question Answer map of a review.
     */
    @SerializedName(value = "questionAsnwerMap", alternate = {"QUESTIONANSWERMAP", "questionanswermap"})
    private Map<String,String> questionAnswerMap;
    
    /**
     * Builds a new ReviewJSONService with the question answer map of the review
     * being serialized
     * 
     * @param questionAnswerMap  question answer map of the review being serialized
     */
    public ReviewJSONService(Map<String,String> questionAnswerMap){
        this.questionAnswerMap = new HashMap<>();
        questionAnswerMap.entrySet().forEach((entry) -> {
            this.questionAnswerMap.put(entry.getKey(),entry.getValue());
        });
    }

    /**
     * Access method to the question answer map.
     * 
     * @return map with the questions and answers of a review
     */
    public Map<String,String> getQuestionAnswerMap(){
        return questionAnswerMap;
    }
}
