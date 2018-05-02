/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
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
        c = new CSVCategoriesReader(new File("Test.csv"));
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of the method testIsFileValid, of the class CSVCategoriesReader.
     */
    @Test
    public void testIsFileValid() {
        System.out.println("isFileValid");
        List<String> fileContent = null;
        assertFalse("Ficheiros null não podem ser lidos", c.isFileValid(fileContent));
        fileContent = new LinkedList<>();
        fileContent.add("Campo1;Campo2;Campo3;Campo4;Campo5;Campo6;Campo7;Campo8;Campo9;Campo10");
        assertTrue("Campos corretos", c.isFileValid(fileContent));
    }

    /**
     * Test of the method getNumberOfCategoriesRead, of the clss CSVCategoriesReader.
     */
    @Test
    public void testGetNumberOfCategoriesRead() {
        System.out.println("getNumberOfCategoriesRead");
        assertTrue(c.getNumberOfCategoriesRead() == 0);
    }
}
