package cdioil.application.utils;

import cdioil.application.utils.services.xml.SurveyAnswersXMLService;
import cdioil.domain.Review;
import cdioil.files.FileWriter;
import java.io.File;
import java.util.List;

/**
 * Utilitary class that writes survey answers into a XML file
 *
 * @author <a href="1161181@isep.ipp.pt">Margarida Guerra</a>
 */
public class XMLSurveyAnswersWriter implements SurveyAnswersWriter {

    /**
     * File with the file that is going to be written with all survey answers
     */
    private final File file;
    /**
     * List with all the reviews of a certain survey being written into a XML file
     */
    private final List<Review> surveyReviews;

    /**
     * Builds a new XMLSurveyAnswersWriter with the file that is going to be written all survey answers
     *
     * @param filename String with the filename that is going to be written all survey answers
     * @param surveyReviews List with all survey reviews being exported
     */
    public XMLSurveyAnswersWriter(String filename, List<Review> surveyReviews) {
        this.file = new File(filename);
        this.surveyReviews = surveyReviews;
    }

    /**
     * Method that writes all answers from a certain survey into a XML file
     *
     * @return boolean true if all answers were writed with success, false if not
     */
    @Override
    public boolean write() {
        return FileWriter.writeFile(file,new SurveyAnswersXMLService(surveyReviews).toXMLDocument());
    }
    
}
