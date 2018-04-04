/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application;

import cdioil.application.utils.QuestoesReader;
import cdioil.application.utils.QuestoesReaderFactory;
import cdioil.domain.GlobalLibrary;
import cdioil.persistence.impl.GlobalLibraryRepositoryImpl;

/**
 * Controlador relativo ao caso de uso Importar Questoes através de ficheiros (US-210).
 *
 * @author Ana Guerra (1161191)
 */
public class ImportQuestionsController {

    
    private GlobalLibrary globalLibrary = new GlobalLibraryRepositoryImpl().findByQuery();
    /**
     * Importa uma lista de questoes de um ficheiro
     *
     * @param filename Caminho do ficheiro em questão
     * @return a lista de questoes lidas. Null se o ficheiro for inválido
     */
    public int lerFicheiro(String filename) {
        QuestoesReader questionsReader = QuestoesReaderFactory.create(filename);
        if (questionsReader != null){
            int cql = questionsReader.readQuestoes(globalLibrary);
            if (new GlobalLibraryRepositoryImpl().merge(globalLibrary) != null) return cql ;
        }
        return 1;
    }

}
