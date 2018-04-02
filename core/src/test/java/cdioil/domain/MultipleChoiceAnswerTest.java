package cdioil.domain;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for MultipleChoiceAnswer.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class MultipleChoiceAnswerTest {

    /**
     * Tests for the constructor.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor Tests");
        LinkedList<Boolean> list = new LinkedList<>();
        assertNull("The condition should succeed because the argument is invalid",
                createMCAnswer(null));
        assertNull("The condition should succeed because the list is empty",
                createMCAnswer(list));
        list.add(Boolean.TRUE);
        assertNotNull("The condition should succeed because the argument is valid",
                createMCAnswer(list));
    }

    /**
     * Test of hashCode method, of class MultipleChoiceAnswer.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        LinkedList<Boolean> list = new LinkedList<>();
        list.add(Boolean.TRUE);
        MultipleChoiceAnswer instance = createMCAnswer(list);

        MultipleChoiceAnswer other = createMCAnswer(list);

        assertEquals(instance.hashCode(), other.hashCode());
    }

    /**
     * Test of equals method, of class MultipleChoiceAnswer.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        LinkedList<Boolean> list = new LinkedList<>();
        list.add(Boolean.TRUE);
        MultipleChoiceAnswer instance = createMCAnswer(list);
        MultipleChoiceAnswer instance2 = createMCAnswer(list);
        LinkedList<Boolean> list2 = new LinkedList<>();
        list2.add(Boolean.FALSE);
        MultipleChoiceAnswer instance3 = createMCAnswer(list2);
        BinaryAnswer outsider = new BinaryAnswer(true);
        assertEquals("The condition should succeed because we are "
                + "comparing the same instances", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing"
                + " instances of different classes", instance, "bananas");
        assertNotEquals("The condition should succeed because we are "
                + "comparing an instance with a null value", instance, null);
        assertNotEquals("The condition should succeed because we are "
                + "comparing answers of different types", instance, outsider);
        assertNotEquals("The condition should succeed because we are comparing two"
                + " instances with different properties", instance3, instance2);
        assertEquals("The condition should succeed because we are comparing "
                + "instances with the same list",
                instance, instance2);
    }

    /**
     * Test of content method, of class MultipleChoiceAnswer.
     */
    @Test
    public void testContent() {
        System.out.println("content");
        LinkedList<Boolean> list = new LinkedList<>();
        list.add(Boolean.TRUE);
        MultipleChoiceAnswer instance = createMCAnswer(list);
        LinkedList<Boolean> expResult = list;
        List<Boolean> result = instance.content();
        assertEquals(expResult, result);
    }

    /**
     * Test of type method, of class MultipleChoiceAnswer.
     */
    @Test
    public void testType() {
        System.out.println("type");
        LinkedList<Boolean> list = new LinkedList<>();
        list.add(Boolean.TRUE);
        MultipleChoiceAnswer instance = createMCAnswer(list);
        String expResult = QuestionAnswerTypes.MULTIPLE_CHOICE.toString();
        String result = instance.type();
        assertEquals(expResult, result);
    }

    /**
     * Builds a MultipleChoiceAnswer with a boolean
     *
     * @param answer list of booleans that represent the answer given
     * @return MultipleChoiceAnswer instance
     */
    private MultipleChoiceAnswer createMCAnswer(LinkedList<Boolean> answer) {
        try {
            return new MultipleChoiceAnswer(answer);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
