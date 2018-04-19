/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

/**
 * Interface for writing statistics of a survey to a file.
 *
 * @author Rita Gonçalves (1160912)
 */
public interface SurveyStatsWriter {

    /**
     * Exports statistics about a survey to a file of any format.
     *
     * @return true, if the stats are successfully exported. Otherwise, returns false
     */
    public abstract boolean writeStats();

}
