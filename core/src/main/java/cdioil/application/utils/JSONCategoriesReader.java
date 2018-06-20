/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.MarketStructure;
import cdioil.files.FileWriter;
import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

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
    /**
     * String with the file path of the converted file (from JSON to XML).
     */
    private static final String CAT_OUTPUT_PATH = "json_category_output.xml";
    
    private final XMLCategoriesReader xmlCatReader = new XMLCategoriesReader(CAT_OUTPUT_PATH);

    /**
     * Builds a new JSONCategoriesReader with the path of the JSON file to import.
     *
     * @param filePath String with the file path to import
     */
    public JSONCategoriesReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public MarketStructure readCategories() {
        InputStream input = null;
        try {
            input = new FileInputStream(new File(filePath));

            File outFile = new File(CAT_OUTPUT_PATH);
            OutputStream output = new FileOutputStream(outFile);

            JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).build();

            //JSON reader
            XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);
            //XML writer
            XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output, "UTF-8");

            //Format output
            writer = new PrettyXMLEventWriter(writer);

            //Copy reader to writer
            writer.add(reader);

            reader.close();
            writer.close();
            input.close();
            //Write XML content to file
            //FileWriter.writeFile(new File(JSON_FILE_PATH), output.getBuffer().toString());
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(JSONCategoriesReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(JSONCategoriesReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return xmlCatReader.readCategories();
    }

    @Override
    public int getNumberOfCategoriesRead() {
        return xmlCatReader.getNumberOfCategoriesRead();
    }
}
