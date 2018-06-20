package cdioil.application.utils;

import cdioil.domain.Question;
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
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

/**
 * Class used for importing Questions from a JSON file
 *
 * @author João
 * @author <a href="1161380@isep.ipp.pt">Joana Pinheiro</a>
 */
public class JSONQuestionsReader implements QuestionsReader {

    /**
     * Path of the file JSON to be imported
     */
    private final File file;

    /**
     * String with the file path of the converted file (from JSON to XML).
     */
    private static final String CAT_QUESTIONS_OUTPUT_PATH = "category_questions_output.xml";
    /**
     * String with the path of the file converted from JSON to XML
     */
    private static final String OUTPUT_FILE_PATH = "independent_questions_output_file.xml";

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
            //StringWriter output = new StringWriter();
            File outFile = new File(CAT_QUESTIONS_OUTPUT_PATH);
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
            //FileWriter.writeFile(new File(CAT_QUESTIONS_OUTPUT_PATH), output.getBuffer().toString());
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
        return new XMLQuestionsReader(CAT_QUESTIONS_OUTPUT_PATH).readCategoryQuestions();
    }

    @Override
    public List<Question> readIndependentQuestions() throws ParserConfigurationException {
        InputStream input = null;

        try {
            input = new FileInputStream(file);
            StringWriter output = new StringWriter();

            JsonXMLConfig jsonXMLConfig = new JsonXMLConfigBuilder().multiplePI(false).build();

            XMLEventReader xmlEventReader = new JsonXMLInputFactory(jsonXMLConfig).createXMLEventReader(input);

            XMLEventWriter xmlEventWriter = XMLOutputFactory.newInstance().createXMLEventWriter(output);

            xmlEventWriter = new PrettyXMLEventWriter(xmlEventWriter);

            xmlEventWriter.add(xmlEventReader);

            xmlEventReader.close();
            xmlEventWriter.close();
            input.close();

            FileWriter.writeFile(new File(OUTPUT_FILE_PATH), output.getBuffer().toString());

        } catch (XMLStreamException | IOException ex) {
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

        return new XMLQuestionsReader(OUTPUT_FILE_PATH).readIndependentQuestions();
    }

}
