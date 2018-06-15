package cdioil.feedbackmonkey.restful.utils.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * JSON Service that represents the serialization/deserialization of an SurveyService
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
 * @since Version 5.0 of FeedbackMonkey
 */

public final class SurveyJSONService {
    /**
     * String with the survey ID
     */
    @SerializedName(value = "surveyID",alternate = {"SurveyID","SURVEYID"})
    private String surveyID;
    /**
     * String with the survey name
     */
    @SerializedName(value = "surveyName",alternate = {"SurveyName","SURVEYNAME"})
    private String surveyName;
    /**
     * String with the survey end date
     */
    @SerializedName(value = "surveyEndDate",alternate = {"SurveyEndDate","SURVEYENDDATE"})
    private String surveyEndDate;
    /**
     * String with the survey average time to complete
     */
    @SerializedName(value = "surveyAverageTime",alternate = {"SurveyAverageTime","SURVEYAVERAGETIME"})
    private String surveyAverageTime;
    /**
     * List with all the survey items
     */
    @SerializedName(value = "surveyItems",alternate = {"SurveyItems","SURVEYITEMS"})
    private List<String> surveyItems;

    /**
     * Returns the survey ID
     * @return String with the survey ID
     */
    public String getSurveyID(){return surveyID;}
    /**
     * Returns the survey name
     * @return String with the survey name
     */
    public String getSurveyName(){return surveyName;}

    /**
     * Returns the survey end date
     * @return String with the survey end date
     */
    public String getSurveyEndDate(){return surveyEndDate;}

    /**
     * Returns the survey average time
     * @return String with the survey average time
     */
    public String getSurveyAverageTime(){return surveyAverageTime;}

    /**
     * Returns all survey items
     * @return List with all survey items
     */
    public List<String> getSurveyItems(){return surveyItems;}
}