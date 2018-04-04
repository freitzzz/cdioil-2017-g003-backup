/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.application;

import cdioil.application.utils.QuestoesReader;
import cdioil.application.utils.QuestoesReaderFactory;
import cdioil.domain.Question;
import java.util.List;

/**
 * Controlador relativo ao caso de uso Importar Questoes através de ficheiros (US-210).
 *
 * @author Ana Guerra (1161191)
 */
public class ImportQuestionsController {

    /**
     * Importa uma lista de questoes de um ficheiro
     *
     * @param filename Caminho do ficheiro em questão
     * @return a lista de questoes lidas. Null se o ficheiro for inválido
     */
    public List<Question> lerFicheiro(String filename) {
        QuestoesReader questoesReader = QuestoesReaderFactory.create(filename);

        return questoesReader != null ? questoesReader.readQuestoes() : null;
    }

}
