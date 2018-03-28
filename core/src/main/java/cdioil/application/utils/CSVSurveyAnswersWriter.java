package cdioil.application.utils;

import java.io.File;

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
     * Builds a new CSVSurveyAnswersWriter with the file that is going to be 
     * written all survey answers
     * @param filename String with the filename that is going to be written all survey answers
     */
    public CSVSurveyAnswersWriter(String filename){this.file=new File(filename);}
    /**
     * Method that writes all answers from a certain survey into a CSV file
     * @return boolean true if all answers were writed with success, false if not
     */
    @Override
    public boolean write() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
