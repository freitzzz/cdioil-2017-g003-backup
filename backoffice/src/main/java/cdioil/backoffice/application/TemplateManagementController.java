package cdioil.backoffice.application;

import cdioil.domain.CategoryTemplatesLibrary;
import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import cdioil.domain.Template;
import cdioil.domain.TemplateGroup;
import cdioil.framework.dto.QuestionDTO;
import cdioil.framework.dto.TemplateDTO;
import cdioil.persistence.impl.CategoryTemplatesLibraryRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller Class for TemplateManagementWindow
 */
public class TemplateManagementController {

    /**
     * Returns the list with all available templates.
     *
     * @return all templates
     */
    public List<TemplateDTO> getAllTemplates() {
        List<Template> templates = new ArrayList<>();

        Iterable<CategoryTemplatesLibrary> templateLibrary =
                new CategoryTemplatesLibraryRepositoryImpl().findAll();

        for (CategoryTemplatesLibrary categoryTemplatesLibrary : templateLibrary) {

            for (TemplateGroup templateGroup : categoryTemplatesLibrary.getLibrary().values()) {
                templates.addAll(templateGroup.getTemplates());
            }
        }

        List<TemplateDTO> templateDTOList = new ArrayList<>();

        for (Template template :
                templates) {
            List<QuestionDTO> questionDTOList = new ArrayList<>();
            for (Question templateQuestion :
                    template.getQuestions()) {
                List<String> questionOptionList = new ArrayList<>();
                for (QuestionOption questionOption :
                        templateQuestion.getOptionList()) {
                    questionOptionList.add(questionOption.toString());
                }

                QuestionDTO questionDTO =
                        new QuestionDTO("question", templateQuestion.getQuestionText(),
                                templateQuestion.getType().name(), questionOptionList);

                questionDTOList.add(questionDTO);
            }

            TemplateDTO dto = new TemplateDTO("template", template.getTitle(), questionDTOList);

            templateDTOList.add(dto);
        }

        return templateDTOList;
    }
}
