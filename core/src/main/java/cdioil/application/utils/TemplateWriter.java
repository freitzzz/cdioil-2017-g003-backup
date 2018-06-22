package cdioil.application.utils;

import cdioil.domain.SurveyItem;
import java.util.List;

/**
 * Utilitary class that writes a template's information into a file.
 *
 * @author Pedro Portela (1150782)
 * @author Ana Guerra (1161191)
 */
public interface TemplateWriter {

    /**
     * Exports the information about a template to a file of any supported format.
     *
     * @return true, if the file is successfully exported. Otherwise, returns false
     */
    public abstract boolean write();
    /**
     * Adds the given SurveyItems to the Template's file's list of SurveyItems
     * @param surveyItems SurveyItems linked to the Template being read
     * @return true - if all items were added successfully<p>false - otherwise
     */
    public abstract boolean addSurveyItems(List<SurveyItem> surveyItems);
}
