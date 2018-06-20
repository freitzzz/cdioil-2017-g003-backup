package cdioil.application.utils;

import cdioil.files.InvalidFileFormattingException;
import cdioil.domain.BinaryQuestion;
import cdioil.domain.MultipleChoiceQuestion;
import cdioil.domain.MultipleChoiceQuestionOption;
import cdioil.domain.QuantitativeQuestion;
import cdioil.domain.QuantitativeQuestionOption;
import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
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

//    /**
//     * Instance of CSVQuestionsReader for test purposes.
//     */
//    CSVQuestionsReader questionsReader;
//
//    @Test(expected = InvalidFileFormattingException.class)
//    public void ensureIsCategoryQuestionsFileValidThrowsException() {
//
//        CSVQuestionsReader reader = new CSVQuestionsReader("Invalid_Category_Questions.csv");
//
//        reader.readCategoryQuestions();
//    }
//
//    @Test
//    public void testReadCategoryQuestions() {
//        System.out.println("readCategoryQuestions");
//
//        CSVQuestionsReader reader = new CSVQuestionsReader("Inquerito_exemplo_v01.csv");
//
//        Map<String, List<Question>> questionsByCategory = reader.readCategoryQuestions();
//
//        Map<String, List<Question>> expected = new HashMap<>();
//
//        String key1 = "10DC-10UN-1002CAT-4SCAT";
//
//        List<Question> key1Values = new LinkedList<>();
//        Question q1 = new BinaryQuestion("Bla, bla?", "12");
//        key1Values.add(q1);
//        Question q2 = new BinaryQuestion("Bla, bla, bla?", "13");
//        key1Values.add(q2);
//        QuantitativeQuestionOption quantitativeOption1 = new QuantitativeQuestionOption(0.0);
//        QuantitativeQuestionOption quantitativeOption2 = new QuantitativeQuestionOption(1.0);
//        QuantitativeQuestionOption quantitativeOption3 = new QuantitativeQuestionOption(2.0);
//        QuantitativeQuestionOption quantitativeOption4 = new QuantitativeQuestionOption(3.0);
//        QuantitativeQuestionOption quantitativeOption5 = new QuantitativeQuestionOption(4.0);
//        QuantitativeQuestionOption quantitativeOption6 = new QuantitativeQuestionOption(5.0);
//        LinkedList<QuestionOption> quantitativeOptionList = new LinkedList<>();
//        quantitativeOptionList.add(quantitativeOption1);
//        quantitativeOptionList.add(quantitativeOption2);
//        quantitativeOptionList.add(quantitativeOption3);
//        quantitativeOptionList.add(quantitativeOption4);
//        quantitativeOptionList.add(quantitativeOption5);
//        quantitativeOptionList.add(quantitativeOption6);
//        Question q3 = new QuantitativeQuestion("Ble, ble", "16", quantitativeOptionList);
//        key1Values.add(q3);
//
//        expected.put(key1, key1Values);
//
//        String key2 = "10DC-10UN-1002CAT-4SCAT-2UB";
//
//        List<Question> key2Values = new LinkedList<>();
//
//        LinkedList<QuestionOption> q4Options = new LinkedList<>();
//        q4Options.add(new MultipleChoiceQuestionOption("Na, na, na"));
//        q4Options.add(new MultipleChoiceQuestionOption("Ne, ne, ne,"));
//        q4Options.add(new MultipleChoiceQuestionOption("Ni, ni, ni"));
//        Question q4 = new MultipleChoiceQuestion("Bli, bli, bli", "34", q4Options);
//
//        key2Values.add(q4);
//        expected.put(key2, key2Values);
//
//        String key3 = "10DC-10UN-1002CAT-6SCAT-1UB";
//
//        List<Question> key3Values = new LinkedList<>();
//
//        Question q5 = new BinaryQuestion("Blo, blo, blo?", "15");
//        key3Values.add(q5);
//
//        expected.put(key3, key3Values);
//
//        assertEquals(expected, questionsByCategory);
//    }
//
//    @Test(expected = InvalidFileFormattingException.class)
//    public void ensureIsIndependentQuestionsFileValidThrowsException() {
//
//        CSVQuestionsReader reader = new CSVQuestionsReader("Invalid_Independent_Questions.csv");
//
//        reader.readIndependentQuestions();
//    }
//
//    @Test
//    public void testReadIndependentQuestions() {
//        System.out.println("readIndependentQuestions");
//
//        CSVQuestionsReader reader = new CSVQuestionsReader("Inquerito_questoes_independentes_exemplo_v01.csv");
//
//        List<Question> questions = reader.readIndependentQuestions();
//
//        List<Question> expected = new LinkedList<>();
//
//        Question q1 = new BinaryQuestion("Já adquiriu produtos de marca Continente?", "A34");
//        expected.add(q1);
//        QuantitativeQuestionOption quantitativeOption1 = new QuantitativeQuestionOption(0.0);
//        QuantitativeQuestionOption quantitativeOption2 = new QuantitativeQuestionOption(1.0);
//        QuantitativeQuestionOption quantitativeOption3 = new QuantitativeQuestionOption(2.0);
//        QuantitativeQuestionOption quantitativeOption4 = new QuantitativeQuestionOption(3.0);
//        QuantitativeQuestionOption quantitativeOption5 = new QuantitativeQuestionOption(4.0);
//        QuantitativeQuestionOption quantitativeOption6 = new QuantitativeQuestionOption(5.0);
//        LinkedList<QuestionOption> quantitativeOptionList = new LinkedList<>();
//        quantitativeOptionList.add(quantitativeOption1);
//        quantitativeOptionList.add(quantitativeOption2);
//        quantitativeOptionList.add(quantitativeOption3);
//        quantitativeOptionList.add(quantitativeOption4);
//        quantitativeOptionList.add(quantitativeOption5);
//        quantitativeOptionList.add(quantitativeOption6);
//        Question q2 = new QuantitativeQuestion("Qual a sua opinião global sobre "
//                + "os produtos de marca Continente?", "A23", quantitativeOptionList);
//        expected.add(q2);
//
//        LinkedList<QuestionOption> options = new LinkedList<>();
//        options.add(new MultipleChoiceQuestionOption("São a minha preferência"));
//        options.add(new MultipleChoiceQuestionOption("Compro quando são mais baratos"));
//        options.add(new MultipleChoiceQuestionOption("Gosto de alguns específicos"));
//        options.add(new MultipleChoiceQuestionOption("Não compro"));
//        Question q3 = new MultipleChoiceQuestion("Como classifica os seus hábitos de compra de produtos de marca Continente?", "B12", options);
//        expected.add(q3);
//        Question q4 = new BinaryQuestion("Recomenda produtos de marca Continente a amigos?", "A15");
//        expected.add(q4);
//
//        assertEquals(expected, questions);
//    }

}
