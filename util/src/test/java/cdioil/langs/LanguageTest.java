package cdioil.langs;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Antonio Sousa
 */
public class LanguageTest {
    
    public LanguageTest() {
    }

    /**
     * Test of values method, of class Language.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        Language[] expResult = {Language.PT, Language.EN_US};
        Language[] result = Language.values();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of valueOf method, of class Language.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "PT";
        Language expResult = Language.PT;
        Language result = Language.valueOf(name);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testToString(){
        System.out.println("toString");
        
        String result = Language.PT.toString();
        String expResult = "Português (Portugal)";
        
        assertEquals(result, expResult);
        
        assertNotEquals(result, Language.PT.name());
        
        
        result = Language.EN_US.toString();
        expResult = "English (USA)";
        
        assertEquals(result, expResult);
    }
}
