package cdioil.application.utils;

import cdioil.domain.Question;
import cdioil.files.FileWriter;
import cdioil.xsl.XSLTransformer;
import java.io.File;
import java.util.Map;

/**
 * Exports statistics of a survey to a .json file.
 *
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class JSONSurveyStatsWriter implements SurveyStatsWriter {

    /**
     * File to write.
     */
    private final String fileName;
    
    /**
     * Survey's ID.
     */
    private long surveyID;

    /**
     * Average value for answers of binary questions.
     */
    private final Map<Question, Double> binaryMean;

    /**
     * Average value for answers of quantitative questions.
     */
    private final Map<Question, Double> quantitativeMean;

    /**
     * Mean deviation for answers of binary questions.
     */
    private final Map<Question, Double> binaryMeanDeviation;

    /**
     * Mean deviation for answers of quantitative questions.
     */
    private final Map<Question, Double> quantitativeMeanDeviation;

    /**
     * Total of binary questions.
     */
    private final Map<Question, Integer> binaryTotal;

    /**
     * Total of quantitative questions.
     */
    private final Map<Question, Integer> quantitativeTotal;

    /**
     * Creates a new JSONSurveyStatsWriter.
     *
     * @param filename Path of the file
     * @param surveyID Survey's ID
     * @param binaryTotal Total of answers to binary questions
     * @param quantitativeTotal Total of answers to quantitative questions
     * @param binaryMean Average value for binary answers
     * @param quantitativeMean Average value for quantitative answers
     * @param binaryMeanDeviation Mean deviation for binary answers
     * @param quantitativeMeanDeviation Mean deviation for quantitative answers
     *
     */
    public JSONSurveyStatsWriter(String filename,long surveyID, Map<Question, Integer> binaryTotal, Map<Question, Integer> quantitativeTotal, Map<Question, Double> binaryMean,
            Map<Question, Double> quantitativeMean, Map<Question, Double> binaryMeanDeviation, Map<Question, Double> quantitativeMeanDeviation) {
        this.fileName = filename;
        this.surveyID = surveyID;
        this.binaryTotal = binaryTotal;
        this.quantitativeTotal = quantitativeTotal;
        this.quantitativeMeanDeviation = quantitativeMeanDeviation;
        this.binaryMeanDeviation = binaryMeanDeviation;
        this.quantitativeMean = quantitativeMean;
        this.binaryMean = binaryMean;
    }

    /**
     * Writes the statistics into a JSON file.
     *
     * @return true, if the statistics are successfully exported. Otherwise, returns false
     */
    @Override
    public boolean writeStats() {
        XMLSurveyStatsWriter xmlWriter = new XMLSurveyStatsWriter(fileName,surveyID,
        binaryTotal,quantitativeTotal,binaryMean,quantitativeMean,
        binaryMeanDeviation,quantitativeMeanDeviation);
        return FileWriter.writeFile(new File(fileName),XSLTransformer.
                create(XSLSurveyStatsDocuments.JSON_SURVEY_STATS_XSLT).
                transform(xmlWriter.getXMLAsString()));
    }

}
