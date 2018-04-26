/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.Question;
import cdioil.files.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Exports statistics of a survey to a .csv file.
 *
 * @author Rita Gonçalves (1160912)
 */
public class CSVSurveyStatsWriter implements SurveyStatsWriter {

    //Constants
    /**
     * Splitter of the columns of the file.
     */
    private static final String SPLITTER = ";";

    //Attributes
    /**
     * File to read.
     */
    private final File file;

    /**
     * Average value for answers of binary questions.
     */
    private Map<Question, Double> binaryMean;

    /**
     * Average value for answers of quantitative questions.
     */
    private Map<Question, Double> quantitativeMean;

    /**
     * Mean deviation for answers of binary questions.
     */
    private Map<Question, Double> binaryMeanDeviation;

    /**
     * Mean deviation for answers of quantitative questions.
     */
    private Map<Question, Double> quantitativeMeanDeviation;

    /**
     * Total of binary questions.
     */
    private Map<Question, Integer> binaryTotal;

    /**
     * Total of quantitative questions.
     */
    private Map<Question, Integer> quantitativeTotal;

    /**
     * Field that identifies the question by its ID.
     */
    private final static String QUESTION_ID = "Questão (ID)";
    
    /**
     * Field that identifies the type of the question.
     */
    private final static String QUESTION_TYPE = "Tipo";
    
    /**
     * Field that contains the number of answers to the question.
     */
    private final static String TOTAL = "Total";
    
    /**
     * Field that contains the average of the answers to the question.
     */
    private final static String AVG = "Média";
    
    /**
     * Field that contains the mean deviation of the answers to the question.
     */
    private final static String MEAN_DEVIATION = "Desvio Padrão";

    /**
     * Creates a new CSVSurveyStatsWriter.
     *
     * @param filename Path of the file
     * @param totalBinary Total of answers to binary questions
     * @param totalQuantitative Total of answers to quantitative questions
     * @param binaryMean Average value for binary answers
     * @param quantitativeMean Average value for quantitative answers
     * @param binaryMeanDeviation Mean deviation for binary answers
     * @param quantitativeMeanDeviation Mean deviation for quantitative answers
     *
     */
    public CSVSurveyStatsWriter(String filename, Map<Question, Integer> totalBinary, Map<Question, Integer> totalQuantitative, Map<Question, Double> binaryMean,
            Map<Question, Double> quantitativeMean, Map<Question, Double> binaryMeanDeviation, Map<Question, Double> quantitativeMeanDeviation) {
        this.file = new File(filename);
        this.binaryTotal = totalBinary;
        this.quantitativeTotal = totalQuantitative;
        this.quantitativeMeanDeviation = quantitativeMeanDeviation;
        this.binaryMeanDeviation = binaryMeanDeviation;
        this.quantitativeMean = quantitativeMean;
        this.binaryMean = binaryMean;
    }

    /**
     * Writes the statistics in a .csv file.
     *
     * @return true, if the statistics are successfully exported. Otherwise, returns false
     */
    @Override
    public boolean writeStats() {
        List<String> fileContent = new ArrayList<>();
        String header = QUESTION_ID + SPLITTER + QUESTION_TYPE + SPLITTER + TOTAL + SPLITTER + AVG + SPLITTER + MEAN_DEVIATION;
        fileContent.add(header);
        
        for(Question q : binaryMean.keySet()){
            String info = q.getQuestionID() + SPLITTER + q.getType() + SPLITTER + binaryTotal.get(q) + SPLITTER
                    + binaryMean.get(q) + binaryMeanDeviation.get(q);
            fileContent.add(info);
        }       
        
        for(Question q : quantitativeMean.keySet()){
            String info = q.getQuestionID() + SPLITTER + q.getType() + SPLITTER + quantitativeTotal.get(q) + SPLITTER
                    + quantitativeMean.get(q) + binaryMeanDeviation.get(q);
            fileContent.add(info);
        }

        return FileWriter.writeFile(file, fileContent);
    }
}
