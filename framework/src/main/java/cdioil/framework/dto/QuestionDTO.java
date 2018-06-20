package cdioil.framework.dto;

import java.util.List;

public class QuestionDTO extends GenericDTO implements DTO {

    public QuestionDTO(String type, String text, String questionType, List<String> options) {
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

    public List<String> getOptions() {
        return (List)super.get("options");
    }

    public void setOptions(List<String> options) {
        super.put("options", options);
    }

    @Override
    public String toString() {
        final String text = getText();
        final String type = getQuestionType();

        // Concatenate Options
        StringBuilder options = new StringBuilder();
        for (String option :
                getOptions()) {
            options.append(option + ",,");
        }

        return String.format("%s,,%ss,,%s", text, type, options);
    }
}
