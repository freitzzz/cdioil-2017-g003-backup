/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import cdioil.domain.Template;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
 * Exports a Template to a .xml file.
 *
 * @author Pedro Portela
 * @author Ana Guerra (1161191)
 */
public class XMLTemplateWriter implements TemplateWriter {

    /**
     * Constant that represents the label used for the template identifier on the XML file
     */
    private static final String TEMPLATE_LABEL = "Template";
    /**
     * Constant that represents the label used for the question identifier on the XML file
     */
    private static final String QUESTION_LABEL = "Question";
    /**
     * Constant that represents the label used for the question text identifier on the XML file
     */
    private static final String QUESTIONTEXT_LABEL = "QuestionText";
    /**
     * Constant that represents the label used for the options identifier on the XML file
     */
    private static final String OPTIONS_LABEL = "Options";
    /**
     * Constant that represents the label used for the option identifier on the XML file
     */
    private static final String OPTION_LABEL = "Option";
    /**
     * Constant that represents the label used for the ID of the question identifier on the XML file
     */
    private static final String ID = "ID";
    /**
     * Constant that represents the label used for the title of the question identifier on the XML file
     */
    private static final String QUESTION_TITLE = "title";
    
    /**
     * File with the file that is going to be written with all survey answers
     */
    private final File file;
    /**
     * Template to export.
     */
    private final Template template;

    /**
     * Builds a new XMLTemplateWriter with the file that is going to be written
     *
     * @param filename String with the filename that is going to be written
     * @param template Template to export
     */
    public XMLTemplateWriter(String filename, Template template) {
        this.file = new File(filename);
        this.template = template;
    }

    /**
     * Method that writes the template into a XML file
     *
     * @return boolean true if template was written with success, false if not
     */
    @Override
    public boolean write() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement(TEMPLATE_LABEL);
            doc.appendChild(rootElement);

            Attr attrTitle = doc.createAttribute(QUESTION_TITLE);
            attrTitle.setValue(template.getTitle());
            rootElement.setAttributeNode(attrTitle);

            writeTemplate(doc, rootElement);

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
     * Writes the template into a XML file.
     *
     * @param doc Representation of the XML document
     * @param rootElement Root element
     */
    private void writeTemplate(Document doc, Element rootElement) {
        for (Question question : template.getQuestions()) {
            Element questionElement = doc.createElement(QUESTION_LABEL);
            rootElement.appendChild(questionElement);

            Attr attrID = doc.createAttribute(ID);
            attrID.setValue(question.getQuestionID());
            questionElement.setAttributeNode(attrID);

            Element questionTextElement = doc.createElement(QUESTIONTEXT_LABEL);
            questionTextElement.appendChild(doc.createTextNode(question.getQuestionText()));
            questionElement.appendChild(questionTextElement);

            Element questionOptionsElement = doc.createElement(OPTIONS_LABEL);
            questionElement.appendChild(questionOptionsElement);

            for (QuestionOption op : question.getOptionList()) {
                Element questionOption = doc.createElement(OPTION_LABEL);
                questionOption.appendChild(doc.createTextNode(op.toString()));
                questionOptionsElement.appendChild(questionOption);
            }
        }
    }
}
