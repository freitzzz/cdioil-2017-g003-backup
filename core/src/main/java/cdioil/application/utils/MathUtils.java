/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import java.util.List;

/**
 * Class for mathematical calculations
 * @author Ana Guerra (1161191)
 */
public class MathUtils {

    /**
     * Method that calculates the average of a set of evaluation
     * @param resultsList List with the values of the evaluations
     * @return the mean of the values provided
     */
    public static double calculateMean(List<Double> resultsList) {

        validateValues(resultsList);
        double mean = 0;
        double sum = 0;

        for (Double value : resultsList) {
            sum += value;
        }

        mean = sum / (double) resultsList.size();
        return mean;
    }

    /**
     * Method that calculates the mean deviation of a set of evaluation
     * @param resultsList List with the values of the evaluations
     * @return the mean deviation of the values provided
     */
    public static double calculateMeanDeviation(List<Double> resultsList) {

        validateValues(resultsList);
        double meanDeviation = 0;
        double sum = 0;

        double mean = calculateMean(resultsList);

        for (Double value : resultsList) {
            sum += Math.pow((value - mean),2);
        }
        meanDeviation = Math.sqrt((sum)/ ((double) resultsList.size()));

        return meanDeviation;
    }

    public static boolean validateValues(List<Double> resultsList) {
        return !(resultsList == null || resultsList.isEmpty());
    }

}
