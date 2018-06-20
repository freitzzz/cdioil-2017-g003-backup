package cdioil.framework.dto;

import java.util.List;

public class TemplateDTO extends GenericDTO implements DTO{
    public TemplateDTO(String type, String name, List<QuestionDTO> questions) {
        super(type);

        setName(name);
        setQuestions(questions);
    }

    public String getName() {
        return super.get("name").toString();
    }

    public void setName(String name) {
        super.put("name", name);
    }

    public List<QuestionDTO> getQuestions() {
        return (List<QuestionDTO>) super.get("questions");
    }

    public void setQuestions(List<QuestionDTO> questions) {
        super.put("questions", questions);
    }

    @Override
    public String toString() {
        return getName();
    }
}
