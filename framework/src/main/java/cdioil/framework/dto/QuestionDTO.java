package cdioil.framework.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionDTO extends GenericDTO implements DTO {

    private String type;

    private Map<String, String> questionOptionFlowMap;

    private String surveyItemID;

    public QuestionDTO(String type, String text, String questionType, List<String> options) {
        super(type);

        setType(type);
        setText(text);
        setQuestionType(questionType);
        setOptions(options);

        questionOptionFlowMap = new HashMap<>();
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return super.get("text").toString();
    }

    public void setText(String text) {
        super.put("text", text);
    }

    public void setQuestionType(String questionType) {
        super.put("questionType", questionType);
    }

    public String getQuestionType() {
        return super.get("questionType").toString();
    }

    public List<String> getOptions() {
        return (List) super.get("options");
    }

    public void setOptions(List<String> options) {
        super.put("options", options);
    }

    public void addNextQuestion(String option, String index) {
        questionOptionFlowMap.put(option, index);
    }

    public Map<String, String> getQuestionOptionFlowMap() {
        return questionOptionFlowMap;
    }

    public String getSurveyItemID() {
        return surveyItemID;
    }

    public void setSurveyItemID(String surveyItemID) {
        this.surveyItemID = surveyItemID;
    }
}
