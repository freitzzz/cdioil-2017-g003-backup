/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.GlobalLibrary;

/**
 * Interface for reading questions from files.
 * 
 * @author Ana Guerra (1161191)
 */
public interface QuestionsReader {
    
    /**
     * Imports questions from a file.
     *
     * @param globalLibrary
     * @return A biblioteca de Questoesde uma dada categoria
     */
    int readQuestions(GlobalLibrary globalLibrary);
    
}
