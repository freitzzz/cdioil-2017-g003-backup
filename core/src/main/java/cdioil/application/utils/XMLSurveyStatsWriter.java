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

    //Attributes
    /**
     * File to write.
     */
    private final File file;

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

    //Constants
    /**
     * Field that represents a list of questions.
     */
    private static final String QUESTIONS_LIST = "Questions";

    /**
     * Field that identified the survey.
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
     * Field that represents a list of binary questions.
     */
    private static final String BINARY_QUESTIONS_LIST = "BinaryQuestions";

    /**
     * Field that represents a list of quantitative questions.
     */
    private static final String QUANTITATIVE_QUESTIONS_LIST = "QuantitativesQuestions";

    /**
     * Field that contains the number of answers to the question.
     */
    private static final String TOTAL = "Total";

    /**
     * Field that contains the average of the answers to the question.
     */
    private static final String AVG = "Average";

    /**
     * Field that contains the mean deviation of the answers to the question.
     */
    private static final String MEAN_DEVIATION = "MeanDeviation";

    /**
     * Creates a new XMLSurveyStatsWriter.
     *
     * @param filename Path of the file
     * @param binaryTotal Total of answers to binary questions
     * @param quantitativeTotal Total of answers to quantitative questions
     * @param binaryMean Average value for binary answers
     * @param quantitativeMean Average value for quantitative answers
     * @param binaryMeanDeviation Mean deviation for binary answers
     * @param quantitativeMeanDeviation Mean deviation for quantitative answers
     *
     */
    public XMLSurveyStatsWriter(String filename, Map<Question, Integer> binaryTotal, Map<Question, Integer> quantitativeTotal, Map<Question, Double> binaryMean,
            Map<Question, Double> quantitativeMean, Map<Question, Double> binaryMeanDeviation, Map<Question, Double> quantitativeMeanDeviation) {
        this.file = new File(filename);
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

            Element rootElement = doc.createElement(SURVEY);
            doc.appendChild(rootElement);

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
        Element totalElement = doc.createElement(TOTAL);
        totalElement.appendChild(doc.createTextNode(String.valueOf(total.get(question))));
        questionElement.appendChild(totalElement);

        Element meanElement = doc.createElement(AVG);
        meanElement.appendChild(doc.createTextNode(String.valueOf(mean.get(question))));
        questionElement.appendChild(meanElement);

        Element meanDeviationElement = doc.createElement(MEAN_DEVIATION);
        meanDeviationElement.appendChild(doc.createTextNode(String.valueOf(meanDeviation.get(question))));
        questionElement.appendChild(meanDeviationElement);
    }
}
