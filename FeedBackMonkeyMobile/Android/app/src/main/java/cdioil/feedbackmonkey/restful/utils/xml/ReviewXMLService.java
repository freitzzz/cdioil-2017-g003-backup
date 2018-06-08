package cdioil.feedbackmonkey.restful.utils.xml;

import android.os.Bundle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Class to handle communication between the XML file that holds the survey graph and the answers
 * of the user's review
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ReviewXMLService {
    /**
     * Index for the current question node of the review XML file.
     */
    private final static int CURRENT_QUESTION_NODE_INDEX = 2;
    /**
     * Index for the suggestion node of the review XML file.
     */
    private final static int SUGGESTION_NODE_INDEX = 3;
    /**
     * Index for the answer map node of the review XML file.
     */
    private final static int ANSWER_MAP_NODE_INDEX = 4;
    /**
     * Static reference to single instance of ReviewXMLService.
     */
    private static ReviewXMLService instance;
    /**
     * Current review file.
     */
    private File reviewFile;
    /**
     * Current review document.
     */
    private Document document;
    /**
     * Map used for fetching the review's question elements by questionID.
     */
    private Map<String, Element> reviewQuestionElements;

    /**
     * Creates a new single instance of ReviewXMLService.
     *
     * @return new instance of ReviewXMLService.
     */
    public static ReviewXMLService newInstance() {

        if (instance == null) {
            synchronized (ReviewXMLService.class) {
                if (instance == null) {
                    instance = new ReviewXMLService();
                }
            }
        }

        return instance;
    }


    /**
     * Private constructor used for instantiating data structures and hiding the implicit public one.
     */
    private ReviewXMLService() {
        reviewQuestionElements = new LinkedHashMap<>();
    }

    /**
     * Creates an XML File that will save the user's review information regarding a certain survey.
     *
     * @param fileContent String with the XML file content
     */
    public File createFile(File dirFile, String fileContent) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(new InputSource(new StringReader(fileContent)));
        Element reviewElement = document.getDocumentElement();
        String reviewID = reviewElement.getAttribute("id");
        String fileSurveyID = reviewElement.getAttribute("surveyID");

        String separator = "_";
        String fileName = "review".concat(separator).concat(reviewID).concat(separator).concat(fileSurveyID).concat(".xml");
        File newFile = new File(dirFile, fileName);
        FileOutputStream outputStream = new FileOutputStream(newFile);
        outputStream.write(fileContent.getBytes());
        outputStream.close();

        return newFile;
    }

    /**
     * Sets the file containing review data.
     *
     * @param file
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public void setFile(File file) throws ParserConfigurationException, IOException, SAXException {
        this.reviewFile = file;
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = documentBuilder.parse(reviewFile);
        if (document != null) {

            Element questionList = (Element) document.getElementsByTagName("Questions").item(0);

            List<Element> questions = getAllChildElements(questionList);

            for (Element question : questions) {
                String questionID = question.getAttribute("questionID");
                reviewQuestionElements.put(questionID, question);
            }
        }
    }

    /**
     * Method used for getting all of the given Element's sub-Elements.
     *
     * @param element preceding Element
     * @return list of all the Element's sub-Elements.
     */
    private List<Element> getAllChildElements(Element element) {
        List<Element> childElements = new ArrayList<>();

        NodeList nodeList = element.getChildNodes();

        int nodeListSize = nodeList.getLength();

        for (int i = 0; i < nodeListSize; i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) node;
                childElements.add(childElement);
            }
        }

        return childElements;
    }

    /**
     * Saves a user's answer to a certain question of a review on its XML file.
     *
     * @param answer Content of the answer itself
     */
    public boolean saveAnswer(String answer) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        boolean canContinueReview = false;
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = documentBuilder.parse(reviewFile);

        if (document != null) {
            Element answerMapElement = (Element) document.getElementsByTagName("Answers").item(0);

            Element answerElement = document.createElement("Answer");
            answerElement.setAttribute("questionID", getCurrentQuestionID());
            answerElement.setAttribute("text", answer);
            answerMapElement.appendChild(answerElement);

            canContinueReview = updateCurrentQuestion(answer);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            Result xmlOutput = new StreamResult(reviewFile);
            Source input = new DOMSource(document);
            transformer.transform(input, xmlOutput);
        }
        return canContinueReview;
    }

    /**
     * Undoes an answer that was given by a user for a review on its XML file.
     */
    public void undoAnswer() {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(reviewFile);

            if (document != null) {
                NodeList nodeList = document.getChildNodes();
                Element answerMapElement = (Element) nodeList.item(ANSWER_MAP_NODE_INDEX);
                NodeList answersNodeList = answerMapElement.getChildNodes();
                answerMapElement.removeChild(answersNodeList.item(answersNodeList.getLength() - 1));
                Element newCurrentQuestionElement = (Element) answersNodeList.item(answersNodeList.getLength() - 1);
                String newCurrentQuestionID = newCurrentQuestionElement.getAttribute("questionID");
                Element currentQuestionElement = (Element) nodeList.item(CURRENT_QUESTION_NODE_INDEX);
                currentQuestionElement.setAttribute("questionID", newCurrentQuestionID);
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Result xmlOutput = new StreamResult(reviewFile);
                Source input = new DOMSource(document);
                transformer.transform(input, xmlOutput);
            }
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the current question node of the review XML file
     *
     * @param answer Answer given to the question that was answered
     */
    private boolean updateCurrentQuestion(String answer) {

        Element questionGraphElement = (Element) document.getElementsByTagName("QuestionGraph").item(0);

        Element currentQuestionElement = (Element) document.getElementsByTagName("CurrentQuestion").item(0);

        String currentQuestionID = getCurrentQuestionID();

        List<Element> graphQuestionElementList = getAllChildElements(questionGraphElement);

        for (Element graphQuestionElement : graphQuestionElementList) {
            String graphQuestionID = graphQuestionElement.getAttribute("questionID");
            if (currentQuestionID.equals(graphQuestionID)) {
                List<Element> nextQuestionElementList = getAllChildElements(graphQuestionElement);
                for (Element nextQuestionElement : nextQuestionElementList) {
                    if (answer.equals(nextQuestionElement.getAttribute("option"))) {
                        currentQuestionElement.setAttribute("questionID",
                                nextQuestionElement.getAttribute("questionID"));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Parses the Review XML file to obtain the information (question ID, question text, question type
     * and question options) of the current question that the user is at while answering a survey.
     * <p>
     * The information is in a list of Strings:
     * <p>
     * Index 0 of the list will contain the questions ID.
     * Index 1 of the list will contain the questions text (the question itself).
     * Index 2 of the list will contain the questions type ("B" for Binary, "Q" for Quantitative and
     * "MC" for Multiple Choice)
     * <p>
     * From index 3 and onward the list will contain all of the options that the question has
     * (e.g. for a Binary Question index 3 will have "true" and index 4 will have "false";
     * <p>
     * for a Quantitative Question index 3 will have "1", index 4 will have "2", index 5 will have "3",
     * so on and so forth until it reaches the maximum value of the scale;
     * <p>
     * for a Multiple Choice Question index 3 will have "Too salty", index 4 will have "Too sour",
     * index 5 will have "Too sweet", so on and so forth until it has all of the options of the question)
     * <p>
     * This method returns this much information to facilitate the manipulation of the Question's
     * Activities to answer a survey.
     *
     * @return Bundle containing a Question's information
     */
    public Bundle getCurrentQuestionBundle() {

        Bundle bundle = new Bundle();

        String currentQuestionID = getCurrentQuestionID();

        Element questionElement = reviewQuestionElements.get(currentQuestionID);

        if (questionElement == null) {
            return null;
        }

        String questionID = questionElement.getAttribute("questionID");

        bundle.putString("questionID", questionID);
        bundle.putString("questionText", questionElement.getElementsByTagName("Text").item(0).getTextContent());
        ArrayList<String> questionOptions = new ArrayList<>();

        String questionType = questionElement.getNodeName();

        if (questionType.equals("BinaryQuestion")) {
            bundle.putString("currentQuestionType", "B");
            questionOptions.add("true");
            questionOptions.add("false");

        } else if (questionType.equals("QuantitativeQuestion")) {

            bundle.putString("currentQuestionType", "Q");

            double scaleMinValue = Double.parseDouble(questionElement.
                    getElementsByTagName("MinScaleValue").item(0).getTextContent());
            double scaleMaxValue = Double.parseDouble(questionElement.
                    getElementsByTagName("MaxScaleValue").item(0).getTextContent());

            for (double scaleValue = scaleMinValue; scaleValue <= scaleMaxValue; scaleValue++) {
                questionOptions.add(Double.toString(scaleValue));
            }

        } else if (questionType.equals("MultipleChoiceQuestion")) {

            bundle.putString("currentQuestionType", "MC");

            NodeList optionsNodeList = questionElement.getElementsByTagName("Option");

            for (int k = 0; k < optionsNodeList.getLength(); k++) {
                questionOptions.add(((Element) optionsNodeList.item(k)).getAttribute("text"));
            }
        }

        bundle.putStringArrayList("options", questionOptions);

        return bundle;
    }


    /**
     * Returns the value of the current question's ID.
     *
     * @return the current question's ID.
     */
    private String getCurrentQuestionID() {

        Element currentQuestionElement = (Element) document.getElementsByTagName("CurrentQuestion").item(0);

        return currentQuestionElement.getAttribute("questionID");
    }

    /**
     *
     * @return
     */
    public String getReviewID(){
        return document.getDocumentElement().getAttribute("id");
    }
    /**
     * Saves a user's suggestion of a review.
     *
     * @param suggestion Content of the suggestion submitted by the user
     */
    public void saveSuggestion(String suggestion) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(reviewFile);

            if (document != null) {
                Element suggestionElement = (Element) document.getElementsByTagName("Suggestion").item(0);

                Element textElement = (Element) suggestionElement.getElementsByTagName("Text").item(0);
                textElement.setTextContent(suggestion);

                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Result xmlOutput = new StreamResult(reviewFile);
                Source input = new DOMSource(document);
                transformer.transform(input, xmlOutput);
            }
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses the XML File's content to a string so it can be sent to the server
     *
     * @return XML File content in a String
     */
    public String parseFileContentToString() {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(reviewFile);
            StringWriter stringWriter = new StringWriter();
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.transform(new DOMSource(document), new StreamResult(stringWriter));
            return stringWriter.toString();
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
