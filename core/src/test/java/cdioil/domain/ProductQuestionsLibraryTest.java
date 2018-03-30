package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for ProductQuestionsLibrary class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ProductQuestionsLibraryTest {

    /**
     * Test of productQuestionSet method, of class ProductQuestionsLibrary.
     */
    @Test
    public void testProductQuestionSet() {
        System.out.println("productQuestionSet");
        Product product = new Product("ProdutoTeste", new EAN("544231234"),
                new QRCode("4324235"));
        ProductQuestionsLibrary instance = new ProductQuestionsLibrary();
        assertNull("The condition should succeed because there aren't any"
                + "products in the library.", instance.productQuestionSet(product));
        instance.addProduct(product);
        assertNotNull("The condition should succeed because there is a product"
                + "in the library.", instance.productQuestionSet(product));
    }

    /**
     * Test of addProduct method, of class ProductQuestionsLibrary.
     */
    @Test
    public void testAddProduct() {
        System.out.println("addProduct");
        Product product = new Product("ProdutoTeste", new EAN("544231234"),
                new QRCode("4324235"));
        ProductQuestionsLibrary instance = new ProductQuestionsLibrary();
        assertTrue("The condition should succeed because the product hasn't "
                + "been added to the library.", instance.addProduct(product));
        assertFalse("The condition should succeed because the product was "
                + "already added to the library.", instance.addProduct(product));
    }

    /**
     * Test of removeProduct method, of class ProductQuestionsLibrary.
     */
    @Test
    public void testRemoveProduct() {
        System.out.println("removeProduct");
        Product product = new Product("ProdutoTeste", new EAN("544231234"),
                new QRCode("4324235"));
        ProductQuestionsLibrary instance = new ProductQuestionsLibrary();
        assertFalse("The condition should succeed because the product doesn't"
                + "exist in the library.", instance.removeProduct(product));
        instance.addProduct(product);
        assertTrue("The condition should succeed because the product exists"
                + "in the library.", instance.removeProduct(product));
    }

    /**
     * Test of doesProductExist method, of class ProductQuestionsLibrary.
     */
    @Test
    public void testDoesProductExist() {
        System.out.println("doesProductExist");
        Product product = new Product("ProdutoTeste", new EAN("544231234"),
                new QRCode("4324235"));
        ProductQuestionsLibrary instance = new ProductQuestionsLibrary();
        assertFalse("The condition should succeed because the product doesn't"
                + "exist in the library.", instance.doesProductExist(product));
        instance.addProduct(product);
        assertTrue("The condition should succeed because the product exists"
                + "in the library.", instance.doesProductExist(product));
    }

    /**
     * Test of addQuestion method, of class ProductQuestionsLibrary.
     */
    @Test
    public void testAddQuestion() {
        System.out.println("addQuestion");
        Question question = new BinaryQuestion("QuestaoTeste");
        Product product = new Product("ProdutoTeste", new EAN("544231234"),
                new QRCode("4324235"));
        ProductQuestionsLibrary instance = new ProductQuestionsLibrary();
        assertFalse("The condition should succeed because the product isn't in"
                + "the library.", instance.addQuestion(question, product));
        instance.addProduct(product);
        assertTrue("The condition should succeed because the product now exists"
                + "in the library.", instance.addQuestion(question, product));
        assertFalse("The condition should succeed because the question already"
                + "exists for this product.", instance.addQuestion(question, product));
        Product product2 = new Product("ProdutoTest2e", new EAN("644231234"),
                new QRCode("5324235"));
        instance.addProduct(product2);
        assertFalse("The condition should succeed because the same question is "
                + "being added to a different product.", instance.addQuestion(question, product2));
    }

    /**
     * Test of removeQuestion method, of class ProductQuestionsLibrary.
     */
    @Test
    public void testRemoveQuestion() {
        System.out.println("removeQuestion");
        Question question = new BinaryQuestion("QuestaoTeste");
        Product product = new Product("ProdutoTeste", new EAN("544231234"),
                new QRCode("4324235"));
        ProductQuestionsLibrary instance = new ProductQuestionsLibrary();
        assertFalse("The condition should succeed because the product doesn't"
                + "exist in the library.", instance.removeQuestion(question, product));
        instance.addProduct(product);
        assertFalse("The condition should succeed because the question doesn't"
                + "exist for that product.", instance.removeQuestion(question, product));
        instance.addQuestion(question, product);
        assertTrue("The condition should succeed because the question exists"
                + "for that product.", instance.removeQuestion(question, product));
        Product product2 = new Product("ProdutoTest2e", new EAN("644231234"),
                new QRCode("5324235"));
        instance.addProduct(product2);
        instance.addQuestion(question, product2);
        assertTrue("The conditions should succeed because we are removing the"
                + "same question from a different product.", instance.removeQuestion(question, product2));
    }

    /**
     * Test of doesQuestionExist method, of class ProductQuestionsLibrary.
     */
    @Test
    public void testDoesQuestionExist() {
        System.out.println("doesQuestionExist");
        Question question = new BinaryQuestion("QuestaoTeste");
        Product product = new Product("ProdutoTeste", new EAN("544231234"),
                new QRCode("4324235"));
        ProductQuestionsLibrary instance = new ProductQuestionsLibrary();
        assertFalse("The condition should succeed because the product doesn't"
                + "exist in the library.", instance.doesQuestionExist(question, product));
        instance.addProduct(product);
        assertFalse("The condition should succeed because the question doesn't"
                + "exist for the product.", instance.doesQuestionExist(question, product));
        instance.addQuestion(question, product);
        assertTrue("The condition should succeed because the question exists"
                + "for the product", instance.doesQuestionExist(question, product));
        Product product2 = new Product("ProdutoTest2e", new EAN("644231234"),
                new QRCode("5324235"));
        instance.addProduct(product2);
        instance.addQuestion(question, product2);
        assertFalse("The condition shuold succeed because the same question exists"
                + "in another product.", instance.doesQuestionExist(question, product2));
    }

    /**
     * Test of hashCode method, of class ProductQuestionsLibrary.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        ProductQuestionsLibrary instance = new ProductQuestionsLibrary();
        ProductQuestionsLibrary other = new ProductQuestionsLibrary();
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class ProductQuestionsLibrary.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        ProductQuestionsLibrary instance = new ProductQuestionsLibrary();
        ProductQuestionsLibrary instance2 = new ProductQuestionsLibrary();

        assertTrue("The condition should succeed because we are comparing "
                + "the same instance.", instance.equals(instance));
        assertTrue("The condition should succeed because we are comparing "
                + "instances that are equal.", instance.equals(instance2));
        assertFalse("The condition should succeed because we are comparing "
                + "the instance to a null value.", instance.equals(null));
        assertFalse("The condition should succeed because we are comparing "
                + "instances of different classes.", instance.equals("banana"));
        Product product = new Product("ProdutoTeste", new EAN("544231234"),
                new QRCode("4324235"));
        BinaryQuestion question = new BinaryQuestion("QuestaoTeste");
        instance.addProduct(product);
        assertFalse("The condition should succeed because the instances have"
                + "different mappings (one has a product, the other"
                + "doesn't.", instance.equals(instance2));
        instance2.addProduct(product);
        assertTrue("The condition should succeed because the instances"
                + "have the same mappings (same products)"
                + ".", instance.equals(instance2));
        instance.addQuestion(question, product);
        assertFalse("The condition should succeed because the instances"
                + "have different mappings (one has questions for a product,"
                + "the other doesn't).", instance.equals(instance2));
        instance2.addQuestion(question, product);
        assertTrue("The condition should succeed because the instances"
                + "have the same mappings (both have the same question"
                + "for the same product).", instance.equals(instance2));
    }

}