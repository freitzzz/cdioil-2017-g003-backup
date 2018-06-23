package cdioil.frontoffice.application.restful.xml;

import cdioil.application.utils.Edge;
import cdioil.application.utils.Graph;
import cdioil.application.utils.Vertex;
import cdioil.domain.BinaryQuestionOption;
import cdioil.domain.MultipleChoiceQuestionOption;
import cdioil.domain.QuantitativeQuestionOption;
import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import cdioil.domain.Review;
import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import cdioil.persistence.impl.ReviewRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author António Sousa [1161371]
 */
public class ReviewXMLService {

    /**
     * Constant representing the Review element tag.
     */
    private static final String REVIEW_ELEMENT_TAG = "Review";

    /**
     * Constant representing the ID attribute tag.
     */
    private static final String ID_ATTRIBUTE_TAG = "id";

    /**
     * Constant representing the Survey's ID attribute tag.
     */
    private static final String SURVEY_ID_ATTRIBUTE_TAG = "surveyID";

    /**
     * Constant representing the Current Question element tag.
     */
    private static final String CURRENT_QUESTION_ELEMENT_TAG = "CurrentQuestion";

    /**
     * Constant representing the QuestionID attribute tag.
     */
    private static final String QUESTION_ID_ATTRIBUTE_TAG = "questionID";

    /**
     * Constant representing the Suggestion element tag.
     */
    private static final String SUGGESTION_ELEMENT_TAG = "Suggestion";

    /**
     * Constant representing the Text element tag.
     */
    private static final String TEXT_ELEMENT_TAG = "Text";

    /**
     * Constant representing the Image element tag.
     */
    private static final String IMAGE_ELEMENT_TAG = "Image";

    /**
     * Constant representing the Answers element tag.
     */
    private static final String ANSWERS_ELEMENT_TAG = "Answers";

    /**
     * Constant representing the Questions element tag.
     */
    private static final String QUESTIONS_ELEMENT_TAG = "Questions";

    /**
     * Constant representing the Option element tag.
     */
    private static final String OPTION_ELEMENT_TAG = "Option";

    /**
     * Constant representing the BinaryQuestion element tag.
     */
    private static final String BINARY_QUESTION_ELEMENT_TAG = "BinaryQuestion";

    /**
     * Constant representing the MultipleChoiceQuestion element tag.
     */
    private static final String MULTIPLE_CHOICE_QUESTION_ELEMENT_TAG = "MultipleChoiceQuestion";

    /**
     * Constant representing the QuantitativeQuestion element tag.
     */
    private static final String QUANTITATIVE_QUESTION_ELEMENT_TAG = "QuantitativeQuestion";

    /**
     * Constant representing the QuestionGraph element tag.
     */
    private static final String QUESTION_GRAPH_ELEMENT_TAG = "QuestionGraph";

    /**
     * Constant representing the Question element tag.
     */
    private static final String QUESTION_ELEMENT_TAG = "Question";

    /**
     * Constant representing the MinScaleValue element tag.
     */
    private static final String MIN_SCALE_VALUE_ELEMENT_TAG = "MinScaleValue";

    /**
     * Constant representing the MaxScaleValue element tag.
     */
    private static final String MAX_SCALE_VALUE_ELEMENT_TAG = "MaxScaleValue";

    /**
     * Constant representing the NextQuestion element tag.
     */
    private static final String NEXT_QUESTION_ELEMENT_TAG = "NextQuestion";

    /**
     * Constant representing the answer element tag.
     */
    private static final String ANSWER_ELEMENT_TAG = "Answer";

    /**
     * Constant representing the text attribute tag.
     */
    private static final String TEXT_ATTRIBUTE_TAG = "text";

    /**
     * Constant representing the option attribute tag.
     */
    private static final String OPTION_ATTRIBUTE_TAG = "option";

    /**
     * Constant representing the end date attribute tag.
     */
    private static final String SURVEY_END_DATE_TAG = "endDate";

    /**
     * Constant representing the time element tag.
     */
    private static final String TIME_ELEMENT_TAG = "Time";

