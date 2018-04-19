package cdioil.backoffice.application.authz;


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
}
