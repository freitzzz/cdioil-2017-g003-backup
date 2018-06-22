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
 * @author <a href="1161371@isep.ipp.pt">António Sousa</a>
 */
public class ReviewXMLService {

    /**
     * Constant representing the XML file extension.
     */
    private static final String XML_EXTENSION = ".xml";
    /**
     * Static reference to single instance of ReviewXMLService.
     */
    private static ReviewXMLService instance;
    /**
     * Current review file.
     */
    private File reviewFile;
    /**
     * Current review XML document.
     */
    private Document document;
    /**
     * Map used for fetching the review's question elements by questionID.
     */
    private Map<String, Element> reviewQuestionElements;
    /**
     * String that holds the review ID.
     */
    private String reviewID;

    /**
     * Creates a new single instance of ReviewXMLService, if one does not already exist, or refers to the previously created instance.
     *
     * @return instance of ReviewXMLService.
     */
    public static ReviewXMLService instance() {

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
     * Returns the reviews ID
     *
     * @return String that holds the reviews ID
     */
    public String getReviewID(){
        return reviewID;
    }

    /**
     * Returns the review
     * @return
     */
    public String getSurveyEndDate(){return document.getElementById(ReviewFileTags.REVIEW_ELEMENT_TAG).getAttribute(ReviewFileTags.SURVEY_END_DATE_ATTRIBUTE);}
    /**
     * Creates an XML File that will save the user's review information regarding a certain survey.
     *
     * @param fileContent String with the XML file content
     */
    public void createNewReviewFile(File dirFile, String fileContent) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = documentBuilder.parse(new InputSource(new StringReader(fileContent)));

        buildReviewQuestionElements();

        Element reviewElement = document.getDocumentElement();
        String reviewID = reviewElement.getAttribute(ReviewFileTags.ID_ATTRIBUTE_TAG);
        String surveyID = reviewElement.getAttribute(ReviewFileTags.SURVEY_ID_ATTRIBUTE_TAG);
        this.reviewID = reviewID;
        String separator = "_";
        String fileName = "review".concat(separator).concat(reviewID).concat(separator).concat(surveyID).concat(XML_EXTENSION);
        reviewFile = new File(dirFile, fileName);
        FileOutputStream outputStream = new FileOutputStream(reviewFile);
        outputStream.write(fileContent.getBytes());
        outputStream.close();
    }

    /**
     * Builds a Map in which the keys represent the question ID's and values the associated Element.
     */
    private void buildReviewQuestionElements() {
        Element questionList = (Element) document.getElementsByTagName(ReviewFileTags.QUESTIONS_ELEMENT_TAG).item(0);

        List<Element> questions = getAllChildElements(questionList);

        for (Element question : questions) {
            String questionID = question.getAttribute(ReviewFileTags.QUESTION_ID_ATTRIBUTE_TAG);
            reviewQuestionElements.put(questionID, question);
        }
    }

    /**
     * Method used for getting all of the given Element's sub-Elements.
     * This is used since the Element's getChildNodes method does not return only Nodes of the type Element.
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

        Element answerMapElement = (Element) document.getElementsByTagName(ReviewFileTags.ANSWERS_ELEMENT_TAG).item(0);

        Element answerElement = document.createElement(ReviewFileTags.ANSWER_ELEMENT_TAG);
        answerElement.setAttribute(ReviewFileTags.QUESTION_ID_ATTRIBUTE_TAG, getCurrentQuestionID());
        answerElement.setAttribute(ReviewFileTags.TEXT_ATTRIBUTE_TAG, answer);
        answerMapElement.appendChild(answerElement);

        canContinueReview = updateCurrentQuestion(answer);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Result xmlOutput = new StreamResult(reviewFile);
        Source input = new DOMSource(document);
        transformer.transform(input, xmlOutput);

        if (!canContinueReview) {

            String newFileName = getFinishedReviewsDirectory().getAbsolutePath().concat(File.separator).concat(reviewFile.getName());
            File newFile = new File(newFileName);
            reviewFile.renameTo(newFile);
            reviewFile.delete();
            reviewFile = newFile;
        }

        return canContinueReview;
    }

    /**
     * Fetches the finished reviews directory or creates it if it does not exist.
     *
     * @return finished reviews directory
     */
    private File getFinishedReviewsDirectory() {

        File pendingReviewsDirectory = new File(reviewFile.getParent());

        File filesDirectory = new File(pendingReviewsDirectory.getParent());

        String finishedReviews = "finished_reviews";

        File finishedReviewsDirectory = new File(filesDirectory, finishedReviews);

        if (!finishedReviewsDirectory.exists()) {
            finishedReviewsDirectory.mkdir();
        }

        return finishedReviewsDirectory;
    }

