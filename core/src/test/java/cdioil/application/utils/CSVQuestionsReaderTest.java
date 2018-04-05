/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;
import cdioil.domain.BinaryQuestion;
import cdioil.domain.Category;
import cdioil.domain.CategoryQuestionsLibrary;
import cdioil.domain.Question;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the class CSVQuestionsReader.
 * 
 * @author Ana Guerra (1161191)
 */
public class CSVQuestionsReaderTest {
     /**
     * Instance of CSVQuestionsReader for test purposes.
     */
    CSVQuestionsReader c;

    public CSVQuestionsReaderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        c = new CSVQuestionsReader("Test.csv");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of the method testIsFileValid, of the class CSVQuestionsReader.
     */
    @Test
    public void testIsFileValid() {
        System.out.println("isFileValid");
        List<String> fileContent = null;
        assertFalse("Ficheiros null não podem ser lidos", c.isFileValid(fileContent));
        fileContent = new LinkedList<>();
        fileContent.add("#DC;#UN;#CAT;#SCAT;#UB;QuestaoID;Tipo;Texto");
        assertTrue("Campos corretos", c.isFileValid(fileContent));
        fileContent = new LinkedList<>();
        fileContent.add("#DC;#UB;#CAT;#SCAT;#UN;QuestaoID;Tipo;Texto");
        assertFalse("Campos incorretos", c.isFileValid(fileContent));
        fileContent.add("#DC;#UB;#CAT;");
        assertFalse("Campos incorretos", c.isFileValid(fileContent));
        fileContent.add("#DC;#UB;#CAT;#SCAT;#UN;QuestaoID;Tipo;Texto:CampoA;CampoB:CampoC");
        assertFalse("Campos incorretos", c.isFileValid(fileContent));
    }
    /**
     * Test of the method testAddQuestion, of the class CSVQuestionsReader
     */
    @Test
    public void testAddQuestion(){
        System.out.println("addQuestion");
        Question q = new BinaryQuestion("Teste1", "Teste1");
        Category cat = new Category("Teste1", "2UB","10DC-10UN-1002CAT-4SCAT-2UB");
        CategoryQuestionsLibrary cql = new CategoryQuestionsLibrary();
        cql.addCategory(cat);
        assertTrue("A questão é válida, logo podia ser adicionada.",c.addQuestion(cql,q, cat));
        c.addQuestion(cql,q, cat);
        assertFalse("A questão não é válida, logo não podia ser adicionada.",c.addQuestion(cql,q, cat));
        
        
        
    }
    
}
