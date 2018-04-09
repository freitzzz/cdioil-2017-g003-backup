package cdioil.backoffice.application;

import cdioil.application.utils.QuestionsReaderFactory;
import cdioil.domain.*;
import cdioil.domain.authz.Manager;
import cdioil.persistence.impl.GlobalLibraryRepositoryImpl;
import cdioil.application.utils.QuestionsReader;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import java.util.HashSet;

import java.util.Map;
import java.util.List;
import java.util.Set;

/**
 * Controller for use cases US-205 (import independent questions from file) and
 * US-210 (import category questions from file).
 *
 * @author Ana Guerra (1161191)
 * @author António Sousa [1161371]
 */
public class ImportQuestionsController {

    /**
     * Instance of Global Library.
     */
    private final GlobalLibrary globalLibrary;

    /**
     * Instance of Global Library Repository.
     */
    private final GlobalLibraryRepositoryImpl globalLibraryRepo;

    /**
     * Currrently logged-in manager.
     */
    private Manager manager;

    /**
     * Instantiates a controller for importing independent questions (US-205).
     */
    public ImportQuestionsController() {
        this.globalLibraryRepo = new GlobalLibraryRepositoryImpl();
        this.globalLibrary = globalLibraryRepo.findGlobalLibrary();
    }

    /**
     * Creates new controller for the Import questions use case (US-210)
     *
     * @param manager Manager currently logged-in Manager
     */
    public ImportQuestionsController(Manager manager) {
        this();
        this.manager = manager;
    }

    /**
     * Import questions related to categories from a file.
     *
     * @param filename Name of the file
     * @return an integer with success
     */
    public int importCategoryQuestions(String filename) {

        Set<Question> successfullyImportedQuestions = new HashSet<>();

        QuestionsReader questionsReader = QuestionsReaderFactory.create(filename);
        if (questionsReader != null) {

            Map<String, List<Question>> questionsByCatPath = questionsReader.readCategoryQuestions();

            MarketStructureRepositoryImpl marketStructureRepository = new MarketStructureRepositoryImpl();

            CategoryQuestionsLibrary categoryQuestionsLibrary = globalLibrary.getCatQuestionsLibrary();

            Set<Map.Entry<String, List<Question>>> entries = questionsByCatPath.entrySet();

            for (Map.Entry<String, List<Question>> mapEntry : entries) {

                String path = mapEntry.getKey();
                List<Question> questionList = mapEntry.getValue();

                //Fetch all the categories in the repository with a matching path pattern
                List<Category> categoryList = marketStructureRepository.findCategoriesByPathPattern(path);

                //If no categories are found with a matching path pattern, then skip to next iteration
                if (categoryList == null) {
                    continue;
                }

                /*For each of the matching categories, check if the manager is associated to that category and, if so,
                add it to the library and then add its questions*/
                for (Category cat : categoryList) {

                    if (manager.isAssociatedWithCategory(cat)) {

                        categoryQuestionsLibrary.addCategory(cat);

                        for (Question q : questionList) {
                            boolean added = categoryQuestionsLibrary.addQuestion(q, cat);
                            if (added) {
                                successfullyImportedQuestions.add(q);
                            }
                        }
                    }
                }
            }
            globalLibraryRepo.merge(globalLibrary);
        }

        return successfullyImportedQuestions.size();
    }

    /**
     * Imports questions independent of categories from a file.
     *
     * @param filename Name of the file
     */
    public void importIndependentQuestions(String filename) {

        QuestionsReader questionsReader = QuestionsReaderFactory.create(filename);

        if (questionsReader != null) {

            IndependentQuestionsLibrary indQuestionLib = globalLibrary.getIndQuestionsLibrary();

            List<Question> questions = questionsReader.readIndependentQuestions();

            if (questions != null) {

                for (Question q : questions) {

                    indQuestionLib.addQuestion(q);
                }
            }
            globalLibraryRepo.merge(globalLibrary);
        }
    }

}
