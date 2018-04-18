package cdioil.domain;

import cdioil.domain.authz.UsersGroup;
import cdioil.time.TimePeriod;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit testing class for GlobalSurvey class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class GlobalSurveyTest {

    /**
     * Constructor tests.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor tests");
        LinkedList<SurveyItem> list = new LinkedList<>();
        TimePeriod timePeriod = new TimePeriod(LocalDateTime.of(1, Month.MARCH, 1, 1, 1),
                LocalDateTime.of(2, Month.MARCH, 2, 2, 2));
        assertNotNull("Empty constructor test", new GlobalSurvey());
        assertNull("The condition should succeed because the item list is "
                + "null", createGlobalSurvey(null, timePeriod));
        assertNull("The condition should succeed because the item list is "
                + "empty", createGlobalSurvey(list, timePeriod));
        list.add(new Product("ProdutoTeste", new EAN("544231234"), "1 L", new QRCode("4324235")));
        assertNull("The condition should succeed because the time period "
                + "is null", createGlobalSurvey(list, null));
        assertNotNull("The condition should succeed because both arguments "
                + "are valid", createGlobalSurvey(list, timePeriod));
    }

    /**
     * Create a new object GlobalSurvey with an item list, begin date and end
     * date.
     *
     * @param itemList survey's item list
     * @param timePeriod time period of the survey
     * @return instance of the Contest
     */
    private GlobalSurvey createGlobalSurvey(List<SurveyItem> itemList, TimePeriod timePeriod) {
        try {
            return new GlobalSurvey(itemList, timePeriod);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
