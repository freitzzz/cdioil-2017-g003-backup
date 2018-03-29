package cdioil.domain;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing for class Survey.
 *
 * @author Rita Gonçalves (1160912)
 */
public class SurveyTest {

    /**
     * Data for testing
     */
    Survey i;
    LocalDateTime data;

    public SurveyTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        data = LocalDateTime.of(0, Month.MARCH, 2, 0, 0, 0);
        this.i = new Survey(new ArrayList<>(), data);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of method hashCode, of class Survey.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Survey outro = new Survey(new ArrayList<>(), data);

        assertEquals(i.hashCode(), outro.hashCode());
    }

    /**
     * Test of method equals, of class Survey.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, i);
        assertNotEquals("Instância de outra classe não é igual", new Category("CategoriaTeste", "100DC", "100DC"), i);
        ArrayList<Product> al = new ArrayList<>();
        al.add(new Product("ProdutoTeste", new EAN("544231234"), new QRCode("4324235")));
        assertNotEquals("Instância de Inquerito diferente", new Survey(al, data), i);
        assertEquals("Instância de Inquerito igual", new Survey(new ArrayList<>(), data), i);
    }

    /**
     * Test of method toString, of class Survey.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Survey s = new Survey(new ArrayList<>(), data);
        assertEquals("A condição deve acertar pois o conteudo das Strings são iguais", i.toString(),
                s.toString());

    }

    /**
     * Test of method addQuestion, of class Survey.
     */
    @Test
    public void testAddQuestion() {
        System.out.println("addQuestion");
        Question q = new BinaryQuestion("QuestaoTeste");
        assertTrue("Deveria ser possível adicionar", i.addQuestion(q));
        i.addQuestion(q);
        assertFalse("Questão null", i.addQuestion(null));
        assertFalse("Questão já existente", i.addQuestion(q));
    }

    /**
     * Test of method removeQuestion, of class Survey.
     */
    @Test
    public void testRemoveQuestion() {
        System.out.println("removeQuestion");
        Question q = new BinaryQuestion("QuestaoTeste");
        i.addQuestion(q);
        assertTrue("Deveria ser possível remover", i.removeQuestion(q));
        i.removeQuestion(q);
        assertFalse("Questão null", i.removeQuestion(null));
        assertFalse("Questão não existente", i.removeQuestion(q));
    }

    /**
     * Test of method isValidQuestion, of class Survey.
     */
    @Test
    public void testIsValidQuestion() {
        System.out.println("isValidQuestion");
        Question q = new BinaryQuestion("QuestaoTeste");
        i.addQuestion(q);
        assertTrue("Deveria ser válida", i.isValidQuestion(q));
        i.removeQuestion(q);
        assertFalse("Questão null", i.isValidQuestion(null));
        assertFalse("Questão não existente", i.isValidQuestion(q));
    }
}
