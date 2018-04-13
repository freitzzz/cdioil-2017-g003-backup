package cdioil.application.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test class for LocalDateAttributeConverter class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class LocalDateAttributeConverterTest {

    /**
     * Test of convertToDatabaseColumn method, of class
     * LocalDateAttributeConverter.
     */
    @Test
    public void testConvertToDatabaseColumn() {
        System.out.println("convertToDatabaseColumn");
        LocalDate localDateToConvert = LocalDate.of(2020, Month.MARCH, 20);
        LocalDateAttributeConverter instance = new LocalDateAttributeConverter();
        Date expResult = Date.valueOf(localDateToConvert);
        Date result = instance.convertToDatabaseColumn(localDateToConvert);
        assertEquals("The condition should succeed because the "
                + "local date and the date instances represent the"
                + " same date", expResult, result);
        localDateToConvert = null;
        assertNull("The condition should succeed because we are sending a null"
                + " value", instance.convertToDatabaseColumn(localDateToConvert));
    }

    /**
     * Test of convertToEntityAttribute method, of class
     * LocalDateAttributeConverter.
     */
    @Test
    public void testConvertToEntityAttribute() {
        System.out.println("convertToEntityAttribute");
        Date dateToConvert = Date.valueOf("2020-03-23");
        LocalDateAttributeConverter instance = new LocalDateAttributeConverter();
        LocalDate expResult = dateToConvert.toLocalDate();
        LocalDate result = instance.convertToEntityAttribute(dateToConvert);
        assertEquals("The condition should succeed because the date instance and"
                + " the local date instance represent the same date", expResult, result);
        dateToConvert = null;
        assertNull("The condition should succeed because we are sending a null "
                + "value", instance.convertToEntityAttribute(dateToConvert));
    }

}
