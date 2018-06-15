package cdioil.frontoffice.application.restful.json;

import cdioil.domain.Survey;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON Service that represents the serialization/deserialization of an Survey
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
     * Builds a new SurveyJSONService with the survey being serialized
     * @param survey SurveyJSONService with the survey being serialized
     */
    public SurveyJSONService(Survey survey){
        this.surveyID=""+survey.getID();
        this.surveyName=survey.getName();
        this.surveyEndDate=survey.getSurveyEndDate();
        this.surveyItems=new ArrayList<>();
        survey.getItemList().forEach((surveyItem)->{surveyItems.add(surveyItem.toString());});
    }
    
    /**
     * Returns the survey name
     * @return String with the survey name
     */
    public String getSurveyName(){return surveyName;}    
}
