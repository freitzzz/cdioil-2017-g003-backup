package cdioil.application.utils;

import cdioil.domain.Category;
import cdioil.domain.Product;
import cdioil.domain.SKU;
import static cdioil.files.FileReader.readFile;
import cdioil.files.FileWriter;
import cdioil.files.InvalidFileFormattingException;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Imports products from a CSV file.
 *
 * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 */
public class CSVProductsReader implements ProductsReader {

    //Spliters and characters
    /**
     * Semicolon used for splitting data within the file.
     */
    private static final String FILE_SPLITTER = ";";

    /**
     * Separator of the path.
     */
    private static final String PATH_SPLITTER = "-";

    /**
     * Inverted commas that may appear as trash in the file.
     */
    private static final Character INVERTED_COMMAS = '"';

    /**
     * Question mark that may appear as trash in the file.
     */
    private static final Character QUESTION_MARK = '?';

    //Category related constants
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
     * Scale used in the paths of the MarketStructure.
     */
    private static final String CATEGORIES_SCALE = "[0-9]+";

    /**
     * Regular expression to validate the path of the Category in the Market Structure.
     */
    private static final String PATH_REGEX = CATEGORIES_SCALE + DC_IDENTIFIER + PATH_SPLITTER + CATEGORIES_SCALE + UN_IDENTIFIER + PATH_SPLITTER
            + CATEGORIES_SCALE + CAT_IDENTIFIER + PATH_SPLITTER + CATEGORIES_SCALE + SCAT_IDENTIFIER + PATH_SPLITTER + CATEGORIES_SCALE + UB_IDENTIFIER;

    //Offsets
    /**
     * Number of the line that contains the identifiers of the columns.
     */
    private static final int IDENTIFIERS_LINE = 0;
    /**
     * Number of expected identifiers (columns) in the CSV file.
     */
    private static final int NUM_IDENTIFIERS = 4;

    /**
     * Number of cells skipped in order to reach the start of a product.
     */
    private static final int PRODUCT_OFFSET = 1;

    //Header fields
    /**
     * Categories identifier.
     */
    private static final String CATEGORY_IDENTIFIER = "ClasseID";

    /**
     * SKU identifier.
     */
    private static final String SKU_IDENTIFIER = "ProdutoID";

    /**
     * Product identifier.
     */
    private static final String PRODUCT_IDENTIFIER = "Designacao";

    /**
     * Quantity (per unit) identifier.
     */
    private static final String QUANTITY_IDENTIFIER = "QuantidadeUnidade";

    /**
     * Line identifier.
     */
    private static final String LINE_IDENTIFIER = "Linha";

    /**
     * Cause identifier.
     */
    private static final String CAUSE_IDENTIFIER = "Razão";

    //Files
    /**
     * File being read.
     */
    private final File file;

    /**
     * File to export.
     */
    private final File logFile;

    /**
     * Name of the logger file.
     */
    private static final String LOG_FILENAME = "Logger.csv";

    /**
     * Message added to the file content if the path of the category is not valid.
     */
    private static final String INVALID_CATEGORY_PATH_MESSAGE = "Caminho da categoria não válido!";

    /**
     * Message added to the file content if the category is not a leaf in the market strucure.
     */
    private static final String NOT_LEAF_CATEGORY_MESSAGE = "Categoria não folha, logo não é possível adicionar um produto!";


    /**
     * Message displayed to the user if the file formatting is not recognized.
     */
    private static final String INVALID_FILE_FORMATTING_MESSAGE = "Unrecognized file formatting";

    /**
     * Message displayed to the user if the line formatting is not valid.
     */
    private static final String INVALID_LINE_FORMATTING_MESSAGE = "O formato dos produtos é inválido na linha ";

    /**
     * Map that will hold all already existent products for the user to decide if they need to be updated or not.
     */
    private final Map<Category, List<Product>> existentProducts;

    /**
     * Creates an instance of CSVProductsReader, receiving the name of the file to read.
     *
     * @param filename Name of the file to read
     * @param existentProducts Map that will hold all already existent products
     */
    public CSVProductsReader(String filename, Map<Category, List<Product>> existentProducts) {
        this.file = new File(filename);
        this.logFile = new File(LOG_FILENAME);
        this.existentProducts = existentProducts;
    }

