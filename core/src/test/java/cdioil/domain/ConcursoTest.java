package cdioil.domain;

import cdioil.domain.authz.GrupoUtilizadores;
import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes unitarios da classe Concurso.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ConcursoTest {

    private String descricao;
    private GrupoUtilizadores gu;
    private Calendar dataInicio;
    private Calendar dataFim;

    @Before
    public void setUp() {
        descricao = "Concurso Teste";
        gu = new GrupoUtilizadores();
        dataInicio = Calendar.getInstance();
        dataFim = Calendar.getInstance();
    }

    /**
     * Testes relacionados com o construtor da classe Concurso.
     */
    @Test
    public void testeConstrutor(){
        System.out.println("Testes Construtor");
        assertNull("A condição deve acertar pois os argumentos são inválidos",
                createConcurso(null, gu,dataInicio,dataFim));
        assertNull("A condição deve acertar pois os argumentos são inválidos",
                createConcurso(descricao,gu,null,dataFim));
        assertNull("A condição deve acertar pois os argumentos são inválidos",
                createConcurso(descricao,gu,dataInicio,null));
        assertNotNull("A condição deve acertar pois os argumentos são válidos",
                createConcurso(descricao,gu,dataInicio,dataFim));
    }

    /**
     * Teste do metodo hashCode, da classe Concurso.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Concurso instance = createConcurso(descricao,gu,dataInicio,dataFim);
        int expResult = descricao.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult,result);
    }

    /**
     * Teste do metodo equals method, da classe Concurso.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Concurso instance = createConcurso(descricao,gu,dataInicio,dataFim);
        Concurso instance2 = createConcurso(descricao,gu,dataInicio,dataFim);
        Concurso instance3 = createConcurso("Concurso 3",gu,dataInicio,dataFim);
        assertEquals("A condição deve acertar pois estamos a comparar"
                + "as mesmas instancias", instance, instance);
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "instancias de classes diferentes", instance, "bananas");
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "uma instancia com outra a null", instance, null);
        assertEquals("A condição deve acertar pois estamos a comparar"
                + "duas instancias iguais", instance, instance2);
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "duas instancias diferentes", instance, instance3);
    }

    /**
     * Test do metodo toString, da classe Concurso.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Concurso instance = createConcurso(descricao,gu,dataInicio,dataFim);
        String expResult = "Concurso Teste";
        String result = instance.toString();
        assertEquals(expResult,result);
    }

    /**
     * Test do metodo info, da classe Concurso.
     */
    @Test
    public void testInfo() {
        System.out.println("info");
        Concurso instance = createConcurso(descricao,gu,dataInicio,dataFim);
        String expResult = "Concurso Teste";
        String result = instance.info();
        assertEquals(expResult, result);
    }

    /**
     * Test of publicoAlvo method, of class Concurso.
     */
    @Test
    public void testPublicoAlvo() {
        System.out.println("publicoAlvo");
        Concurso instance = createConcurso(descricao,gu,dataInicio,dataFim);
        GrupoUtilizadores expResult = gu;
        GrupoUtilizadores result = instance.publicoAlvo();
        assertEquals(expResult, result);
    }

    /**
     * Cria um novo objeto Concurso com uma descricao, grupo de utilizadores,
     * data de inicio e data de fim.
     * @param descricao
     * @param gu
     * @param dataInicio
     * @param dataFim
     * @return instancia de Concurso
     */
    private Concurso createConcurso(String descricao, GrupoUtilizadores gu,
            Calendar dataInicio, Calendar dataFim) {
        try {
            return new Concurso(descricao, gu, dataInicio, dataFim);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
