/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.MarketStructure;
import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Imports categories from a JSON file by converting it in a XML file.
 *
 * @author Ana Guerra (1161191)
 */
public class JSONCategoriesReader implements CategoriesReader {

    /**
     * Path of the JSON file to import.
     */
    private final String filePath;

    private XMLCategoriesReader xmlCatReader = null;

    /**
     * Builds a new JSONCategoriesReader with the path of the JSON file to
     * import.
     *
     * @param filePath String with the file path to import
     */
    public JSONCategoriesReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Imports Categories from a JSON file.
     *
     * @return MarketStructur with the Categories that were read. Null if the
     * file is not valid
     */
    @Override
    public MarketStructure readCategories() {
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
            output.close();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(output.toString())));
                xmlCatReader = new XMLCategoriesReader(doc);
                output.close();
                return xmlCatReader.readCategories();
            } catch (Exception e) {
                ExceptionLogger.logException(LoggerFileNames.CORE_LOGGER_FILE_NAME, Level.SEVERE, e.getMessage());
            }
            return null;
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(JSONCategoriesReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            closeStream(input);
        }
    }

    /**
     * Returns the number of Categories in the list of Categories.
     *
     * @return the number of Categories that were read
     */
    @Override
    public int getNumberOfCategoriesRead() {
        if (xmlCatReader == null) {
            return 0;
        }
        return xmlCatReader.getNumberOfCategoriesRead();
    }

    /**
     * Closes (or attempts) a stream
     *
     * @param stream Closeable with the stream being close
     */
    private void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException ioException) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ioException);
            }
        }
    }
}
