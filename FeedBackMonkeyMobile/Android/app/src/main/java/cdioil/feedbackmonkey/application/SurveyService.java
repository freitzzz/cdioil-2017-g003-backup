package cdioil.feedbackmonkey.application;

import java.util.List;

/**
 * SurveyService class that represents the information of a certain survey which
 * the user will review
 * @author @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */

public final class SurveyService {
    /**
     * String with the survey name
     */
    private String surveyName;
    /**
     * String with the survey end date
     */
    private String surveyEndDate;
    /**
     * String with the survey average time to complete
     */
    private String surveyAverageTime;
    /**
     * List with all the survey items being reviewed
     */
    private List<String> surveyItems;

    /**
     * Builds a new SurveyService with all the information of a certain survey
     * @param surveyName String with the survey name
     * @param surveyEndDate String with the survey end date
     * @param surveyAverageTime String with the survey average time
     * @param surveyItems List with the survey items
     */
    public SurveyService(String surveyName,String surveyEndDate,String surveyAverageTime,List<String> surveyItems){
        this.surveyName=surveyName;
        this.surveyEndDate=surveyEndDate;
        this.surveyAverageTime=surveyAverageTime;
        this.surveyItems=surveyItems;
    }

    /**
     * Returns the current survey name
     * @return String with the survey name
     */
    public String getSurveyName(){return surveyName;}
    /**
     * Returns the current survey end date
     * @return String with the survey end date
     */
    public String getSurveyEndDate(){return surveyEndDate;}
    /**
     * Returns the current survey average time
     * @return String with the survey average time
     */
    public String getSurveyAverageTime(){return surveyAverageTime;}
    /**
     * Returns the current survey items
     * @return String with the survey items
     */
    public List<String> getSurveyItems(){return surveyItems;}

}
