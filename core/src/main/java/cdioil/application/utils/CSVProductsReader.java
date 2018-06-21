package cdioil.application.utils;

import cdioil.domain.Category;
import cdioil.domain.Product;
import cdioil.files.FileReader;
import cdioil.files.FileWriter;
import cdioil.files.InvalidFileFormattingException;
import java.io.File;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
    private static final String SPLITTER = ";";

    /**
     * Inverted commas that may appear as trash in the file.
     */
    private static final Character INVERTED_COMMAS = '"';

    /**
     * Question mark that may appear as trash in the file.
     */
    private static final Character QUESTION_MARK = '?';

    //Offsets
    /**
     * Number of the line that contains the identifiers of the columns.
     */
    private static final int IDENTIFIERS_LINE = 0;
    /**
     * Number of expected identifiers (columns) in the CSV file.
     */
    private static final int NUM_IDENTIFIERS = 5;

    //Header fields
    /**
     * Categories identifier in the CSV file.
     */
    private static final String CSV_CATEGORY_IDENTIFIER = "ClasseID";

    /**
     * SKU identifier in the CSV file.
     */
    private static final String CSV_CODE_IDENTIFIER = "ProdutoID";

    /**
     * Product's name identifier in the CSV file.
     */
    private static final String CSV_PRODUCT_IDENTIFIER = "Designacao";

    /**
     * Quantity (per unit) identifier in the CSV file.
     */
    private static final String CSV_QUANTITY_IDENTIFIER = "Quantidade";

    /**
     * Unity identifier in the CSV file.
     */
    private static final String CSV_UNITY_IDENTIFIER = "Unidade";

    /**
     * Product identifier in the XML file.
     */
    private static final String XML_PRODUCT_IDENTIFIER = "produto";

    /**
     * Products list identifier in the XML file.
     */
    private static final String XML_PRODUCTS_LIST_IDENTIFIER = "lista_produtos";

    /**
     * Categories identifier in the XML file.
     */
    private static final String XML_CATEGORY_IDENTIFIER = "ID";

    /**
     * EAN identifier in the XML file.
     */
    private static final String XML_CODE_IDENTIFIER = "COD";

    /**
     * Product's name identifier in the XML file.
     */
    private static final String XML_PRODUCT_NAME_IDENTIFIER = "descritivo";

    /**
     * Quantity (per unit) identifier in the XML file.
     */
    private static final String XML_QUANTITY_IDENTIFIER = CSV_QUANTITY_IDENTIFIER.toLowerCase();

    /**
     * Unity identifier in the XML file.
     */
    private static final String XML_UNITY_IDENTIFIER = CSV_UNITY_IDENTIFIER.toLowerCase();

    //Files
    /**
     * File being read.
     */
    private final File file;

    /**
     * String with the file path of the converted file (from CSV to XML).
     */
    private static final String CSV_FILE_PATH = "csvToXml.xml";

    /**
     * Message displayed to the user if the file formatting is not recognized.
     */
    private static final String INVALID_FILE_FORMATTING_MESSAGE = "Unrecognized file formatting";

    /**
     * Map that will hold all already existent products for the user to decide if they need to be updated or not.
     */
    private final Map<Category, List<Product>> repeatedProducts;

    /**
     * Creates an instance of CSVProductsReader, receiving the name of the file to read.
     *
     * @param filename Name of the file to read
     * @param repeatedProducts Map that will hold all already existent products for the user to decide if they need to be updated or not
     */
    public CSVProductsReader(String filename, Map<Category, List<Product>> repeatedProducts) {
        this.file = new File(filename);
        this.repeatedProducts = repeatedProducts;
    }

    /**
     * Imports products from a CSV file, by converting it into a XML file.
     *
     * @return a map with the categories as keys and their products as values
     */
    @Override
    public Map<Category, List<Product>> readProducts() {

        List<String> fileContent = FileReader.readFile(file);
        if (!isFileValid(fileContent)) {
            throw new InvalidFileFormattingException(INVALID_FILE_FORMATTING_MESSAGE);
        }

        try {
            Document doc = DocumentBuilderFactory.newInstance().
                    newDocumentBuilder().newDocument();

            writeToDocument(doc, fileContent);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter output = new StringWriter();
            StreamResult result = new StreamResult(output);

            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(source, result);

            //Write XML content to file
            FileWriter.writeFile(new File(CSV_FILE_PATH), output.getBuffer().toString());
        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
            Logger.getLogger(XMLSurveyStatsWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLSurveyStatsWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new XMLProductsReader(CSV_FILE_PATH, repeatedProducts).readProducts();
    }

    /**
     * Writes the information about the products of the CSV file into a XML document.
     *
     * @param doc Document instance
     * @param fileContent Content of the CSV file
     */
    private void writeToDocument(Document doc, List<String> fileContent) {
        Element rootElement = doc.createElement(XML_PRODUCTS_LIST_IDENTIFIER);
        doc.appendChild(rootElement);
        for (int i = IDENTIFIERS_LINE + 1; i < fileContent.size(); i++) {
            String[] line = fileContent.get(i).split(SPLITTER);
            if (line.length == NUM_IDENTIFIERS) {
                Element product = doc.createElement(XML_PRODUCT_IDENTIFIER);
                rootElement.appendChild(product);

                Element categoryPathElement = doc.createElement(XML_CATEGORY_IDENTIFIER);
                categoryPathElement.setTextContent(line[0].replace(INVERTED_COMMAS, ' ').trim()); //Replace to remove trash from file
                product.appendChild(categoryPathElement);

                Element productCodeElement = doc.createElement(XML_CODE_IDENTIFIER);
                productCodeElement.setTextContent(line[1].trim());
                product.appendChild(productCodeElement);

                Element productNameElement = doc.createElement(XML_PRODUCT_NAME_IDENTIFIER);
                productNameElement.setTextContent(line[2].trim());
                product.appendChild(productNameElement);

                Element productQuantityElement = doc.createElement(XML_QUANTITY_IDENTIFIER);
                productQuantityElement.setTextContent(line[3].trim());
                product.appendChild(productQuantityElement);

                Element quantityUnityElement = doc.createElement(XML_UNITY_IDENTIFIER);
                quantityUnityElement.setTextContent(line[4].replace(INVERTED_COMMAS, ' ').trim()); //Replace to remove trash from file
                product.appendChild(quantityUnityElement);
            }
        }
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
        String[] line = fileContent.get(IDENTIFIERS_LINE).split(SPLITTER);

        //Remove trash from file
        line[0] = line[0].replace(QUESTION_MARK, ' ').trim();
        return (line.length == NUM_IDENTIFIERS
                && line[0].trim().contains(CSV_CATEGORY_IDENTIFIER)
                && line[1].trim().equalsIgnoreCase(CSV_CODE_IDENTIFIER)
                && line[2].trim().equalsIgnoreCase(CSV_PRODUCT_IDENTIFIER)
                && line[3].trim().contains(CSV_QUANTITY_IDENTIFIER))
                && line[4].trim().contains(CSV_UNITY_IDENTIFIER);
    }
}
