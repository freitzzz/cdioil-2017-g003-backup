package cdioil.application.utils;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.MultipleChoiceQuestion;
import cdioil.domain.MultipleChoiceQuestionOption;
import cdioil.domain.QuantitativeQuestion;
import cdioil.domain.QuantitativeQuestionOption;
import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class used for importing Questions from a file with the .xml extension.
 *
 * @author João
 */
public class XMLQuestionsReader implements QuestionsReader {

    /**
     * File being read.
     */
    private final File file;
    /**
     * name of question node in xml file
     */
    private static final String QUESTION = "question";
    /**
     * Name of root node in xml file
     */
    private static final String LISTA_QUESTOES = "lista_questoes";
    /**
     * name of scale minimum value node in xml file
     */
    private static final String SCALE_MAX_VALUE = "scaleMaxValue";
    /**
     * name of scale maximum value node in xml file
     */
    private static final String SCALE_MIN_VALUE = "scaleMinValue";
    /**
     * name of question id attribute of question node in xml file
     */
    private static final String QUESTION_ID_TEXT = "questionID";
    /**
     * name of question text node in xml file
     */
    private static final String TEXT = "Text";
    /**
     * name multiple choice question option node in xml file
     */
    private static final String OPTION_TEXT = "Option";
    /**
     * name of category id node in xml file
     */
    private static final String CATEGORY_ID_NODE = "CAT_ID";
    /**
     * name of quantitative question node in xml file
     */
    private static final String QUANTITATIVE_QUESTION_NODE = "QuantitativeQuestion";
    /**
     * name of multiple choice question node in xml file
     */
    private static final String MULTIPLE_CHOICE_QUESTION_NODE = "MultipleChoiceQuestion";
    /**
     * name of binary question node in xml file
     */
    private static final String BINARY_QUESTION_NODE = "BinaryQuestion";

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
        Map<String, List<Question>> readQuestions = new HashMap<>();
        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            if (doc.hasChildNodes()) {
                iterateCategoryQuestionNodes(doc.getChildNodes(), readQuestions);
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            return null;
        }
        return readQuestions;
    }

    @Override
    public List<Question> readIndependentQuestions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Iterates through category question xml nodes
     *
     * @param childNodes child nodes of root node
     * @param readQuestions map of questions
     */
    private void iterateCategoryQuestionNodes(NodeList childNodes, Map<String, List<Question>> readQuestions) {
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node questionListNode = childNodes.item(i);
            if (questionListNode.getNodeType() == Node.ELEMENT_NODE && questionListNode.getNodeName().equals(LISTA_QUESTOES)) {
                NodeList questionNodes = questionListNode.getChildNodes();
                for (int j = 0; j < questionNodes.getLength(); j++) {
                    Node questionNode = questionNodes.item(j);
                    if (questionNode.getNodeType() == Node.ELEMENT_NODE && questionNode.getNodeName().equals(QUESTION)) {
                        NodeList contentNodes = questionNode.getChildNodes();
                        putContentOnMap(contentNodes, readQuestions);
                    }
                }
            }
        }
    }

    /**
     * Iterates question content nodes
     *
     * @param contentNodes list of content nodes
     * @param readQuestions map of questions
     */
    private void putContentOnMap(NodeList contentNodes, Map<String, List<Question>> readQuestions) {
        Question question = null;
        String catPath = null;
        for (int j = 0; j < contentNodes.getLength(); j++) {
            Node contentNode = contentNodes.item(j);
            if (contentNode.getNodeType() == Node.ELEMENT_NODE) {
                switch (contentNode.getNodeName()) {
                    case BINARY_QUESTION_NODE:
                        question = createBinaryQuestion(contentNode.getChildNodes(), contentNode.getAttributes().getNamedItem(QUESTION_ID_TEXT).getTextContent());
                        break;
                    case MULTIPLE_CHOICE_QUESTION_NODE:
                        question = createMCQuestion(contentNode.getChildNodes(), contentNode.getAttributes().getNamedItem(QUESTION_ID_TEXT).getTextContent());
                        break;
                    case QUANTITATIVE_QUESTION_NODE:
                        question = crateQuantitativeQuestion(contentNode.getChildNodes(), contentNode.getAttributes().getNamedItem(QUESTION_ID_TEXT).getTextContent());
                        break;
                    case CATEGORY_ID_NODE:
                        catPath = contentNode.getTextContent();
                        break;
                    default:
                        break;
                }
            }
        }
        if (question != null && catPath != null) { //if question was created and category path exists
            if (!readQuestions.containsKey(catPath)) { //if map does not contain category path add it to the map along with en empty list of questions
                readQuestions.put(catPath, new ArrayList<>());
            }
            readQuestions.get(catPath).add(question); //add question to map
        }
    }

    /**
     * Creates a binary question from xml nodes
     *
     * @param bQuestionNodes child nodes of BinaryQuestion node
     * @param id attribute questionID of BinaryQuestion node
     * @return a new binary question if enough information was found within the
     * nodes, null if not
     */
    private Question createBinaryQuestion(NodeList bQuestionNodes, String id) {
        for (int i = 0; i < bQuestionNodes.getLength(); i++) {
            Node node = bQuestionNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(TEXT)) {
                return new BinaryQuestion(node.getTextContent(), id);
            }
        }
        return null;
    }

    /**
     * Creates a multiple choice question from xml nodes
     *
     * @param bQuestionNodes child nodes of BinaryQuestion node
     * @param id attribute questionID of BinaryQuestion node
     * @return a new multiple choice if enough information was found within the
     * nodes, null if not
     */
    private Question createMCQuestion(NodeList mcQuestionNodes, String id) {
        String text = null;
        List<QuestionOption> options = new ArrayList<>();
        for (int i = 0; i < mcQuestionNodes.getLength(); i++) {
            Node node = mcQuestionNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                switch (node.getNodeName()) {
                    case TEXT:
                        text = node.getTextContent();
                        break;
                    case OPTION_TEXT:
                        String optionText = node.getAttributes().getNamedItem("text").getTextContent();
                        options.add(new MultipleChoiceQuestionOption(optionText));
                        break;
                    default:
                        break;
                }
            }
        }
        if (text != null && !options.isEmpty()) {
            return new MultipleChoiceQuestion(text, id, options);
        }
        return null;
    }

    /**
     * Creates a quantitative question from xml nodes
     *
     * @param bQuestionNodes child nodes of BinaryQuestion node
     * @param id attribute questionID of BinaryQuestion node
     * @return a new quantitative if enough information was found within the
     * nodes, null if not
     */
    private Question crateQuantitativeQuestion(NodeList quantitativeQuestionNodes, String id) {
        String text = null;
        int min = Integer.MIN_VALUE, max = Integer.MAX_VALUE;
        for (int i = 0; i < quantitativeQuestionNodes.getLength(); i++) {
            Node node = quantitativeQuestionNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                switch (node.getNodeName()) {
                    case TEXT:
                        text = node.getTextContent();
                        break;
                    case SCALE_MIN_VALUE:
                        min = Integer.parseInt(node.getTextContent());
                        break;
                    case SCALE_MAX_VALUE:
                        max = Integer.parseInt(node.getTextContent());
                        break;
                    default:
                        break;
                }
            }
        }
        if (text != null && min != Integer.MIN_VALUE && max != Integer.MAX_VALUE) {
            List<QuestionOption> scale = new ArrayList<>();
            for (int i = min; i <= max; i++) {
                scale.add(new QuantitativeQuestionOption((double) i));
            }
            return new QuantitativeQuestion(text, id, scale);
        }
        return null;
    }
}
