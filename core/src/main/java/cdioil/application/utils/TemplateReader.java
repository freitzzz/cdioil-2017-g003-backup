package cdioil.application.utils;

import cdioil.domain.Question;
import cdioil.domain.SurveyItemType;
import cdioil.domain.Template;
import java.util.List;
import java.util.Map;

/**
 * Interface defining the behaviour for all implementations of TemplateReader.
 *
 * @author António Sousa [1161371]
 */
public interface TemplateReader {

    /**
     * Creates a template from a given file.
     *
     * @return a Template with the data within the file
     */
    public Template createTemplate();

    /**
     * Returns a list of identifiers of the items associated to the Template.
     *
     * @return list containing the identifiers of the items
     */
    public Map<SurveyItemType, List<String>> getSurveyItemIdentifiers();

    /**
     * Returns a list containing all the identifiers of questions to be fetched
     * from the Database.
     *
     * @return list of questionID's
     */
    public List<String> getDatabaseQuestionIDList();
    
    /**
     * Adds a database Question to the template's Collection of Questions
     * @param q question being added
     */
    public void addDatabaseQuestion(Question q);

}
