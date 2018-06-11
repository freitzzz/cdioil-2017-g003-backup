package cdioil.application.utils.services.xml;

import cdioil.application.utils.XMLSurveyStatsWriter;
import cdioil.domain.Answer;
import cdioil.domain.Question;
import cdioil.domain.Review;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * SurveyAnswersXMLService class that represents a service that allows the serialization 
 * of the reviews of a Survey into a XML document
 * @author <a href="1161191@isep.ipp.pt">Margarida Guerra</a>
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 6.0 & 7.0 of FeedbackMonkey
 */
public final class SurveyAnswersXMLService {
    /**
     * Constant that represents the label used for the survey identifier on the XML file
     */
    private static final String SURVEY_LABEL = "Survey";
    /**
     * Constant that represents the label used for the question identifier on the XML file
     */
    private static final String QUESTION_LABEL = "Question";
    /**
     * Constant that represents the label used for the type of question identifier on the XML file
     */
    private static final String TYPE_LABEL = "Type";
    /**
     * Constant that represents the label used for the answer identifier on the XML file
     */
    private static final String ANSWERS_LABEL = "Answer";
    /**
     * List with all survey reviews being exported
     */
    private final List<Review> surveyReviews;
    /**
     * Builds a new SurveyAnswersXMLService with the list of the reviews of a certain 
     * survey being serialized into a XML document
     * @param surveyReviews List with all the reviews of a certain Survey being serialized 
     * into a XML document
     */
    public SurveyAnswersXMLService(List<Review> surveyReviews){
        this.surveyReviews=surveyReviews;
    }
    /**
     * Method that serializes the current reviews of a certain survey into a XML document
     * @return String with the serialized survey reviews as a XML document
     */
    public String toXMLDocument(){
        return serializeReviewsXMLDocument();
    }
    /**
     * Method that serializes the reviews of a certain survey into a XML document
     * @return String with the XML document content containing the serialized survey reviews
     */
    private String serializeReviewsXMLDocument(){
        Map<Question, List<Answer>> mapQuestionAnswers = allQuestionsPerAnswers();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement(SURVEY_LABEL);
            doc.appendChild(rootElement);

            writeAnswers(doc, rootElement, mapQuestionAnswers);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter xmlDocument=new StringWriter();
            StreamResult result = new StreamResult(xmlDocument);
            transformer.transform(source,result);
            return xmlDocument.getBuffer().toString();
        } catch (ParserConfigurationException |TransformerException transformationException) {
            Logger.getLogger(XMLSurveyStatsWriter.class.getName()).log(Level.SEVERE, null, transformationException);
            throw new IllegalStateException(transformationException.getCause());
        }
    }
    /**
     * Method that returns a map with all answers given for each question
     *
     * @return Map with all answers given for each question
     */
    private Map<Question, List<Answer>> allQuestionsPerAnswers() {
        Map<Question, List<Answer>> mapQuestionAnswers = new HashMap<>();
        surveyReviews.forEach(t -> {
            Map<Question, Answer> surveyAnswers = t.getReviewQuestionAnswers();
            surveyAnswers.forEach((question, answer) -> {
                List<Answer> questionAnswers = mapQuestionAnswers.get(question);
                if (questionAnswers == null) {
                    questionAnswers = new ArrayList<>();
                    mapQuestionAnswers.put(question, questionAnswers);
                }
                questionAnswers.add(answer);
            });
        });
        return mapQuestionAnswers;
    }

    /**
     * Writes the answers into a XML file.
     *
     * @param doc Representation of the XML document
     * @param rootElement Root element
     * @param mapQuestionAnswers Map with all answers given for each question
     */
    private void writeAnswers(Document doc, Element rootElement, Map<Question, List<Answer>> mapQuestionAnswers) {

        for (Question question : mapQuestionAnswers.keySet()) {

            Element idQuestion = doc.createElement(QUESTION_LABEL);
            rootElement.appendChild(idQuestion);

            Attr attrType = doc.createAttribute("type");
            attrType.setValue(question.getType().name());
            idQuestion.setAttributeNode(attrType);

            Attr attrID = doc.createAttribute("ID");
            attrID.setValue(question.getQuestionID());
            idQuestion.setAttributeNode(attrID);

            for (Answer answer : mapQuestionAnswers.get(question)) {
                Element elementAnswer = doc.createElement(ANSWERS_LABEL);
                elementAnswer.appendChild(doc.createTextNode(answer.toString()));
                idQuestion.appendChild(elementAnswer);
            }
        }

    }
}
