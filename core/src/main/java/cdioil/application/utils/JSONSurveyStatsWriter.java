/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.Question;
import java.io.File;
import java.util.Map;

/**
 * Exports statistics of a survey to a .json file.
 *
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class JSONSurveyStatsWriter implements SurveyStatsWriter {

    //Attributes
    /**
     * File to write.
     */
    private final File file;

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
     * Field that identifies the question by its ID.
     */
    private static final String QUESTION_ID = "Questão (ID)";

    /**
     * Field that identifies the type of the question.
     */
    private static final String QUESTION_TYPE = "Tipo";

    /**
     * Field that contains the number of answers to the question.
     */
    private static final String TOTAL = "Total";

    /**
     * Field that contains the average of the answers to the question.
     */
    private static final String AVG = "Média";

    /**
     * Field that contains the mean deviation of the answers to the question.
     */
    private static final String MEAN_DEVIATION = "Desvio Padrão";

    /**
     * Creates a new JSONSurveyStatsWriter.
     *
     * @param filename Path of the file
     * @param binaryTotal Total of answers to binary questions
     * @param quantitativeTotal Total of answers to quantitative questions
     * @param binaryMean Average value for binary answers
     * @param quantitativeMean Average value for quantitative answers
     * @param binaryMeanDeviation Mean deviation for binary answers
     * @param quantitativeMeanDeviation Mean deviation for quantitative answers
     *
     */
    public JSONSurveyStatsWriter(String filename, Map<Question, Integer> binaryTotal, Map<Question, Integer> quantitativeTotal, Map<Question, Double> binaryMean,
            Map<Question, Double> quantitativeMean, Map<Question, Double> binaryMeanDeviation, Map<Question, Double> quantitativeMeanDeviation) {
        this.file = new File(filename);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
