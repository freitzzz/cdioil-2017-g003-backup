/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.files.CommonFileExtensions;


/**
 * Factory de QuestionsReader.
 * 
 * @author Ana Guerra (1161191)
 */
public final class QuestionsReaderFactory {

    /**
     * Creates a QuestionsReader instance according to the format of the file being read.
     *
     * @param filename file name
     * @return an instance of QuestionsReader
     */
    public static QuestionsReader create(String filename) {
        if (filename.endsWith((CommonFileExtensions.CSV_EXTENSION))) {
            return new CSVQuestionsReader(filename);
        }
        return null;
    }
    /**
     * Hides default constructoro
     */
    private QuestionsReaderFactory() {
    }
    
}
