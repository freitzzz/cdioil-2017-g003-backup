/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for QuestionAnswerTypes enum.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class QuestionAnswerTypesTest {

    /**
     * Test of values method, of class QuestionAnswerTypes.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        QuestionAnswerTypes[] expResult = {QuestionAnswerTypes.BINARY,
            QuestionAnswerTypes.MULTIPLE_CHOICE, QuestionAnswerTypes.QUANTITATIVE};
        QuestionAnswerTypes[] result = QuestionAnswerTypes.values();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of valueOf method, of class QuestionAnswerTypes.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "BINARY";
        QuestionAnswerTypes expResult = QuestionAnswerTypes.BINARY;
        QuestionAnswerTypes result = QuestionAnswerTypes.valueOf(name);
        assertEquals(expResult, result);
        name = "MULTIPLE_CHOICE";
        expResult = QuestionAnswerTypes.MULTIPLE_CHOICE;
        result = QuestionAnswerTypes.valueOf(name);
        assertEquals(expResult, result);
        name = "QUANTITATIVE";
        expResult = QuestionAnswerTypes.QUANTITATIVE;
        result = QuestionAnswerTypes.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method for all the values in the QuestionAnswerTypes
     * enum.
     */
    @Test
    public void testToStrings() {
        System.out.println("toStrings");
        String expResult = "Binária";
        String result = QuestionAnswerTypes.BINARY.toString();
        assertEquals(expResult, result);
        expResult = "Escolha Múltipla";
        result = QuestionAnswerTypes.MULTIPLE_CHOICE.toString();
        assertEquals(expResult, result);
        expResult = "Quantitativa";
        result = QuestionAnswerTypes.QUANTITATIVE.toString();
        assertEquals(expResult, result);
    }

}
