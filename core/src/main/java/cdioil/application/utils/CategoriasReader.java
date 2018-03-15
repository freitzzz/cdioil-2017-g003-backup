/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;
        
import java.util.List;
import cdioil.domain.Categoria;

/**
 * Interface que permite a leitura de Categorias.
 *
 * @author Rita Gonçalves (1160912)
 */
public interface CategoriasReader {

    /**
     * Importa Categorias de um ficheiro de um determinado formato.
     *
     * @return Lista com as Categorias lidas
     */
    public abstract List<Categoria> lerFicheiro();
}
