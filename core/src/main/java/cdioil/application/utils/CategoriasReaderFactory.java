/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

/**
 * Factory de CategoriesReader.
 *
 * @author Rita Gonçalves (1160912)
 */
public final class CategoriasReaderFactory {

    /**
     * Identificador do formato .csv.
     */
    public static final String EXTENSAO_CSV = ".csv";

    /**
     * Cria uma instância de CategoriesReader de acordo com o formato do ficheiro a ler.
     *
     * @param filename Nome do ficheiro a ler
     * @return uma instância de CategoriesReader
     */
    public static CategoriesReader create(String filename) {
        if (filename.endsWith(EXTENSAO_CSV)) {
            return new CSVCategoriasReader(filename);
        }
        return null;
    }

    /**
     * Esconde o construtor privado
     */
    private CategoriasReaderFactory() {
    }
}
