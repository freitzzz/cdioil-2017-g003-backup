package cdioil.domain;

import cdioil.application.utils.Graph;
import static cdioil.domain.Event_.description;
import static cdioil.domain.TemplateGroup_.title;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.Name;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.SystemUser;
import cdioil.domain.authz.UsersGroup;
import cdioil.time.TimePeriod;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit testing class for TargetedSurvey class.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class TargetedSurveyTest {

    private List<SurveyItem> list;
    private TimePeriod timePeriod;
    private UsersGroup gu;
    private Graph graph;

    @Before
    public void setUp() {
        list = new ArrayList<>();
        list.add(new Product("ProdutoTeste", new SKU("544261234"), "1 L", new QRCode("4324235")));
        LocalDateTime surveyDate = LocalDateTime.of(1, Month.MARCH, 2, 0, 0);
        LocalDateTime endingDate = LocalDateTime.of(2, Month.MARCH, 23, 10, 10);
        timePeriod = new TimePeriod(surveyDate, endingDate);
        gu = new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))));
        graph = new Graph();
    }

    /**
     * Test of the constructor of the class Constest.
     */
    @Test
    public void constructorTest() {
        System.out.println("Testes Construtor");
        assertNull("The condition should succeed because the item list is "
                + "null", createTargetedSurvey(null, timePeriod, gu));
        assertNull("The condition should succeed because the item list is "
                + "empty", createTargetedSurvey(new ArrayList<>(), timePeriod, gu));
        assertNull("The condition should succeed because the time period is "
                + "null", createTargetedSurvey(list, null, gu));
        assertNull("The condition should succeed because the target audience is "
                + "null", createTargetedSurvey(list, timePeriod, null));
        assertNotNull("The condition should succeed because the arguments are "
                + "valid", createTargetedSurvey(list, timePeriod, gu));
        assertNotNull("Empty constructor test", new TargetedSurvey());
    }

    /**
     * Test of addUsersToGroup method, of class TargetedSurvey
     */
    @Test
    public void testAddUsersToGroup() {
        System.out.println("addUsersToGroup");
        TargetedSurvey tSurvey = createTargetedSurvey(list, timePeriod, gu);
        assertFalse(tSurvey.addUsersToGroup(null));
        List<RegisteredUser> lru = new LinkedList<>();
        lru.add(new RegisteredUser(new SystemUser(new Email("myPrecious@gmail.com"), new Name("Gollum", "Smeagol"), new Password("Precious3"))));
        assertTrue(tSurvey.addUsersToGroup(lru));
    }

    /**
     * Create a new object TargetedSurvey with an item list, a group of users,
     * begin date and end date.
     *
     * @param itemList survey's item list
     * @param timePeriod time period of the survey
     * @return instance of the Contest
     */
    private TargetedSurvey createTargetedSurvey(List<SurveyItem> itemList, TimePeriod timePeriod,
            UsersGroup targetAudience) {
        try {
            return new TargetedSurvey(itemList, timePeriod, targetAudience);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
