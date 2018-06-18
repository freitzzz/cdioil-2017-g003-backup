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
import cdioil.domain.Survey;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * SurveyFluxXMLService class that represents a service for representing the 
 * flux of a survey for either a Survey or Review representation
 * @author António Sousa [1161371]
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @since Version 6.0 & 7.0 of FeedbackMonkey
 */
public final class SurveyFluxXMLService {
    /**
     * Constant representing the Review element tag.
     */
    private static final String REVIEW_ELEMENT_TAG = "Review";
    /**
     * Constant that represents the Survey element tag
     */
    private static final String SURVEY_ELEMENT_TAG="Survey";
    /**
     * Constant that represents the Surveys element tag
     */
    private static final String SURVEYS_ELEMENT_TAG="Surveys";

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
     * Constant that represents the message that ocures if an error ocures while creating the XML document
     */
    private static final String ERROR_CREATING_DOCUMENT_MESSAGE="Ocorreu um erro na criacao do documento";
    /**
     * Constant that represents the message that ocures if a review which survey flux is trying to be represented 
     * currently doesn't exist on the database
     */
    private static final String INVALID_REVIEW_MESSAGE="A Review não está disponivel de momento!";
    /**
     * Constant that represents the message that ocures if a Survey which survey flux is trying to be represented 
     * currently doesn't exist on the database
     */
    private static final String INVALID_SURVEY_MESSAGE="O Inquérito não está disponivel de momento";
    /**
     * Document with the XML document being created for a certain service type
     */
    private final Document xmlDocument;
    /**
     * Creates a new SurveyFluxXMLService for representing a certain Survey Flux service on a 
     * XML document
     * @return SurveyFluxXMLService with the Survey Flux Service that will be used to represent 
     * a certain service on a XML document
     */
    public static SurveyFluxXMLService create(){
        try {
            return new SurveyFluxXMLService();
        } catch (ParserConfigurationException parserConfigurationException) {
            throw new IllegalStateException(ERROR_CREATING_DOCUMENT_MESSAGE);
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

            Element answerMapElement = (Element)reviewElement.getElementsByTagName(ANSWERS_ELEMENT_TAG).item(0);

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
    /**
     * Gets the suggestion of a certain XML document
     * @param xmlDocument String with the XML document
     * @return String with the xml document suggestion
     */
    public static String getSuggestion(String xmlDocument) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(xmlDocument)));
            Element reviewElement = document.getDocumentElement();

            Element suggestionElement = (Element) reviewElement.getElementsByTagName(SUGGESTION_ELEMENT_TAG).item(0);
            String suggestion = suggestionElement.getElementsByTagName(TEXT_ELEMENT_TAG).item(0).getTextContent();
            
