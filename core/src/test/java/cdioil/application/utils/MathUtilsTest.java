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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests of the class MathUtils.
 *
 * @author Ana Guerra (1161191)
 */
public class MathUtilsTest {

    public MathUtilsTest() {
    }

    /**
     * Test of the method calculateMean, of the class MathUtils, with valid values.
     */
    @Test
    public void testCalculateMean() {
        System.out.println("calculateMean with valid values");
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

        assertEquals(expected, MathUtils.calculateMean(results), 0.01);
        assertNotEquals(3.0, MathUtils.calculateMean(results), 0.01);
    }

    /**
     * Test of the method calculateMean, of the class MathUtils, with an invalid result list.
     */
    @Test
    public void calculateMeanWithInvalidResultList() {
        System.out.println("calculateMean with an invalid result list");
        assertEquals(-1, MathUtils.calculateMean(null), 0);
    }

    /**
     * Test of the method calculateMean, of the class MathUtils, with sum = 0.
     */
    @Test
    public void calculateMeanWithZeroSum() {
        System.out.println("calculateMean with an invalid result list");
        ArrayList<Double> results = new ArrayList<>();
        results.add(0.0);
        assertEquals(0, MathUtils.calculateMean(results), 0);
    }

    /**
     * Test of the method calculateMeanDeviation, of the class MathUtils, with a valid result list and mean.
     */
    @Test
    public void testCalculateMeanDeviationWithValidValues() {
        System.out.println("calculateMeanDeviation with valid values");
        ArrayList<Double> results = new ArrayList<>();
        results.add(1.0);
        results.add(0.0);
        double mean = MathUtils.calculateMean(results);
        double sum = Math.pow((1 - mean), 2) + Math.pow((0 - mean), 2);
        double meanDeviation = Math.sqrt(sum / ((double) results.size()));

        assertEquals(meanDeviation, MathUtils.calculateMeanDeviation(results), 0.1);
    }

    /**
     * Test of the method calculateMeanDeviation, of the class MathUtils, with an invalid result list.
     */
    @Test
    public void testCalculateMeanDeviationWithInvalidResultList() {
        System.out.println("calculateMeanDeviation with an invalid list");

        assertEquals(-1, MathUtils.calculateMeanDeviation(null), 0);
    }

    /**
     * Test of the method calculateMeanDeviation, of the class MathUtils, with an invalid mean (mean = -1).
     */
    @Test
    public void testCalculateMeanDeviationWithNegativeMean() {
        System.out.println("calculateMeanDeviation with an invalid mean (mean = -1)");
        ArrayList<Double> results = new ArrayList<>();
        results.add(-1.0);
        assertEquals(-1, MathUtils.calculateMeanDeviation(results), 0);
    }

    /**
     * Test of the method calculateMeanDeviation, of the class MathUtils, with an invalid mean (mean = 0).
     */
    @Test
    public void testCalculateMeanDeviationWithZeroMean() {
        System.out.println("calculateMeanDeviation with an invalid mean (mean = 0)");
        ArrayList<Double> results = new ArrayList<>();
        results.add(0.0);
        assertEquals(0, MathUtils.calculateMeanDeviation(results), 0);
    }

    /**
     * Test of the method validateValues, of the class MathUtils, with a valid result list.
     */
    @Test
    public void testValidateValuesWithValidList() {
        System.out.println("validateValues with a valid list");

        ArrayList<Double> results = new ArrayList<>();
        results.add(1.0);
        results.add(4.0);

        assertTrue(MathUtils.validateValues(results));
    }

    /**
     * Test of the method validateValues, of the class MathUtils, with an empty result list.
     */
    @Test
    public void testValidateValuesWithEmptyList() {
        System.out.println("validateValues with an empty list");
        assertFalse(MathUtils.validateValues(new ArrayList<>()));
    }

    /**
     * Test of the method validateValues, of the class MathUtils, with a null result list.
     */
    @Test
    public void testValidateValuesWithNullList() {
        System.out.println("validateValues with an empty list");
        assertFalse(MathUtils.validateValues(null));
    }
}
