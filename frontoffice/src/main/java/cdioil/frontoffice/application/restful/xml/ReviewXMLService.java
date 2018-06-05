package cdioil.frontoffice.application.restful.xml;

import cdioil.application.utils.Edge;
import cdioil.application.utils.Graph;
import cdioil.application.utils.Vertex;
import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import cdioil.domain.Review;
import cdioil.persistence.impl.ReviewRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import java.io.StringWriter;
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

/**
 * 
 * @author António Sousa [1161371]
 */
public class ReviewXMLService {

    /**
     * Builds an XML file for a given review and returns its content.
     * @param review review
     * @return String with the XML file's content
     */
    public static String createReviewXML(Review review) {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            
            Element reviewElement = doc.createElement("Review");
            doc.appendChild(reviewElement);
            Long reviewID = new ReviewRepositoryImpl().getReviewID(review);
            //check if review has been persisted
            if(reviewID == null){
               return null;
            }
            reviewElement.setAttribute("id", reviewID.toString());
            
            Long surveyID = new SurveyRepositoryImpl().getSurveyID(review.getSurvey());
            if(surveyID == null){
                return null;
            }
            reviewElement.setAttribute("surveyID", surveyID.toString());
            
            Graph reviewGraph = review.getAnswerGraph();
            buildQuestionList(reviewGraph, doc, reviewElement);
            buildQuestionGraph(reviewGraph, doc, reviewElement);

            Element currentQuestionElement = doc.createElement("CurrentQuestion");
            reviewElement.appendChild(currentQuestionElement);
            currentQuestionElement.setAttribute("questionID", review.getCurrentQuestion().getQuestionID());

            //Suggestion's text is initially empty
            Element suggestionElement = doc.createElement("Suggestion");
            reviewElement.appendChild(suggestionElement);
            Element suggestionTextElement = doc.createElement("Text");
            suggestionElement.appendChild(suggestionTextElement);

            Element answersElement = doc.createElement("Answers");
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
            return null;
        }
    }

    /**
     * Builds the Review's question list and appends it to the root element.
     * @param reviewGraph the review's question graph
     * @param doc XML document
     * @param reviewElement parent element
     */
    private static void buildQuestionList(Graph reviewGraph, Document doc, Element reviewElement) {

        Element questionsElement = doc.createElement("Questions");
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
     * Builds a binary question element and appends it to the question list element.
     * @param question question
     * @param doc XML document
     * @param questionsElement question list element 
     */
    private static void buildBinaryQuestion(Question question, Document doc, Element questionsElement) {

        Element questionElement = doc.createElement("BinaryQuestion");
        questionElement.setAttribute("questionID", question.getQuestionID());

        Element binaryTextElement = doc.createElement("Text");
        binaryTextElement.appendChild(doc.createTextNode(question.getQuestionText()));

        questionElement.appendChild(binaryTextElement);

        questionsElement.appendChild(questionElement);
    }

    /**
     * Builds a multiple choice question element and appends it to the question list element.
     * @param question question
     * @param doc XML document
     * @param questionsElement question list element 
     */
    private static void buildMultipleChoiceQuestion(Question question, Document doc, Element questionsElement) {
        Element questionElement = doc.createElement("MultipleChoiceQuestion");
        questionElement.setAttribute("questionID", question.getQuestionID());

        Element multipleChoiceTextElement = doc.createElement("Text");
        multipleChoiceTextElement.appendChild(doc.createTextNode(question.getQuestionText()));
        questionElement.appendChild(multipleChoiceTextElement);

        for (QuestionOption option : question.getOptionList()) {
            Element multipleChoiceOptionElement = doc.createElement("Option");

            multipleChoiceOptionElement.setAttribute("text", option.toString());

            questionElement.appendChild(multipleChoiceOptionElement);
        }

        questionsElement.appendChild(questionElement);
    }

    /**
     * Builds a quantitative question element and appends it to the question list element.
     * @param question question
     * @param doc XML document
     * @param questionsElement question list element 
     */
    private static void buildQuantitativeQuestion(Question question, Document doc, Element questionsElement) {
        Element questionElement = doc.createElement("QuantitativeQuestion");
        questionElement.setAttribute("questionID", question.getQuestionID());

        Element quantitativeQuestionText = doc.createElement("Text");
        quantitativeQuestionText.appendChild(doc.createTextNode(question.getQuestionText()));
        questionElement.appendChild(quantitativeQuestionText);

        List<QuestionOption> optionList = question.getOptionList();
        int lastIndex = optionList.size() - 1;

        Element quantitativeQuestionMinScaleValue = doc.createElement("MinScaleValue");
        quantitativeQuestionMinScaleValue.appendChild(doc.createTextNode(optionList.get(0).toString()));
        questionElement.appendChild(quantitativeQuestionMinScaleValue);

        Element quantitativeQuestionMaxScaleValue = doc.createElement("MaxScaleValue");
        quantitativeQuestionMaxScaleValue.appendChild(doc.createTextNode(optionList.get(lastIndex).toString()));
        questionElement.appendChild(quantitativeQuestionMaxScaleValue);

        questionsElement.appendChild(questionElement);
    }

    /**
     * Builds a question graph element and appends it to the review element.
     * @param reviewGraph review's question graph
     * @param doc XML document
     * @param reviewElement review element
     */
    private static void buildQuestionGraph(Graph reviewGraph, Document doc, Element reviewElement) {

        Element questionGraphElement = doc.createElement("QuestionGraph");
        reviewElement.appendChild(questionGraphElement);

        Question firstQuestion = reviewGraph.getFirstQuestion();
        Element firstQuestionElement = doc.createElement("Question");
        questionGraphElement.appendChild(firstQuestionElement);
        firstQuestionElement.setAttribute("questionID", firstQuestion.getQuestionID());
        buildGraphFlow(firstQuestionElement, doc, firstQuestion, reviewGraph);
    }

    /**
     * Builds the question graph element by setting the next question elements recursively. 
     * @param questionElement current question element
     * @param doc XML document
     * @param question question
     * @param reviewGraph review's question graph
     */
    private static void buildGraphFlow(Element questionElement, Document doc, Question question, Graph reviewGraph) {

        Iterable<Edge> outgoingEdges = reviewGraph.outgoingEdges(question);

        for (Edge edge : outgoingEdges) {

            Question nextQuestion = edge.getDestinationVertexElement();

            Element nextQuestionElement = doc.createElement("NextQuestion");
            questionElement.appendChild(nextQuestionElement);
            nextQuestionElement.setAttribute("option", edge.getElement().toString());

            Element qElement = doc.createElement("Question");
            nextQuestionElement.appendChild(qElement);
            qElement.setAttribute("questionID", nextQuestion.getQuestionID());

            buildGraphFlow(qElement, doc, nextQuestion, reviewGraph);
        }
    }
}
