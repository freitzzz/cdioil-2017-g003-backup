package cdioil.application.utils;

import cdioil.domain.Category;
import cdioil.domain.MarketStructure;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import cdioil.files.FileReader;
import cdioil.files.InvalidFileFormattingException;

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
    private final List<Category> lc;

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
     * Capacity of the StringBuilder that stores the path of the Category.
     */
    private static final int CAPACITY = 128;

    /**
     * Unrecognized file format Message content
     */
    private static final String UNRECOGNIZED_FILE_FORMAT = "Unrecognized file formatting";

    /**
     * Creates an instance of CSVCategoriesReader, receiving the name of the file to read.
     *
     * @param file File to read
     */
    public CSVCategoriesReader(File file) {
        this.file = file;
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
            throw new InvalidFileFormattingException(UNRECOGNIZED_FILE_FORMAT);
        }

        MarketStructure em = new MarketStructure();

        for (int i = IDENTIFIERS_LINE + 1; i < fileContent.size(); i++) {
            String[] line = fileContent.get(i).split(SPLITTER);
            StringBuilder path = new StringBuilder(CAPACITY);

            if (line.length != 0) { //Doesn't read empty lines
                try {
                    path.append(line[0]).
                            append(Category.Sufixes.SUFIX_DC);
                    Category c = new Category(line[1], path.toString().trim());
                    if (em.addCategory(c)) {
                        lc.add(c);
                    }

                    path.append("-").append(line[2]).
                            append(Category.Sufixes.SUFIX_UN);
                    Category c1 = new Category(line[3], path.toString().trim());
                    if (em.addCategory(c1)) {
                        lc.add(c1);
                    }

                    path.append("-").append(line[4]).
                            append(Category.Sufixes.SUFIX_CAT);
                    Category c2 = new Category(line[5], path.toString().trim());
                    if (em.addCategory(c2)) {
                        lc.add(c2);
                    }

                    path.append("-").append(line[6]).
                            append(Category.Sufixes.SUFIX_SCAT);
                    Category c3 = new Category(line[7], path.toString().trim());
                    if (em.addCategory(c3)) {
                        lc.add(c3);
                    }

                    path.append("-").append(line[8]).
                            append(Category.Sufixes.SUFIX_UB);
                    Category c4 = new Category(line[9], path.toString().trim());
                    if (em.addCategory(c4)) {
                        lc.add(c4);
                    }

                } catch (IllegalArgumentException ex) {
                    System.out.println("The category in the line " + i + " is not valid.");
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
    public int getNumberOfCategoriesRead() {
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
