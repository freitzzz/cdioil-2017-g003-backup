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
     * File to export.
     */
    private final File fileExp;
    /**
     * List with the product
     */
    private final Map<String, List<Product>> existsProducts;
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
    private static final int NUM_IDENTIFIERS = 4;
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
    private static final String UNIT_IDENTIFIER = "QuantidadeUnidade";
    /**
     * Path Identifier.
     */
    private static final String PATH_IDENTIFIER = "-";
    /**
     * The number of cells skipped in order to reach the start of a new question in a file with questions relative to categories.
     */
    private static final int CATEGORIES_FILE_OFFSET = 1;
    /**
     * Scale used in the paths of the MarketStructure.
     */
    private static final String SCALE = "[0-9]+";
    /**
     * Separator of the path.
     */
    private static final String SEPARATOR = "-";
    /**
     * Regular expression to validate the path of the Category in the Market Structure.
     */
    private static final String PATH_REGEX = SCALE + DC_IDENTIFIER + SEPARATOR + SCALE + UN_IDENTIFIER + SEPARATOR
            + SCALE + CAT_IDENTIFIER + SEPARATOR + SCALE + SCAT_IDENTIFIER + SEPARATOR + SCALE + UB_IDENTIFIER;

    /**
     * Creates an instance of CSVProductsReader, receiving the name of the file to read.
     *
     * @param filename Name of the file to read
     * @param fileExport Name of the file to export
     * @param existsProducts List of the products
     */
    public CSVProductsReader(String filename, String fileExport, Map<String, List<Product>> existsProducts) {
        this.file = new File(filename);
        this.fileExp = new File(fileExport);
        this.existsProducts = existsProducts;
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

        Map<Integer, Integer> invalidProducts = new HashMap<>();

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
                        } else {
                            if (product != null) {
                                if (existsProducts.containsKey(path)) {
                                    existsProducts.get(path).add(product);
                                } else {
                                    List<Product> listE = new LinkedList<>();
                                    listE.add(product);
                                    existsProducts.put(path, listE);
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
                    System.out.println("O formato dos produtos é inválido na linha " + i + ".");
                }
            }
        }
        if (!invalidProducts.isEmpty()) {
            notImportedProducstFile(invalidProducts);
        }
        return readProducts;
    }

    /**
     * Create the path of the product
     *
     * @param path String which will be converted into category path
     * @return the path of the category
     */
    private String createProductPath(String path) {

        StringBuilder sb = new StringBuilder();

        String dc = path.charAt(0) + "" + path.charAt(1);
        sb.append(dc).append(DC_IDENTIFIER);

        String un = path.charAt(2) + "" + path.charAt(3);
        sb.append(PATH_IDENTIFIER).append(un).append(UN_IDENTIFIER);

        String cat = path.charAt(4) + "" + path.charAt(5) + "" + path.charAt(6) + "" + path.charAt(7);
        sb.append(PATH_IDENTIFIER).append(cat).append(CAT_IDENTIFIER);

        String scat = path.charAt(8) + "" + path.charAt(9);
        int scat_val = Integer.parseInt(scat);
        sb.append(PATH_IDENTIFIER).append(scat_val).append(SCAT_IDENTIFIER);

        String ub = path.charAt(10) + "" + path.charAt(11);
        int ub_val = Integer.parseInt(ub);
        sb.append(PATH_IDENTIFIER).append(ub_val).append(UB_IDENTIFIER);

        return sb.toString();
    }

    /**
     * Checks if the path of the Product is valid.
     *
     * @param path String to check
     * @return true, if the path is valid. Otherwise, returns false
     */
    private boolean isPathProductValid(String path) {
        return path != null
                && (path.matches(PATH_REGEX));
    }

    /**
     * Reads a product from a CSV file.
     *
     * @param currentLine the line currently being read in the file
     * @param offset number of cells skipped to reach the start of a product
     * @return product
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
     * @return true, if the content is valid. Otherwise, returns false
     */
    private boolean isProductsFileValid(List<String> fileContent) {
        if (fileContent == null) {
            return false;
        }
        String[] line = fileContent.get(IDENTIFIERS_LINE).split(SPLITTER);

        line[0] = line[0].replace('?', ' ').trim();

        return ((line.length == NUM_IDENTIFIERS)
                && line[0].trim().contains(CATEGORY_IDENTIFIER)
                && line[1].trim().equalsIgnoreCase(SKU_IDENTIFIER)
                && line[2].trim().equalsIgnoreCase(PRODUCT_IDENTIFIER)
                && line[3].trim().contains(UNIT_IDENTIFIER));

    }

    /**
     * Method that exports no imported products successfully
     *
     */
    private void notImportedProducstFile(Map<Integer, Integer> invalidProducts) {
        List<String> fileContent = new ArrayList<>();
        String header = "Linha" + SPLITTER + "Razao";
        fileContent.add(header);
        for (Integer line : invalidProducts.keySet()) {
            if (invalidProducts.get(line) == 1) {
                fileContent.add(line + SPLITTER + "Caminho da categoria não válido!");
            }
            if (invalidProducts.get(line) == 2) {
                fileContent.add(line + SPLITTER + "Categoria não folha, logo não é possível adicionar um produto!");
            }
            if (invalidProducts.get(line) == 3) {
                fileContent.add(line + SPLITTER + "Produto já existe!");
            }
        }
        FileWriter.writeFile(fileExp, fileContent);
    }
}
