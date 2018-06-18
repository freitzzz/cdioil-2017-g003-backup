package cdioil.application.utils;

import cdioil.application.utils.services.xml.SurveyAnswersXMLService;
import cdioil.domain.Review;
import cdioil.files.FileWriter;
import cdioil.xsl.XSLTransformer;
import java.io.File;
import java.util.List;

/**
 * Utilitary class that writes survey answers into a CSV file
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class CSVSurveyAnswersWriter implements SurveyAnswersWriter{
    /**
     * File with the file that is going to be written with all survey answers
     */
    private final File file;
    /**
     * List with all survey reviews being exported
     */
    private final List<Review> surveyReviews;
    /**
     * Builds a new CSVSurveyAnswersWriter with the file that is going to be 
     * written all survey answers
     * @param filename String with the filename that is going to be written all survey answers
     * @param surveyReviews List with all survey reviews being exported
     */
    public CSVSurveyAnswersWriter(String filename,List<Review> surveyReviews){
        this.file=new File(filename);
        this.surveyReviews=surveyReviews;
    }
    /**
     * Method that writes all answers from a certain survey into a CSV file
     * @return boolean true if all answers were writed with success, false if not
     */
    @Override
    public boolean write() {
        return FileWriter.writeFile(file,XSLTransformer.create(XSLSurveyAnswersDocuments
                .CSV_SURVEY_ANSWERS_XSLT)
                .transform(new SurveyAnswersXMLService(surveyReviews)
                        .toXMLDocument()));
    }
}
