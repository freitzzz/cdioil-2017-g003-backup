/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.GlobalLibrary;
import cdioil.domain.authz.Manager;

/**
 * Interface for reading questions from a file.
 * 
 * @author Ana Guerra (1161191)
 */
public interface QuestionssReader {
    /**
     * Imports Questions from a file of any format.
     * 
     * @param globalLibrary Global Library
     * @param manager Manager
     * @return a integer with option
     */
    public abstract int readQuestoes(GlobalLibrary globalLibrary, Manager manager);
    
}
