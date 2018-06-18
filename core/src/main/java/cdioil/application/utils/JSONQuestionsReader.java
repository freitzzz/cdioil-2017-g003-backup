package cdioil.application.utils;

import cdioil.domain.Question;
import cdioil.files.FileWriter;
import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;

import java.io.*;
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
 * @author <a href="1161380@isep.ipp.pt">Joana Pinheiro</a>
 */
public class JSONQuestionsReader implements QuestionsReader {

    /**
     * Path of the file JSON to be imported
     */
    private final File file;

    /**
     * String with the path of the file converted from JSON to XML
     */
    private static final String outputFilePath = "independent_questions_output_file.xml";

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Question> readIndependentQuestions() {
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

            FileWriter.writeFile(new File("caminho"), output.getBuffer().toString());

        } catch (XMLStreamException | IOException ex) {
            Logger.getLogger(JSONProductsReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(JSONProductsReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //return new XMLQuestionReader(...).readIndependentQuestions();
        return null;
    }

}
