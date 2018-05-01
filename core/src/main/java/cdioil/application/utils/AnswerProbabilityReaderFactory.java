package cdioil.application.utils;

import cdioil.files.CommonFileExtensions;

/**
 * Class used for instantiating implementations of AnswerProbabilityReader.
 *
 * @author Antonio Sousa
 */
public final class AnswerProbabilityReaderFactory {

    /**
     * Constructs a AnswerProbabilityReader instance based on the file's
     * extension.
     *
     * @param filename file's absolute path
     * @return implementation of AnswerProbabilityReader adequate to the file
     */
    public static AnswerProbabilityReader create(String filename) {
        if (filename.endsWith(CommonFileExtensions.CSV_EXTENSION)) {
            return new CSVAnswerProbabilityReader(filename);
        }
        return null;
    }

    /**
     * Private constructor for hiding the implicit public one.
     */
    private AnswerProbabilityReaderFactory() {

    }

}
