package cdioil.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for class TimePeriod.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class TimePeriodTest {

    /**
     * Tests for the constructor of TimePeriod.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor tests");
        LocalDate startLDate = LocalDate.of(2019, Month.MARCH, 3);
        LocalTime startLTime = LocalTime.of(10, 10, 10);

        LocalDate endLDate = LocalDate.of(2020, Month.MARCH, 1);
        LocalTime endLTime = LocalTime.of(9, 20, 50);

        LocalDateTime endDate = LocalDateTime.of(endLDate, endLTime);
        LocalDateTime startDate = LocalDateTime.of(startLDate, startLTime);

        assertNull("The condition should succeed because both arguments"
                + "are invalid", createTimePeriod(null, null));
        assertNull("The condition should succeed because the end date is"
                + "null", createTimePeriod(startDate, null));
        assertNull("The condition should succeed because the start date is "
                + "null", createTimePeriod(null, endDate));
        assertNotNull("The condition should succeed because both arguments"
                + " are valid", createTimePeriod(startDate, endDate));

        startLDate = LocalDate.of(2021, Month.MARCH, 23);
        startDate = LocalDateTime.of(startLDate, startLTime);

        assertNull("The condition should succeed because the starting date"
                + "is after the ending date", createTimePeriod(startDate, endDate));

        endLDate = LocalDate.of(2020, Month.MARCH, 2);
        endDate = LocalDateTime.of(endLDate, endLTime);

        assertNull("The condition should succeed because the ending date "
                + "is before the starting date", createTimePeriod(startDate, endDate));

        startLDate = LocalDate.of(2010, Month.MARCH, 1);
        endLDate = LocalDate.of(2010, Month.MARCH, 1);
        startLTime = LocalTime.of(0, 0, 0);
        endLTime = LocalTime.of(0, 0, 0);
        startDate = LocalDateTime.of(startLDate, startLTime);
        endDate = LocalDateTime.of(endLDate, endLTime);

        assertNull("The condition should succeed because the starting date "
                + "and the ending date are the same", createTimePeriod(startDate, endDate));
    }

    /**
     * Test of hashCode method, of class TimePeriod.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        LocalDate sDate = LocalDate.of(2019, Month.MARCH, 3);
        LocalTime sTime = LocalTime.of(10, 10, 10);
        LocalDateTime sDateTime = LocalDateTime.of(sDate, sTime);

        LocalDate eDate = LocalDate.of(2029, Month.MARCH, 3);
        LocalTime eTime = LocalTime.of(10, 10, 10);
        LocalDateTime eDateTime = LocalDateTime.of(eDate, eTime);

        TimePeriod instance = createTimePeriod(sDateTime, eDateTime);
        TimePeriod instance2 = createTimePeriod(sDateTime, eDateTime);

        int expResult = instance2.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class TimePeriod.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        LocalDate sDate = LocalDate.of(2019, Month.MARCH, 3);
        LocalTime sTime = LocalTime.of(10, 10, 10);
        LocalDateTime sDateTime = LocalDateTime.of(sDate, sTime);

        LocalDate eDate = LocalDate.of(2020, Month.MARCH, 3);
        LocalTime eTime = LocalTime.of(10, 10, 10);
        LocalDateTime eDateTime = LocalDateTime.of(eDate, eTime);

        TimePeriod instance = createTimePeriod(sDateTime, eDateTime);
        TimePeriod instance2 = createTimePeriod(sDateTime, eDateTime);

        assertNotEquals("The condition should succeed because we are comparing"
                + "an instance to a null value.", instance, null);
        assertNotEquals("The condition should succeed because we are comparing"
                + "instances of different classes.", instance, "I'M RUNNING"
                + "OUT OF TIME");
        assertEquals("The condition should succeed because we are comparing "
                + "the same instance.", instance, instance);
        assertEquals("The condition should succeed because we are comparing "
                + "instances with the same properties.", instance, instance2);

        LocalDate diffStartDay = LocalDate.of(2019, Month.MARCH, 4);
        LocalDateTime diffSDay = LocalDateTime.of(diffStartDay, sTime);
        instance2 = createTimePeriod(diffSDay, eDateTime);

        assertNotEquals("The condition should succeed because we are comparing "
                + "time periods that start in a different day.", instance, instance2);

        LocalDate diffStartMonth = LocalDate.of(2019, Month.APRIL, 3);
        LocalDateTime diffSMonth = LocalDateTime.of(diffStartMonth, sTime);
        instance2 = createTimePeriod(diffSMonth, eDateTime);

        assertNotEquals("The condition should succeed because we are comparing "
                + "time periods that start in a different month.", instance, instance2);

        LocalDate diffStartYear = LocalDate.of(2018, Month.MARCH, 3);
        LocalDateTime diffSYear = LocalDateTime.of(diffStartYear, sTime);
        instance2 = new TimePeriod(diffSYear, eDateTime);

        assertNotEquals("The condition should succeed because we are comparing "
                + "time periods that start in a different year", instance, instance2);

        LocalTime diffStartSecond = LocalTime.of(10, 10, 9);
        LocalDateTime diffSSecond = LocalDateTime.of(sDate, diffStartSecond);
        instance2 = createTimePeriod(diffSSecond, eDateTime);

        assertNotEquals("The condition should succeed because we are comparing "
                + "time periods that start in a different second", instance, instance2);

        LocalTime diffStartMinute = LocalTime.of(10, 9, 10);
        LocalDateTime diffSMinute = LocalDateTime.of(sDate, diffStartMinute);
        instance2 = createTimePeriod(diffSMinute, eDateTime);

        assertNotEquals("The condition should succeed because we are comparing "
                + "time periods that start in a different minute", instance, instance2);

        LocalTime diffStartHour = LocalTime.of(11, 10, 10);
        LocalDateTime diffSHour = LocalDateTime.of(sDate, diffStartHour);
        instance2 = createTimePeriod(diffSHour, eDateTime);

        assertNotEquals("The condition should succeed because we are comparing "
                + "time periods that start in a different hour", instance, instance2);

        LocalDate diffEndDay = LocalDate.of(2020, Month.MARCH, 4);
        LocalDateTime diffEDay = LocalDateTime.of(diffEndDay, eTime);
        instance2 = createTimePeriod(sDateTime, diffEDay);

        assertNotEquals("The condition should succeed because we are comparing "
                + "time periods that end in a different day", instance, instance2);

        LocalDate diffEndMonth = LocalDate.of(2020, Month.APRIL, 3);
        LocalDateTime diffEMonth = LocalDateTime.of(diffEndMonth, eTime);
        instance2 = createTimePeriod(sDateTime, diffEMonth);

        assertNotEquals("The condition should succeed because we are comparing "
                + "time periods that end in a different month", instance, instance2);

        LocalDate diffEndYear = LocalDate.of(2021, Month.MARCH, 3);
        LocalDateTime diffEYear = LocalDateTime.of(diffEndYear, eTime);
        instance2 = createTimePeriod(sDateTime, diffEYear);

        assertNotEquals("The condition should succeed because we are comparing "
                + "time periods that end in a different year", instance, instance2);

        LocalTime diffEndSecond = LocalTime.of(10, 10, 9);
        LocalDateTime diffESecond = LocalDateTime.of(eDate, diffEndSecond);
        instance2 = createTimePeriod(sDateTime, diffESecond);

        assertNotEquals("The condition should succeed because we are comparing "
                + "time periods that end in a different second", instance, instance2);

        LocalTime diffEndMinute = LocalTime.of(10, 9, 10);
        LocalDateTime diffEMinute = LocalDateTime.of(eDate, diffEndMinute);
        instance2 = createTimePeriod(sDateTime, diffEMinute);

        assertNotEquals("The condition should succeed because we are comparing "
                + "time periods that end in a different minute", instance, instance2);

        LocalTime diffEndHour = LocalTime.of(11, 10, 10);
        LocalDateTime diffEHour = LocalDateTime.of(eDate, diffEndHour);
        instance2 = createTimePeriod(sDateTime, diffEHour);

        assertNotEquals("The condition should succeed because we are comparing "
                + "time periods that end in a different hour", instance, instance2);
    }

    /**
     * Test of toString method, of class TimePeriod.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        LocalDate d = LocalDate.of(2010, Month.MARCH, 2);
        LocalTime t = LocalTime.of(10, 10, 10);
        LocalDateTime dt = LocalDateTime.of(d, t);
        LocalDate d2 = LocalDate.of(2010, Month.MARCH, 4);
        LocalTime t2 = LocalTime.of(10, 10, 10);
        LocalDateTime dt2 = LocalDateTime.of(d2, t2);
        TimePeriod instance = createTimePeriod(dt, dt2);
        String expResult = "Data de Inicio: 2010-03-02 10:10:10\n"
                + "Data de Fim: 2010-03-04 10:10:10";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Builds a TimePeriod object with a start date and an end date.
     *
     * @param startDate starting date
     * @param endDate ending date
     * @return TimePeriod object or null if an exception occured.
     */
    private TimePeriod createTimePeriod(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            return new TimePeriod(startDate, endDate);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
