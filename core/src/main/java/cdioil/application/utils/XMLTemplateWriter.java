package cdioil.application.utils;

import cdioil.application.utils.SurveyTemplateElement.*;
import cdioil.domain.BinaryQuestion;
import cdioil.domain.Category;
import cdioil.domain.MultipleChoiceQuestion;
import cdioil.domain.Product;
import cdioil.domain.QuantitativeQuestion;
import cdioil.domain.QuantitativeQuestionOption;
import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import cdioil.domain.ScriptedTemplate;
import cdioil.domain.SurveyItem;
import cdioil.domain.Template;
import cdioil.files.FileWriter;
import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Exports a Template to a .xml file.
 *
 * @author Pedro Portela
 * @author Ana Guerra (1161191)
 */
public class XMLTemplateWriter implements TemplateWriter {

    private final String filename;

    /**
     * Template to export.
     */
    private final Template template;

    private final List<SurveyItem> surveyItems;

    /**
     * Builds a new XMLTemplateWriter with the file that is going to be written
     *
     * @param filename String with the filename that is going to be written
     * @param template Template to export
     */
    public XMLTemplateWriter(String filename, Template template) {
        this.filename = filename;
        this.template = template;
        this.surveyItems = new LinkedList<>();
    }

    @Override
    public boolean addSurveyItems(List<SurveyItem> surveyItems) {
        return this.surveyItems.addAll(surveyItems);
    }

    @Override
    public boolean write() {

        try {
            //Set template's title
            SurveyTemplateElement templateElement = new SurveyTemplateElement(template.getTitle());
            
            QuestionsElement questionsElement = templateElement.getTemplateQuestions();
            
            Iterable<Question> templateQuestions = template.getQuestions();
            
            for (Question question : templateQuestions) {
                questionsElement.addQuestion(buildQuestionElement(question));
            }
            
            List<SurveyItemElement> surveyItemElementList = templateElement.getTemplateSurveyItems().getSurveyItems();
            for (SurveyItem item : surveyItems) {
                if (item instanceof Category) {
                    CategoryElement categoryElement = new CategoryElement(((Category) item).categoryPath());
                    surveyItemElementList.add(categoryElement);
                } else if (item instanceof Product) {
                    ProductElement productElement = new ProductElement(((Product) item).getID().toString());
                    surveyItemElementList.add(productElement);
                }
            }
            
            if (template instanceof ScriptedTemplate) {
                buildQuestionScript(templateElement, ((ScriptedTemplate) template).getGraph());
            }
            
            JAXBContext jaxbContext = JAXBContext.newInstance(SurveyTemplateElement.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            Writer writer = new StringWriter();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(jaxbContext, writer);
            
            return FileWriter.writeFile(new File(filename), ((StringWriter)writer).getBuffer().toString());
            
        } catch (JAXBException ex) {
            Logger.getLogger(XMLTemplateWriter.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private QuestionScriptElement buildQuestionScript(SurveyTemplateElement templateElement, Graph graph) {

        QuestionScriptElement questionScript = templateElement.getTemplateQuestionScript();

        Question question = graph.getFirstQuestion();

        ScriptedQuestionElement scriptedQuestion = new ScriptedQuestionElement(question.getQuestionID());

        questionScript.addScriptedQuestion(scriptedQuestion);

        buildQuestionScriptRec(questionScript, graph, question, scriptedQuestion);

        return questionScript;
    }

    private void buildQuestionScriptRec(QuestionScriptElement questionScriptElement, Graph graph, Question currentQuestion, ScriptedQuestionElement scriptedQuestion) {

        if (currentQuestion == null) {
            return;
        }

        Iterable<Question> adjacentQuestions = graph.adjacentQuestions(currentQuestion);

        for (Question nextQuestion : adjacentQuestions) {

            String nextQuestionID = nextQuestion.getQuestionID();

            ScriptedQuestionElement nextScriptedQuestion = new ScriptedQuestionElement(nextQuestionID);

            Iterable<Edge> edges = graph.edgesConnectingQuestions(currentQuestion, nextQuestion);

            //Fill the options attribute
            List<String> options = new LinkedList<>();
            for (Edge connectingEdge : edges) {
                options.add(connectingEdge.getElement().toString());
            }

            OnReplyElement onReplyElement = new OnReplyElement(nextScriptedQuestion, options);

            scriptedQuestion.addOnReply(onReplyElement);

            buildQuestionScriptRec(questionScriptElement, graph, nextQuestion, nextScriptedQuestion);
        }
    }

    private QuestionElement buildQuestionElement(Question question) {

        String questionID = question.getQuestionID();

        String questionText = question.getQuestionText();

        if (question instanceof BinaryQuestion) {

            BinaryQuestionElement questionElement = new BinaryQuestionElement(questionID, questionText);

            return questionElement;

        } else if (question instanceof MultipleChoiceQuestion) {

            MultipleChoiceQuestionElement questionElement = new MultipleChoiceQuestionElement(questionID, questionText);

            int optionNumber = 1;

            for (QuestionOption option : question.getOptionList()) {

                OptionElement optionElement = new OptionElement(optionNumber, option.toString());

                optionNumber++;

                questionElement.addOption(optionElement);
            }

            return questionElement;

        } else if (question instanceof QuantitativeQuestion) {

            double maximum = Double.MIN_VALUE;
            double minimum = Double.MAX_VALUE;

            for (QuestionOption option : question.getOptionList()) {

                QuantitativeQuestionOption qntOption = (QuantitativeQuestionOption) option;

                Double optionValue = qntOption.getContent();

                if (Double.compare(optionValue, minimum) < 0) {
                    minimum = optionValue;
                }
                if (Double.compare(optionValue, maximum) > 0) {
                    maximum = optionValue;
                }
            }

            return new QuantitativeQuestionElement(questionID, questionText, minimum, maximum);
        }

        return null;
    }

    /**
     * Returns a String with the content of a XML file with the template's
     * information.
     *
     * @return a String with the content of the XML file
     */
    public String getXMLAsString() {
//        try {
//            Document doc = DocumentBuilderFactory.newInstance().
//                    newDocumentBuilder().newDocument();
//
//            writeTemplate(doc);
//
//            Transformer transformer = TransformerFactory.newInstance().newTransformer();
//            DOMSource source = new DOMSource(doc);
//            StringWriter stringWriter = new StringWriter();
//            StreamResult result = new StreamResult(stringWriter);
//            transformer.transform(source, result);
//            return stringWriter.getBuffer().toString();
//        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
//            Logger.getLogger(XMLTemplateWriter.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (TransformerException ex) {
//            Logger.getLogger(XMLTemplateWriter.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
        return "";
    }
}
