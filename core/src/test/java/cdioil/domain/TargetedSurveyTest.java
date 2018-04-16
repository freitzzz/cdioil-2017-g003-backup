package cdioil.domain;

import cdioil.application.utils.Graph;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.Name;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.SystemUser;
import cdioil.domain.authz.UsersGroup;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
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
    private LocalDateTime surveyDate;
    private LocalDateTime endingDate;
    private UsersGroup gu;
    private Graph graph;

    @Before
    public void setUp() {
        list = new ArrayList<>();
        surveyDate = LocalDateTime.of(1, Month.MARCH, 2, 0, 0);
        endingDate = LocalDateTime.of(2, Month.MARCH, 23, 10, 10);
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
        assertNull("The condition should succeed because the target audience is "
                + "null", createTargetedSurvey(list, surveyDate, endingDate, null));
        assertNotNull("The condition should succeed because the arguments are "
                + "valid", createTargetedSurvey(list, surveyDate, endingDate, gu));
        assertNotNull("Empty constructor test", new TargetedSurvey());
    }

    /**
     * Test of the hashCode method, of the class Constest.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        TargetedSurvey instance = createTargetedSurvey(list, surveyDate, endingDate, gu);
        TargetedSurvey other = createTargetedSurvey(list, surveyDate, endingDate, gu);
        int expResult = other.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of the equals method, of the class Constest.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        TargetedSurvey instance = createTargetedSurvey(list, surveyDate, endingDate, gu);
        TargetedSurvey instance2 = createTargetedSurvey(list, surveyDate, endingDate, gu);
        List<SurveyItem> otherList = new ArrayList<>();
        otherList.add(new Category("CategoriaTeste", "10DC-10UN-100CAT"));
        TargetedSurvey instance3 = createTargetedSurvey(otherList, surveyDate, endingDate, gu);
        assertEquals("A condição deve acertar pois estamos a comparar"
                + "as mesmas instancias", instance, instance);
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "instancias de classes diferentes", instance, "bananas");
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "uma instancia com outra a null", instance, null);
        assertEquals("A condição deve acertar pois estamos a comparar"
                + "duas instancias iguais", instance, instance2);
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "duas instancias diferentes", instance, instance3);
    }

    /**
     * Test of the toString method, of the class Constest.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        TargetedSurvey instance = createTargetedSurvey(list, surveyDate, endingDate, gu);
        TargetedSurvey other = createTargetedSurvey(list, surveyDate, endingDate, gu);
        assertTrue(instance.toString().equals(other.toString()));
    }

    /**
     * Create a new object TargetedSurvey with a description, group users, begin
     * date and end date.
     *
     * @param description
     * @param gu
     * @param beginDate
     * @param endDate
     * @return instance of the Contest
     */
    private TargetedSurvey createTargetedSurvey(List<SurveyItem> itemList, LocalDateTime date,
            LocalDateTime endingDate, UsersGroup targetAudience) {
        try {
            return new TargetedSurvey(itemList, date, endingDate, targetAudience);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
