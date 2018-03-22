/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application;

import cdioil.domain.Categoria;
import cdioil.application.utils.CategoriasReader;
import cdioil.application.utils.CategoriasReaderFactory;

import java.util.List;

/**
 * Controlador relativo ao caso de uso Importar Categorias através de ficheiros
 * (US-201).
 *
 * @author Rita Gonçalves (1160912)
 */
public class ImportarCategoriasController {


    /**
     * Importa uma lista de Categorias de um ficheiro
     *
     * @param filename Caminho do ficheiro em questão
     * @return a lista de Categorias lidas. Null se o ficheiro for inválido
     */
    public List<Categoria> lerFicheiro(String filename) {
        CategoriasReader categoriasReader = CategoriasReaderFactory.create(filename);
        
        return categoriasReader != null ? categoriasReader.lerFicheiro() : null;
    }
}
