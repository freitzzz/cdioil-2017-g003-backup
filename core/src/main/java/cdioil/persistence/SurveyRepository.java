package cdioil.persistence;

import cdioil.domain.TargetedSurvey;
import java.util.List;

/**
 * Interface for the Survey Repository
 *
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public interface SurveyRepository {

    /**
     * Returns all Targeted Surveys
     *
     * @return list of targeted surveys
     */
    public abstract List<TargetedSurvey> getTargetedSurveys();
}
