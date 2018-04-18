package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for QuestionTypes enum.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class QuestionTypesTest {

    /**
     * Test of values method, of class QuestionTypes.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        QuestionTypes[] expResult = {QuestionTypes.BINARY,
            QuestionTypes.MULTIPLE_CHOICE, QuestionTypes.QUANTITATIVE};
        QuestionTypes[] result = QuestionTypes.values();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of valueOf method, of class QuestionTypes.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "BINARY";
        QuestionTypes expResult = QuestionTypes.BINARY;
        QuestionTypes result = QuestionTypes.valueOf(name);
        assertEquals(expResult, result);
        name = "MULTIPLE_CHOICE";
        expResult = QuestionTypes.MULTIPLE_CHOICE;
        result = QuestionTypes.valueOf(name);
        assertEquals(expResult, result);
        name = "QUANTITATIVE";
        expResult = QuestionTypes.QUANTITATIVE;
        result = QuestionTypes.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method for all the values in the QuestionTypes enum.
     */
    @Test
    public void testToStrings() {
        System.out.println("toStrings");
        String expResult = "Binária";
        String result = QuestionTypes.BINARY.toString();
        assertEquals(expResult, result);
        expResult = "Escolha Múltipla";
        result = QuestionTypes.MULTIPLE_CHOICE.toString();
        assertEquals(expResult, result);
        expResult = "Quantitativa";
        result = QuestionTypes.QUANTITATIVE.toString();
        assertEquals(expResult, result);
    }

}
