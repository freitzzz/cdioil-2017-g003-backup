package cdioil.application.utils;

import cdioil.domain.Category;
import cdioil.domain.Product;
import cdioil.files.FileWriter;
import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

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

    @Override
    public Map<Category, List<Product>> readProducts() {
        InputStream input = null;
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
            
            //Write XML content to file
            FileWriter.writeFile(new File(JSON_FILE_PATH), output.getBuffer().toString());
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(JSONProductsReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(input != null){
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(JSONProductsReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return new XMLProductsReader(JSON_FILE_PATH, repeatedProducts).readProducts();
    }
}
