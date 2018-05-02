package cdioil.application.utils;

import cdioil.files.CommonFileExtensions;
import java.io.File;

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
     * @param file file to be read
     * @return implementation of AnswerProbabilityReader adequate to the file
     */
    public static AnswerProbabilityReader create(File file) {
        if (file.getAbsolutePath().endsWith(CommonFileExtensions.CSV_EXTENSION)) {
            return new CSVAnswerProbabilityReader(file);
        }
        return null;
    }

    /**
     * Private constructor for hiding the implicit public one.
     */
    private AnswerProbabilityReaderFactory() {

    }

}
