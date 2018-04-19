/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.files.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private double binaryMean;

    /**
     * Average value for answers of quantitative questions.
     */
    private double quantitativeMean;

    /**
     * Mean deviation for answers of binary questions.
     */
    private double binaryMeanDeviation;

    /**
     * Mean deviation for answers of quantitative questions.
     */
    private double quantitativeMeanDeviation;

    /**
     * Total of binary questions.
     */
    private double totalBinary;

    /**
     * Total of quantitative questions.
     */
    private double totalQuantitative;

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
    public CSVSurveyStatsWriter(String filename, int totalBinary, int totalQuantitative, double binaryMean,
            double quantitativeMean, double binaryMeanDeviation, double quantitativeMeanDeviation) {
        this.file = new File(filename);
        this.totalBinary = totalBinary;
        this.totalQuantitative = totalQuantitative;
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
        String header = "Tipo" + SPLITTER + "Total" + SPLITTER + "Média" + SPLITTER + "Desvio Padrão";
        fileContent.add(header);
        fileContent.add("Quantitativa" + SPLITTER + totalQuantitative + SPLITTER + quantitativeMean
                + SPLITTER + quantitativeMeanDeviation);
        fileContent.add("Binária" + SPLITTER + totalBinary + SPLITTER + binaryMean
                + SPLITTER + binaryMeanDeviation);
        
        return FileWriter.writeFile(file, fileContent);
    }
}
