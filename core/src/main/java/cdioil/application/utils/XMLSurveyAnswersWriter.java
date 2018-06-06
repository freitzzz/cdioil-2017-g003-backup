/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.Answer;
import cdioil.domain.Question;
import cdioil.domain.Review;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Utilitary class that writes survey answers into a XML file
 *
 * @author <a href="1161181@isep.ipp.pt">Margarida Guerra</a>
 */
public class XMLSurveyAnswersWriter implements SurveyAnswersWriter {

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
     * File with the file that is going to be written with all survey answers
     */
    private final File file;
    /**
     * List with all survey reviews being exported
     */
    private final List<Review> surveyReviews;

    /**
     * Builds a new XMLSurveyAnswersWriter with the file that is going to be written all survey answers
     *
     * @param filename String with the filename that is going to be written all survey answers
     * @param surveyReviews List with all survey reviews being exported
     */
    public XMLSurveyAnswersWriter(String filename, List<Review> surveyReviews) {
        this.file = new File(filename);
        this.surveyReviews = surveyReviews;
    }

    /**
     * Method that writes all answers from a certain survey into a XML file
     *
     * @return boolean true if all answers were writed with success, false if not
     */
    @Override
    public boolean write() {
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
            StreamResult result = new StreamResult(file);
            try {
                transformer.transform(source, result);
            } catch (TransformerException ex) {
                Logger.getLogger(XMLSurveyStatsWriter.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
            Logger.getLogger(XMLSurveyStatsWriter.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
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
