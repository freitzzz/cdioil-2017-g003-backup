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
     * String with the question name.
     */
    @SerializedName(value = "questionName", alternate = {"QUESTIONNAME", "questionname"})
    private final String questionName;

    /**
     * List with all options of that question.
     */
    @SerializedName(value = "questionOptions", alternate = {"QUESTIONOPTIONS", "questionoptions"})
    private List<String> questionOptions;
    
    /**
     * Question Answer map of a review.
     */
    @SerializedName(value = "questionAsnwerMap", alternate = {"QUESTIONANSWERMAP", "questionanswermap"})
    private Map<String,String> questionAnswerMap;

    /**
     * Builds a new QuestionJSONService with the survey being serialized.
     *
     * @param question QuestionJSONService with the survey being serialized
     */
    public ReviewJSONService(Question question) {
        this.questionName = question.getQuestionText();
        questionOptions = new ArrayList<>();
        question.getOptionList().forEach(questionOption
                -> this.questionOptions.add(questionOption.toString())
        );
    }
    
    /**
     * Builds a new ReviewJSONService with the question answer map of the review
     * being serialized
     * 
     * @param questionAnswerMap  question answer map of the review being serialized
     */
    public ReviewJSONService(Map<Question,Answer> questionAnswerMap){
        this.questionName = "";
        this.questionAnswerMap = new HashMap<>();
        questionAnswerMap.entrySet().forEach((entry) -> {
            this.questionAnswerMap.put(entry.getKey().getQuestionText(),
                    entry.getValue().getContent());
        });
    }

    /**
     * Access method to the question name.
     *
     * @return String with the question name
     */
    public String getQuestionName() {
        return questionName;
    }

    /**
     * Access method to thq question options.
     *
     * @return List with the options of that question
     */
    public List<String> getQuestionOptions() {
        return questionOptions;
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
