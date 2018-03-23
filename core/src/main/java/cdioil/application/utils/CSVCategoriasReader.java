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
import cdioil.persistence.impl.MarketStructureRepositoryImpl;

/**
 * Permite a leitura de Categorias a partir de ficheiros .csv.
 *
 * @author Rita Gonçalves (1160912)
 */
public class CSVCategoriasReader implements CategoriasReader {

    /**
     * File com o ficheiro a ler.
     */
    private final File file;

    /**
     * Divisória entre as colunas do ficheiro.
     */
    private static final String SPLITTER = ";";

    /**
     * Número da linha onde são identificados os campos a ler (colunas) no
     * ficheiro CSV.
     */
    private static final int LINHA_IDENTIFICADOR = 0;

    /**
     * Número de identificadores (colunas) existentes no ficheiro CSV.
     */
    private static final int NUMERO_IDENTIFICADORES = 10;

    /**
     * Constrói uma instância de CSVCategoriasReader com o nome do ficheiro a
     * ler.
     *
     * @param filename Nome do ficheiro a ler
     */
    public CSVCategoriasReader(String filename) {
        this.file = new File(filename);
    }

    /**
     * Importa Categorias de um ficheiro com formato .csv.
     *
     * @return Lista com as Categorias lidas. Null caso o ficheiro seja inválido
     */
    @Override
    public List<Categoria> lerFicheiro() {
        List<String> conteudoFicheiro = readFile(file);

        if (!isFicheiroValido(conteudoFicheiro)) {
            return null;
        }

        MarketStructureRepositoryImpl repo = new MarketStructureRepositoryImpl();
        
        List<Categoria> categorias = new LinkedList<>();

        EstruturaMercadologica em = new EstruturaMercadologica();
        
        for (int i = LINHA_IDENTIFICADOR + 1; i < conteudoFicheiro.size(); i++) {
            String[] line = conteudoFicheiro.get(i).split(SPLITTER);
            if (line.length != 0) { //Não lê linhas sem informação
                try {

                /*Os identificadores das categorias têm de ser compostos pelo 
                identificador da categoria pai de modo a evitar colisões quando 
                se adiciona novas categorias.*/
                    Categoria pai = new Categoria(line[1].toLowerCase(), line[0] + Categoria.Sufixos.SUFIXO_DC);
                    boolean added = em.adicionarCategoriaRaiz(pai);
                    if (added) {
                        categorias.add(pai);
                    }

                    Categoria filha1 = new Categoria(line[3].toLowerCase(), line[2]
                            + Categoria.Sufixos.SUFIXO_UN);
                    added = em.adicionarCategoria(pai, filha1);
                    if (added) {
                        categorias.add(filha1);
                    }

                    Categoria filha2 = new Categoria(line[5].toLowerCase(), line[4]
                            + Categoria.Sufixos.SUFIXO_CAT);
                    added = em.adicionarCategoria(filha1, filha2);
                    if (added) {
                        categorias.add(filha2);
                    }

                    Categoria filha3 = new Categoria(line[7].toLowerCase(), line[6]
                            + Categoria.Sufixos.SUFIXO_SCAT);
                    added = em.adicionarCategoria(filha2, filha3);
                    if (added) {
                        categorias.add(filha3);
                    }

                    Categoria filha4 = new Categoria(line[9].toLowerCase(), line[8]
                            + Categoria.Sufixos.SUFIXO_UB);
                    added = em.adicionarCategoria(filha3, filha4);
                    if (added) {
                        categorias.add(filha4);
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("O formato das Categorias inválido na linha" + i + ".");
                }
            }
        }
        
        repo.add(em);
        
        return categorias;
    }

    /**
     * Verifica se o conteúdo do ficheiro é válido (se não é null e se o número
     * de colunas é o esperado).
     *
     * @param conteudoFicheiro Todas as linhas do ficheiro
     * @return true, caso o conteúdo seja válido. Caso contrário, retorna false
     */
    protected boolean isFicheiroValido(List<String> conteudoFicheiro) {
        if (conteudoFicheiro == null) {
            return false;
        }
        return conteudoFicheiro.get(LINHA_IDENTIFICADOR).split(SPLITTER).length == NUMERO_IDENTIFICADORES;
    }
}
