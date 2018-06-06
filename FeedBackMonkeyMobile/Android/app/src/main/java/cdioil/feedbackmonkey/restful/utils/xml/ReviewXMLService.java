package cdioil.feedbackmonkey.restful.utils.xml;

import android.os.Environment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
     * Index for the question list node of the review XML file.
     */
    private final static int QUESTION_LIST_NODE_INDEX = 0;
    /**
     * Index for the question graph node of the review XML file.
     */
    private final static int QUESTION_GRAPH_NODE_INDEX = 1;
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
     * File Path for a Review.
     */
    private String reviewFilePath;

    /**
     * TODO Discuss constructor and flaws that it brings due to being able to load a review when it's midway through
     * Builds an instance of ReviewXMLService receiving the XML file content as a String
     * @param fileContent String with the XML file content
     */
    public ReviewXMLService(String fileContent){
        createFile(fileContent);
    }

    /**
     * Creates an XML File that will save the user's review information regarding a certain survey.
     * @param fileContent String with the XML file content
     */
    private void createFile(String fileContent){
        try {
            InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes("UTF-8"));
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element reviewElement = document.getDocumentElement();
            String fileName = "review" + reviewElement.getAttribute("id") + ".xml";
             reviewFilePath = Environment.getExternalStorageDirectory().toString()+File.separator+
                    "Reviews"+File.separator+fileName;
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StreamResult xmlOutput = new StreamResult(new File(reviewFilePath));
            DOMSource input = new DOMSource(document);
            transformer.transform(input,xmlOutput);
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a user's answer to a certain question of a review on its XML file.
     * @param questionID ID of the question the answer was given to
     * @param answer Content of the answer itself
     */
    public boolean saveAnswer(String questionID, String answer){
        boolean canContinueReview = false;
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(reviewFilePath);

            if(document != null){
                NodeList nodeList = document.getChildNodes();
                Element answerMapElement = (Element) nodeList.item(ANSWER_MAP_NODE_INDEX);
                Element answerElement = document.createElement("Answer");
                answerElement.setAttribute("questionID",questionID);
                answerElement.setAttribute("text",answer);
                answerMapElement.appendChild(answerElement);
                canContinueReview = updateCurrentQuestion(nodeList,answer);
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Result xmlOutput = new StreamResult(new File(reviewFilePath));
                Source input = new DOMSource(document);
                transformer.transform(input,xmlOutput);
            }
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return canContinueReview;
    }

    /**
     * Undoes an answer that was given by a user for a review on its XML file.
     */
    public void undoAnswer(){
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(reviewFilePath);

            if(document != null){
                NodeList nodeList = document.getChildNodes();
                Element answerMapElement = (Element) nodeList.item(ANSWER_MAP_NODE_INDEX);
                NodeList answersNodeList = answerMapElement.getChildNodes();
                answerMapElement.removeChild( answersNodeList.item(answersNodeList.getLength()-1));
                Element newCurrentQuestionElement = (Element) answersNodeList.item(answersNodeList.getLength()-1);
                String newCurrentQuestionID = newCurrentQuestionElement.getAttribute("questionID");
                Element currentQuestionElement = (Element) nodeList.item(CURRENT_QUESTION_NODE_INDEX);
                currentQuestionElement.setAttribute("questionID",newCurrentQuestionID);
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Result xmlOutput = new StreamResult(new File(reviewFilePath));
                Source input = new DOMSource(document);
                transformer.transform(input,xmlOutput);
            }
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the current question node of the review XML file
     * @param nodeList node list of the review XML file
     * @param answer Answer given to the question that was answered
     */
    private boolean updateCurrentQuestion(NodeList nodeList, String answer) {
        Element questionGraphElement = (Element) nodeList.item(QUESTION_GRAPH_NODE_INDEX);
        Element currentQuestionElement = (Element) nodeList.item(CURRENT_QUESTION_NODE_INDEX);
        String currentQuestionID = currentQuestionElement.getAttribute("questionID");
        NodeList graphNodeList = questionGraphElement.getChildNodes();

        for(int i = 0; i < graphNodeList.getLength(); i++){
            Element graphQuestionElement = (Element) graphNodeList.item(i);
            String graphQuestionID = graphQuestionElement.getAttribute("questionID");
            if(currentQuestionID.equals(graphQuestionID)){
                NodeList nextQuestionNodeList = graphQuestionElement.getChildNodes();
                for(int j = 0; j < nextQuestionNodeList.getLength(); j++){
                    Element nextQuestionElement = (Element) nextQuestionNodeList.item(j);
                    if(answer.equals(nextQuestionElement.getAttribute("option"))){
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
     *
     * The information is in a list of Strings:
     *
     * Index 0 of the list will contain the questions ID.
     * Index 1 of the list will contain the questions text (the question itself).
     * Index 2 of the list will contain the questions type ("B" for Binary, "Q" for Quantitative and
     * "MC" for Multiple Choice)
     *
     * From index 3 and onward the list will contain all of the options that the question has
     * (e.g. for a Binary Question index 3 will have "true" and index 4 will have "false";
     *
     * for a Quantitative Question index 3 will have "1", index 4 will have "2", index 5 will have "3",
     * so on and so forth until it reaches the maximum value of the scale;
     *
     * for a Multiple Choice Question index 3 will have "Too salty", index 4 will have "Too sour",
     * index 5 will have "Too sweet", so on and so forth until it has all of the options of the question)
     *
     * This method returns this much information to facilitate the manipulation of the Question's
     * Activities to answer a survey.
     *
     * @return list of Strings containing the information of a Question
     */
    public List<String> getCurrentQuestionInfo(){
        LinkedList<String> questionInfo = new LinkedList<>();

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(reviewFilePath);
            if(document != null){
                NodeList nodeList = document.getChildNodes();
                Element currentQuestionElement = (Element) nodeList.item(CURRENT_QUESTION_NODE_INDEX);
                Element questionListElement = (Element) nodeList.item(QUESTION_LIST_NODE_INDEX);
                String currentQuestionID = currentQuestionElement.getAttribute("questionID");
                NodeList questionListNodeList = questionListElement.getChildNodes();

                for(int i = 0; i < questionListNodeList.getLength(); i++){
                    Element questionElement = (Element) questionListNodeList.item(i);
                    String questionID = questionElement.getAttribute("questionID");
                    if(currentQuestionID.equals(questionID)){
                        questionInfo.add(questionID);
                        questionInfo.add(questionElement.getElementsByTagName("Text").item(0).getTextContent());
                        switch(questionElement.getNodeName()){
                            case "BinaryQuestion":
                                questionInfo.add("B");
                                questionInfo.add("true");
                                questionInfo.add("false");
                                break;
                            case "QuantitativeQuestion":
                                questionInfo.add("Q");
                                double scaleMinValue = Double.parseDouble(questionElement.
                                    getElementsByTagName("scaleMinValue").item(0).getTextContent());
                                double scaleMaxValue = Double.parseDouble(questionElement.
                                        getElementsByTagName("scaleMaxValue").item(0).getTextContent());
                                for(double j = scaleMinValue; j == scaleMaxValue; j++){
                                    questionInfo.add(Double.toString(j));
                                }
                                break;
                            case "MultipleChoiceQuestion":
                                questionInfo.add("MC");
                                NodeList optionsNodeList = questionElement.getElementsByTagName("Option");
                                for(int k = 0; k < optionsNodeList.getLength(); k++){
                                    questionInfo.add(((Element)optionsNodeList.item(k)).getAttribute("text"));
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return questionInfo;
    }



    /**
     * Saves a user's suggestion of a review.
     * @param suggestion Content of the suggestion submitted by the user
     */
    public void saveSuggestion(String suggestion){
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(reviewFilePath);

            if(document != null){
                NodeList nodeList = document.getChildNodes();
                Element suggestionElement = (Element) nodeList.item(SUGGESTION_NODE_INDEX);
                Text suggestionNode = document.createTextNode("Text");
                suggestionNode.setTextContent(suggestion);
                suggestionElement.appendChild(suggestionNode);
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Result xmlOutput = new StreamResult(new File(reviewFilePath));
                Source input = new DOMSource(document);
                transformer.transform(input,xmlOutput);
            }
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses the XML File's content to a string so it can be sent to the server
     * @return XML File content in a String
     */
    public String parseFileContentToString(){
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(reviewFilePath);
            StringWriter stringWriter = new StringWriter();
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.transform(new DOMSource(document),new StreamResult(stringWriter));
            return stringWriter.toString();
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
