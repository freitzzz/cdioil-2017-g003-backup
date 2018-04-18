/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 *
 * @author Ana Guerra (1161191)
 */
public class MathUtilsTest {

    /**
     * Instance of CSVCategoriesReader for test purposes.
     */
    MathUtils math;

    public MathUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        math = new MathUtils();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of the method calculateMean, of the class MathUtils. //
     */
    @Test
    public void testCalculateMean() {
        System.out.println("calculateMean");
        ArrayList<Double> results = new ArrayList<>();
        results.add(1.0);
        results.add(1.0);
        results.add(1.0);
        results.add(1.0);
        results.add(1.0);
        results.add(0.0);
        results.add(0.0);
        results.add(0.0);
        results.add(0.0);
        results.add(0.0);
        double expected = (1 + 1 + 1 + 1 + 1 + 0 + 0 + 0 + 0 + 0) / (double) 10;
        System.out.println("E : " + expected);
        System.out.println("A: " +  math.calculateMean(results));

        assertEquals(expected, math.calculateMean(results), 0.01);
        assertNotEquals(3.0, math.calculateMean(results), 0.01);

    }
    /**
     * Test of the method calculateMeanDeviation, of the class MathUtils.
     */
    @Test
    public void testCalculateMeanDeviation() {
        System.out.println("calculateMeanDeviation");
        ArrayList<Double> results = new ArrayList<>();
        results.add(1.0);
        results.add(0.0);
        double mean = math.calculateMean(results);
        double sum = Math.pow((1-mean),2) + Math.pow((0-mean),2);
        double meanDeviation = Math.sqrt(sum / ((double) results.size()));
        assertEquals(meanDeviation, math.calculateMeanDeviation(results), 0.1);
    }
    /**
     * Test of the method validateValues, of the class MathUtils.
     */
    @Test
    public void testValidateValues() {
        System.out.println("validateValues");

        ArrayList<Double> results = new ArrayList<>();
        ArrayList<Double> fail = new ArrayList<>();
        results.add(1.0);
        results.add(4.0);

        assertEquals(true, math.validateValues(results));
        assertEquals(false, math.validateValues(fail));

    }

}
