package cdioil.domain.authz;

import java.time.LocalDate;
import java.time.Month;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit class for BirthDate.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class BirthDateTest {

    /**
     * Tests for BirthDate constructors.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor tests");
        assertNotNull("Empty constructor test", new BirthDate());
        assertNull("The condition should succeed because the birth date is "
                + "null", createBirthDate(null));
        assertNotNull("The condition should succeed because the birth date is "
                + "valid", createBirthDate(LocalDate.of(2010, Month.MARCH, 23)));
    }

    /**
     * Test of hashCode method, of class BirthDate.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        LocalDate locDate = LocalDate.of(2010, Month.APRIL, 23);
        BirthDate instance = createBirthDate(locDate);
        BirthDate other = createBirthDate(locDate);
        int expResult = other.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
        
        //Mutation test
        assertNotEquals("".hashCode(),instance.hashCode());
    }

    /**
     * Test of equals method, of class BirthDate.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        LocalDate locDate = LocalDate.of(2020, Month.MARCH, 23);
        BirthDate instance = createBirthDate(locDate);
        assertNotEquals("The condition should succeed because we are comparing "
                + "an instance with a null value", instance, null);
        assertEquals("The condition should succeed because we are comparing "
                + "the same instance", instance, instance);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes", instance, "bananas");
        BirthDate other = createBirthDate(locDate);
        assertEquals("The condition should succeed because we are comparing "
                + "instances that have the same birth date", instance, other);
    }

    /**
     * Test of toString method, of class BirthDate.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        LocalDate locDate = LocalDate.of(2020, Month.MARCH, 23);
        BirthDate instance = createBirthDate(locDate);
        String expResult = locDate.toString();
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Builds a BirthDate instance with a LocalDate object.
     *
     * @param birthDate LocalDate object representing the birth date
     * @return new instance or null if an exception occurs
     */
    private BirthDate createBirthDate(LocalDate birthDate) {
        try {
            return new BirthDate(birthDate);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
