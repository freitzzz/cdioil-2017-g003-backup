package cdioil.application.utils;

import cdioil.application.utils.SurveyTemplateElement.*;
import cdioil.domain.BinaryQuestion;
import cdioil.domain.MultipleChoiceQuestion;
import cdioil.domain.MultipleChoiceQuestionOption;
import cdioil.domain.QuantitativeQuestion;
import cdioil.domain.QuantitativeQuestionOption;
import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import cdioil.domain.ScriptedTemplate;
import cdioil.domain.SimpleTemplate;
import cdioil.domain.SurveyItemType;
import cdioil.domain.Template;
import cdioil.files.FilesUtils;
import cdioil.files.ValidatorXML;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Class responsible for parsing an XML File containing Template data.
 *
 * @author António Sousa [1161371]
 */
public class XMLTemplateReader implements TemplateReader {

    /**
     * Constant representing the Template definition XSD File.
     */
    private static final File SCHEMA_FILE
            = new File(FilesUtils.convertStringToUTF8(
                    XMLTemplateReader.class.getClassLoader()
                            .getResource("template_schema.xsd").getFile()));

    /**
     * File containing the Template's information.
     */
    private final File templateFile;

    private final TemplateQuestionsMap questionsMap;

    private final TemplateQuestionsMap notAddedQuestionsMap;

    private SurveyTemplateElement templateElement;

    /**
     * Creates a new instance of XMLTemplateReader.
     *
     * @param filePath Template file's path
     * @throws java.io.IOException if the XML is not compliant with the template
     * schema or if an error occurred while unmarshaling the XML file
     */
    public XMLTemplateReader(String filePath) throws IOException {

        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid file path");
        }
        this.templateFile = new File(filePath);

        if (!templateFile.exists()) {
            throw new IOException("The file does not exist");
        }

        if (!validateFile()) {
            throw new IOException("The XML file is not compliant with the schema.");
        }

        /*An IOException is thrown since instances of template reader are built
        by the factory and there can be multiple types of reader, not all of 
        which require a JAXBException to be thrown.*/
        try {
            unmarshalFile();
        } catch (JAXBException e) {
            throw new IOException(e.getMessage());
        }

