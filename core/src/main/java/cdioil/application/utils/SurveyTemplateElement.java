package cdioil.application.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * SurveyTemplate class that represents the structure of a Template.
 *
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 6.0 of FeedbackMonkey
 */
@XmlRootElement(name = "Template")
public final class SurveyTemplateElement {

    /**
     * String that represents the name of a template
     */
    @XmlAttribute(name = "name")
    private String name;
    /**
     * Questions with the template templateQuestions
     */
    @XmlElement(name = "Questions")
    private QuestionsElement templateQuestions;
    /**
     * SurveyItems with the template survey items
     */
    @XmlElement(name = "SurveyItems")
    private SurveyItemsElement templateSurveyItems;
    /**
     * QuestionScript with the current template question script
     */
    @XmlElement(name = "QuestionScript")
    private QuestionScriptElement templateQuestionScript;

    public SurveyTemplateElement() {
        this.templateQuestions = new QuestionsElement();
        this.templateSurveyItems = new SurveyItemsElement();
        this.templateQuestionScript = new QuestionScriptElement();
    }

    public SurveyTemplateElement(String name) {
        this();
        this.name = name;
    }

    /**
     * Returns the current Survey Template name
     *
     * @return String with the current template name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the current template questions
     *
     * @return Questions with the current template questions
     */
    public QuestionsElement getTemplateQuestions() {
        return templateQuestions;
    }

    /**
     * Returns the current template survey items
     *
     * @return SurveyItems with the current template survey items
     */
    public SurveyItemsElement getTemplateSurveyItems() {
        return templateSurveyItems;
    }

    /**
     * Returns the current template question script
     *
     * @return QuestionScript with the current template question script
     */
    public QuestionScriptElement getTemplateQuestionScript() {
        return templateQuestionScript;
    }

    /**
     * Class that represents an aggregate of templateQuestions
     */
    public static class QuestionsElement {

        /**
         * List with all the template QuestionElement.
         */
        @XmlElements(value = {
            @XmlElement(name = "BinaryQuestion", type = BinaryQuestionElement.class)
            ,
            @XmlElement(name = "QuantitativeQuestion", type = QuantitativeQuestionElement.class)
            ,
            @XmlElement(name = "MultipleChoiceQuestion", type = MultipleChoiceQuestionElement.class)
        })
        private final List<QuestionElement> questions;

        public QuestionsElement() {
            this.questions = new LinkedList<>();
        }

        /**
         * Returns all template templateQuestions
         *
         * @return List with all template templateQuestions
         */
        public List<QuestionElement> getQuestions() {
            return questions;
        }

        public boolean addQuestion(QuestionElement questionElement) {
            return questions.add(questionElement);
        }
    }

    /**
     * Class that represents a base question from the template
     */
    public static abstract class QuestionElement {

        /**
         * String that represents the id of a question
         */
        @XmlAttribute(name = "questionID")
        private String id;
        /**
         * String that represents the text of the question
         */
        @XmlElement(name = "Text")
        private String text;

        public QuestionElement() {
        }

        public QuestionElement(String questionID, String text) {
            this.id = questionID;
            this.text = text;
        }

        /**
         * Returns the String representing the question's text.
         *
         * @return question's text
         */
        public String getText() {
            return text;
        }

        /**
         * Returns the String representing the question's ID.
         *
         * @return question's ID
         */
        public String getQuestionID() {
            return id;
        }
    }

    /**
     * Class that represents a binary question from the template
     */
    public static class BinaryQuestionElement extends QuestionElement {

        public BinaryQuestionElement() {

        }

        public BinaryQuestionElement(String questionID, String text) {
            super(questionID, text);
        }
    }

    /**
     * Class that represents a multiple choice question from the template
     */
    public static class MultipleChoiceQuestionElement extends QuestionElement {

        /**
         * List with all the multiple choice question options
         */
        @XmlElements(
                @XmlElement(name = "Option", type = OptionElement.class))
        private final List<OptionElement> questionOptions;

        public MultipleChoiceQuestionElement() {
            this.questionOptions = new LinkedList<>();
        }

        public MultipleChoiceQuestionElement(String questionID, String text) {
            super(questionID, text);
            this.questionOptions = new LinkedList<>();
        }

        /**
         * Returns all of the MultipleChoiceQuestion's options.
         *
         * @return a list containing all of the question's options.
         */
        public List<OptionElement> getQuestionOptions() {
            return questionOptions;
        }

        public boolean addOption(OptionElement option) {
            return questionOptions.add(option);
        }
    }

    /**
     * Class that represents a multiple choice question option
     */
    public static class OptionElement {

        /**
         * Short that represents the option number
         */
        @XmlAttribute(name = "num")
        private int optionNumber;
        /**
         * String that represents the option text
         */
        @XmlAttribute(name = "text")
        private String text;

        public OptionElement() {

        }

        public OptionElement(int optionNumber, String text) {
            this.optionNumber = optionNumber;
            this.text = text;
        }

        public int getOptionNumber() {
            return optionNumber;
        }

        public String getText() {
            return text;
        }
    }

    /**
     * Class that represents a quantitive question from the template
     */
    public static class QuantitativeQuestionElement extends QuestionElement {

        /**
         * Short with the quantitive question min value scale
         */
        @XmlElement(name = "scaleMinValue")
        private double minValueScale;
        /**
         * Short with the quantitive question min value scale
         */
        @XmlElement(name = "scaleMaxValue")
        private double maxValueScale;