    /**
     * Reads a products from a CSV file.
     *
     * @return Map with the categories and list of products associated with them
     */
    @Override
    public Map<Category, List<Product>> readProducts() {

        List<String> fileContent = readFile(file);
        if (fileContent == null) {
            return null;
        }
        if (!isFileValid(fileContent)) {
            throw new InvalidFileFormattingException(INVALID_FILE_FORMATTING_MESSAGE);
        }

        Map<String, List<Product>> readProducts = new HashMap<>();
        MarketStructureRepositoryImpl marketStructureRepository = new MarketStructureRepositoryImpl();
        int numLines = fileContent.size();

        Map<Integer, Integer> invalidProducts = new HashMap<>();

        for (int i = IDENTIFIERS_LINE + 1; i < numLines; i++) {

            String[] currentLine = fileContent.get(i).split(FILE_SPLITTER);
            if (currentLine.length > 0) { //Doesn't read empty lines

                try {
                    currentLine[0] = currentLine[0].replace(INVERTED_COMMAS, ' ').trim();
                    String path = createProductPath(currentLine[0].trim());
                    List<Category> list = marketStructureRepository.findCategoriesByPathPattern(path);
                    if (isPathProductValid(path) && list.size() == 1) {
                        Product product = createProduct(currentLine, PRODUCT_OFFSET);
                        if (product != null && marketStructureRepository.findIfProductExists(product.productName())) {
                            if (!readProducts.containsKey(path)) { // New products
                                List<Product> newList = new LinkedList<>();
                                newList.add(product);
                                readProducts.put(path, newList);
                            } else {
                                readProducts.get(path).add(product);
                            }
                        } else { // Already existent products
                            if (product != null) {
                                if (existentProducts.containsKey(path)) {
                                    existentProducts.get(path).add(product);
                                } else {
                                    List<Product> listE = new LinkedList<>();
                                    listE.add(product);
                                   // existentProducts.put(path, listE);
                                }
                            }
                        }
                    } else {
                        if (!isPathProductValid(path)) {
                            invalidProducts.put(i, 1);
                        }
                        if (list.size() != 1) {
                            invalidProducts.put(i, 2);
                        }
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println(INVALID_LINE_FORMATTING_MESSAGE + i);
                }
            }
        }
        if (!invalidProducts.isEmpty()) {
            addInvalidProdutctsToLogFile(invalidProducts);
        }

        return new HashMap<Category,List<Product>>();
    }

    /**
     * Recreates the path of the product in the market structure.
     *
     * @param path String that represents the path of the product
     * @return the path of the product in the market structure
     */
    private String createProductPath(String path) {
        StringBuilder sb = new StringBuilder();

        String dc = Character.toString(path.charAt(0)) + Character.toString(path.charAt(1));
        sb.append(dc).append(DC_IDENTIFIER);

        String un = Character.toString(path.charAt(2)) + Character.toString(path.charAt(3));
        sb.append(PATH_SPLITTER).append(un).append(UN_IDENTIFIER);

        String cat = Character.toString(path.charAt(4)) + Character.toString(path.charAt(5)) + Character.toString(path.charAt(6)) + Character.toString(path.charAt(7));
        sb.append(PATH_SPLITTER).append(cat).append(CAT_IDENTIFIER);

        String scat = Character.toString(path.charAt(8)) + Character.toString(path.charAt(9));
        int scatVal = Integer.parseInt(scat);
        sb.append(PATH_SPLITTER).append(scatVal).append(SCAT_IDENTIFIER);

        String ub = Character.toString(path.charAt(10)) + Character.toString(path.charAt(11));
        int ubVal = Integer.parseInt(ub);
        sb.append(PATH_SPLITTER).append(ubVal).append(UB_IDENTIFIER);

        return sb.toString();
    }

    /**
     * Checks if the path of the product in the market structure is valid.
     *
     * @param path String to check (represents the path of the product)
     * @return true, if the path is valid. Otherwise, returns false
     */
    private boolean isPathProductValid(String path) {
        return path != null
                && (path.matches(PATH_REGEX));
    }

    /**
     * Reads a product from a CSV file.
     *
     * @param currentLine Line of the file currently being read
     * @param offset Number of cells skipped to reach the start of a product
     * @return the instance of Product
     */
    private Product createProduct(String[] currentLine, int offset) {
        SKU sku = new SKU(currentLine[offset].trim());
        String productName = currentLine[offset + 1].trim();
        String quantity = currentLine[offset + 2].trim();

        return new Product(productName, sku, quantity);
    }

    /**
     * Checks if the content of the file is valid - not null and has all the expected identifiers properly splitted.
     *
     * @param fileContent All the lines of the file
     * @return true, if the content of the file is valid. Otherwise, returns false
     */
    private boolean isFileValid(List<String> fileContent) {
        if (fileContent == null) {
            return false;
        }
        String[] line = fileContent.get(IDENTIFIERS_LINE).split(FILE_SPLITTER);

        line[0] = line[0].replace(QUESTION_MARK, ' ').trim();

        return ((line.length == NUM_IDENTIFIERS)
                && line[0].trim().contains(CATEGORY_IDENTIFIER)
                && line[1].trim().equalsIgnoreCase(SKU_IDENTIFIER)
                && line[2].trim().equalsIgnoreCase(PRODUCT_IDENTIFIER)
                && line[3].trim().contains(QUANTITY_IDENTIFIER));
    }

    /**
     * Adds all invalid lines from the imported file to a log file.
     *
     * The keys of the map represent the invalid lines. The mapped values represent the cause for that line being invalid.
     *
     * If value = 1, the line is invalid because the category path does not exist in the market structure; If value = 2, the line is invalid because the category is not a leaf and therefore, cannot hold products; If value = 3, the line is invalid because the product already exists in the market structure.
     *
     * @param invalidLines Map with the invalid lines of the file
     */
    private void addInvalidProdutctsToLogFile(Map<Integer, Integer> invalidLines) {
        List<String> fileContent = new ArrayList<>();
        String header = LINE_IDENTIFIER + FILE_SPLITTER + CAUSE_IDENTIFIER;
        fileContent.add(header);
        for (Map.Entry<Integer, Integer> entry : invalidLines.entrySet()) {
            int line = entry.getKey();
            int cause = invalidLines.get(line);
            if (cause == 1) {
                fileContent.add(line + FILE_SPLITTER + INVALID_CATEGORY_PATH_MESSAGE);
            }
            if (cause == 2) {
                fileContent.add(line + FILE_SPLITTER + NOT_LEAF_CATEGORY_MESSAGE);
            }
           }
        FileWriter.writeFile(logFile, fileContent);
    }
}
