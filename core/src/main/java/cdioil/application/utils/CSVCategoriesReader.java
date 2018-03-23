/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.Category;
import cdioil.domain.EstruturaMercadologica;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import static cdioil.application.utils.FileReader.readFile;

/**
 * Imports Categories from .csv files.
 *
 * @author Rita Gonçalves (1160912)
 */
public class CSVCategoriesReader implements CategoriesReader {

    /**
     * File to read.
     */
    private final File file;

    /**
     * Splitter of the columns of the file.
     */
    private static final String SPLITTER = ";";

    /**
     * Number of the line that contains the identifiers of the columns.
     */
    private static final int IDENTIFIERS_LINE = 0;

    /**
     * Number of identifiers (columns) in the CSV file.
     */
    private static final int NUMBER_OF_IDENTIFIERS = 10;

    /**
     * Creates an instance of CSVCategoriesReader, receiving the name of the file to read.
     *
     * @param filename Name of the file to read
     */
    public CSVCategoriesReader(String filename) {
        this.file = new File(filename);
    }

    /**
     * Imports Categories from a .csv file.
     *
     * @return List with the Categories that were read. Null if the file is not valid
     */
    @Override
    public List<Category> readCategories() {
        List<String> fileContent = readFile(file);

        if (!isFileValid(fileContent)) {
            return null;
        }

        List<Category> categories = new LinkedList<>();

        EstruturaMercadologica em = new EstruturaMercadologica();

        for (int i = IDENTIFIERS_LINE + 1; i < fileContent.size(); i++) {
            String[] line = fileContent.get(i).split(SPLITTER);
            if (line.length != 0) { //Doesn't read empty lines
                try {

                    Category c = new Category(line[1], line[0]
                            + Category.Sufixes.SUFIX_DC);
                    boolean added = em.adicionarCategoriaRaiz(c);
                    if (added) {
                        categories.add(c);
                    }

                    Category c1 = new Category(line[3], line[2]
                            + Category.Sufixes.SUFIX_UN);
                    added = em.adicionarCategoria(c, c1);
                    if (added) {
                        categories.add(c1);
                    }

                    Category c2 = new Category(line[5], line[4]
                            + Category.Sufixes.SUFIX_CAT);
                    added = em.adicionarCategoria(c1, c2);
                    if (added) {
                        categories.add(c2);
                    }

                    Category c3 = new Category(line[7], line[6]
                            + Category.Sufixes.SUFIX_SCAT);
                    added = em.adicionarCategoria(c2, c3);
                    if (added) {
                        categories.add(c3);
                    }

                    Category c4 = new Category(line[9], line[8]
                            + Category.Sufixes.SUFIX_UB);
                    added = em.adicionarCategoria(c3, c4);
                    if (added) {
                        categories.add(c4);
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("O formato das Categorias inválido na linha " + i + ".");
                }
            }
        }
        return categories;
    }

    /**
     * Checks if the content of the file is valid - not null and has all the expected identifiers properly splitted.
     *
     * @param fileContent All the lines of the file
     * @return true, if the content is valid. Otherwise, returns false
     */
    protected boolean isFileValid(List<String> fileContent) {
        if (fileContent == null) {
            return false;
        }
        return fileContent.get(IDENTIFIERS_LINE).split(SPLITTER).length == NUMBER_OF_IDENTIFIERS;
    }
}
