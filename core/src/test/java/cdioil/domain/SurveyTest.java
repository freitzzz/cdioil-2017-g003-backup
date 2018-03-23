package cdioil.domain;

import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.UsersGroup;
import cdioil.domain.authz.Name;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.SystemUser;
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
    UsersGroup gu;
    Calendar data;

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
        gu = new UsersGroup((new Manager(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41")))));
        data = Calendar.getInstance();
        this.i = new Survey(new Product("UmProduto", new EAN("73292")), data, gu);
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
        Survey outro = new Survey(new Product("UmProduto", new EAN("73292")), data, gu);

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
        assertNotEquals("Instância de Inquerito diferente", new Survey(new Product("OutroProduto", new EAN("123")), data, gu), i);
        assertEquals("Instância de Inquerito igual", new Survey(new Product("UmProduto", new EAN("73292")), data, gu), i);
    }

    /**
     * Test of method toString, of class Survey.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        System.out.println(i.toString());
        assertEquals("A condição deve acertar pois o conteudo das Strings são iguais", i.toString(),
                "Inquerito sobre o produto:\n" + new Product("UmProduto", new EAN("73292"))
                + "\nData:\n" + data);
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

    /**
     * Test do metodo info, da classe Concurso.
     */
    @Test
    public void testInfo() {
        System.out.println("info");
        Survey inquerito = new Survey(new Product("Teste", new EAN("123456789")), data, gu);
        String expResult = "Inquerito sobre o produto:\n" + (new Product("Teste", new EAN("123456789"))).toString()
                + "\nData:\n" + data;
        String result = inquerito.info();
        assertEquals(expResult, result);
    }

    /**
     * Test of targetAudience method, of class Survey.
     */
    @Test
    public void testTargetAudience() {
        System.out.println("targetAudience");
        Survey inquerito = new Survey(new Product("Teste", new EAN("123456789")), data, gu);
        UsersGroup expResult = gu;
        UsersGroup result = inquerito.targetAudience();
    }

}
