package cdioil.application.utils;

import cdioil.domain.Category;
import cdioil.domain.MarketStructure;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import cdioil.files.FileReader;

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
     * List with the Categories that were read.
     */
    private List<Category> lc;

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
        lc = new LinkedList<>();
    }

    /**
     * Imports Categories from a .csv file.
     *
     * @return List with the Categories that were read. Null if the file is not valid
     */
    @Override
    public MarketStructure readCategories() {
        List<String> fileContent = FileReader.readFile(file);

        if (!isFileValid(fileContent)) {
            return null;
        }

        MarketStructure em = new MarketStructure();

        for (int i = IDENTIFIERS_LINE + 1; i < fileContent.size(); i++) {
            String[] line = fileContent.get(i).split(SPLITTER);
            if (line.length != 0) { //Doesn't read empty lines
                try {
                    String path = line[0] + Category.Sufixes.SUFIX_DC;
                    Category c = new Category(line[1], path);
                    boolean added = em.addCategory(c);
                    if(added) lc.add(c);

                    path = path + "-" + line[2] + Category.Sufixes.SUFIX_UN;
                    Category c1 = new Category(line[3], path);
                    added = em.addCategory(c1);
                    if(added) lc.add(c1);

                    
                    path = path + "-" + line[4] + Category.Sufixes.SUFIX_CAT;
                    Category c2 = new Category(line[5], path);
                    added = em.addCategory(c2);
                    if(added) lc.add(c2);
                    
                    path = path + "-" + line[6] + Category.Sufixes.SUFIX_SCAT;
                    Category c3 = new Category(line[7], path);
                    added = em.addCategory(c3);
                    if(added) lc.add(c3);
                    
                    path = path + "-" + line[8] + Category.Sufixes.SUFIX_UB;
                    Category c4 = new Category(line[9], path);
                    added = em.addCategory(c4);
                    if(added) lc.add(c4);
                    
                } catch (IllegalArgumentException ex) {
                    System.out.println("O formato das Categorias inválido na linha " + i + ".");
                }
            }
        }
        return em;
    }

    /**
     * Returns the number of Categories in the list of Categories.
     * 
     * @return the number of Categories that were read
     */
    @Override
    public int getNumberOfCategoriesRead(){
        return lc.size();
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
