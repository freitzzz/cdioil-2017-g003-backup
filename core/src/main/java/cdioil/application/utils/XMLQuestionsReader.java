package cdioil.application.utils;

import cdioil.domain.Question;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Class used for importing Questions from a file with the .xml extension.
 *
 * @author João
 * @author Joana Pinheiro [1161380]
 */
public class XMLQuestionsReader implements QuestionsReader {

    /**
     * File being read.
     */
    private final File file;

    /**
     * Creates an instance of XMLQuestionsReader, receiving the name of the file
     * to read.
     *
     * @param filename Name of the file to read
     */
    public XMLQuestionsReader(String filename) {
        this.file = new File(filename);
    }

    /**
     * Reads a category questions from a XML file.
     *
     * @return a map with the path of category and the list of the questions
     */
    @Override
    public Map<String, List<Question>> readCategoryQuestions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Question> readIndependentQuestions() throws ParserConfigurationException {


        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        document.getDocumentElement().normalize();
        // If there is a name then check it
        //Question
        NodeList nodeList = document.getElementsByTagName("question");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;
                //Binary Question
                NodeList binaryQuestions = element.getElementsByTagName("BinaryQuestion");

                if (binaryQuestions != null) {
                    for (int b = 0; b < binaryQuestions.getLength(); b++) {
                        Node binary = binaryQuestions.item(b);

                        if (binary.getNodeType() == Node.ELEMENT_NODE) {
                            Element binaryQuestion = (Element) binary;

                            binaryQuestion.getAttribute("questionID");
                            binaryQuestion.getAttribute("Text");
                        }
                    }
                }

                NodeList multipleChoiceQuestions = element.getElementsByTagName("MultipleChoiceQuestion");

                if (multipleChoiceQuestions != null) {
                    for (int m = 0; m < multipleChoiceQuestions.getLength(); m++) {
                        Node multiple = multipleChoiceQuestions.item(m);

                        if (multiple.getNodeType() == Node.ELEMENT_NODE) {
                            Element multipleQuestion = (Element) multiple;

                            multipleQuestion.getAttribute("questionID");
                            multipleQuestion.getAttribute("Text");
                            NodeList options = multipleQuestion.getElementsByTagName("choice");

                            for (int o = 0; o < options.getLength(); o++) {
                                Node option = options.item(o);

                                if (option.getNodeType() == Node.ELEMENT_NODE) {
                                    Element oneOption = (Element) option;
                                    oneOption.getAttribute("text");
                                }
                            }

                        }
                    }
                }

                NodeList quantitativeQuestions = element.getElementsByTagName("QuantitativeQuestion");

                if (quantitativeQuestions != null) {
                    for (int q = 0; q < quantitativeQuestions.getLength(); q++) {
                        Node quantitative = quantitativeQuestions.item(q);

                        if (quantitative.getNodeType() == Node.ELEMENT_NODE) {
                            Element quantitativeQuestion = (Element) quantitative;

                            quantitativeQuestion.getAttribute("questionID");
                            quantitativeQuestion.getAttribute("Text");
                            quantitativeQuestion.getAttribute("scaleMinValue");
                            quantitativeQuestion.getAttribute("scaleMaxValue");
                        }
                    }
                }

            }
        }
        return null;
    }
}
