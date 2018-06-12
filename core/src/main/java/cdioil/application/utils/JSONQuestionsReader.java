package cdioil.application.utils;

import cdioil.domain.Question;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Class used for importing Questions from a JSON file
 *
 * @author João
 */
public class JSONQuestionsReader implements QuestionsReader {

    /**
     * File being read.
     */
    private final File file;

    /**
     * Creates an instance of JSONQuestionsReader, receiving the name of the
     * file to read.
     *
     * @param filename Name of the file to read
     */
    public JSONQuestionsReader(String filename) {
        this.file = new File(filename);
    }

    @Override
    public Map<String, List<Question>> readCategoryQuestions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Question> readIndependentQuestions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
