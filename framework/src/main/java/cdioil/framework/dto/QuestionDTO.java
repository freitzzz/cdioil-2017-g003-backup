package cdioil.framework.dto;

public class QuestionDTO extends GenericDTO implements DTO {

    public QuestionDTO(String type, String text, String questionType, String options) {
        super(type);
        setText(text);
        setQuestionType(questionType);
        setOptions(options);
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

    public String getOptions() {
        return super.get("options").toString();
    }

    public void setOptions(String options) {
        super.put("options", options);
    }
}
