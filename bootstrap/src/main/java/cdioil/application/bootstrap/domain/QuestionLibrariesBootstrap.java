package cdioil.application.bootstrap.domain;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.BinaryQuestionOption;
import cdioil.domain.Category;
import cdioil.domain.GlobalLibrary;
import cdioil.domain.MultipleChoiceQuestion;
import cdioil.domain.MultipleChoiceQuestionOption;
import cdioil.domain.Product;
import cdioil.domain.QuantitativeQuestion;
import cdioil.domain.QuantitativeQuestionOption;
import cdioil.domain.QuestionOption;
import cdioil.persistence.impl.GlobalLibraryRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import java.util.LinkedList;
import java.util.List;

/**
 * Bootstrap class for all question libraries.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class QuestionLibrariesBootstrap {

    private static final String BOOTSTRAP_CAT_PATH = "10938DC";
    private final MarketStructureRepositoryImpl marketRepo = new MarketStructureRepositoryImpl();
    private final Category cat = marketRepo.findCategoriesByPathPattern(BOOTSTRAP_CAT_PATH).get(0);
    private final List<QuestionOption> multipleChoiceOptionList = createMultipleChoiceOptionList();
    private final List<QuestionOption> quantitativeOptionlist = createQuantitativeOptionList();

    /**
     * Creates a global library, sets up the libraries that it contains and
     * persists it in the database.
     */
    public QuestionLibrariesBootstrap() {
        GlobalLibrary globalLibrary = new GlobalLibrary();
        GlobalLibraryRepositoryImpl repo = new GlobalLibraryRepositoryImpl();
        bootstrapCategoryQuestionsLibrary(globalLibrary);
        bootstrapProductQuestionsLibrary(globalLibrary);
        bootstrapIndependentQuestionsLibrary(globalLibrary);
        repo.add(globalLibrary);
    }

    /**
     * Sets up a CategoryQuestionsLibrary.
     */
    private void bootstrapCategoryQuestionsLibrary(GlobalLibrary globalLibrary) {
        String id = "3";
        String id2 = "2";
        BinaryQuestion q = new BinaryQuestion("Questao Binaria Bootstrap 3", id);
        BinaryQuestion q2 = new BinaryQuestion("Questao Binaria Bootstrap 2", id2);
        MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Questao EM Bootstrap 5", "5",
                multipleChoiceOptionList);
        QuantitativeQuestion q4 = new QuantitativeQuestion("Questao Quantitativa Bootstrap 98",
                "98", quantitativeOptionlist);
        globalLibrary.getCatQuestionsLibrary().addCategory(cat);
        marketRepo.findMarketStructure().getAllCategories().forEach((databaseCat) -> {
            globalLibrary.getCatQuestionsLibrary().addCategory(databaseCat);
        });
        globalLibrary.getCatQuestionsLibrary().addQuestion(q, cat);
        globalLibrary.getCatQuestionsLibrary().addQuestion(q2, cat);
        globalLibrary.getCatQuestionsLibrary().addQuestion(q3, cat);
        globalLibrary.getCatQuestionsLibrary().addQuestion(q4, cat);
    }

    /**
     * Sets up a ProductQuestionsLibrary.
     */
    private void bootstrapProductQuestionsLibrary(GlobalLibrary globalLibrary) {
        Product prod = cat.getProductSetIterator().next();
        String id = "1";
        BinaryQuestion binQuestion = new BinaryQuestion("Questao Binaria Bootstrap 1", id);
        MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("Questao EM Bootstrap 100", "100",
                multipleChoiceOptionList);
        QuantitativeQuestion q3 = new QuantitativeQuestion("Questao Quantitativa Bootstrap 2000",
                "2000", quantitativeOptionlist);
        globalLibrary.getProdQuestionsLibrary().addProduct(prod);
        globalLibrary.getProdQuestionsLibrary().addQuestion(binQuestion, prod);
        globalLibrary.getProdQuestionsLibrary().addQuestion(q2, prod);
        globalLibrary.getProdQuestionsLibrary().addQuestion(q3, prod);
    }

    /**
     * Sets up an IndependentQuestionsLibrary.
     */
    private void bootstrapIndependentQuestionsLibrary(GlobalLibrary globalLibrary) {
        String id = "A4";
        String id2 = "B6";
        BinaryQuestion q = new BinaryQuestion("Questao Binaria Bootstrap A4", id);
        BinaryQuestion q2 = new BinaryQuestion("Questao Binaria Bootstrap B6", id2);
        MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Questao Escolha Multipla Bootstrap I5",
                "I5", multipleChoiceOptionList);
        QuantitativeQuestion q4 = new QuantitativeQuestion("Questao Quantitativa Bootstrap M2", "M2",
                quantitativeOptionlist);
        globalLibrary.getIndQuestionsLibrary().addQuestion(q2);
        globalLibrary.getIndQuestionsLibrary().addQuestion(q);
        globalLibrary.getIndQuestionsLibrary().addQuestion(q3);
        globalLibrary.getIndQuestionsLibrary().addQuestion(q4);
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
