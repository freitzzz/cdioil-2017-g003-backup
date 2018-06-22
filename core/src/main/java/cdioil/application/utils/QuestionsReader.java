package cdioil.application.utils;

import cdioil.domain.Question;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Interface for reading questions from files.
 *
 * @author Ana Guerra (1161191)
 * @author António Sousa [1161371]
 */
public interface QuestionsReader {

    /**
     * Imports questions associated to categories from a file.
     *
     * @return map with categories' path as key and list of questions related to
     * that category as values.
     */
    Map<String, List<Question>> readCategoryQuestions();

    /**
     * Imports questions independent of categories from a file.
     *
     * @return list of questions read from file.
     */
    List<Question> readIndependentQuestions() throws ParserConfigurationException, IOException, TransformerException;

}
