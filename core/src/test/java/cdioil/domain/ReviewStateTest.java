package cdioil.domain;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for enum ReviewState.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ReviewStateTest {

    /**
     * Test of values method, of class ReviewState.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        ReviewState[] expResult = {ReviewState.PENDING, ReviewState.FINISHED};
        ReviewState[] result = ReviewState.values();
        assertTrue(Arrays.equals(expResult, result));
    }

    /**
     * Test of valueOf method, of class ReviewState.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "PENDING";
        ReviewState expResult = ReviewState.PENDING;
        ReviewState result = ReviewState.valueOf(name);
        assertEquals(expResult, result);
        name = "FINISHED";
        expResult = ReviewState.FINISHED;
        result = ReviewState.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class ReviewState.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "Pendente";
        String result = ReviewState.PENDING.toString();
        assertEquals(expResult, result);
        expResult = "Terminada";
        result = ReviewState.FINISHED.toString();
        assertEquals(expResult, result);
    }

}
