package cdioil.application.utils;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * SurveyTemplate class that represents a template of a survey
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 6.0 of FeedbackMonkey
 */
@XmlRootElement(name = "Template")
public final class SurveyTemplate {
    /**
     * String that represents the name of a template
     */
    @XmlAttribute(name = "name")
    private String name;
    /**
     * SurveyItems with the template survey items
     */
    @XmlElement(name = "SurveyItems")
    private SurveyItems templateSurveyItems;
    /**
     * Questions with the template templateQuestions
     */
    @XmlElement(name = "Questions")
    private Questions templateQuestions;
    /**
     * QuestionScript with the current template question script
     */
    @XmlElement(name = "QuestionScript")
    private QuestionScript templateQuestionScript;
    /**
     * Returns the current Survey Template name
     * @return String with the current template name
     */
    public String getName(){return name;}
    /**
     * Returns the current template questions
     * @return Questions with the current template questions
     */
    public Questions getTemplateQuestions(){return templateQuestions;}
    /**
     * Returns the current template survey items
     * @return SurveyItems with the current template survey items
     */
    public SurveyItems getTemplateSurveyItems(){return templateSurveyItems;}
    /**
     * Returns the current template question script
     * @return QuestionScript with the current template question script
     */
    public QuestionScript getTemplateQuestionScript(){return templateQuestionScript;}
    /**
     * Class that represents an aggregate of templateQuestions
     */
    public static class Questions{
        /**
         * List with all the template templateQuestions
         */
        @XmlElements(value = {
            @XmlElement(name = "BinaryQuestion" ,type = BinaryQuestion.class),
            @XmlElement(name = "QuantitativeQuestion",type = QuantitiveQuestion.class),
            @XmlElement(name = "MultipleChoiceQuestion",type = MultipleChoiceQuestion.class)
        })
        private List<Question> questions;
        /**
         * Returns all template templateQuestions
         * @return List with all template templateQuestions
         */
        public List<Question> getQuestions(){return questions;}
    }
    /**
     * Class that represents a base question from the template
     */
    public static class Question{
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
    }
    /**
     * Class that represents a binary question from the template
     */
    private static class BinaryQuestion extends Question{}
    /**
     * Class that represents a multiple choice question from the template
     */
    private static class MultipleChoiceQuestion extends Question{
        /**
         * List with all the multiple choice question options
         */
        @XmlElements(@XmlElement(name = "Option",type = Option.class))
        private List<Option> questionOptions;
    }
    /**
     * Class that represents a quantitive question from the template
     */
    private static class QuantitiveQuestion extends Question{
        /**
         * Short with the quantitive question min value scale
         */
        @XmlElement(name = "scaleMinValue")
        private short minValueScale;
        /**
         * Short with the quantitive question min value scale
         */
        @XmlElement(name = "scaleMaxValue")
        private short maxValueScale;
    }
    /**
     * Class that represents a multiple choice question option
     */
    private static class Option{
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
    public static class SurveyItems{
        /**
         * List with the template survey items
         */
        @XmlElements(value = {
            @XmlElement(name = "Category",type = Category.class,required = false),
            @XmlElement(name = "Product",type = Product.class,required = false)
        })
        private List<SurveyItem> surveyItems;
        /**
         * Returns the current template survey items
         * @return List with the current template survey items
         */
        public List<SurveyItem> getSurveyItems(){return surveyItems;}
    }
    /**
     * Class that represents a survey item of the template
     */
    public static abstract class SurveyItem{}
    /**
     * Class that represents a category
     */
    private static class Category extends SurveyItem{
        /**
         * String with the category path
         */
        @XmlAttribute(name = "path")
        private String path;
    }
    /**
     * Class that represents a product
     */
    private static class Product extends SurveyItem{
        /**
         * String with the product sku
         */
        @XmlAttribute(name = "sku")
        private String sku;
    }
    /**
     * Class that represents the template Question Script
     */
    public static class QuestionScript{
        /**
         * List with all the on reply trigger questions of the question script
         */
        @XmlElements(@XmlElement(name = "Question"))
        private List<Question> onReplyQuestions;
        /**
         * Returns all the on reply questions of the template question script
         * @return List with all the on reply questions of the the template question script
         */
        public List<Question> getOnReplyQuestions(){return onReplyQuestions;}
    }
    /**
     * Class that represents a question that leads to another question on the 
     * question script
     */
    private static class OnReplyQuestion extends Question{
        /**
         * OnReply with the question trigger
         */
        @XmlElement(name = "OnReply")
        private OnReply onReply;
    }
    /**
     * Class that represents the on reply trigger question
     */
    private static class OnReply{
        /**
         * OnReplyQuestion with the next question that is proceeded
         */
        @XmlElement(name = "Question")
        private OnReplyQuestion onReplyQuestion;
        /**
         * String with the options that trigger the on reply question
         */
        @XmlAttribute(name = "option")
        private String onReplyOptions;
    }
}
