/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;


/**
 * Factory of QuestionsReader.
 * 
 * @author Ana Guerra (1161191)
 */
public final class QuestionsReaderFactory {

    /**
     * Creates an instance of QuestionsReader.
     *
     * @param filename Name of the file to read
     * @return an instance of QuestionsReader
     */
    public static QuestionssReader create(String filename) {
        if (filename.endsWith((CommonFileExtensions.CSV_EXTENSION))) {
            return new CSVQuestionsReader(filename);
        }
        return null;
    }

    /**
     * Hides the private constructor.
     */
    private QuestionsReaderFactory() {
    }
    
}
