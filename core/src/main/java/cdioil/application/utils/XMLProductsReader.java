/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.Category;
import cdioil.domain.Product;
import cdioil.domain.SKU;
import cdioil.files.FileWriter;
import cdioil.files.InputSchemaFiles;
import cdioil.files.ValidatorXML;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Imports products from a XML file.
 *
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 */
public class XMLProductsReader implements ProductsReader {

    //Categories related constants
    /**
     * Separator of the path.
     */
    private static final String PATH_SPLITTER = "-";

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

    /**
     * File to read.
     */
    private final File file;

    /**
     * Logger file with the invalid lines.
     */
    private final File logFile;

    /**
     * Name of the logger file.
     */
    private static final String LOG_FILENAME = "Logger.csv";

    /**
     * Product identifier.
     */
    private static final String ERRONEOUS_CONTENT_IDENTIFIER = "Conteúdo inválido";

    /**
     * Cause identifier.
     */
    private static final String CAUSE_IDENTIFIER = "Causa";

    /**
     * Semicolon used for splitting data within the file.
     */
    private static final String FILE_SPLITTER = ";";

    /**
     * Message added to the file content if the path of the category is not valid.
     */
    private static final String INVALID_CATEGORY_PATH_MESSAGE = "Caminho da categoria não válido!";

    /**
     * Message added to the file content if the category is not a leaf in the market strucure.
     */
    private static final String NOT_LEAF_CATEGORY_MESSAGE = "Categoria não folha, logo não é possível adicionar um produto!";

    /**
     * Map that contains categories as keys and its products as values.
     */
    private final Map<Category, List<Product>> productsReadFromFile;

    /**
     * Map that will hold all already existent products for the user to decide if they need to be updated or not.
     */
    private final Map<Category, List<Product>> repeatedProducts;

    /**
     * Schema file (XSD) used for validating the input file.
     */
    private static final File SCHEMA_FILE = new File(InputSchemaFiles.LOCALIZATION_SCHEMA_PATH);

    /**
     * Builds an instance of XMLProductsReader, receiving the path of the file to read.
     *
     * @param filePath Path of the file
     * @param repeatedProducts Map that will hold all already existent products for the user to decide if they need to be updated or not
     */
    public XMLProductsReader(String filePath, Map<Category, List<Product>> repeatedProducts) {
        this.file = new File(filePath);
        this.logFile = new File(LOG_FILENAME);
        this.productsReadFromFile = new HashMap<>();
        this.repeatedProducts = repeatedProducts;
    }

