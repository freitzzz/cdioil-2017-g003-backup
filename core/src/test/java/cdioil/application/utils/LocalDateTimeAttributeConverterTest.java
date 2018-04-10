package cdioil.application.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
        assertEquals(expResult, result);
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
        assertEquals(expResult, result);
    }

}
