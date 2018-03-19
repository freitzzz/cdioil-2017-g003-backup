/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.domain.authz;

import cdioil.domain.CodigoQR;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes da classe GrupoUtilizadores.
 *
 * @author Rita Gonçalves (1160912)
 */
public class GrupoUtilizadoresTest {

    /**
     * Instância de GrupoUtilizadores para testes.
     */
    private GrupoUtilizadores gu;

    public GrupoUtilizadoresTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.gu = new GrupoUtilizadores(new Gestor(new SystemUser(new Email("quimBarreiros@gmail.com"), new Nome("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))));
    }

    @After
    public void tearDown() {
    }

    /**
     * Teste do método isUserValido, da classe GrupoUtilizadores.
     */
    @Test
    public void testIsUserValido() {
        System.out.println("isUserValido");
        UserRegistado u = null;
        assertFalse("Objeto null não é válido", gu.isUserValido(u));
        u = new UserRegistado(new SystemUser(new Email("dinaMagalhaes@gmail.com"), new Nome("Zindeira",
                "da Bela"), new Password("ThisIsTheP455w0rd")));
        assertFalse("Objeto não pertence à lista", gu.isUserValido(u));
        gu.adicionarUser(u);
        assertTrue("Objeto existente na lista é válido", gu.isUserValido(u));
    }

    /**
     * Teste do método adicionarUser, da classe GrupoUtilizadores.
     */
    @Test
    public void testAdicionarUser() {
        System.out.println("adicionarUser");
        assertFalse("Objeto null não é válido", gu.adicionarUser(null));
        UserRegistado u = new UserRegistado(new SystemUser(new Email("dinaMagalhaes@gmail.com"), new Nome("Zindeira",
                "da Bela"), new Password("ThisIsTheP455w0rd")));
        assertTrue("Objeto não pertence à lista, logo pode ser adicionado", gu.adicionarUser(u));
        gu.adicionarUser(u);
        assertFalse("Objeto existente na lista, logo não pode ser adicionado", gu.adicionarUser(u));
    }

    /**
     * Teste do método removerUser, da classe GrupoUtilizadores.
     */
    @Test
    public void testRemoverUser() {
        System.out.println("removerUser");
        UserRegistado u = null;
        assertFalse("Objeto null não é válido", gu.removerUser(u));
        u = new UserRegistado(new SystemUser(new Email("dinaMagalhaes@gmail.com"), new Nome("Zindeira",
                "da Bela"), new Password("ThisIsTheP455w0rd")));
        assertFalse("Objeto não pertence à lista, logo não pode ser removido", gu.removerUser(u));
        gu.adicionarUser(u);
        assertTrue("Objeto existente na lista, logo pode ser removido", gu.removerUser(u));
    }

    /**
     * Teste do método toString, da classe GrupoUtilizadores.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        assertEquals("Deveriam ser iguais", "GESTOR RESPONSÁVEL:\nNome: Quim  Barreiros\n"
                + "Email: quimBarreiros@gmail.com\n\nUSERS:\n", gu.toString());
    }

    /**
     * Teste do método hashCode, da classe GrupoUtilizadores.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertEquals("Deveriam ser iguais", 329, gu.hashCode());
    }

    /**
     * Teste do método equals, da classe GrupoUtilizadores.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        assertNotEquals("Objeto null não é igual", null, gu);
        assertNotEquals("Instância de outra classe não é igual", new CodigoQR(40), gu);
        assertEquals("Instância de GrupoUtilizadores igual", gu, gu);
    }
}
