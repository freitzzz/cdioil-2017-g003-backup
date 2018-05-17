/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.frontoffice.application.restful;

import cdioil.domain.Question;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON Service that represents a user that is reviewing a product.
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
}
