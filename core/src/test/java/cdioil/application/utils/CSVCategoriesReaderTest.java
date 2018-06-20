/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the class CSVCategoriesReader.
 *
 * @author Rita Gonçalves (1160912)
 */
public class CSVCategoriesReaderTest {

    /**
     * Instance of CSVCategoriesReader for test purposes.
     */
    CSVCategoriesReader c;

    public CSVCategoriesReaderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        c = new CSVCategoriesReader("Test.csv");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of the method getNumberOfCategoriesRead, of the clss CSVCategoriesReader.
     */
//    @Test
//    public void testGetNumberOfCategoriesRead() {
//        System.out.println("getNumberOfCategoriesRead");
//        assertTrue(c.getNumberOfCategoriesRead() == 0);
//    }
}