    /**
     * Reads the content of the received file.
     *
     * @return a map with categories as keys and all products that were read as values
     */
    @Override
    public Map<Category, List<Product>> readProducts() {
        if (isFileValid()) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            try {
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);

                doc.getDocumentElement().normalize();

                Element rootElement = doc.getDocumentElement(); //gets the root element (list with all products)

                NodeList products = rootElement.getChildNodes();

                for (int i = 0; i < products.getLength(); i++) {
                    Node product = products.item(i);
                    if (product.getNodeType() == Node.ELEMENT_NODE) {
                        Element productElement = (Element) product;
                        String categoryPath = recreateProductPath(productElement.getElementsByTagName("ClasseID").item(0).getTextContent());

                        if (isProductPathValid(categoryPath)) {
                            SKU sku = new SKU(productElement.getElementsByTagName("ProdutoID").item(0).getTextContent());
                            String name = productElement.getElementsByTagName("Designacao").item(0).getTextContent();
                            String quantity = productElement.getElementsByTagName("Quantidade").item(0).getTextContent()
                                    + productElement.getElementsByTagName("Unidade").item(0).getTextContent();

                            Category category = getCategoryByPath(categoryPath);
                            Product p = new Product(name, sku, quantity);
                            if (category != null) {
                                addProductToMap(category, p);
                            } else {
                                addInvalidProdutctsToLogFile(p.productName(), NOT_LEAF_CATEGORY_MESSAGE);
                            }
                        } else {
                            addInvalidProdutctsToLogFile(categoryPath, INVALID_CATEGORY_PATH_MESSAGE);
                        }
                    }
                }
                return productsReadFromFile;
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(XMLProductsReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Recreates the path of the product in the market structure.
     *
     * @param path String that represents the path of the product
     * @return the path of the product in the market structure
     */
    private String recreateProductPath(String path) {
        StringBuilder actualPath = new StringBuilder();

        String dcString = Character.toString(path.charAt(0)) + Character.toString(path.charAt(1));
        int dc = Integer.parseInt(dcString);
        actualPath.append(dc).append(DC_IDENTIFIER);

        String unString = Character.toString(path.charAt(2)) + Character.toString(path.charAt(3));
        int un = Integer.parseInt(unString);
        actualPath.append(PATH_SPLITTER).append(un).append(UN_IDENTIFIER);

        String catString = Character.toString(path.charAt(4)) + Character.toString(path.charAt(5))
                + Character.toString(path.charAt(6)) + Character.toString(path.charAt(7));
        int cat = Integer.parseInt(catString);
        actualPath.append(PATH_SPLITTER).append(cat).append(CAT_IDENTIFIER);

        String scatString = Character.toString(path.charAt(8)) + Character.toString(path.charAt(9));
        int scat = Integer.parseInt(scatString);
        actualPath.append(PATH_SPLITTER).append(scat).append(SCAT_IDENTIFIER);

        String ubString = Character.toString(path.charAt(10)) + Character.toString(path.charAt(11));
        int ub = Integer.parseInt(ubString);
        actualPath.append(PATH_SPLITTER).append(ub).append(UB_IDENTIFIER);
        return actualPath.toString();
    }

    /**
     * Checks if the received file follows the schema's guidelines.
     *
     * @return true, if the file is valid. Otherwise, returns false
     */
    private boolean isFileValid() {
        return ValidatorXML.validateFile(SCHEMA_FILE, file);
    }

    private Category getCategoryByPath(String path) {
        List<Category> categories = new MarketStructureRepositoryImpl().findCategoriesByPathPattern(path);
        if (categories != null && categories.size() == 1 && categories.get(0).categoryPath().equalsIgnoreCase(path)) {
            return categories.get(0);
        }
        return null;
    }

    private void addProductToMap(Category category, Product product) {
        if (product != null) {
            List<Product> products = new LinkedList<>();

            if (!new MarketStructureRepositoryImpl().findIfProductExists(product.productName())) { //Already existent products
                if (!productsReadFromFile.containsKey(category)) {
                    products.add(product);
                    productsReadFromFile.put(category, products);
                } else {
                    productsReadFromFile.get(category).add(product);
                }
            } else { // Non existent products
                if (repeatedProducts.containsKey(category)) {
                    repeatedProducts.get(category).add(product);
                } else {
                    products.add(product);
                    repeatedProducts.put(category, products);
                }
            }
        }
    }

    /**
     * Checks if the path of the product in the market structure is valid.
     *
     * @param path String to check (represents the path of the product)
     * @return true, if the path is valid. Otherwise, returns false
     */
    private boolean isProductPathValid(String path) {
        return path != null
                && (path.matches(PATH_REGEX));
    }

    /**
     * Adds all erroneous content from the imported file to a log file.
     *
     * @param erroneousContent Product with the error
     * @param message Message describing why the line is invalid
     */
    private void addInvalidProdutctsToLogFile(String erroneousContent, String message) {
        List<String> fileContent = new ArrayList<>();
        String header = ERRONEOUS_CONTENT_IDENTIFIER + FILE_SPLITTER + CAUSE_IDENTIFIER;
        fileContent.add(header);
        fileContent.add(erroneousContent + FILE_SPLITTER + message);

        FileWriter.writeFile(logFile, fileContent);
    }
}
