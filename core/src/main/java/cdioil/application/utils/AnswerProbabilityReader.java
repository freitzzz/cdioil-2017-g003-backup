package cdioil.application.utils;

import java.util.List;
import java.util.Map;

/**
 * Interface defining behaviour for reading Questions' probabilistic
 * distribution functions.
 *
 * @author Antonio Sousa
 */
public interface AnswerProbabilityReader {

    /**
     * Reads Questions' answer's probabibilities from a file.
     *
     * @return Map in which the key is a Question's identifier and the value is
     * a list of probabilities
     */
    public abstract Map<String, List<Double>> readProbabilities();
}
