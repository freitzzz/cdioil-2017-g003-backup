package cdioil.application.utils;

import cdioil.files.CommonFileExtensions;

/**
 * Factory de QuestionsReader.
 *
 * @author Ana Guerra (1161191)
 */
public final class QuestionsReaderFactory {

    /**
     * Hides default constructor
     */
    private QuestionsReaderFactory() {
    }

    /**
     * Creates a QuestionsReader instance according to the format of the file
     * being read.
     *
     * @param filename file name
     * @return an instance of QuestionsReader
     */
    public static QuestionsReader create(String filename) {
        if (filename.endsWith((CommonFileExtensions.CSV_EXTENSION))) {
            return new CSVQuestionsReader(filename);
        }
        if(filename.endsWith((CommonFileExtensions.XML_EXTENSION))){
            return new XMLQuestionsReader(filename);
        }
        if(filename.endsWith((CommonFileExtensions.JSON_EXTENSION))){
            return new JSONQuestionsReader(filename);
        }
        return null;
    }

}
