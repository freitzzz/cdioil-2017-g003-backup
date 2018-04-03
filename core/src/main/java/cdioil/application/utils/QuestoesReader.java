/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.Question;
import java.util.List;

/**
 * Interface que permite a leitura de questoes.
 * 
 * @author Ana Guerra (1161191)
 */
public interface QuestoesReader {
    
    /**
     * Importa questoes de um ficheiro de um determinado formato.
     *
     * @return Lista com as questoes lidas
     */
    public abstract List<Question> readQuestoes();
    
}
