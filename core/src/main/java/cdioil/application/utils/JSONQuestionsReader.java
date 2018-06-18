package cdioil.application.utils;

import cdioil.domain.Question;
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
 * Class used for importing Questions from a JSON file
 *
 * @author João
 */
public class JSONQuestionsReader implements QuestionsReader {

    /**
     * File being read.
     */
    private final File file;

    /**
     * String with the file path of the converted file (from JSON to XML).
     */
    private static final String JSON_FILE_PATH = "jsonToXml.xml";

    /**
     * Creates an instance of JSONQuestionsReader, receiving the name of the
     * file to read.
     *
     * @param filename Name of the file to read
     */
    public JSONQuestionsReader(String filename) {
        this.file = new File(filename);
    }

    @Override
    public Map<String, List<Question>> readCategoryQuestions() {
        InputStream input = null;
        try {
            input = new FileInputStream(file);
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
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(JSONProductsReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return new XMLQuestionsReader(file.getName()).readCategoryQuestions();
    }

    @Override
    public List<Question> readIndependentQuestions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
