/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes da classe CSVCategoriesReader.
 *
 * @author Rita Gonçalves (1160912)
 */
public class CSVCategoriasReaderTest {

    /**
     * Instância de CSVCategoriesReader para testes.
     */
    CSVCategoriesReader c;

    public CSVCategoriasReaderTest() {
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
     * Teste do método testIsFicheiroValido, da classe CSVCategoriesReader.
     */
    @Test
    public void testIsFicheiroValido() {
        System.out.println("lerFicheiro");
        List<String> conteudoFicheiro = null;
        assertFalse("Ficheiros null não podem ser lidos", c.isFileValid(conteudoFicheiro));
        conteudoFicheiro = new LinkedList<>();
        conteudoFicheiro.add("Campo1;Campo2;Campo3;Campo4;Campo5;Campo6;Campo7;Campo8;Campo9;Campo10");
        assertTrue("Campos corretos", c.isFileValid(conteudoFicheiro));
    }
}
