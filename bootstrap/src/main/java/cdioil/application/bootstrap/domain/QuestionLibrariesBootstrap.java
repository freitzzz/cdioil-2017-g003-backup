package cdioil.application.bootstrap.domain;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.Category;
import cdioil.domain.CategoryQuestionsLibrary;
import cdioil.domain.EAN;
import cdioil.domain.IndependentQuestionsLibrary;
import cdioil.domain.Product;
import cdioil.domain.ProductQuestionsLibrary;
import cdioil.domain.QRCode;
import cdioil.persistence.impl.CategoryQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.IndependentQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.ProductQuestionsLibraryRepositoryImpl;

/**
 * Bootstrap class for all question libraries.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class QuestionLibrariesBootstrap {

    public QuestionLibrariesBootstrap() {
        bootstrapCategoryQuestionsLibrary();
        bootstrapProductQuestionsLibrary();
        bootstrapIndependentQuestionsLibrary();
    }

    /**
     * Creates a CategoryQuestionsLibrary and persists it in the database.
     */
    private void bootstrapCategoryQuestionsLibrary() {
        CategoryQuestionsLibrary library = new CategoryQuestionsLibrary();
        Category cat = new Category("Vinhos", "100CAT", "10DC-10UN-100CAT");
        BinaryQuestion q = new BinaryQuestion("Acha que a percentagem de alcool"
                + "nos vinhos e satisfatoria?");
        BinaryQuestion q2 = new BinaryQuestion("Os nomes das marcas de vinhos"
                + "sao apelativos?");
        library.addCategory(cat);
        library.addQuestion(q, cat);
        library.addQuestion(q2, cat);
        CategoryQuestionsLibraryRepositoryImpl repo = new CategoryQuestionsLibraryRepositoryImpl();
        repo.add(library);
    }

    /**
     * Creates a ProductQuestionsLibrary and persists it in the database.
     */
    private void bootstrapProductQuestionsLibrary() {
        ProductQuestionsLibrary library = new ProductQuestionsLibrary();
        Product prod = new Product("Chocolate Nestle", new EAN("544231234"),
                new QRCode("4324235"));
        BinaryQuestion q = new BinaryQuestion("Gostaria de ver mais produtos"
                + "Nestle nas lojas Continente?");
        library.addProduct(prod);
        library.addQuestion(q, prod);
        ProductQuestionsLibraryRepositoryImpl repo = new ProductQuestionsLibraryRepositoryImpl();
        repo.add(library);
    }

    /**
     * Creates an IndependentQuestionsLibrary and persists it in the database.
     */
    private void bootstrapIndependentQuestionsLibrary() {
        IndependentQuestionsLibrary library = new IndependentQuestionsLibrary();
        BinaryQuestion q = new BinaryQuestion("Acha a embalagem apelativa?");
        BinaryQuestion q2 = new BinaryQuestion("Acha que este produto e caro?");
        library.addQuestion(q2);
        library.addQuestion(q);
        IndependentQuestionsLibraryRepositoryImpl repo = new IndependentQuestionsLibraryRepositoryImpl();
        repo.add(library);
    }

}
