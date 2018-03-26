package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for class GlobalLibrary.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class GlobalLibraryTest {

    /**
     * Test of getCatQuestionsLibrary method, of class GlobalLibrary.
     */
    @Test
    public void testGetCatQuestionsLibrary() {
        System.out.println("getCatQuestionsLibrary");
        GlobalLibrary instance = new GlobalLibrary();
        CategoryQuestionsLibrary expResult = new CategoryQuestionsLibrary();
        CategoryQuestionsLibrary result = instance.getCatQuestionsLibrary();
        assertEquals(expResult, result);
    }

    /**
     * Test of getProdQuestionsLibrary method, of class GlobalLibrary.
     */
    @Test
    public void testGetProdQuestionsLibrary() {
        System.out.println("getProdQuestionsLibrary");
        GlobalLibrary instance = new GlobalLibrary();
        ProductQuestionsLibrary expResult = new ProductQuestionsLibrary();
        ProductQuestionsLibrary result = instance.getProdQuestionsLibrary();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIndQuestionsLibrary method, of class GlobalLibrary.
     */
    @Test
    public void testGetIndQuestionsLibrary() {
        System.out.println("getIndQuestionsLibrary");
        GlobalLibrary instance = new GlobalLibrary();
        IndependentQuestionsLibrary expResult = new IndependentQuestionsLibrary();
        IndependentQuestionsLibrary result = instance.getIndQuestionsLibrary();
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class GlobalLibrary.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        GlobalLibrary instance = new GlobalLibrary();
        GlobalLibrary other = new GlobalLibrary();
        int expResult = other.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class GlobalLibrary.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        GlobalLibrary instance = new GlobalLibrary();
        GlobalLibrary instance2 = new GlobalLibrary();
        Question question = new BinaryQuestion("QuestaoTeste");
        Product product = new Product("ProdutoTeste", new EAN("544231234"),
                new QRCode("4324235"));
        Category category = new Category("CategoryTest", "100CAT",
                "10DC-10UN-100CAT");
        assertEquals("The condition should succeed because we are comparing"
                + "the same instance.", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing"
                + "an instance with a null value.", instance, null);
        assertNotEquals("The condition should succeed because we are comparing"
                + "instances of different classes.", instance, "bananas");
        assertEquals("The condition should succeed because we are comparing"
                + "instance with equal properties.", instance, instance2);
        instance.getCatQuestionsLibrary().addCategory(category);
        assertNotEquals("The condition should succeed because we're comparing"
                + "instances with different CategoryQuestionLibrary objects.",
                instance, instance2);
        instance2.getCatQuestionsLibrary().addCategory(category);
        assertEquals("The condition should succeed because we're comparing"
                + "instances with equal CategoryQuestionLibrary objects.",
                instance, instance2);
        instance.getProdQuestionsLibrary().addProduct(product);
        assertNotEquals("The condition should succeed because we're comparing "
                + "instances with different ProductQuestionLibrary objects.",
                instance, instance2);
        instance2.getProdQuestionsLibrary().addProduct(product);
        assertEquals("The condition should succeed because we're comparing"
                + "instances with equal ProductQuestionLibrary objects.",
                instance, instance2);
        instance.getIndQuestionsLibrary().addQuestion(question);
        assertNotEquals("The condition should succeed because we're comparing "
                + "instances with different IndependentQuestionLibrary objects.",
                instance, instance2);
        instance2.getIndQuestionsLibrary().addQuestion(question);
        assertEquals("The condition should succeed because we're comparing "
                + "instances with equal IndependentQuestionLibrary objects.",
                instance, instance2);
    }

}
