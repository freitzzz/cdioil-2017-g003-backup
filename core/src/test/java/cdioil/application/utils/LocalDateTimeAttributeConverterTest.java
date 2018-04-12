package cdioil.application.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for LocalDateTimeAttributeConverter class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class LocalDateTimeAttributeConverterTest {

    /**
     * Test of convertToDatabaseColumn method, of class
     * LocalDateTimeAttributeConverter.
     */
    @Test
    public void testConvertToDatabaseColumn() {
        System.out.println("convertToDatabaseColumn");
        LocalDateTime x = LocalDateTime.of(2020, Month.MARCH, 2, 10, 10, 43);
        LocalDateTimeAttributeConverter instance = new LocalDateTimeAttributeConverter();
        Timestamp expResult = Timestamp.valueOf(x);
        Timestamp result = instance.convertToDatabaseColumn(x);
        assertEquals("The condition should succeed because both instances represent"
                + " the same date and time", expResult, result);
        assertNull("The condition should succeed because we are sending a null "
                + "value", instance.convertToDatabaseColumn(null));
    }

    /**
     * Test of convertToEntityAttribute method, of class
     * LocalDateTimeAttributeConverter.
     */
    @Test
    public void testConvertToEntityAttribute() {
        System.out.println("convertToEntityAttribute");
        Timestamp y = Timestamp.from(Instant.parse("2007-12-03T10:15:30.00Z"));
        LocalDateTimeAttributeConverter instance = new LocalDateTimeAttributeConverter();
        LocalDateTime expResult = y.toLocalDateTime();
        LocalDateTime result = instance.convertToEntityAttribute(y);
        assertEquals("The condition should succeed because both instances "
                + "represent the same date and time", expResult, result);
        assertNull("The condition should succeed because we are sending a"
                + " null value", instance.convertToEntityAttribute(null));
    }

}
