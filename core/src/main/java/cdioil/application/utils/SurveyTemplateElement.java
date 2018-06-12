package cdioil.application.utils;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * SurveyTemplate class that represents a template of a survey
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
     * SurveyItems with the template survey items
     */
    @XmlElement(name = "SurveyItems")
    private SurveyItemsElement templateSurveyItems;
    /**
     * Questions with the template templateQuestions
     */
    @XmlElement(name = "Questions")
    private QuestionsElement templateQuestions;
    /**
     * QuestionScript with the current template question script
     */
    @XmlElement(name = "QuestionScript")
    private QuestionScriptElement templateQuestionScript;

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
         * List with all the template templateQuestions
         */
        @XmlElements(value = {
            @XmlElement(name = "BinaryQuestion", type = BinaryQuestionElement.class)
            ,
            @XmlElement(name = "QuantitativeQuestion", type = QuantitativeQuestionElement.class)
            ,
            @XmlElement(name = "MultipleChoiceQuestion", type = MultipleChoiceQuestionElement.class)
        })
        private List<QuestionElement> questions;

        /**
         * Returns all template templateQuestions
         *
         * @return List with all template templateQuestions
         */
        public List<QuestionElement> getQuestions() {
            return questions;
        }
    }

    /**
     * Class that represents a base question from the template
     */
    public static class QuestionElement {

        /**
         * String that represents the id of a question
         */
        @XmlAttribute(name = "questionID")
        private String id;
        /**
         * String that represents the text of the question
         */
        @XmlElement(name = "text")
        private String text;

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
    private static class BinaryQuestionElement extends QuestionElement {
    }

    /**
     * Class that represents a multiple choice question from the template
     */
    private static class MultipleChoiceQuestionElement extends QuestionElement {

        /**
         * List with all the multiple choice question options
         */
        @XmlElements(
                @XmlElement(name = "Option", type = OptionElement.class))
        private List<OptionElement> questionOptions;

        /**
         * Returns all of the MultipleChoiceQuestion's options.
         *
         * @return a list containing all of the question's options.
         */
        public List<OptionElement> getQuestionOptions() {
            return questionOptions;
        }
    }

    /**
     * Class that represents a quantitive question from the template
     */
    private static class QuantitativeQuestionElement extends QuestionElement {

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
    }

    /**
     * Class that represents a multiple choice question option
     */
    private static class OptionElement {

        /**
         * Short that represents the option number
         */
        @XmlAttribute(name = "num")
        private short optionNumber;
        /**
         * String that represents the option text
         */
        @XmlAttribute(name = "text")
        private String text;
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
        private List<SurveyItemElement> surveyItems;

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
        private List<QuestionElement> onReplyQuestions;

        /**
         * Returns all the on reply questions of the template question script
         *
         * @return List with all the on reply questions of the the template
         * question script
         */
        public List<QuestionElement> getOnReplyQuestions() {
            return onReplyQuestions;
        }
    }

    /**
     * Class that represents a question that leads to another question on the
     * question script
     */
    private static class OnReplyQuestionElement extends QuestionElement {

        /**
         * OnReply with the question trigger
         */
        @XmlElement(name = "OnReply")
        private OnReplyElement onReply;
    }

    /**
     * Class that represents the on reply trigger question
     */
    private static class OnReplyElement {

        /**
         * OnReplyQuestion with the next question that is proceeded
         */
        @XmlElement(name = "Question")
        private OnReplyQuestionElement onReplyQuestion;
        /**
         * String with the options that trigger the on reply question
         */
        @XmlAttribute(name = "option")
        private String onReplyOptions;
    }
}
