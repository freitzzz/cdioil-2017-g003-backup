package cdioil.application.bootstrap.domain;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.Category;
import cdioil.domain.GlobalLibrary;
import cdioil.domain.MultipleChoiceQuestion;
import cdioil.domain.Product;
import cdioil.domain.QuantitativeQuestion;
import cdioil.persistence.impl.GlobalLibraryRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Bootstrap class for all question libraries.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class QuestionLibrariesBootstrap {

    private static final String BOOTSTRAP_CAT_PATH = "10938DC";
    private final MarketStructureRepositoryImpl marketRepo = new MarketStructureRepositoryImpl();
    private final Category cat = marketRepo.findCategoriesByPathPattern(BOOTSTRAP_CAT_PATH).get(0);

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
        LinkedList<String> q3List = new LinkedList<>();
        q3List.add("A");
        q3List.add("B");
        q3List.add("C");
        q3List.add("D");
        MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Questao EM Bootstrap 5", "5", q3List);
        QuantitativeQuestion q4 = new QuantitativeQuestion("Questao Quantitativa Bootstrap 98", "98", 0.0, 5.0);
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
        Product prod = cat.getProductSet().iterator().next();
        String id = "1";
        BinaryQuestion q = new BinaryQuestion("Questao Binaria Bootstrap 1", id);
        LinkedList<String> q2List = new LinkedList<>();
        q2List.add("A");
        q2List.add("B");
        q2List.add("C");
        q2List.add("D");
        MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("Questao EM Bootstrap 100", "100", q2List);
        QuantitativeQuestion q3 = new QuantitativeQuestion("Questao Quantitativa Bootstrap 2000", "2000", 0.0, 10.0);
        globalLibrary.getProdQuestionsLibrary().addProduct(prod);
        globalLibrary.getProdQuestionsLibrary().addQuestion(q, prod);
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
        LinkedList<String> q3List = new LinkedList<>();
        q3List.add("A");
        q3List.add("B");
        q3List.add("C");
        q3List.add("D");
        MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Questao Quantitativa Bootstrap I5", "I5", q3List);
        QuantitativeQuestion q4 = new QuantitativeQuestion("Questao Quantitativa Bootstrap M2", "M2", 0.0, 3.0);
        globalLibrary.getIndQuestionsLibrary().addQuestion(q2);
        globalLibrary.getIndQuestionsLibrary().addQuestion(q);
        globalLibrary.getIndQuestionsLibrary().addQuestion(q3);
        globalLibrary.getIndQuestionsLibrary().addQuestion(q4);
    }

}
