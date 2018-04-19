package cdioil.backoffice.application;


import cdioil.domain.*;
import cdioil.domain.authz.Manager;
import cdioil.persistence.impl.CategoryQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.CategoryTemplatesLibraryRepositoryImpl;
import cdioil.persistence.impl.IndependentQuestionsLibraryRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class CreateTemplateController {

    private CategoryQuestionsLibrary categoryQuestionsLibrary;
    private IndependentQuestionsLibrary independentQuestionsLibrary;

    public CreateTemplateController() {
        CategoryQuestionsLibraryRepositoryImpl categoryQuestionsLibraryRepository = new CategoryQuestionsLibraryRepositoryImpl();
        IndependentQuestionsLibraryRepositoryImpl independentQuestionsLibraryRepository = new IndependentQuestionsLibraryRepositoryImpl();

        categoryQuestionsLibrary = categoryQuestionsLibraryRepository.findCategoryQuestionsLibrary();
        independentQuestionsLibrary = independentQuestionsLibraryRepository.findLibrary();

    }

    /**
     * Lists all the categories available for the manager
     *
     * @param manager manager to search the categories for
     * @return all categories assigned to the manager by the admin
     */
    public List<Category> listAllCategoriesForManager(Manager manager) {
        return manager.categoriesFromManager();
    }

    /**
     * Lists all the questions for the category
     *
     * @param category category chosen by the manager
     * @return all questions for the category
     */
    public List<Question> listQuestionsForCategory(Category category) {
        return new ArrayList<>(categoryQuestionsLibrary.categoryQuestionSet(category));
    }

    /**
     * Lists all the independent questions
     *
     * @return all the independent questions available
     */
    public List<Question> listIndependentQuestions() {
        return new ArrayList<>(independentQuestionsLibrary.getID());
    }

    /**
     * Creates Templates and persists it
     *
     * @param category category for the template
     * @param title tile of the template
     * @param questionGroup questionGroup of the template
     * @return true is the template was persisted with success and false if it wasn't persisted
     */
    public boolean createTemplate(Category category, String title, QuestionGroup questionGroup) {
        Template template = new Template(title, questionGroup);
        CategoryTemplatesLibraryRepositoryImpl categoryTemplatesLibraryRepository = new CategoryTemplatesLibraryRepositoryImpl();
        CategoryTemplatesLibrary templatesLibrary = categoryTemplatesLibraryRepository.findTemplatesForCategory();
        templatesLibrary.addTemplate(category, template);

        return categoryTemplatesLibraryRepository.merge(templatesLibrary) != null;
    }





}
