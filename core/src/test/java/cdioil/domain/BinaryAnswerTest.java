package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for BinaryAnswer class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class BinaryAnswerTest {

    /**
     * Tests for the constructor.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor Tests");
        assertNull("The condition should succeed because the argument is invalid",
                createBinaryAnswer(null));
        assertNotNull("\"The condition should succeed because the argument is valid",
                createBinaryAnswer(true));
        assertNotNull("\"The condition should succeed because the argument is valid",
                createBinaryAnswer(false));
    }

    /**
     * Test of hashCode method, of class BinaryAnswer.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Boolean b = true;
        BinaryAnswer instance = new BinaryAnswer(b);

        BinaryAnswer other = new BinaryAnswer(b);

        assertEquals(instance.hashCode(), other.hashCode());
    }

    /**
     * Test of equals method, of class BinaryAnswer.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        BinaryAnswer instance = new BinaryAnswer(true);
        BinaryAnswer instance2 = new BinaryAnswer(false);
        BinaryAnswer instance3 = new BinaryAnswer(true);
        BinaryAnswer instance4 = new BinaryAnswer(false);
        assertEquals("The condition should succeed because we are "
                + "comparing the same instances", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing"
                + " instances of different classes", instance, "bananas");
        assertNotEquals("The condition should succeed because we are "
                + "comparing an instance with a null value", instance, null);
        assertNotEquals("The condition should succeed because we are comparing two"
                + " instances with different properties", instance3, instance2);
        assertEquals("The condition should succeed because we are comparing "
                + "instances with the same boolean value (true)",
                instance, instance3);
        assertEquals("The condition should succeed because we are comparing "
                + "instances with the same boolean value (false)",
                instance2, instance4);
    }

    /**
     * Test of content method, of class BinaryAnswer.
     */
    @Test
    public void testContent() {
        System.out.println("content");
        Boolean b = true;
        BinaryAnswer instance = new BinaryAnswer(b);
        Boolean expResult = b;
        Boolean result = instance.content();
        assertEquals(expResult, result);
    }

    /**
     * Test of type method, of class BinaryAnswer.
     */
    @Test
    public void testType() {
        System.out.println("type");
        BinaryAnswer instance = new BinaryAnswer(true);
        String expResult = QuestionAnswerTypes.BINARY.toString();
        String result = instance.type();
        assertEquals(expResult, result);
    }

    /**
     * Builds a BinaryAnswer with a boolean
     *
     * @param answer boolean value that represents the answer
     * @return BinaryAnswer instance
     */
    private BinaryAnswer createBinaryAnswer(Boolean answer) {
        try {
            return new BinaryAnswer(answer);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
