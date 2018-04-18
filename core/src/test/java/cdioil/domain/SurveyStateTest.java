package cdioil.domain;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for Enum SurveyState.
 *
 * @author @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class SurveyStateTest {

    /**
     * Test of values method, of class SurveyState.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        SurveyState[] expResult = {SurveyState.DRAFT, SurveyState.ACTIVE,
            SurveyState.CLOSED};
        SurveyState[] result = SurveyState.values();
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of valueOf method, of class SurveyState.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "DRAFT";
        SurveyState expResult = SurveyState.DRAFT;
        SurveyState result = SurveyState.valueOf(name);
        assertEquals(expResult, result);
        name = "CLOSED";
        expResult = SurveyState.CLOSED;
        result = SurveyState.valueOf(name);
        assertEquals(expResult, result);
        name = "ACTIVE";
        expResult = SurveyState.ACTIVE;
        result = SurveyState.valueOf(name);
        assertEquals(expResult, result);;
    }

    /**
     * Test of toString method, of class SurveyState.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "Draft";
        String result = SurveyState.DRAFT.toString();
        assertEquals(expResult, result);
        expResult = "Ativo";
        result = SurveyState.ACTIVE.toString();
        assertEquals(expResult, result);
        expResult = "Fechado";
        result = SurveyState.CLOSED.toString();
        assertEquals(expResult, result);
    }
}
