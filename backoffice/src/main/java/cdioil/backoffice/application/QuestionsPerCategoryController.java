package cdioil.backoffice.application;

import cdioil.domain.Category;
import cdioil.domain.CategoryQuestionsLibrary;
import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import cdioil.framework.dto.QuestionDTO;
import cdioil.persistence.impl.CategoryQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Questions per Category Controller Class
 */
public class QuestionsPerCategoryController {

    /**
     * Market Structure Repository
     */
    private MarketStructureRepositoryImpl mkRepo = new MarketStructureRepositoryImpl();

    /**
     * CategoryQuestions Library
     */
    private CategoryQuestionsLibraryRepositoryImpl catQuestionsRepo =
            new CategoryQuestionsLibraryRepositoryImpl();

    /**
     * Finds all Category Questions
     *
     * @param catPath category path
     * @return list of question text from a given category
     */
    public List<QuestionDTO> getCategoryQuestions(String catPath) {
        List<Category> categories = mkRepo.findCategoriesByIdentifierPattern(catPath);

        if (categories == null || categories.isEmpty()) return null;

        Category cat = categories.get(0);

        CategoryQuestionsLibrary lib = catQuestionsRepo.findCategoryQuestionsLibrary();

        Set<Question> questionSet = lib.categoryQuestionSet(cat);

        if (questionSet == null) return null;

        Iterator<Question> it = questionSet.iterator();

        List<QuestionDTO> questions = new ArrayList<>();

        while (it.hasNext()) {
            Question q = it.next();

            List<String> options = new ArrayList<>();

            for (QuestionOption option:
                 q.getOptionList()) {
                options.add(option.toString());
            }

            QuestionDTO dto = new QuestionDTO("question", q.getQuestionText(),
                    q.getType().toString(), options.toString());
            questions.add(dto);
        }

        return questions;
    }
}