    /**
     * Undoes an answer that was given by a user for a review on its XML file.
     */
    public boolean undoAnswer() throws TransformerException {

        boolean canUndo = false;

        if (document != null) {
            Element answerMapElement = (Element) document.getElementsByTagName(ReviewFileTags.ANSWERS_ELEMENT_TAG).item(0);
            NodeList answersNodeList = answerMapElement.getElementsByTagName(ReviewFileTags.ANSWER_ELEMENT_TAG);

            int numAnswers = answersNodeList.getLength();

            if (numAnswers > 0) {
                Element currentQuestionElement = (Element) document.getElementsByTagName(ReviewFileTags.CURRENT_QUESTION_ELEMENT_TAG).item(0);
                Element lastAnswerElement = (Element) answersNodeList.item(answersNodeList.getLength() - 1);

                answerMapElement.removeChild(lastAnswerElement);

                currentQuestionElement.setAttribute(ReviewFileTags.QUESTION_ID_ATTRIBUTE_TAG,
                        lastAnswerElement.getAttribute(ReviewFileTags.QUESTION_ID_ATTRIBUTE_TAG));

                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Result xmlOutput = new StreamResult(reviewFile);
                Source input = new DOMSource(document);
                transformer.transform(input, xmlOutput);

                canUndo = true;
            }
        }

        return canUndo;
    }

