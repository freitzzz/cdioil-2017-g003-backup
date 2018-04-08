package cdioil.application.utils;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.MultipleChoiceQuestion;
import cdioil.domain.QuantitativeQuestion;
import cdioil.domain.Question;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    CSVQuestionsReader questionsReader;
        
    @Test(expected = InvalidFileFormattingException.class)
    public void ensureIsCategoryQuestionsFileValidThrowsException(){
        
        CSVQuestionsReader reader = new CSVQuestionsReader("Invalid_Category_Questions.csv");
        
        reader.readCategoryQuestions();
    }
    
    @Test
    public void testReadCategoryQuestions() {
        System.out.println("readCategoryQuestions");

        CSVQuestionsReader reader = new CSVQuestionsReader("Inquerito_exemplo_v01.csv");

        Map<String, List<Question>> questionsByCategory = reader.readCategoryQuestions();

        Map<String, List<Question>> expected = new HashMap<>();

        String key1 = "10DC-10UN-1002CAT-4SCAT";

        List<Question> key1Values = new LinkedList<>();
        Question q1 = new BinaryQuestion("Bla, bla?", "12");
        key1Values.add(q1);
        Question q2 = new BinaryQuestion("Bla, bla, bla?", "13");
        key1Values.add(q2);
        Question q3 = new QuantitativeQuestion("Ble, ble", "16", new Double(0), new Double(5), 0.5);
        key1Values.add(q3);

        expected.put(key1, key1Values);
        
        

        String key2 = "10DC-10UN-1002CAT-4SCAT-2UB";

        List<Question> key2Values = new LinkedList<>();

        LinkedList<String> q4Options = new LinkedList<>();
        q4Options.add("Na, na, na");
        q4Options.add("Ne, ne, ne,");
        q4Options.add("Ni, ni, ni");
        Question q4 = new MultipleChoiceQuestion("Bli, bli, bli","34" , q4Options);
        
        key2Values.add(q4);
        expected.put(key2, key2Values);
        
        
        
        String key3 = "10DC-10UN-1002CAT-6SCAT-1UB";
        
        List<Question> key3Values = new LinkedList<>();
        
        Question q5 = new BinaryQuestion("Blo, blo, blo?", "15");
        key3Values.add(q5);
        
        expected.put(key3, key3Values);
        
        assertEquals(expected, questionsByCategory);
    }

    @Test(expected = InvalidFileFormattingException.class)
    public void ensureIsIndependentQuestionsFileValidThrowsException(){
        
        CSVQuestionsReader reader = new CSVQuestionsReader("Invalid_Independent_Questions.csv");
        
        reader.readIndependentQuestions();
    }
    
    @Test
    public void testReadIndependentQuestions() {
        System.out.println("readIndependentQuestions");

        CSVQuestionsReader reader = new CSVQuestionsReader("Inquerito_questoes_independentes_exemplo_v01.csv");

        List<Question> questions = reader.readIndependentQuestions();

        List<Question> expected = new LinkedList<>();

        Question q1 = new BinaryQuestion("Já adquiriu produtos de marca Continente?", "A34");
        expected.add(q1);
        Question q2 = new QuantitativeQuestion("Qual a sua opinião global sobre os produtos de marca Continente?", "A23", new Double(0), new Double(5), 0.5);
        expected.add(q2);

        LinkedList<String> options = new LinkedList<>();
        options.add("São a minha preferência");
        options.add("Compro quando são mais baratos");
        options.add("Gosto de alguns específicos");
        options.add("Não compro");
        Question q3 = new MultipleChoiceQuestion("Como classifica os seus hábitos de compra de produtos de marca Continente?", "B12", options);
        expected.add(q3);
        Question q4 = new BinaryQuestion("Recomenda produtos de marca Continente a amigos?", "A15");
        expected.add(q4);

        assertEquals(expected, questions);
    }

}