    /**
     * Builds an XML file for a given review and returns its content.
     *
     * @param review review
     * @return String with the XML file's content
     */
    public static String createReviewXML(Review review) {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();

            Element reviewElement = doc.createElement(REVIEW_ELEMENT_TAG);
            doc.appendChild(reviewElement);
            Long reviewID = new ReviewRepositoryImpl().getReviewID(review);
            //check if review has been persisted
            if (reviewID == null) {
                return null;
            }
            reviewElement.setAttribute(ID_ATTRIBUTE_TAG, reviewID.toString());
            reviewElement.setAttribute(SURVEY_END_DATE_TAG, review.getSurvey().getSurveyEndDate());

            Long surveyID = new SurveyRepositoryImpl().getSurveyID(review.getSurvey());
            if (surveyID == null) {
                return null;
            }
            reviewElement.setAttribute(SURVEY_ID_ATTRIBUTE_TAG, surveyID.toString());

            Graph reviewGraph = review.getAnswerGraph();
            buildQuestionList(reviewGraph, doc, reviewElement);
            buildQuestionGraph(reviewGraph, doc, reviewElement);

            Element currentQuestionElement = doc.createElement(CURRENT_QUESTION_ELEMENT_TAG);
            reviewElement.appendChild(currentQuestionElement);
            currentQuestionElement.setAttribute(QUESTION_ID_ATTRIBUTE_TAG, review.getCurrentQuestion().getQuestionID());

            //Suggestion's text is initially empty
            Element suggestionElement = doc.createElement(SUGGESTION_ELEMENT_TAG);
            reviewElement.appendChild(suggestionElement);
            Element suggestionTextElement = doc.createElement(TEXT_ELEMENT_TAG);
            suggestionElement.appendChild(suggestionTextElement);
            Element suggestionImageElement = doc.createElement(IMAGE_ELEMENT_TAG);
            suggestionElement.appendChild(suggestionImageElement);

            Element timeElement = doc.createElement(TIME_ELEMENT_TAG);
            timeElement.setTextContent("0");
            reviewElement.appendChild(timeElement);

            Element answersElement = doc.createElement(ANSWERS_ELEMENT_TAG);
            reviewElement.appendChild(answersElement);

            //Write content to string
            StringWriter sw = new StringWriter();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(sw);

            transformer.transform(source, result);

            return sw.toString();

        } catch (ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(ReviewXMLService.class.getName()).log(Level.SEVERE, null, ex);
            ExceptionLogger.logException(LoggerFileNames.FRONTOFFICE_LOGGER_FILE_NAME, Level.SEVERE, ex.getMessage());
            return null;
        }
    }

    /**
     * Builds the Review's question list and appends it to the root element.
     *
     * @param reviewGraph the review's question graph
     * @param doc XML document
     * @param reviewElement parent element
     */
    private static void buildQuestionList(Graph reviewGraph, Document doc, Element reviewElement) {

        Element questionsElement = doc.createElement(QUESTIONS_ELEMENT_TAG);
        reviewElement.appendChild(questionsElement);

        for (Vertex vert : reviewGraph.allVertices()) {

            Question question = vert.getElement();

            switch (question.getType()) {
                case BINARY:
                    buildBinaryQuestion(question, doc, questionsElement);
                    break;

                case MULTIPLE_CHOICE:
                    buildMultipleChoiceQuestion(question, doc, questionsElement);
                    break;

                case QUANTITATIVE:
                    buildQuantitativeQuestion(question, doc, questionsElement);
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * Builds a binary question element and appends it to the question list
     * element.
     *
     * @param question question
     * @param doc XML document
     * @param questionsElement question list element
     */
    private static void buildBinaryQuestion(Question question, Document doc, Element questionsElement) {

        Element questionElement = doc.createElement(BINARY_QUESTION_ELEMENT_TAG);
        questionElement.setAttribute(QUESTION_ID_ATTRIBUTE_TAG, question.getQuestionID());

        Element binaryTextElement = doc.createElement(TEXT_ELEMENT_TAG);
        binaryTextElement.appendChild(doc.createTextNode(question.getQuestionText()));

        questionElement.appendChild(binaryTextElement);

        questionsElement.appendChild(questionElement);
    }

    /**
     * Builds a multiple choice question element and appends it to the question
     * list element.
     *
     * @param question question
     * @param doc XML document
     * @param questionsElement question list element
     */
    private static void buildMultipleChoiceQuestion(Question question, Document doc, Element questionsElement) {
        Element questionElement = doc.createElement(MULTIPLE_CHOICE_QUESTION_ELEMENT_TAG);
        questionElement.setAttribute(QUESTION_ID_ATTRIBUTE_TAG, question.getQuestionID());

        Element multipleChoiceTextElement = doc.createElement(TEXT_ELEMENT_TAG);
        multipleChoiceTextElement.appendChild(doc.createTextNode(question.getQuestionText()));
        questionElement.appendChild(multipleChoiceTextElement);

        for (QuestionOption option : question.getOptionList()) {
            Element multipleChoiceOptionElement = doc.createElement(OPTION_ELEMENT_TAG);

            multipleChoiceOptionElement.setAttribute(TEXT_ATTRIBUTE_TAG, option.toString());

            questionElement.appendChild(multipleChoiceOptionElement);
        }

        questionsElement.appendChild(questionElement);
    }

    /**
     * Builds a quantitative question element and appends it to the question
     * list element.
     *
     * @param question question
     * @param doc XML document
     * @param questionsElement question list element
     */
    private static void buildQuantitativeQuestion(Question question, Document doc, Element questionsElement) {
        Element questionElement = doc.createElement(QUANTITATIVE_QUESTION_ELEMENT_TAG);
        questionElement.setAttribute(QUESTION_ID_ATTRIBUTE_TAG, question.getQuestionID());

        Element quantitativeQuestionText = doc.createElement(TEXT_ELEMENT_TAG);
        quantitativeQuestionText.appendChild(doc.createTextNode(question.getQuestionText()));
        questionElement.appendChild(quantitativeQuestionText);

        List<QuestionOption> optionList = question.getOptionList();
        int lastIndex = optionList.size() - 1;

        Element quantitativeQuestionMinScaleValue = doc.createElement(MIN_SCALE_VALUE_ELEMENT_TAG);
        quantitativeQuestionMinScaleValue.appendChild(doc.createTextNode(optionList.get(0).toString()));
        questionElement.appendChild(quantitativeQuestionMinScaleValue);

        Element quantitativeQuestionMaxScaleValue = doc.createElement(MAX_SCALE_VALUE_ELEMENT_TAG);
        quantitativeQuestionMaxScaleValue.appendChild(doc.createTextNode(optionList.get(lastIndex).toString()));
        questionElement.appendChild(quantitativeQuestionMaxScaleValue);

        questionsElement.appendChild(questionElement);
    }

    /**
     * Builds a question graph element and appends it to the review element.
     *
     * @param reviewGraph review's question graph
     * @param doc XML document
     * @param reviewElement review element
     */
    private static void buildQuestionGraph(Graph reviewGraph, Document doc, Element reviewElement) {

        Element questionGraphElement = doc.createElement(QUESTION_GRAPH_ELEMENT_TAG);
        reviewElement.appendChild(questionGraphElement);

        Iterable<Vertex> vertices = reviewGraph.allVertices();

        for (Vertex vert : vertices) {
            Iterable<Edge> outgoingEdges = vert.getAllOutgoingEdges();

            if (outgoingEdges.iterator().hasNext()) {
                Element questionElement = doc.createElement(QUESTION_ELEMENT_TAG);
                questionGraphElement.appendChild(questionElement);
                questionElement.setAttribute(QUESTION_ID_ATTRIBUTE_TAG, vert.getElement().getQuestionID());

                for (Edge edge : outgoingEdges) {
                    Element nextQuestionElement = doc.createElement(NEXT_QUESTION_ELEMENT_TAG);
                    questionElement.appendChild(nextQuestionElement);
                    nextQuestionElement.setAttribute(OPTION_ATTRIBUTE_TAG, edge.getElement().toString());
                    nextQuestionElement.setAttribute(QUESTION_ID_ATTRIBUTE_TAG, edge.getDestinationVertexElement().getQuestionID());
                }
            }
        }
    }

    /**
     * Parses a String that represents an XML File of a Finished Review and
     * extracts the answers that were given, turns them into their respective
     * QuestionOptions and returns a list with all of them
     *
     * @param fileContent String that represents an XML File of a Finished
     * Review
     * @return List of QuestionOption objects
     */
    public static List<QuestionOption> getAnswerList(String fileContent) {
        List<QuestionOption> answersList = new LinkedList<>();

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(fileContent)));
            Element reviewElement = document.getDocumentElement();

            Element answerMapElement = (Element) reviewElement.getElementsByTagName(ANSWERS_ELEMENT_TAG).item(0);

            NodeList answersNodeList = answerMapElement.getElementsByTagName(ANSWER_ELEMENT_TAG);

            for (int i = 0; i < answersNodeList.getLength(); i++) {
                Element answerNode = (Element) answersNodeList.item(i);

                String answer = answerNode.getAttribute(TEXT_ATTRIBUTE_TAG);

                if (answer.equals("true") || answer.equals("false")) {
                    BinaryQuestionOption binaryAnswer
                            = new BinaryQuestionOption(Boolean.parseBoolean(answer));
                    answersList.add(binaryAnswer);
                } else if (answer.matches("[0-9]+.[0-9]+")) {
                    QuantitativeQuestionOption quantitativeAnswer
                            = new QuantitativeQuestionOption(Double.parseDouble(answer));
                    answersList.add(quantitativeAnswer);
                } else {
                    MultipleChoiceQuestionOption multipleChoiceAnswer
                            = new MultipleChoiceQuestionOption(answer);
                    answersList.add(multipleChoiceAnswer);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ReviewXMLService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return answersList;
    }

    public static String getSuggestion(String fileContent) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(fileContent)));
            Element reviewElement = document.getDocumentElement();

            Element suggestionElement = (Element) reviewElement.getElementsByTagName(SUGGESTION_ELEMENT_TAG).item(0);
            String suggestion = suggestionElement.getElementsByTagName(TEXT_ELEMENT_TAG).item(0).getTextContent();

            if (suggestion != null && !suggestion.isEmpty()) {
                return suggestion;
            }

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ReviewXMLService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static String getSuggestionImage(String fileContent) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(fileContent)));
            Element reviewElement = document.getDocumentElement();

            Element suggestionElement = (Element) reviewElement.getElementsByTagName(SUGGESTION_ELEMENT_TAG).item(0);
            String imageBytes = suggestionElement.getElementsByTagName(IMAGE_ELEMENT_TAG).item(0).getTextContent();
            return imageBytes == null || imageBytes.isEmpty() ? null : imageBytes;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ReviewXMLService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static long getTime(String fileContent) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(fileContent)));

            Element reviewElement = document.getDocumentElement();

            Element timeElement = (Element) reviewElement.getElementsByTagName(TIME_ELEMENT_TAG).item(0);
            return Long.parseLong(timeElement.getTextContent());
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(ReviewXMLService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
