package cdioil.domain;

import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for CategoryQuestionsLibrary class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class CategoryQuestionsLibraryTest {

    /**
     * Test of categoryQuestionSet method, of class CAtegoryQuestionsLibrary.
     */
    @Test
    public void testCategoryQuestionSet() {
        System.out.println("categoryQuestionSet");
        Category category = new Category("CategoryTest", "10DC-10UN-100CAT");
        CategoryQuestionsLibrary instance = new CategoryQuestionsLibrary();
        assertNull("The condition should succeed because there are no categories "
                + "in the library.", instance.categoryQuestionSet(category));
        instance.addCategory(category);
        assertNotNull("The condition should succeed because a category exists "
                + "in the library.", instance.categoryQuestionSet(category));
    }

    /**
     * Test of addCategory method, of class CategoryQuestionsLibrary.
     */
    @Test
    public void testAddCategory() {
        System.out.println("addCategory");
        Category category = new Category("CategoryTest", "10DC-10UN-100CAT");
        CategoryQuestionsLibrary instance = new CategoryQuestionsLibrary();
        assertTrue("The condition should succeed because the category hasn't "
                + "been added to the library.", instance.addCategory(category));
        assertFalse("The condition should succeed because the category was "
                + "already added to the library.", instance.addCategory(category));
    }

    /**
     * Test of removeCategory method, of class CategoryQuestionsLibrary.
     */
    @Test
    public void testRemoveCategory() {
        System.out.println("removeCategory");
        Category category = new Category("CategoryTest", "10DC-10UN-100CAT");
        CategoryQuestionsLibrary instance = new CategoryQuestionsLibrary();
        assertFalse("The condition should succeed because the category doesn't"
                + "exist in the library.", instance.removeCategory(category));
        instance.addCategory(category);
        assertTrue("The condition should succeed because the category exists"
                + "in the library.", instance.removeCategory(category));
    }

    /**
     * Test of doesCategoryExist method, of class CategoryQuestionsLibrary.
     */
    @Test
    public void testDoesCategoryExist() {
        System.out.println("doesCategoryExist");
        Category category = new Category("CategoryTest", "10DC-10UN-100CAT");
        CategoryQuestionsLibrary instance = new CategoryQuestionsLibrary();
        assertFalse("The condition should succeed because the category doesn't"
                + "exist in the library.", instance.doesCategoryExist(category));
        instance.addCategory(category);
        assertTrue("The condition should succeed because the category exists"
                + "in the library.", instance.doesCategoryExist(category));
    }

    /**
     * Test of addQuestion method, of class CategoryQuestionsLibrary.
     */
    @Test
    public void testAddQuestion() {
        System.out.println("addQuestion");
        String id = "144";
        BinaryQuestionOption option1 = new BinaryQuestionOption(Boolean.FALSE);
        BinaryQuestionOption option2 = new BinaryQuestionOption(Boolean.TRUE);
        LinkedList<QuestionOption> list = new LinkedList<>();
        list.add(option1);
        list.add(option2);
        Question question = new BinaryQuestion("QuestaoTeste", id, list);
        Category category = new Category("CategoryTest", "10DC-10UN-100CAT");
        CategoryQuestionsLibrary instance = new CategoryQuestionsLibrary();
        assertFalse("The condition should succeed because the category isn't in"
                + "the library.", instance.addQuestion(question, category));
        instance.addCategory(category);
        assertTrue("The condition should succeed because the category now exists"
                + "in the library.", instance.addQuestion(question, category));
        assertFalse("The condition should succeed because the question already"
                + "exists for this category.", instance.addQuestion(question, category));
        Category category2 = new Category();
        instance.addCategory(category2);
        assertTrue("The condition should succeed because the same question is "
                + "being added to a different category.", instance.addQuestion(question, category2));
    }

    /**
     * Test of removeQuestion method, of class CategoryQuestionsLibrary.
     */
    @Test
    public void testRemoveQuestion() {
        System.out.println("removeQuestion");
        String id = "4";
        BinaryQuestionOption option1 = new BinaryQuestionOption(Boolean.FALSE);
        BinaryQuestionOption option2 = new BinaryQuestionOption(Boolean.TRUE);
        LinkedList<QuestionOption> list = new LinkedList<>();
        list.add(option1);
        list.add(option2);
        Question question = new BinaryQuestion("QuestaoTeste", id, list);
        Category category = new Category("CategoryTest", "10DC-10UN-100CAT");
        CategoryQuestionsLibrary instance = new CategoryQuestionsLibrary();
        assertFalse("The condition should succeed because the category doesn't"
                + "exist in the library.", instance.removeQuestion(question, category));
        instance.addCategory(category);
        assertFalse("The condition should succeed because the question doesn't"
                + "exist for that category.", instance.removeQuestion(question, category));
        instance.addQuestion(question, category);
        assertTrue("The condition should succeed because the question exists"
                + "for that category.", instance.removeQuestion(question, category));
        Category category2 = new Category();
        instance.addCategory(category2);
        instance.addQuestion(question, category2);
        assertTrue("The conditions should succeed because we are removing the"
                + "same question from a different category.", instance.removeQuestion(question, category2));
    }

    /**
     * Test of doesQuestionExist method, of class CategoryQuestionsLibrary.
     */
    @Test
    public void testDoesQuestionExist() {
        System.out.println("doesQuestionExist");
        String id = "4L";
        BinaryQuestionOption option1 = new BinaryQuestionOption(Boolean.FALSE);
        BinaryQuestionOption option2 = new BinaryQuestionOption(Boolean.TRUE);
        LinkedList<QuestionOption> list = new LinkedList<>();
        list.add(option1);
        list.add(option2);
        Question question = new BinaryQuestion("QuestaoTeste", id, list);
        Category category = new Category("CategoryTest", "10DC-10UN-100CAT");
        CategoryQuestionsLibrary instance = new CategoryQuestionsLibrary();
        assertFalse("The condition should succeed because the category doesn't"
                + "exist in the library.", instance.doesQuestionExist(question, category));
        instance.addCategory(category);
        assertFalse("The condition should succeed because the question doesn't"
                + "exist for the category.", instance.doesQuestionExist(question, category));
        instance.addQuestion(question, category);
        assertTrue("The condition should succeed because the question exists"
                + "for the category", instance.doesQuestionExist(question, category));
        Category category2 = new Category();
        instance.addCategory(category2);
        instance.addQuestion(question, category2);
        assertTrue("The condition should succeed because the same question exists"
                + "in another category.", instance.doesQuestionExist(question, category2));
    }

    /**
     * Test of hashCode method, of class CategoryQuestionsLibrary.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        CategoryQuestionsLibrary instance = new CategoryQuestionsLibrary();
        CategoryQuestionsLibrary other = new CategoryQuestionsLibrary();
        int expResult = other.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class CategoryQuestionsLibrary.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        CategoryQuestionsLibrary instance = new CategoryQuestionsLibrary();
        CategoryQuestionsLibrary instance2 = new CategoryQuestionsLibrary();

        assertTrue("The condition should succeed because we are comparing"
                + "the same instance.", instance.equals(instance));
        assertTrue("The condition should succeed because we are comparing"
                + "instances that are equal.", instance.equals(instance2));
        assertFalse("The condition should succeed because we are comparing"
                + "the instance to a null value.", instance.equals(null));
        assertFalse("The condition should succeed because we are comparing"
                + "instances of different classes.", instance.equals("banana"));
        Category cat = new Category("CategoryTest", "10DC-10UN-100CAT");
        String id = "4B";
        BinaryQuestionOption option1 = new BinaryQuestionOption(Boolean.FALSE);
        BinaryQuestionOption option2 = new BinaryQuestionOption(Boolean.TRUE);
        LinkedList<QuestionOption> list = new LinkedList<>();
        list.add(option1);
        list.add(option2);
        BinaryQuestion question = new BinaryQuestion("QuestaoTeste", id, list);
        instance.addCategory(cat);
        assertFalse("The condition should succeed because the instances have"
                + "different mappings (one has a category, the other"
                + "doesn't.", instance.equals(instance2));
        instance2.addCategory(cat);
        assertTrue("The condition should succeed because the instances"
                + "have the same mappings (same categories)"
                + ".", instance.equals(instance2));
        instance.addQuestion(question, cat);
        assertFalse("The condition should succeed because the instances"
                + "have different mappings (one has questions for a category,"
                + "the other doesn't).", instance.equals(instance2));
        instance2.addQuestion(question, cat);
        assertTrue("The condition should succeed because the instances"
                + "have the same mappings (both have the same question"
                + "for the same category).", instance.equals(instance2));
    }

}
