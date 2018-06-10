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
