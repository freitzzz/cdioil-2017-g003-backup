package cdioil.application.bootstrap.domain;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.Category;
import cdioil.domain.GlobalLibrary;
import cdioil.domain.MultipleChoiceQuestion;
import cdioil.domain.Product;
import cdioil.domain.QuantitativeQuestion;
import cdioil.persistence.impl.GlobalLibraryRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
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
        BinaryQuestion q = new BinaryQuestion("Acha que a percentagem de alcool"
                + "nos vinhos e satisfatoria?", id);
        BinaryQuestion q2 = new BinaryQuestion("Os nomes das marcas de vinhos"
                + "sao apelativos?", id2);
        LinkedList<String> q3List = new LinkedList<>();
        q3List.add("A");
        q3List.add("B");
        q3List.add("C");
        q3List.add("D");
        MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Bla bla", "5", q3List);
        QuantitativeQuestion q4 = new QuantitativeQuestion("Wow?", "98", 0.0, 5.0, 1.0);
        globalLibrary.getCatQuestionsLibrary().addCategory(cat);
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
        BinaryQuestion q = new BinaryQuestion("Gostaria de ver mais produtos"
                + "Nestle nas lojas Continente?", id);
        LinkedList<String> q2List = new LinkedList<>();
        q2List.add("A");
        q2List.add("B");
        q2List.add("C");
        q2List.add("D");
        MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("Bla bla", "100", q2List);
        QuantitativeQuestion q3 = new QuantitativeQuestion("Wow?", "2000", 0.0, 10.0, 2.0);
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
        BinaryQuestion q = new BinaryQuestion("Acha a embalagem apelativa?", id);
        BinaryQuestion q2 = new BinaryQuestion("Acha que este produto e caro?", id2);
        LinkedList<String> q3List = new LinkedList<>();
        q3List.add("A");
        q3List.add("B");
        q3List.add("C");
        q3List.add("D");
        MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Bla bla", "I5", q3List);
        QuantitativeQuestion q4 = new QuantitativeQuestion("Wow?", "M2", 0.0, 3.0, 0.5);
        globalLibrary.getIndQuestionsLibrary().addQuestion(q2);
        globalLibrary.getIndQuestionsLibrary().addQuestion(q);
        globalLibrary.getIndQuestionsLibrary().addQuestion(q3);
        globalLibrary.getIndQuestionsLibrary().addQuestion(q4);
    }

}
