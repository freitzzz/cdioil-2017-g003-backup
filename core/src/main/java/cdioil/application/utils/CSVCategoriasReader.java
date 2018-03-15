/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.Categoria;
import cdioil.domain.EstruturaMercadologica;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import static cdioil.application.utils.FileReader.readFile;

/**
 * Permite a leitura de Categorias a partir de ficheiros .csv.
 *
 * @author Rita Gonçalves (1160912)
 */
public class CSVCategoriasReader implements CategoriasReader {

    /**
     * Nome do ficheiro a ler.
     */
    private String filename;

    /**
     * Divisória entre as colunas do ficheiro.
     */
    private static final String SPLITTER = ";";

    /**
     * Constrói uma instância de CSVCategoriasReader com o nome do ficheiro a ler.
     *
     * @param filename Nome do ficheiro a ler
     */
    public CSVCategoriasReader(String filename) {
        this.filename = filename;
    }

    /**
     * Importa Categorias de um ficheiro com formato .csv.
     *
     * @return Lista com as Categorias lidas
     */
    @Override
    public List<Categoria> lerFicheiro() {
        List<String> conteudoFicheiro = readFile(new File(filename));

        if (conteudoFicheiro == null) {
            return null;
        }

        System.out.println(conteudoFicheiro.size());

        List<Categoria> categorias = new LinkedList<>();

        EstruturaMercadologica em = new EstruturaMercadologica();

        boolean header = true; //Para não ser lido o cabeçalho

        for (int i = 0; i < conteudoFicheiro.size(); i++) {
            if (!header) {
                String[] line = conteudoFicheiro.get(i).split(SPLITTER);
                if (line.length != 0) { //Termina a leitura quando não existirem mais linhas com informação
                    Categoria pai = new Categoria(line[1], line[0] + "DC");
                    boolean added = em.adicionarCategoriaRaiz(pai);
                    if (added) {
                        categorias.add(pai);
                    }

                    Categoria filha1 = new Categoria(line[3], line[2] + "UN");
                    added = em.adicionarCategoria(pai, filha1);
                    if (added) {
                        categorias.add(filha1);
                    }

                    Categoria filha2 = new Categoria(line[5], line[4] + "CAT");
                    added = em.adicionarCategoria(filha1, filha2);
                    if (added) {
                        categorias.add(filha2);
                    }

                    Categoria filha3 = new Categoria(line[7], line[6] + "SCAT");
                    added = em.adicionarCategoria(filha2, filha3);
                    if (added) {
                        categorias.add(filha3);
                    }

                }
            }
            header = false;
        }
        return categorias;
    }
}
