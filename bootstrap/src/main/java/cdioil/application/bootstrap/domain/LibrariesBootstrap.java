package cdioil.application.bootstrap.domain;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.Category;
import cdioil.domain.CategoryQuestionsLibrary;
import cdioil.domain.CategoryTemplatesLibrary;
import cdioil.domain.IndependentQuestionsLibrary;
import cdioil.domain.IndependentTemplatesLibrary;
import cdioil.domain.MultipleChoiceQuestion;
import cdioil.domain.MultipleChoiceQuestionOption;
import cdioil.domain.Product;
import cdioil.domain.ProductQuestionsLibrary;
import cdioil.domain.ProductTemplatesLibrary;
import cdioil.domain.QuantitativeQuestion;
import cdioil.domain.QuantitativeQuestionOption;
import cdioil.domain.QuestionGroup;
import cdioil.domain.QuestionOption;
import cdioil.domain.SimpleTemplate;
import cdioil.domain.Template;
import cdioil.persistence.impl.CategoryQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.CategoryTemplatesLibraryRepositoryImpl;
import cdioil.persistence.impl.IndependentQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.IndependentTemplatesLibraryRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import cdioil.persistence.impl.ProductQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.ProductTemplatesLibraryRepositoryImpl;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Bootstrap class for all libraries.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class LibrariesBootstrap {

    private static final String BOOTSTRAP_CAT_PATH = "10938DC";
    private final MarketStructureRepositoryImpl marketRepo = new MarketStructureRepositoryImpl();
    private final Category cat = marketRepo.findCategoriesByPathPattern(BOOTSTRAP_CAT_PATH).get(0);

    /**
     * Creates all the question libraries and persists it in the database.
     */
    public LibrariesBootstrap() {
        //QUESTION LIBRARIES REPOS
        CategoryQuestionsLibraryRepositoryImpl categoryQuestionsLibraryRepo
                = new CategoryQuestionsLibraryRepositoryImpl();
        ProductQuestionsLibraryRepositoryImpl productQuestionsLibraryRepo
                = new ProductQuestionsLibraryRepositoryImpl();
        IndependentQuestionsLibraryRepositoryImpl independentQuestionsLibraryRepo
                = new IndependentQuestionsLibraryRepositoryImpl();

        //TEMPLATE LIBRARIES REPOS
        CategoryTemplatesLibraryRepositoryImpl categoryTemplatesLibraryRepo
                = new CategoryTemplatesLibraryRepositoryImpl();
        ProductTemplatesLibraryRepositoryImpl productTemplatesLibraryRepo
                = new ProductTemplatesLibraryRepositoryImpl();
        IndependentTemplatesLibraryRepositoryImpl independentTemplatesLibraryRepo
                = new IndependentTemplatesLibraryRepositoryImpl();

        //QUESTION LIBRRARIES
        CategoryQuestionsLibrary categoryQuestionsLibrary = new CategoryQuestionsLibrary();
        ProductQuestionsLibrary productQuestionsLibrary = new ProductQuestionsLibrary();
        IndependentQuestionsLibrary independentQuestionsLibrary = new IndependentQuestionsLibrary();

        bootstrapCategoryQuestionsLibrary(categoryQuestionsLibrary);
        categoryQuestionsLibraryRepo.add(categoryQuestionsLibrary);

        bootstrapProductQuestionsLibrary(productQuestionsLibrary);
        productQuestionsLibraryRepo.add(productQuestionsLibrary);

        bootstrapIndependentQuestionsLibrary(independentQuestionsLibrary);
        independentQuestionsLibraryRepo.add(independentQuestionsLibrary);

        //TEMPLATE LIBRARIES
        CategoryTemplatesLibrary categoryTemplatesLibrary = new CategoryTemplatesLibrary();
        ProductTemplatesLibrary productTemplatesLibrary = new ProductTemplatesLibrary();
        IndependentTemplatesLibrary independentTemplatesLibrary = new IndependentTemplatesLibrary();

        bootstrapCategoryTemplatesLibrary(categoryTemplatesLibrary);
        categoryTemplatesLibraryRepo.add(categoryTemplatesLibrary);

        //TODO: add missing bootstrapping
        productTemplatesLibraryRepo.add(productTemplatesLibrary);
        independentTemplatesLibraryRepo.add(independentTemplatesLibrary);
    }

    /**
     * Sets up a CategoryQuestionsLibrary.
     */
    private void bootstrapCategoryQuestionsLibrary(CategoryQuestionsLibrary categoryQuestionsLibrary) {
        String id = "3";
        String id2 = "2";
        BinaryQuestion q = new BinaryQuestion("Questao Binaria Bootstrap 3", id);
        BinaryQuestion q2 = new BinaryQuestion("Questao Binaria Bootstrap 2", id2);
        MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Questao EM Bootstrap 5", "5",
                createMultipleChoiceOptionList());
        QuantitativeQuestion q4 = new QuantitativeQuestion("Questao Quantitativa Bootstrap 98",
                "98", createQuantitativeOptionList());
        categoryQuestionsLibrary.addCategory(cat);
        addAllCategories(categoryQuestionsLibrary);
        categoryQuestionsLibrary.addQuestion(q, cat);
        categoryQuestionsLibrary.addQuestion(q2, cat);
        categoryQuestionsLibrary.addQuestion(q3, cat);
        categoryQuestionsLibrary.addQuestion(q4, cat);

        /*==================================
        Bootstrap questions for Stress Test
        ====================================*/
        QuantitativeQuestionOption qo1 = new QuantitativeQuestionOption(1.0);
        QuantitativeQuestionOption qo2 = new QuantitativeQuestionOption(2.0);
        QuantitativeQuestionOption qo3 = new QuantitativeQuestionOption(3.0);
        QuantitativeQuestionOption qo4 = new QuantitativeQuestionOption(4.0);

        List<QuestionOption> questionOptions16 = Arrays.asList(qo1, qo2, qo3, qo4);

        MultipleChoiceQuestionOption mo1 = new MultipleChoiceQuestionOption("Na, na, na");
        MultipleChoiceQuestionOption mo2 = new MultipleChoiceQuestionOption("Ne, ne, ne");
        MultipleChoiceQuestionOption mo3 = new MultipleChoiceQuestionOption("Ni, ni, ni");

        List<QuestionOption> questionOptions34 = Arrays.asList(mo1, mo2, mo3);

        // Create questions
        BinaryQuestion q12
                = new BinaryQuestion("Bla, bla?", "12");
        BinaryQuestion q13
                = new BinaryQuestion("Bla, bla, bla?", "13");
        BinaryQuestion q15
                = new BinaryQuestion("Blo, blo, blo?", "15");
        QuantitativeQuestion q16
                = new QuantitativeQuestion("Ble, ble", "16",
                        questionOptions16);
        MultipleChoiceQuestion q34
                = new MultipleChoiceQuestion("Bli, bli, bli", "34",
                        questionOptions34);

        Category category = marketRepo.
                findCategoriesByPathPattern("10DC-10UN-1002CAT-4SCAT-2UB").iterator().next();

        categoryQuestionsLibrary.addQuestion(q12, category);
        categoryQuestionsLibrary.addQuestion(q13, category);
        categoryQuestionsLibrary.addQuestion(q15, category);
        categoryQuestionsLibrary.addQuestion(q16, category);
        categoryQuestionsLibrary.addQuestion(q34, category);
    }

    /**
     * Adds all categories to the library
     *
     * @param categoryQuestionsLibrary category questions library being built
     */
    private void addAllCategories(CategoryQuestionsLibrary categoryQuestionsLibrary) {
        for (Category category : marketRepo.findMarketStructure().getAllCategories()) {
            categoryQuestionsLibrary.addCategory(category);
        }
    }

    /**
     * Sets up a ProductQuestionsLibrary.
     */
    private void bootstrapProductQuestionsLibrary(ProductQuestionsLibrary productQuestionsLibrary) {
        Product prod = cat.getProductSetIterator().next();
        String id = "1";
        BinaryQuestion binQuestion = new BinaryQuestion("Questao Binaria Bootstrap 1", id);
        MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("Questao EM Bootstrap 100", "100",
                createMultipleChoiceOptionList());
        QuantitativeQuestion q3 = new QuantitativeQuestion("Questao Quantitativa Bootstrap 2000",
                "2000", createQuantitativeOptionList());
        productQuestionsLibrary.addProduct(prod);
        productQuestionsLibrary.addQuestion(binQuestion, prod);
        productQuestionsLibrary.addQuestion(q2, prod);
        productQuestionsLibrary.addQuestion(q3, prod);
    }

    /**
     * Sets up an IndependentQuestionsLibrary.
     */
    private void bootstrapIndependentQuestionsLibrary(IndependentQuestionsLibrary independentQuestionsLibrary) {
        String id = "A4";
        String id2 = "B6";
        BinaryQuestion q = new BinaryQuestion("Questao Binaria Bootstrap A4", id);
        BinaryQuestion q2 = new BinaryQuestion("Questao Binaria Bootstrap B6", id2);
        MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Questao EM Bootstrap I5",
                "I5", createMultipleChoiceOptionList());
        QuantitativeQuestion q4 = new QuantitativeQuestion("Questao Quantitativa Bootstrap M2", "M2",
                createQuantitativeOptionList());
        independentQuestionsLibrary.addQuestion(q2);
        independentQuestionsLibrary.addQuestion(q);
        independentQuestionsLibrary.addQuestion(q3);
        independentQuestionsLibrary.addQuestion(q4);
    }

    private void bootstrapCategoryTemplatesLibrary(CategoryTemplatesLibrary categoryTemplatesLibrary) {
        BinaryQuestion q = new BinaryQuestion("Questao Binaria Template Bootstrap A578", "A578");
        BinaryQuestion q2 = new BinaryQuestion("Questao Binaria Template Bootstrap K999", "K999");
        MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Questao EM Template "
                + "Bootstrap Q123", "Q123", createMultipleChoiceOptionList());
        QuantitativeQuestion q4 = new QuantitativeQuestion("Questao Quantitativa Template Bootstrap V956",
                "V956", createQuantitativeOptionList());
        Template bootstrapTemplate = new SimpleTemplate("template");
        bootstrapTemplate.addQuestion(q);
        bootstrapTemplate.addQuestion(q2);
        bootstrapTemplate.addQuestion(q3);
        bootstrapTemplate.addQuestion(q4);
        categoryTemplatesLibrary.addCategory(cat);
        categoryTemplatesLibrary.addTemplate(cat, bootstrapTemplate);
    }

    /**
     * Creates a multiple choice option list.
     *
     * @return question option list
     */
    private List<QuestionOption> createMultipleChoiceOptionList() {
        LinkedList<QuestionOption> optionList = new LinkedList<>();
        MultipleChoiceQuestionOption option1 = new MultipleChoiceQuestionOption("Option 1");
        MultipleChoiceQuestionOption option2 = new MultipleChoiceQuestionOption("Option 2");
        MultipleChoiceQuestionOption option3 = new MultipleChoiceQuestionOption("Option 3");
        MultipleChoiceQuestionOption option4 = new MultipleChoiceQuestionOption("Option 4");
        MultipleChoiceQuestionOption option5 = new MultipleChoiceQuestionOption("Other");
        optionList.add(option1);
        optionList.add(option2);
        optionList.add(option3);
        optionList.add(option4);
        optionList.add(option5);
        return optionList;
    }

    /**
     * Creates a quantitative option list.
     *
     * @return question option list
     */
    private List<QuestionOption> createQuantitativeOptionList() {
        QuantitativeQuestionOption quantitativeOption1 = new QuantitativeQuestionOption(0.0);
        QuantitativeQuestionOption quantitativeOption2 = new QuantitativeQuestionOption(1.0);
        QuantitativeQuestionOption quantitativeOption3 = new QuantitativeQuestionOption(2.0);
        QuantitativeQuestionOption quantitativeOption4 = new QuantitativeQuestionOption(3.0);
        QuantitativeQuestionOption quantitativeOption5 = new QuantitativeQuestionOption(4.0);
        QuantitativeQuestionOption quantitativeOption6 = new QuantitativeQuestionOption(5.0);
        LinkedList<QuestionOption> quantitativeOptionList = new LinkedList<>();
        quantitativeOptionList.add(quantitativeOption1);
        quantitativeOptionList.add(quantitativeOption2);
        quantitativeOptionList.add(quantitativeOption3);
        quantitativeOptionList.add(quantitativeOption4);
        quantitativeOptionList.add(quantitativeOption5);
        quantitativeOptionList.add(quantitativeOption6);
        return quantitativeOptionList;
    }
}
