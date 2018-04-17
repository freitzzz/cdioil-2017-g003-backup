/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.EAN;
import cdioil.domain.Product;
import static cdioil.files.FileReader.readFile;
import cdioil.files.InvalidFileFormattingException;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class used for importing Products from a file with the .csv extension.
 *
 * @author Ana Guerra (1161191)
 */
public class CSVProductsReader implements ProductsReader {

    /**
     * File being read.
     */
    private final File file;
    /**
     * Character used for splitting data within the file.
     */
    private static final String SPLITTER = ";";
    /**
     * Number of the line that contains the identifiers of the columns (the first one in this case).
     */
    private static final int IDENTIFIERS_LINE = 0;
    /**
     * Max number of identifiers (columns) in the CSV file.
     */
    private static final int MAX_NUM_IDENTIFIERS = 7;
    /**
     * Min number of identifiers (columns) in the CSV file.
     */
    private static final int MIN_NUM_IDENTIFIERS = 8;
    /**
     * Hashtag Identifier.
     */
    private static final String HASHTAG_IDENTIFIER = "#";
    /**
     * Categories' DC Identifier.
     */
    private static final String DC_IDENTIFIER = "DC";
    /**
     * Categories' UN Identifier.
     */
    private static final String UN_IDENTIFIER = "UN";
    /**
     * Categories' CAT Identifier.
     */
    private static final String CAT_IDENTIFIER = "CAT";
    /**
     * Categories' SCAT Identifier.
     */
    private static final String SCAT_IDENTIFIER = "SCAT";
    /**
     * Categories' UB Identifier.
     */
    private static final String UB_IDENTIFIER = "UB";
    /**
     * ProductSs Identifier.
     */
    private static final String PRODUCT_IDENTIFIER = "Produto";
    /**
     * Codigo Identifier.
     */
    private static final String EAN_IDENTIFIER = "Codigo";
    /**
     * Path Identifier.
     */
    private static final String PATH_IDENTIFIER = "-";
    /**
     * The number of cells skipped in order to reach the start of a new question in a file with questions relative to categories.
     */
    private static final int CATEGORIES_FILE_OFFSET = 5;
    /**
     * The number of cells skipped in order to reach the start of a new question in a file with independent questions.
     */
    private static final int INDEPENDENT_FILE_OFFSET = 0;

    /**
     * Regular expression to validate the path of the Category in the Market Structure.
     */
    private final static String PATH_REGEX = "[0-9]+" + DC_IDENTIFIER + "-[0-9]+" + UN_IDENTIFIER + "-[0-9]+"
            + CAT_IDENTIFIER + "-[0-9]+" + SCAT_IDENTIFIER + "-[0-9]+" + UB_IDENTIFIER;

    /**
     * Creates an instance of CSVProductsReader, receiving the name of the file to read.
     *
     * @param filename Name of the file to read
     */
    public CSVProductsReader(String filename) {
        this.file = new File(filename);
    }

    /**
     * Reads a products from a CSV file.
     *
     * @return Map with the categories and list of products associated with them
     */
    @Override
    public Map<String, List<Product>> readProducts() {

        List<String> fileContent = readFile(file);

        if (fileContent == null) {
            return null;
        }

        if (!isProductsFileValid(fileContent)) {
            throw new InvalidFileFormattingException("Unrecognized file formatting");
        }
        Map<String, List<Product>> readProducts = new HashMap<>();

        int numLines = fileContent.size();

        for (int i = IDENTIFIERS_LINE + 1; i < numLines; i++) {

            String[] currentLine = fileContent.get(i).split(SPLITTER);
            if (currentLine.length > 0) { //Doesn't read empty lines

                try {
                    String DC = currentLine[0].trim();

                    StringBuilder sb = new StringBuilder(DC);
                    sb.append(DC_IDENTIFIER);
                    String UN = currentLine[1].trim();

                    sb.append(PATH_IDENTIFIER).append(UN).append(UN_IDENTIFIER);

                    String CAT = currentLine[2].trim();

                    sb.append(PATH_IDENTIFIER).append(CAT).append(CAT_IDENTIFIER);

                    String SCAT = currentLine[3].trim();

                    sb.append(PATH_IDENTIFIER).append(SCAT).append(SCAT_IDENTIFIER);

                    String UB = currentLine[4].trim();

                    sb.append(PATH_IDENTIFIER).append(UB).append(UB_IDENTIFIER);

                    String path = sb.toString();

                    if (!isPathProductValid(path)) {
                        throw new InvalidFileFormattingException("Unrecognized path of product formatting");
                    }
                    Product product = createProduct(currentLine, CATEGORIES_FILE_OFFSET);
                    if (product != null) {
                        if (!readProducts.containsKey(path)) {
                            List<Product> newList = new LinkedList<>();
                            newList.add(product);
                            readProducts.put(path, newList);
                        } else {
                            readProducts.get(path).add(product);
                        }
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("O formato dos produtos é inválido na linha " + i + ".");
                }
            }
        }

        return readProducts;
    }

    /**
     * Checks if the path of the Product is valid.
     *
     * @param path String to check
     * @return true, if the path is valid. Otherwise, returns false
     */
    public boolean isPathProductValid(String path) {
        return path != null
                && (path.matches(PATH_REGEX));
    }

    /**
     * Reads a product from a CSV file.
     *
     * @param currentLine the line currently being read in the file
     * @param offset number of cells skipped to reach the start of a question
     * @return product
     */
    private Product createProduct(String[] currentLine, int offset) {

        String productName = currentLine[offset].trim();
        EAN ean = new EAN(currentLine[offset + 1].trim());

        return new Product(productName, ean);
    }

    /**
     * Checks if the content of the file is valid - not null and has all the expected identifiers properly splitted.
     *
     * @param fileContent All the lines of the file
     * @return true, if the content is valid. Otherwise, returns false
     */
    private boolean isProductsFileValid(List<String> fileContent) {
        if (fileContent == null) {
            return false;
        }
        String[] line = fileContent.get(IDENTIFIERS_LINE).split(SPLITTER);

        line[0] = line[0].replace('?', ' ').trim();

        return ((line.length == MAX_NUM_IDENTIFIERS)
                && line[0].contains(HASHTAG_IDENTIFIER + DC_IDENTIFIER)
                && line[1].equalsIgnoreCase(HASHTAG_IDENTIFIER + UN_IDENTIFIER)
                && line[2].equalsIgnoreCase(HASHTAG_IDENTIFIER + CAT_IDENTIFIER)
                && line[3].equalsIgnoreCase(HASHTAG_IDENTIFIER + SCAT_IDENTIFIER)
                && line[4].equalsIgnoreCase(HASHTAG_IDENTIFIER + UB_IDENTIFIER)
                && line[5].equalsIgnoreCase(PRODUCT_IDENTIFIER)
                && line[6].equalsIgnoreCase(EAN_IDENTIFIER));

    }
}