    /**
     * Updates the current question node of the review XML file
     *
     * @param answer Answer given to the question that was answered
     * @return true - if the current question can be updated
     * <p>false - if the question can't be updated, meaning that a final question has been answered</p>
     */
    private boolean updateCurrentQuestion(String answer) {

        Element questionGraphElement = (Element) document.getElementsByTagName(ReviewFileTags.QUESTION_GRAPH_ELEMENT_TAG).item(0);

        Element currentQuestionElement = (Element) document.getElementsByTagName(ReviewFileTags.CURRENT_QUESTION_ELEMENT_TAG).item(0);

        String currentQuestionID = getCurrentQuestionID();

        List<Element> graphQuestionElementList = getAllChildElements(questionGraphElement);

        for (Element graphQuestionElement : graphQuestionElementList) {
            String graphQuestionID = graphQuestionElement.getAttribute(ReviewFileTags.QUESTION_ID_ATTRIBUTE_TAG);
            if (currentQuestionID.equals(graphQuestionID)) {
                List<Element> nextQuestionElementList = getAllChildElements(graphQuestionElement);
                for (Element nextQuestionElement : nextQuestionElementList) {
                    if (answer.equals(nextQuestionElement.getAttribute(ReviewFileTags.OPTION_ATTRIBUTE_TAG))) {
                        currentQuestionElement.setAttribute(ReviewFileTags.QUESTION_ID_ATTRIBUTE_TAG,
                                nextQuestionElement.getAttribute(ReviewFileTags.QUESTION_ID_ATTRIBUTE_TAG));
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
     * The information is in a Bundle and has the following keys:
     * <p>
     * questionID;
     * questionText;
     * currentQuestionType: which might have a value of "B", "Q" or "MC";
     * options: which holds an ArrayList of Strings containing the question's options
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

        String questionID = questionElement.getAttribute(ReviewFileTags.QUESTION_ID_ATTRIBUTE_TAG);

        bundle.putString("questionID", questionID);
        bundle.putString("questionText", questionElement.getElementsByTagName(ReviewFileTags.TEXT_ELEMENT_TAG).item(0).getTextContent());
        ArrayList<String> questionOptions = new ArrayList<>();

        String questionType = questionElement.getNodeName();

        if (questionType.equals(ReviewFileTags.BINARY_QUESTION_ELEMENT_TAG)) {
            bundle.putString("currentQuestionType", "B");
            questionOptions.add("true");
            questionOptions.add("false");

        } else if (questionType.equals(ReviewFileTags.QUANTITATIVE_QUESTION_ELEMENT_TAG)) {

            bundle.putString("currentQuestionType", "Q");

            double scaleMinValue = Double.parseDouble(questionElement.
                    getElementsByTagName(ReviewFileTags.MIN_SCALE_VALUE_ELEMENT_TAG).item(0).getTextContent());
            double scaleMaxValue = Double.parseDouble(questionElement.
                    getElementsByTagName(ReviewFileTags.MAX_SCALE_VALUE_ELEMENT_TAG).item(0).getTextContent());

            for (double scaleValue = scaleMinValue; scaleValue <= scaleMaxValue; scaleValue++) {
                questionOptions.add(Double.toString(scaleValue));
            }

        } else if (questionType.equals(ReviewFileTags.MULTIPLE_CHOICE_QUESTION_ELEMENT_TAG)) {

            bundle.putString("currentQuestionType", "MC");

            NodeList optionsNodeList = questionElement.getElementsByTagName(ReviewFileTags.OPTION_ELEMENT_TAG);

            for (int k = 0; k < optionsNodeList.getLength(); k++) {
                questionOptions.add(((Element) optionsNodeList.item(k)).getAttribute(ReviewFileTags.TEXT_ATTRIBUTE_TAG));
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

        Element currentQuestionElement = (Element) document.getElementsByTagName(ReviewFileTags.CURRENT_QUESTION_ELEMENT_TAG).item(0);

        return currentQuestionElement.getAttribute(ReviewFileTags.QUESTION_ID_ATTRIBUTE_TAG);
    }

    /**
     * Saves the user's review suggestion.
     *
     * @param suggestion Content of the suggestion submitted by the user
     */
    public void saveSuggestion(String suggestion) throws TransformerException {

        Element suggestionElement = (Element) document.getElementsByTagName(ReviewFileTags.SUGGESTION_ELEMENT_TAG).item(0);

        Element textElement = (Element) suggestionElement.getElementsByTagName(ReviewFileTags.TEXT_ELEMENT_TAG).item(0);
        textElement.setTextContent(suggestion);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result xmlOutput = new StreamResult(reviewFile);
        Source input = new DOMSource(document);
        transformer.transform(input, xmlOutput);
    }

    /**
     * Saves the user's review suggestion with text and image.
     *
     * @param suggestion   Content of the suggestion submitted by the user
     * @param imageContent Content of the suggestion's image being submitted by the the user
     */
    public void saveSuggestion(String suggestion, String imageContent) throws TransformerException {
        saveSuggestion(suggestion);

        Element suggestionElement = (Element) document.getElementsByTagName(ReviewFileTags.SUGGESTION_ELEMENT_TAG).item(0);
        Element imageElement = (Element) suggestionElement.getElementsByTagName(ReviewFileTags.IMAGE_ELEMENT_TAG).item(0);
        imageElement.setTextContent(imageContent);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result xmlOutput = new StreamResult(reviewFile);
        Source input = new DOMSource(document);
        transformer.transform(input, xmlOutput);
    }

    /**
     * Parses an XML review file. This method is static since it will not be used for review's that are currently being answered.
     *
     * @param finishedReviewFile a finished review's XML File
     * @return the given XML file's content
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws TransformerException
     */
    public static String parseReviewFile(File finishedReviewFile) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        if (finishedReviewFile == null || !finishedReviewFile.getName().endsWith(XML_EXTENSION)) {
            return null;
        }

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(finishedReviewFile);
        StringWriter stringWriter = new StringWriter();
        Transformer serializer = TransformerFactory.newInstance().newTransformer();
        serializer.transform(new DOMSource(document), new StreamResult(stringWriter));
        return stringWriter.toString();
    }
}