            if(suggestion != null && !suggestion.isEmpty()){
                return suggestion;
            }
            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ReviewXMLService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    /**
     * Method that represents a Review with a certain survey flux as a XML document
     * @param review Review with the review being represented with the survey flux 
     * as a XML document
     * @return String with the Review represented with the survey flux as a XML service
     */
    public String createReviewXML(Review review){
        initializeReviewDocument(review);
        addSurveyFlux(review.getAnswerGraph(),xmlDocument.getDocumentElement());
        finalizeReviewXMLDocument(review,xmlDocument.getDocumentElement());
        return documentAsString();
    }
    /**
     * Method that represents a Survey with a certain flux as a XML document
     * @param survey Survey with the survey flux being represented as a XML document
     * @return String with the Survey represented with the flux as a XML service
     */
    public String createSurveyXML(Survey survey){
        initializeSurveyDocument(survey);
        addSurveyFlux(survey.getGraphCopy(),xmlDocument.getDocumentElement());
        return documentAsString();
    }
    /**
     * Method that represents a List of Surveys with a certain flux as a XML document
     * @param surveys List with all the Surveys flux being represented as a XML document
     * @return String with the XML document containing a List of Surveys with a certain flux
     */
    public String createSurveyListXML(List<Survey> surveys){
        initializeSurveyListDocument();
        surveys.forEach((survey)->{
            Element surveyElement=initializeSurveyDocument(survey);
            addSurveyFlux(survey.getGraphCopy(),surveyElement);
        });
        return documentAsString();
    }
    /**
     * Initializes the document as a list of Survey Flux
     */
    private void initializeSurveyListDocument(){
        xmlDocument.appendChild(xmlDocument.createElement(SURVEYS_ELEMENT_TAG));
    }
    /**
     * Initializes the XML document as a Review Service
     * @param review Review with the review which document is being represented on
     * @return Element with the element which document was created on
     */
    private Element initializeReviewDocument(Review review){
        Long reviewID = new ReviewRepositoryImpl().getReviewID(review);
        //check if review has been persisted
        if (reviewID == null) {
            throw new IllegalStateException(INVALID_REVIEW_MESSAGE);
        }
        Element reviewElement = xmlDocument.createElement(REVIEW_ELEMENT_TAG);
        Node nodeToAppendChild=xmlDocument.getDocumentElement()==null ? xmlDocument : xmlDocument.getDocumentElement();
        nodeToAppendChild.appendChild(reviewElement);
        reviewElement.setAttribute(ID_ATTRIBUTE_TAG, reviewID.toString());
        
        Long surveyID = new SurveyRepositoryImpl().getSurveyID(review.getSurvey());
        if (surveyID == null) {
            throw new IllegalStateException(INVALID_SURVEY_MESSAGE);
        }
        reviewElement.setAttribute(SURVEY_ID_ATTRIBUTE_TAG,surveyID.toString());
        return reviewElement;
    }
    /**
     * Initializes the XML document as a Survey Service
     * @param survey Survey with the survey which document is being represented on
     * @return Element with the element which document was created on
     */
    private Element initializeSurveyDocument(Survey survey){
        Long surveyID = new SurveyRepositoryImpl().getSurveyID(survey);
        if (surveyID == null) {
            throw new IllegalStateException(INVALID_SURVEY_MESSAGE);
        }
        Element surveyRootElement = xmlDocument.createElement(SURVEY_ELEMENT_TAG);
        Node nodeToAppendChild=xmlDocument.getDocumentElement()==null ? xmlDocument : xmlDocument.getDocumentElement();
        nodeToAppendChild.appendChild(surveyRootElement);
        surveyRootElement.setAttribute(ID_ATTRIBUTE_TAG, surveyID.toString());
        return surveyRootElement;
    }
    /**
     * Adds the survey flux to the current service XML document
     * @param graph Graph with the survey flux
     * @param xmlRootElement Element with the element which holds the root of the XML 
     * document
     */
    private void addSurveyFlux(Graph graph,Element xmlRootElement){
        buildQuestionList(graph, xmlRootElement);
        buildQuestionGraph(graph, xmlRootElement);
    }
    /**
     * Builds the Review's question list and appends it to the root element.
     *
     * @param reviewGraph the review's question graph
     * @param reviewElement parent element
     */
    private void buildQuestionList(Graph reviewGraph, Element reviewElement) {

        Element questionsElement = xmlDocument.createElement(QUESTIONS_ELEMENT_TAG);
        reviewElement.appendChild(questionsElement);

        for (Vertex vert : reviewGraph.allVertices()) {

            Question question = vert.getElement();

            switch (question.getType()) {
                case BINARY:
                    buildBinaryQuestion(question, questionsElement);
                    break;

                case MULTIPLE_CHOICE:
                    buildMultipleChoiceQuestion(question, questionsElement);
                    break;

                case QUANTITATIVE:
                    buildQuantitativeQuestion(question, questionsElement);
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
     * @param questionsElement question list element
     */
    private void buildBinaryQuestion(Question question,Element questionsElement) {

        Element questionElement = xmlDocument.createElement(BINARY_QUESTION_ELEMENT_TAG);
        questionElement.setAttribute(QUESTION_ID_ATTRIBUTE_TAG, question.getQuestionID());

        Element binaryTextElement = xmlDocument.createElement(TEXT_ELEMENT_TAG);
        binaryTextElement.appendChild(xmlDocument.createTextNode(question.getQuestionText()));

        questionElement.appendChild(binaryTextElement);

        questionsElement.appendChild(questionElement);
    }

    /**
     * Builds a multiple choice question element and appends it to the question
     * list element.
     *
     * @param question question
     * @param questionsElement question list element
     */
    private void buildMultipleChoiceQuestion(Question question, Element questionsElement) {
        Element questionElement = xmlDocument.createElement(MULTIPLE_CHOICE_QUESTION_ELEMENT_TAG);
        questionElement.setAttribute(QUESTION_ID_ATTRIBUTE_TAG, question.getQuestionID());

        Element multipleChoiceTextElement = xmlDocument.createElement(TEXT_ELEMENT_TAG);
        multipleChoiceTextElement.appendChild(xmlDocument.createTextNode(question.getQuestionText()));
        questionElement.appendChild(multipleChoiceTextElement);

        for (QuestionOption option : question.getOptionList()) {
            Element multipleChoiceOptionElement = xmlDocument.createElement(OPTION_ELEMENT_TAG);

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
     * @param questionsElement question list element
     */
    private void buildQuantitativeQuestion(Question question, Element questionsElement) {
        Element questionElement = xmlDocument.createElement(QUANTITATIVE_QUESTION_ELEMENT_TAG);
        questionElement.setAttribute(QUESTION_ID_ATTRIBUTE_TAG, question.getQuestionID());

        Element quantitativeQuestionText = xmlDocument.createElement(TEXT_ELEMENT_TAG);
        quantitativeQuestionText.appendChild(xmlDocument.createTextNode(question.getQuestionText()));
        questionElement.appendChild(quantitativeQuestionText);

        List<QuestionOption> optionList = question.getOptionList();
        int lastIndex = optionList.size() - 1;

        Element quantitativeQuestionMinScaleValue = xmlDocument.createElement(MIN_SCALE_VALUE_ELEMENT_TAG);
        quantitativeQuestionMinScaleValue.appendChild(xmlDocument.createTextNode(optionList.get(0).toString()));
        questionElement.appendChild(quantitativeQuestionMinScaleValue);

        Element quantitativeQuestionMaxScaleValue = xmlDocument.createElement(MAX_SCALE_VALUE_ELEMENT_TAG);
        quantitativeQuestionMaxScaleValue.appendChild(xmlDocument.createTextNode(optionList.get(lastIndex).toString()));
        questionElement.appendChild(quantitativeQuestionMaxScaleValue);

        questionsElement.appendChild(questionElement);
    }
    /**
     * Builds a question graph element and appends it to the review element.
     * @param reviewGraph review's question graph
     * @param reviewElement review element
     */
    private void buildQuestionGraph(Graph reviewGraph, Element reviewElement) {

        Element questionGraphElement = xmlDocument.createElement(QUESTION_GRAPH_ELEMENT_TAG);
        reviewElement.appendChild(questionGraphElement);

        Iterable<Vertex> vertices = reviewGraph.allVertices();

        for (Vertex vert : vertices) {
            Iterable<Edge> outgoingEdges = vert.getAllOutgoingEdges();

            if (outgoingEdges.iterator().hasNext()) {
                Element questionElement = xmlDocument.createElement(QUESTION_ELEMENT_TAG);
                questionGraphElement.appendChild(questionElement);
                questionElement.setAttribute(QUESTION_ID_ATTRIBUTE_TAG, vert.getElement().getQuestionID());

                for (Edge edge : outgoingEdges) {
                    Element nextQuestionElement = xmlDocument.createElement(NEXT_QUESTION_ELEMENT_TAG);
                    questionElement.appendChild(nextQuestionElement);
                    nextQuestionElement.setAttribute(OPTION_ATTRIBUTE_TAG, edge.getElement().toString());
                    nextQuestionElement.setAttribute(QUESTION_ID_ATTRIBUTE_TAG, edge.getDestinationVertexElement().getQuestionID());
                }
            }
        }
    }
    /**
     * Prepares the current XML document for the reviews answers and suggetions
     * @param review Review with the current review
     * @param xmlRootElement Element with the element which holds the root of the 
     * current XML document
     */
    private void finalizeReviewXMLDocument(Review review,Element xmlRootElement){
        Element currentQuestionElement = xmlDocument.createElement(CURRENT_QUESTION_ELEMENT_TAG);
        xmlRootElement.appendChild(currentQuestionElement);
        currentQuestionElement.setAttribute(QUESTION_ID_ATTRIBUTE_TAG, review.getCurrentQuestion().getQuestionID());
        //Suggestion's text is initially empty
        Element suggestionElement = xmlDocument.createElement(SUGGESTION_ELEMENT_TAG);
        xmlRootElement.appendChild(suggestionElement);
        Element suggestionTextElement = xmlDocument.createElement(TEXT_ELEMENT_TAG);
        suggestionElement.appendChild(suggestionTextElement);

        Element answersElement = xmlDocument.createElement(ANSWERS_ELEMENT_TAG);
        xmlRootElement.appendChild(answersElement);
    }
    /**
     * Method that represents the current XML document as a String
     * @return String with the XML document as a String
     */
    private String documentAsString(){
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(xmlDocument);
            StreamResult result = new StreamResult(sw);

            transformer.transform(source, result);

            return sw.toString();
        } catch (TransformerException transformException){
            throw new IllegalStateException(transformException.getMessage());
        }
    }
    /**
     * Builds a new SurveyFluxXMLService for representing a certain service 
     * @throws ParserConfigurationException Throws {@link ParserConfigurationException} if an error occures 
     * during the creation of the general document
     */
    private SurveyFluxXMLService() throws ParserConfigurationException{
        this.xmlDocument=DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    }
}
