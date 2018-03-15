/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.domain.authz;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes Unitarios da classe Nome.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class NomeTest {

    /**
     * Teste para o construtor da classe Nome.
     */
    @Test
    public void testeConstrutor() {
        System.out.println("Testes Construtor");
        assertNull("A condição deve acertar pois os argumentos são inválidos",
                createNome(null, null));
        assertNull("A condição deve acertar pois os argumentos são inválidos",
                createNome("", ""));
        assertNull("A condição deve acertar pois os argumentos são inválidos",
                createNome("aa214124", "gds12345"));
        assertNull("A condição deve acertar pois os argumentos são inválidos",
                createNome("!#vv", "??=ad"));
        assertNotNull("A condição deve acertar pois os argumentos são válidos",
                createNome("Lil", "Pump"));
        assertNotNull("A condição deve acertar pois os argumentos são válidos",
                createNome("nao sei", "USAR CAPS LOCK"));
    }

    /**
     * Test para o método hashCode da classe Nome.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        String pNome = "Gucci";
        String apelido = "Gang";
        Nome instance = createNome(pNome, apelido);
        int expResult = pNome.hashCode() + apelido.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Teste do metodo equals da classe Nome.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Nome instance = createNome("dez", "centimos");
        Nome instance2 = createNome("dez", "centimos");
        Nome instance3 = createNome("no", "funny name");
        Nome instance4 = createNome("dez", "euros");
        Nome instance5 = createNome("cinco", "centimos");
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
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "nomes com o primeiro nome diferente", instance, instance4);
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "nomes com apelidos diferentes", instance, instance5);
    }

    /**
     * Teste do metodo toString da classe Nome.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Nome instance = createNome("Lil", "Pump");
        String expResult = "Lil  Pump";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Cria um novo objecto Nome com um primeiro nome e apelido
     *
     * @param pNome primeiro nome
     * @param apelido apelido
     * @return Nome com determinado primeiro nome e apelido ou null caso tenha
     * ocurrido alguma excecao
     */
    private Nome createNome(String pNome, String apelido) {
        try {
            return new Nome(pNome, apelido);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
