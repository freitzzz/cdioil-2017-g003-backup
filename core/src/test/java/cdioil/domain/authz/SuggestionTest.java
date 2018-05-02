package cdioil.domain.authz;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class of class Suggestion.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class SuggestionTest {

    /**
     * Test of the constructores, of class Suggestion.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor tests");
        assertNotNull("Empty constructor test", new Suggestion());
        assertNull("The condition should succeed because the suggestion text "
                + "is null", createSuggestion(null));
        assertNull("The condition should succeed because the suggestion text "
                + "is empty", createSuggestion(""));
        assertNotNull("The condition should succeed because the suggestion text "
                + "is valid", createSuggestion("text"));

    }

    /**
     * Test of hashCode method, of class Suggestion.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Suggestion instance = new Suggestion("text");
        Suggestion other = new Suggestion("text");
        int expResult = other.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
        
        //Mutation test
        assertNotEquals("".hashCode(),result);
    }

    /**
     * Test of equals method, of class Suggestion.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Suggestion instance = new Suggestion("text");
        assertNotEquals("The condition should succeed because we are comparing "
                + "an instance to a null value", instance, null);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes", instance, "bananas");
        assertEquals("The condition should succeed because we are comparing the"
                + " same instance", instance, instance);
        assertEquals("The condition should succeed because we are comparing "
                + "instances with the same properties", instance, new Suggestion("text"));
        assertNotEquals("The condition should succed because we are comparing "
                + "instances with different properties", instance, new Suggestion("other"));
    }

    /**
     * Test of toString method, of class Suggestion.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Suggestion instance = new Suggestion("text");
        String expResult = "text";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Builds a new Suggestion instance.
     *
     * @param text text of the suggestion
     * @return new Suggestion instance, or null if an exception occured
     */
    private Suggestion createSuggestion(String text) {
        try {
            return new Suggestion(text);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