        public QuantitativeQuestionElement() {
        }

        public QuantitativeQuestionElement(String questionID, String questionText, double minScaleValue, double maxScaleValue) {
            this.minValueScale = minScaleValue;
            this.maxValueScale = maxScaleValue;
        }

        public double getMinScaleValue() {
            return minValueScale;
        }

        public double getMaxScaleValue() {
            return maxValueScale;
        }
    }

    /**
     * Class that represents the template survey items
     */
    public static class SurveyItemsElement {

        /**
         * List with the template survey items
         */
        @XmlElements(value = {
            @XmlElement(name = "Category", type = CategoryElement.class, required = false)
            ,
            @XmlElement(name = "Product", type = ProductElement.class, required = false)
        })
        private final List<SurveyItemElement> surveyItems;

        public SurveyItemsElement() {
            this.surveyItems = new LinkedList<>();
        }

        /**
         * Returns the current template survey items
         *
         * @return List with the current template survey items
         */
        public List<SurveyItemElement> getSurveyItems() {
            return surveyItems;
        }
    }

    /**
     * Class that represents a survey item of the template
     */
    public static abstract class SurveyItemElement {

        public SurveyItemElement() {
        }
    }

    /**
     * Class that represents a category
     */
    public static class CategoryElement extends SurveyItemElement {

        /**
         * String with the category path
         */
        @XmlAttribute(name = "path")
        private String path;

        public CategoryElement() {
        }

        public CategoryElement(String path) {
            this.path = path;
        }

        /**
         * Returns the Category's path attribute.
         *
         * @return path attribute
         */
        public String getPath() {
            return path;
        }
    }

    /**
     * Class that represents a product
     */
    public static class ProductElement extends SurveyItemElement {

        /**
         * String with the product sku
         */
        @XmlAttribute(name = "sku")
        private String sku;

        public ProductElement() {
        }

        public ProductElement(String sku) {
            this.sku = sku;
        }

        /**
         * Returns the Product's SKU attribute.
         *
         * @return SKU attribute
         */
        public String getSku() {
            return sku;
        }
    }

    /**
     * Class that represents the template Question Script
     */
    public static class QuestionScriptElement {

        /**
         * List with all the on reply trigger questions of the question script
         */
        @XmlElements(
                @XmlElement(name = "Question"))
        private final List<ScriptedQuestionElement> scriptedQuestions;

        public QuestionScriptElement() {
            this.scriptedQuestions = new LinkedList<>();
        }

        /**
         * Returns all the on reply questions of the template question script
         *
         * @return List with all the on reply questions of the the template
         * question script
         */
        public List<ScriptedQuestionElement> getScriptedQuestions() {
            return scriptedQuestions;
        }

        public boolean addScriptedQuestion(ScriptedQuestionElement scriptedQuestion) {
            return scriptedQuestions.add(scriptedQuestion);
        }
    }

    /**
     * Class that represents a question that leads to another question on the
     * question script
     */
    public static class ScriptedQuestionElement {

        /**
         * ScriptedQuestion's questionID.
         */
        @XmlAttribute(name = "questionID")
        private String questionID;

        /**
         * ScriptedQuestion's list of OnReplyElement.
         */
        @XmlElements(
                @XmlElement(name = "OnReply"))
        private final List<OnReplyElement> onReplyList;

        public ScriptedQuestionElement() {
            this.onReplyList = new LinkedList<>();
        }

        public ScriptedQuestionElement(String questionID) {
            this();
            this.questionID = questionID;
        }

        /**
         * Retrieves the question's ID.
         *
         * @return the question's ID.
         */
        public String getQuestionID() {
            return questionID;
        }

        /**
         * Retrieves a list of OnReplyElement.
         *
         * @return list of OnReplyElement
         */
        public List<OnReplyElement> getOnReplyList() {
            return onReplyList;
        }

        public boolean addOnReply(OnReplyElement onReply) {
            return onReplyList.add(onReply);
        }
    }

    /**
     * Class that represents the on reply trigger question
     */
    public static class OnReplyElement {

        /**
         * OnReplyQuestion with the next question that is proceeded
         */
        @XmlElement(name = "Question")
        private ScriptedQuestionElement onReplyQuestion;

        /**
         * String with the options that trigger the on reply question
         */
        @XmlAttribute(name = "option")
        private String onReplyOptions;

        public OnReplyElement() {
        }

        public OnReplyElement(ScriptedQuestionElement nexQuestion, List<String> options) {
            this.onReplyQuestion = nexQuestion;
            setOnReplyOptions(options);
        }

        /**
         * Retrieves the option leading to the next question.
         *
         * @return option that leads to the next question
         */
        public List<String> getOnReplyOptions() {

            String[] options = onReplyOptions.split(",");

            for (int i = 0; i < options.length; i++) {
                options[i] = options[i].trim();
            }

            return Arrays.asList(options);
        }

        private void setOnReplyOptions(List<String> options) {
            String result = "";

            for (String option : options) {
                result = result.concat(option).concat(",");
            }

            result = result.substring(0, result.length() - 1);

            this.onReplyOptions = result;
        }

        /**
         * Retrieves the next question.
         *
         * @return the next question
         */
        public ScriptedQuestionElement getOnReplyQuestion() {
            return onReplyQuestion;
        }
    }
}
