/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import java.util.List;

/**
 * Class for mathematical calculations.
 *
 * @author Ana Guerra (1161191)
 */
public class MathUtils {

    /**
     * Method that calculates the average of a list of values.
     *
     * @param resultList List with the values
     * @return the mean of the values provided. -1 if the result list is invalid
     */
    public static double calculateMean(List<Double> resultList) {
        if (!validateValues(resultList)) {
            return -1;
        }

        double sum = 0;
        for (Double value : resultList) {
            sum += value;
        }

        if (sum == 0) {
            return 0;
        }
        
        return sum / (double) resultList.size();
    }

    /**
     * Method that calculates the mean deviation of a list of values.
     *
     * @param resultList List with the values
     * @return the mean deviation of the values provided. -1 if the result list is invalid or the mean is 0
     */
    public static double calculateMeanDeviation(List<Double> resultList) {
        if (!validateValues(resultList)) {
            return -1;
        }

        double mean = calculateMean(resultList);
        
        if (mean == -1 || mean == 0) {
            return mean;
        }
        
        double sum = 0;
        for (Double value : resultList) {
            sum += Math.pow((value - mean), 2);
        }
        
        return Math.sqrt((sum) / ((double) resultList.size()));
    }

    /**
     * Checks if the result list is valid.
     *
     * @param resultList List to check
     * @return true, if the list isn't null or empty. Otherwise, returns false
     */
    public static boolean validateValues(List<Double> resultList) {
        return resultList != null && !resultList.isEmpty();
    }
}
