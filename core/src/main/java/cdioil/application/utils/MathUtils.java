/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import java.util.ArrayList;

/**
 *
 * @author Ana Guerra (1161191)
 */
public class MathUtils {

    public double calculateMean(ArrayList<Double> resultsList) {

        validateValues(resultsList);
        double mean = 0;
        double sum = 0;

        for (Double value : resultsList) {
            sum += value;
        }

        mean = sum / (double) resultsList.size();
        return mean;
    }

    public double calculateMeanDeviation(ArrayList<Double> resultsList) {

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

    public boolean validateValues(ArrayList<Double> resultsList) {
        return !(resultsList == null || resultsList.isEmpty());
    }

}
