/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.Category;
import cdioil.domain.Product;
import cdioil.domain.SKU;
import static cdioil.files.FileReader.readFile;
import cdioil.files.InvalidFileFormattingException;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
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
     * Number of identifiers (columns) in the CSV file.
     */
    private static final int NUM_IDENTIFIERS = 5;
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
     * Categories Identifier.
     */
    private static final String CATEGORY_IDENTIFIER = "ClasseID";
    /**
     * Code Identifier.
     */
    private static final String SKU_IDENTIFIER = "ProdutoID";
    /**
     * Products Identifier.
     */
    private static final String PRODUCT_IDENTIFIER = "Designacao";
    /**
     * Unit Identifier.
     */
    private static final String UNIT_IDENTIFIER = "Unidade";
    /**
     * Quantity Identifier.
     */
    private static final String QUANTITY_IDENTIFIER = "Quantidade";
    /**
     * Path Identifier.
     */
    private static final String PATH_IDENTIFIER = "-";
    /**
     * The number of cells skipped in order to reach the start of a new question in a file with questions relative to categories.
     */
    private static final int CATEGORIES_FILE_OFFSET = 1;
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
        
         MarketStructureRepositoryImpl marketStructureRepository = new MarketStructureRepositoryImpl();
         

        int numLines = fileContent.size();

        for (int i = IDENTIFIERS_LINE + 1; i < numLines; i++) {

            String[] currentLine = fileContent.get(i).split(SPLITTER);
            if (currentLine.length > 0) { //Doesn't read empty lines

                try {
                    currentLine[0] = currentLine[0].replace('"', ' ').trim();
                    String path = createProductPath(currentLine[0].trim());
                    List<Category> list = marketStructureRepository.findCategoriesByPathPattern(path);
                    if (isPathProductValid(path) && list.size() == 1) {
                        Product product = createProduct(currentLine, CATEGORIES_FILE_OFFSET);
                        if (product != null && marketStructureRepository.findIfProductExist(product.productName())) {
                            if (!readProducts.containsKey(path)) {
                                List<Product> newList = new LinkedList<>();
                                newList.add(product);
                                readProducts.put(path, newList);
                            } else {
                                readProducts.get(path).add(product);
                            }
                        }else{
                            System.out.println("O produto da linha " + i + " já existe na estrutra mercadológica.");
                        }
                    } else {
                        System.out.println("A categoria na linha " + i + 
                                " não é folha, logo não pode ser adicionado um produto.");
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("O formato dos produtos é inválido na linha " + i + ".");
                }
            }
        }

        return readProducts;
    }

    public String createProductPath(String path) {

        
        StringBuilder sb = new StringBuilder();
        
        String DC = path.charAt(0)+ "" + path.charAt(1);
        sb.append(DC).append(DC_IDENTIFIER);
        
        String UN = path.charAt(2)+ "" + path.charAt(3);
        sb.append(PATH_IDENTIFIER).append(UN).append(UN_IDENTIFIER);

        String CAT = path.charAt(4)+ "" + path.charAt(5) + ""+ path.charAt(6) + ""+ path.charAt(7);
        sb.append(PATH_IDENTIFIER).append(CAT).append(CAT_IDENTIFIER);

        String SCAT = path.charAt(8) + ""+ path.charAt(9);
        int scat = Integer.parseInt(SCAT);
        sb.append(PATH_IDENTIFIER).append(scat).append(SCAT_IDENTIFIER);

        String UB = path.charAt(10) + ""+  path.charAt(11);
        int ub = Integer.parseInt(UB);
        sb.append(PATH_IDENTIFIER).append(ub).append(UB_IDENTIFIER);
        
        return sb.toString();
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

        SKU sku = new SKU(currentLine[offset].trim());
        String productName = currentLine[offset + 1].trim();
        String quantity = currentLine[offset + 2].trim();

        return new Product(productName, sku,quantity );
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

        return ((line.length == NUM_IDENTIFIERS)
                && line[0].contains(CATEGORY_IDENTIFIER)
                && line[1].trim().equalsIgnoreCase(SKU_IDENTIFIER)
                && line[2].trim().equalsIgnoreCase(PRODUCT_IDENTIFIER)
                && line[3].trim().equalsIgnoreCase(QUANTITY_IDENTIFIER)
                && line[4].trim().equalsIgnoreCase(UNIT_IDENTIFIER));

    }
}
