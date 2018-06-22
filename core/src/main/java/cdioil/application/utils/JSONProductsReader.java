package cdioil.application.utils;

import cdioil.domain.Category;
import cdioil.domain.Product;
import cdioil.files.FileWriter;
import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

/**
 * Imports products from a JSON file by converting it in a XML file.
 *
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 */
public class JSONProductsReader implements ProductsReader {

    /**
     * Path of the JSON file to import.
     */
    private final String filePath;

    /**
     * Map that will hold all already existent products for the user to decide if they need to be updated or not.
     */
    private final Map<Category, List<Product>> repeatedProducts;

    /**
     * String with the file path of the converted file (from JSON to XML).
     */
    private static final String JSON_FILE_PATH = "jsonToXml.xml";

    /**
     * Builds a new JSONProductsReader with the path of the JSON file to import.
     *
     * @param filePath String with the file path to import
     * @param repeatedProducts Map that will hold all already existent products for the user to decide if they need to be updated or not
     */
    public JSONProductsReader(String filePath, Map<Category, List<Product>> repeatedProducts) {
        this.filePath = filePath;
        this.repeatedProducts = repeatedProducts;
    }

    /**
     * Imports products from a JSON file, by converting it into a XML file.
     *
     * @return a map with the categories as keys and their products as values
     */
    @Override
    public Map<Category, List<Product>> readProducts() throws TransformerException {
        InputStream input = null;
        Document document = null;
        try {
            input = new FileInputStream(new File(filePath));
            StringWriter output = new StringWriter();

            JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).build();

            //JSON reader
            XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);
            //XML writer
            XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);

            //Format output
            writer = new PrettyXMLEventWriter(writer);

            //Copy reader to writer
            writer.add(reader);

            reader.close();
            writer.close();
            input.close();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder;
            //Write XML content to file
//            FileWriter.writeFile(new File(JSON_FILE_PATH), output.getBuffer().toString());
            try {
                documentBuilder = documentBuilderFactory.newDocumentBuilder();
                document = documentBuilder.parse(new InputSource(new StringReader(output.toString())));
                output.close();
            } catch (ParserConfigurationException | SAXException ex) {
                ExceptionLogger.logException(LoggerFileNames.CORE_LOGGER_FILE_NAME, Level.SEVERE, ex.getMessage());
            }
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(JSONProductsReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(JSONProductsReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return new XMLProductsReader(JSON_FILE_PATH, repeatedProducts, document).readProducts();
    }
}
