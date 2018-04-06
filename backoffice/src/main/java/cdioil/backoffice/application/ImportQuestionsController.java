/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.application;

import cdioil.application.utils.QuestionsReaderFactory;
import cdioil.domain.GlobalLibrary;
import cdioil.domain.authz.Manager;
import cdioil.persistence.impl.GlobalLibraryRepositoryImpl;
import cdioil.application.utils.QuestionsReader;

/**
 * Controller class for the User Story 210 - Import Questions from a File.
 *
 * @author Ana Guerra (1161191)
 */
public class ImportQuestionsController {

    /**
     * Instance of Global Library.
     */
    private final GlobalLibrary globalLibrary;
    /**
     * Instance of Manager.
     */
    private final Manager manager;

    /**
     * Creates new controller for the Import quetions use case (US-210)
     * @param manager Manager
     */
    public ImportQuestionsController(Manager manager) {
        this.manager = manager;
        globalLibrary = new GlobalLibraryRepositoryImpl().findGlobalLibrary();
    }

    /**
     * Import the questions from a file.
     *
     * @param filename Path of the file
     * @return an integer with suces
     */
    public int lerFicheiro(String filename) {
        QuestionsReader questionsReader = QuestionsReaderFactory.create(filename);
        if (questionsReader != null) {
            int cql = questionsReader.readQuestions(globalLibrary, manager);
            try {
                new GlobalLibraryRepositoryImpl().merge(globalLibrary);
                return cql;
            } catch (IllegalArgumentException ex) {
                System.out.println("As QuestÃµes nÃ£o foram persistidas.");
            }
        }
        return 1;
    }

}