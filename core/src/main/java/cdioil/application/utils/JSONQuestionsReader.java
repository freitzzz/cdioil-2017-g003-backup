package cdioil.application.utils;

import cdioil.domain.Question;
import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

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
        ByteArrayOutputStream output=null;
        try {
            input = new FileInputStream(file);
            //StringWriter output = new StringWriter();
            output = new ByteArrayOutputStream();

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
            return new XMLQuestionsReader(output.toString()).readCategoryQuestions();
            //Write XML content to file
            //FileWriter.writeFile(new File(CAT_QUESTIONS_OUTPUT_PATH), output.getBuffer().toString());
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(JSONQuestionsReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            closeStream(output);
            closeStream(input);
        }
    }

    @Override
    public List<Question> readIndependentQuestions() throws ParserConfigurationException {
        InputStream input = null;
        ByteArrayOutputStream output=null;
        try {
            input = new FileInputStream(file);
            output = new ByteArrayOutputStream();

            JsonXMLConfig jsonXMLConfig = new JsonXMLConfigBuilder().multiplePI(false).build();

            XMLEventReader xmlEventReader = new JsonXMLInputFactory(jsonXMLConfig).createXMLEventReader(input);

            XMLEventWriter xmlEventWriter = XMLOutputFactory.newInstance().createXMLEventWriter(output, "UTF-8");

            xmlEventWriter = new PrettyXMLEventWriter(xmlEventWriter);

            xmlEventWriter.add(xmlEventReader);

            xmlEventReader.close();
            xmlEventWriter.close();
            input.close();
            return new XMLQuestionsReader(output.toString()).readIndependentQuestions();
        } catch (XMLStreamException | IOException|TransformerException ex) {
            Logger.getLogger(JSONQuestionsReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            closeStream(input);
            closeStream(output);
        }

        
    }
    /**
     * Closes (or attempts) a stream
     * @param stream Closeable with the stream being close
     */
    private void closeStream(Closeable stream){
        if(stream!=null){
            try{
                stream.close();
            }catch(IOException ioException){
                Logger.getLogger(JSONQuestionsReader.class.getName()).log(Level.SEVERE, null, ioException);
            }
        }
    }

}
