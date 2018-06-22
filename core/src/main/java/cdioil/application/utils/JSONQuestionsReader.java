package cdioil.application.utils;

import cdioil.domain.Question;
import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
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
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

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
            output.close();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(output.toString())));
                output.close();
                return new XMLQuestionsReader(doc).readCategoryQuestions();
            } catch (Exception e) {
                ExceptionLogger.logException(LoggerFileNames.CORE_LOGGER_FILE_NAME, Level.SEVERE, e.getMessage());
            }
            return null;
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(JSONQuestionsReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            closeStream(input);
        }
    }

    @Override
    public List<Question> readIndependentQuestions() throws ParserConfigurationException {
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
            output.close();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(output.toString())));
                output.close();
                return new XMLQuestionsReader(doc).readIndependentQuestions();
            } catch (Exception e) {
                ExceptionLogger.logException(LoggerFileNames.CORE_LOGGER_FILE_NAME, Level.SEVERE, e.getMessage());
            }
            return null;
        } catch (XMLStreamException | IOException /*| TransformerException */ ex) {
            Logger.getLogger(JSONQuestionsReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            closeStream(input);
        }

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
                Logger.getLogger(JSONQuestionsReader.class.getName()).log(Level.SEVERE, null, ioException);
            }
        }
    }

}
