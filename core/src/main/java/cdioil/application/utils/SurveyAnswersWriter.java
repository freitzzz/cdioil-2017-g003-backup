package cdioil.application.utils;

/**
 * Interface that represents the write of survey answers to a file
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public interface SurveyAnswersWriter {
    /**
     * Method that writes all answers from a certain survey into a file
     * @return boolean true if all answers were writed with success, false if not
     */
    public abstract boolean write();
}
