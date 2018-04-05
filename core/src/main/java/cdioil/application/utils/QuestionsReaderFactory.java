/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;


/**
 * Factory de QuestionsReader.
 * 
 * @author Ana Guerra (1161191)
 */
public final class QuestionsReaderFactory {

    /**
     * Cria uma instância de QuestionsReader de acordo com o formato do ficheiro a ler.
     *
     * @param filename Nome do ficheiro a ler
     * @return uma instância de QuestionsReader
     */
    public static QuestionsReader create(String filename) {
        if (filename.endsWith((CommonFileExtensions.CSV_EXTENSION))) {
            return new CSVQuestionsReader(filename);
        }
        return null;
    }

    /**
     * Esconde o construtor privado
     */
    private QuestionsReaderFactory() {
    }
    
}
