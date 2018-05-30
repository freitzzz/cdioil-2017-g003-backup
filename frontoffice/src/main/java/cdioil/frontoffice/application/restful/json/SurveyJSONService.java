package cdioil.frontoffice.application.restful.json;

import cdioil.domain.Survey;
import com.google.gson.annotations.SerializedName;

/**
 * JSON Service that represents the serialization/deserialization of an Survey
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
 * @since Version 5.0 of FeedbackMonkey
 */

public final class SurveyJSONService {
    /**
     * String with the survey name
     */
    @SerializedName(value = "surveyName")
    private String surveyName;
    /**
     * Builds a new SurveyJSONService with the survey being serialized
     * @param survey SurveyJSONService with teh survey being serialized
     */
    public SurveyJSONService(Survey survey){
        this.surveyName=survey.getName();
    }
    
    /**
     * Returns the survey name
     * @return String with the survey name
     */
    public String getSurveyName(){return surveyName;}    
}
