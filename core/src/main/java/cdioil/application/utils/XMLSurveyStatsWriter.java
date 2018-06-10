/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.logging.Level;
import java.util.logging.Logger;
import cdioil.domain.Question;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.Set;
import java.util.Map;
import java.io.File;

/**
 * Exports statistics of a survey to a .xml file.
 *
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class XMLSurveyStatsWriter implements SurveyStatsWriter {

    //Constants
    /**
     * Field that identifies the list of questions.
     */
    private static final String QUESTIONS_LIST = "Questions";

    /**
     * Field that identifies the survey.
     */
    private static final String SURVEY = "Survey";

    /**
     * Field that identifies the question by its ID.
     */
    private static final String QUESTION_ID = "questionID";

    /**
     * Field that identifies a binary question.
     */
    private static final String BINARY_QUESTION_TYPE = "BinaryQuestion";

    /**
     * Field that identifies a quantitative question.
     */
    private static final String QUANTITATIVE_QUESTION_TYPE = "QuantitativeQuestion";

    /**
     * Field that identifies the list of binary questions.
     */
    private static final String BINARY_QUESTIONS_LIST = "BinaryQuestions";

    /**
     * Field that identifies the list of quantitative questions.
     */
    private static final String QUANTITATIVE_QUESTIONS_LIST = "QuantitativesQuestions";

    /**
     * Field that identifies the number of answers to the question.
     */
    private static final String TOTAL = "Total";

    /**
     * Field that identifies the average of the answers to the question.
     */
    private static final String AVG = "Average";

    /**
     * Field that identifies the mean deviation of the answers to the question.
     */
    private static final String MEAN_DEVIATION = "MeanDeviation";

    /**
     * Field that identifies the text of the question.
     */
    private static final String QUESTION_TEXT = "QuestionText";

    /**
     * Field that identifies the survey by its ID.
     */
    private static final String SURVEY_ID = "surveyID";
    
    /**
     * Field that identifies the survey by its title.
     */
    private static final String SURVEY_NAME = "SurveyTitle";
    
    //Attributes
    /**
     * File to write.
     */
    private final File file;

    /**
     * ID of the survey.
     */
    private final long surveyID;

    /**
     * Name of the survey.
     */
    private final String surveyName;

    /**
     * Average value for answers of binary questions.
     */
    private final Map<Question, Double> binaryMean;

    /**
     * Average value for answers of quantitative questions.
     */
    private final Map<Question, Double> quantitativeMean;

    /**
     * Mean deviation for answers of binary questions.
     */
    private final Map<Question, Double> binaryMeanDeviation;

    /**
     * Mean deviation for answers of quantitative questions.
     */
    private final Map<Question, Double> quantitativeMeanDeviation;

    /**
     * Total of binary questions.
     */
    private final Map<Question, Integer> binaryTotal;

    /**
     * Total of quantitative questions.
     */
    private final Map<Question, Integer> quantitativeTotal;

    /**
     * Creates a new XMLSurveyStatsWriter.
     *
     * @param filename Path of the file
     * @param surveyID ID of the survey
     * @param surveyName Name of the survey
     * @param binaryTotal Total of answers to binary questions
     * @param quantitativeTotal Total of answers to quantitative questions
     * @param binaryMean Average value for binary answers
     * @param quantitativeMean Average value for quantitative answers
     * @param binaryMeanDeviation Mean deviation for binary answers
     * @param quantitativeMeanDeviation Mean deviation for quantitative answers
     *
     */
    public XMLSurveyStatsWriter(String filename, long surveyID, String surveyName, Map<Question, Integer> binaryTotal,
            Map<Question, Integer> quantitativeTotal, Map<Question, Double> binaryMean, Map<Question, Double> quantitativeMean,
            Map<Question, Double> binaryMeanDeviation, Map<Question, Double> quantitativeMeanDeviation) {
        this.file = new File(filename);
        this.surveyID = surveyID;
        this.surveyName = surveyName;
        this.binaryTotal = binaryTotal;
        this.quantitativeTotal = quantitativeTotal;
        this.quantitativeMeanDeviation = quantitativeMeanDeviation;
        this.binaryMeanDeviation = binaryMeanDeviation;
        this.quantitativeMean = quantitativeMean;
        this.binaryMean = binaryMean;
    }

    /**
     * Writes the statistics into a XML file.
     *
     * @return true, if the statistics are successfully exported. Otherwise, returns false
     */
    @Override
    public boolean writeStats() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            //Survey element
            Element rootElement = doc.createElement(SURVEY);
            doc.appendChild(rootElement);
            //Survey ID field
            rootElement.setAttribute(SURVEY_ID, String.valueOf(surveyID));
            //Survey title field
            Element surveyNameElement = doc.createElement(SURVEY_NAME);
            surveyNameElement.appendChild(doc.createTextNode(surveyName));
            rootElement.appendChild(surveyNameElement);
            //Questions element
            Element questionsElement = doc.createElement(QUESTIONS_LIST);
            rootElement.appendChild(questionsElement);

            writeBinaryStatistics(doc, questionsElement);

            writeQuantitativeStatistics(doc, questionsElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            try {
                transformer.transform(source, result);
                return true;
            } catch (TransformerException ex) {
                Logger.getLogger(XMLSurveyStatsWriter.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
            Logger.getLogger(XMLSurveyStatsWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Writes the binary questions statistics into a XML file.
     *
     * @param doc Representation of the XML document
     * @param questionsElement XML element that contains all questions
     */
    private void writeBinaryStatistics(Document doc, Element questionsElement) {
        Set<Map.Entry<Question, Double>> binaryAnswers = binaryMean.entrySet();

        if (!binaryAnswers.isEmpty()) {
            Element binaryQuestionsElement = doc.createElement(BINARY_QUESTIONS_LIST);
            questionsElement.appendChild(binaryQuestionsElement);
            for (Map.Entry<Question, Double> entry : binaryAnswers) {
                Question q = entry.getKey();
                String type = q.getType().toString();
                Element binaryQuestionElement;
                binaryQuestionElement = doc.createElement(BINARY_QUESTION_TYPE);

                writeQuestionStatistics(doc, binaryQuestionElement, q, binaryTotal, binaryMean, binaryMeanDeviation);
                binaryQuestionsElement.appendChild(binaryQuestionElement);
            }
        }
    }

    /**
     * Writes the quantitative questions statistics into a XML file.
     *
     * @param doc Representation of the XML document
     * @param questionsElement XML element that contains all questions
     */
    private void writeQuantitativeStatistics(Document doc, Element questionsElement) {
        Set<Map.Entry<Question, Double>> quantitativeAnswers = quantitativeMean.entrySet();

        if (!quantitativeAnswers.isEmpty()) {
            Element quantitativeQuestionsElement = doc.createElement(QUANTITATIVE_QUESTIONS_LIST);
            questionsElement.appendChild(quantitativeQuestionsElement);

            for (Map.Entry<Question, Double> entry : quantitativeAnswers) {
                Question q = entry.getKey();
                String type = q.getType().toString();
                Element quantitativeQuestionElement = doc.createElement(QUANTITATIVE_QUESTION_TYPE);

                quantitativeQuestionsElement.appendChild(quantitativeQuestionElement);
                quantitativeQuestionElement.setAttribute(QUESTION_ID, q.getQuestionID());

                writeQuestionStatistics(doc, quantitativeQuestionsElement, q, quantitativeTotal, quantitativeMean, quantitativeMeanDeviation);
                quantitativeQuestionsElement.appendChild(quantitativeQuestionElement);
            }
        }
    }

    /**
     * Writes the statistics related to a certain question into a XML file.
     *
     * @param doc Representation of the XML document
     * @param questionElement XML element that represents the question
     * @param question Question itself
     * @param total Total answers for the question
     * @param mean Calculated mean of the question
     * @param meanDeviation Calculated mean deviation of the question
     */
    private void writeQuestionStatistics(Document doc, Element questionElement, Question question,
            Map<Question, Integer> total, Map<Question, Double> mean, Map<Question, Double> meanDeviation) {
        //Question text field
        Element questionTextElement = doc.createElement(QUESTION_TEXT);
        questionTextElement.appendChild(doc.createTextNode(question.getQuestionText()));
        questionElement.appendChild(questionTextElement);
        //Total answers field
        Element totalElement = doc.createElement(TOTAL);
        totalElement.appendChild(doc.createTextNode(String.valueOf(total.get(question))));
        questionElement.appendChild(totalElement);
        //Average field
        Element averageElement = doc.createElement(AVG);
        averageElement.appendChild(doc.createTextNode(String.valueOf(mean.get(question))));
        questionElement.appendChild(averageElement);
        //Mean deviation field
        Element meanDeviationElement = doc.createElement(MEAN_DEVIATION);
        meanDeviationElement.appendChild(doc.createTextNode(String.valueOf(meanDeviation.get(question))));
        questionElement.appendChild(meanDeviationElement);
    }
}