        this.questionsMap = new TemplateQuestionsMap();
        this.notAddedQuestionsMap = new TemplateQuestionsMap();
    }

    /**
     * Validates the XML file against the Template schema.
     *
     * @return true if the file is compliant with the schema
     */
    private boolean validateFile() {
        return ValidatorXML.validateFile(SCHEMA_FILE, templateFile);
    }

    /**
     * Unmarshals the file and creates a SurveyTemplateElementObject.
     *
     * @throws JAXBException if an error occurred while unmarshalling the file
     */
    private void unmarshalFile() throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(SurveyTemplateElement.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        templateElement = (SurveyTemplateElement) jaxbUnmarshaller.unmarshal(templateFile);
    }

    @Override
    public Template createTemplate() {

        String templateName = templateElement.getName();
        SurveyTemplateElement.QuestionsElement templateQuestions = templateElement.getTemplateQuestions();

        //check if template is directed or not
        if (templateElement.getTemplateQuestionScript() == null) {
            SimpleTemplate template = new SimpleTemplate(templateName);
            addQuestionsToTemplate(template, templateQuestions);
            return template;
        } else {
            ScriptedTemplate template = new ScriptedTemplate(templateName);
            addQuestionsToTemplate(template, templateQuestions);
            buildQuestionGraph(template, templateElement.getTemplateQuestionScript());
            return template;
        }
    }

    @Override
    public Map<SurveyItemType, List<String>> getSurveyItemIdentifiers() {

        SurveyItemsElement surveyItemsElement = templateElement.getTemplateSurveyItems();

        Map<SurveyItemType, List<String>> surveyItemIdentifiers = new HashMap<>();

        //since the survey items tag is optional, this check is required
        if (surveyItemsElement == null) {
            return surveyItemIdentifiers;
        }

        List<SurveyItemElement> surveyItemElements = surveyItemsElement.getSurveyItems();

        if (surveyItemElements.isEmpty()) {
            return surveyItemIdentifiers;
        }

        SurveyItemType categoryKey = SurveyItemType.CATEGORY;
        surveyItemIdentifiers.put(categoryKey, new LinkedList<>());

        SurveyItemType productKey = SurveyItemType.PRODUCT;
        surveyItemIdentifiers.put(productKey, new LinkedList<>());

        for (SurveyItemElement itemElement : surveyItemElements) {

            if (itemElement instanceof CategoryElement) {

                String path = ((CategoryElement) itemElement).getPath();

                surveyItemIdentifiers.get(categoryKey).add(path);

            } else if (itemElement instanceof ProductElement) {

                String sku = ((ProductElement) itemElement).getSku();

                surveyItemIdentifiers.get(productKey).add(sku);
            }
        }

        return surveyItemIdentifiers;
    }

    @Override
    public List<String> getDatabaseQuestionIDList() {

        List<String> questionIDList = new LinkedList<>();
        List<QuestionElement> questionElements = templateElement.getTemplateQuestions().getQuestions();

        for (QuestionElement questionElement : questionElements) {

            //if no text element is defined, fetch the question with the given ID from the database
            if (questionElement.getText() == null) {
                questionIDList.add(questionElement.getQuestionID());
            }
        }

        return questionIDList;
    }

    @Override
    public void addDatabaseQuestion(Question q) {

        String questionID = q.getQuestionID();

        questionsMap.addQuestion(questionID, q);

        if (q instanceof BinaryQuestion) {
            questionsMap.addOption(questionID, "yes", q.getOptionList().get(0));
            questionsMap.addOption(questionID, "no", q.getOptionList().get(1));

        } else if (q instanceof MultipleChoiceQuestion) {

            int optionNum = 1;

            for (QuestionOption option : q.getOptionList()) {
                questionsMap.addOption(questionID, Integer.toString(optionNum), option);
                optionNum++;
            }

        } else if (q instanceof QuantitativeQuestion) {

            for (QuestionOption option : q.getOptionList()) {
                questionsMap.addOption(questionID,
                        Double.toString(((QuantitativeQuestionOption) option).getContent()), option);
            }
        }
    }

    /**
     * Builds the Graph associated to the given ScriptedTemplate
     *
     * @param template ScriptedTemplated containing the Graph
     * @param questionScriptElement QuestionScriptElement in which the
     * Template's Graph connections are defined
     */
    private void buildQuestionGraph(ScriptedTemplate template, QuestionScriptElement questionScriptElement) {

        List<ScriptedQuestionElement> scriptedQuestions = questionScriptElement.getScriptedQuestions();

        for (ScriptedQuestionElement scriptedQuestion : scriptedQuestions) {
            buildQuestionGraphRec(template, scriptedQuestion);
        }
    }

    /**
     * Recursively creates all the Graph's connections.
     *
     * @param template ScriptedTemplate containing the Graph information
     * @param scriptedQuestion current ScriptedQuestionElement
     */
    private void buildQuestionGraphRec(ScriptedTemplate template, ScriptedQuestionElement scriptedQuestion) {

        if (scriptedQuestion == null) {
            return;
        }

        String questionID = scriptedQuestion.getQuestionID();

        Question originQuestion = questionsMap.getQuestion(questionID);

        List<OnReplyElement> onReplyList = scriptedQuestion.getOnReplyList();

        if (onReplyList == null) {
            notAddedQuestionsMap.addQuestion(questionID, originQuestion);

            return;
        }

        for (OnReplyElement onReply : onReplyList) {

            ScriptedQuestionElement nextQuestionElement = onReply.getOnReplyQuestion();

            String nextQuestionID = nextQuestionElement.getQuestionID();

            Question destinationQuestion = questionsMap.getQuestion(nextQuestionID);

            List<String> onReplyOptions = onReply.getOnReplyOptions();

            //more than one option can lead to the same next question
            for (String onReplyOption : onReplyOptions) {

                QuestionOption option = questionsMap.getOption(questionID, onReplyOption);

                //this check is needed since file might be incorrect
                if (option != null) {
                    template.setNextQuestion(originQuestion, destinationQuestion, option, 0);
                }
            }

            //set next question's flow recursively
            buildQuestionGraphRec(template, nextQuestionElement);
        }
    }

    /**
     * Adds all of QuestionElement within the QuestionsElement list of
     * QuestionElement to the template.
     *
     * @param template template to which the questions are being added to
     * @param templateQuestions QuestionsElement containing a list of
     * QuestionElement
     */
    private void addQuestionsToTemplate(Template template, QuestionsElement templateQuestions) {

        for (QuestionElement questionElement : templateQuestions.getQuestions()) {

            Question question = buildQuestion(questionElement);
            String questionID = question.getQuestionID();

            template.addQuestion(question);
            questionsMap.addQuestion(questionID, question);

            if (questionElement instanceof BinaryQuestionElement) {

                questionsMap.addOption(questionID, "yes", question.getOptionList().get(0));
                questionsMap.addOption(questionID, "no", question.getOptionList().get(1));

            } else if (questionElement instanceof MultipleChoiceQuestionElement) {

                List<OptionElement> optionElements = ((MultipleChoiceQuestionElement) questionElement).getQuestionOptions();
                List<QuestionOption> options = question.getOptionList();

                int size = optionElements.size();

                for (int i = 0; i < size; i++) {

                    OptionElement optionElement = optionElements.get(i);
                    QuestionOption option = options.get(i);
                    String optionID = Integer.toString(optionElement.getOptionNumber());

                    questionsMap.addOption(questionID, optionID, option);
                }

            } else if (questionElement instanceof QuantitativeQuestionElement) {

                List<QuestionOption> options = question.getOptionList();

                int size = options.size();

                for (int i = 0; i < size; i++) {

                    QuantitativeQuestionOption option = (QuantitativeQuestionOption) options.get(i);
                    String value = Double.toString(option.getContent());

                    questionsMap.addOption(questionID, value, option);
                }
            }
        }
    }

    /**
     * Builds a <code>Question</code> from a given <code>QuestionElement</code>
     *
     * @param questionElement question element containing question information
     * @return an instance of <code>Question</code> with the attributes of
     * <code>QuestionElement</code>
     */
    private Question buildQuestion(QuestionElement questionElement) {

        Question q = null;

        String questionText = questionElement.getText();
        String questionID = questionElement.getQuestionID();

        if (questionElement instanceof BinaryQuestionElement) {

            q = new BinaryQuestion(questionText, questionID);

        } else if (questionElement instanceof MultipleChoiceQuestionElement) {

            List<QuestionOption> options = new LinkedList<>();

            for (OptionElement optionElement : ((MultipleChoiceQuestionElement) questionElement).getQuestionOptions()) {

                MultipleChoiceQuestionOption mcOption = new MultipleChoiceQuestionOption(optionElement.getText());
                options.add(mcOption);
            }

            q = new MultipleChoiceQuestion(questionText, questionID, options);

        } else if (questionElement instanceof SurveyTemplateElement.QuantitativeQuestionElement) {

            List<QuestionOption> options = new LinkedList<>();

            double minValue = ((QuantitativeQuestionElement) questionElement).getMinScaleValue();
            double maxValue = ((QuantitativeQuestionElement) questionElement).getMaxScaleValue();

            if (minValue > maxValue) {
                double temp = minValue;
                minValue = maxValue;
                maxValue = temp;
            }

            for (double value = minValue; value <= maxValue; value++) {

                QuantitativeQuestionOption qntOption = new QuantitativeQuestionOption(value);
                options.add(qntOption);
            }

            q = new QuantitativeQuestion(questionText, questionID, options);
        }

        return q;
    }

    /**
     * Auxialiary data structure that maps a <code>Question</code> to a
     * QuestionID and its <code>QuestionOption</code> elements to a value.
     */
    private final class TemplateQuestionsMap {

        /**
         * Map associating a QuestionID to a QuestionData.
         */
        private final Map<String, QuestionData> templateDataMap;

        /**
         * Constructs a new instance of <code>TemplateDataMap</code>.
         */
        private TemplateQuestionsMap() {
            this.templateDataMap = new HashMap<>();
        }

        /**
         * Adds a question to the data structure.
         *
         * @param questionID a question's QuestionID attribute
         * @param question Question being mapped to the QuestionID
         */
        private void addQuestion(String questionID, Question question) {
            templateDataMap.put(questionID, new QuestionData(question));
        }

        /**
         * Retrieves a question's option based on the questionID and the option
         * identifier.
         *
         * @param questionID a question's QuestionID attribute
         * @param optionID string used for identifying the option
         * @return QuestionOption associated with the given optionID
         */
        private QuestionOption getOption(String questionID, String optionID) {
            return templateDataMap.get(questionID).getOption(optionID);
        }

        /**
         * Retrieves the <code>Question</code> mapped to the given questionID.
         *
         * @param questionID a question's questionID attribute
         * @return Question mapped to the questionID
         */
        private Question getQuestion(String questionID) {
            return templateDataMap.get(questionID).getQuestion();
        }

        /**
         * Adds an option to the question mapped to the questionID and maps the
         * option to the optionID.
         *
         * @param questionID a question's questionID attribute
         * @param optionID option's mapping key
         * @param option option to be mapped
         */
        private void addOption(String questionID, String optionID, QuestionOption option) {
            templateDataMap.get(questionID).addOption(optionID, option);
        }

        /**
         * Class responsible for mapping a Question's Collection of
         * QuestionOption to the specified identifier.
         */
        private final class QuestionData {

            /**
             * Map associating an optionID to a QuestionOption
             */
            private final Map<String, QuestionOption> options;

            /**
             * Question contained in the QuestionData.
             */
            private final Question question;

            /**
             * Creates a new instance of QuestionData containing a given
             * Question.
             *
             * @param question to be stored
             */
            private QuestionData(Question question) {
                this.question = question;
                this.options = new HashMap<>();
            }

            /**
             * Adds a new entry, mapping the given QuestionOption to the given optionID.
             * @param optionID value to which the QuestionOption is mapped to
             * @param option QuestionOption mapped to the optionID
             */
            private void addOption(String optionID, QuestionOption option) {
                options.put(optionID, option);
            }

            /**
             * Fetches the QuestionOption mapped to the given optionID.
             *
             * @param optionID String to which the QuestionOption is mapped to
             * @return QuestionOption mapped to the given optionID
             */
            private QuestionOption getOption(String optionID) {
                return options.get(optionID);
            }

            /**
             * Question contained in the QuestionData.
             *
             * @return Question in the QuestionData
             */
            private Question getQuestion() {
                return question;
            }
        }
    }
}
