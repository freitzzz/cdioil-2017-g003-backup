package cdioil.application.utils;

import cdioil.domain.Question;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Class used for importing Questions from a file with the .xml extension.
 *
 * @author João
 */
public class XMLQuestionsReader implements QuestionsReader {

    /**
     * File being read.
     */
    private final File file;

    /**
     * Creates an instance of XMLQuestionsReader, receiving the name of the file
     * to read.
     *
     * @param filename Name of the file to read
     */
    public XMLQuestionsReader(String filename) {
        this.file = new File(filename);
    }

    /**
     * Reads a category questions from a XML file.
     *
     * @return a map with the path of category and the list of the questions
     */
    @Override
    public Map<String, List<Question>> readCategoryQuestions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Question> readIndependentQuestions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
