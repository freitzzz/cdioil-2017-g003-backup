package cdioil.application.utils;

/**
 * SurveyAnswersWriter Factory that creates a new writer depending on 
 * the file extension of the file that is going to be written
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class SurveyAnswersWriterFactory {
    /**
     * Creates a new SurveyAnswersWriter with the filename that is going to 
     * be written all survey answers
     * @param filename String with the file name that is going to be written all survey answers
     * @return SurveyAnswersWriter with the writter for the respective file extension, 
     * or null if the file extension is not allowed
     */
    public static SurveyAnswersWriter create(String filename){
        if(filename.endsWith(CommonFileExtensions.CSV_EXTENSION)){
            return new CSVSurveyAnswersWriter(filename);
        }
        return null;
    }
    /**
     * Hides default constructor
     */
    private SurveyAnswersWriterFactory(){}
}
