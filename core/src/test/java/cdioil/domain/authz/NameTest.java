package cdioil.domain.authz;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for class Name.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class NameTest {

    /**
     * Tests for the constructor of class Name.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor Tests");
        assertNull("The condition should succeed because the arguments"
                + "are invalid", createName(null, null));
        assertNull("The condition should succeed because the first name can't"
                + " be null", createName(null, "Apelido"));
        assertNull("The condition should succeed because the last name can't "
                + "be null", createName("First Name", null));
        assertNull("The condition should succeed because the first name can't "
                + "be empty", createName("", "Apelido"));
        assertNull("The condition should succeed because the last name can't be"
                + " empty", createName("First Name", ""));
        assertNull("The condition should succeed because the arguments"
                + "are invalid", createName("", ""));
        assertNull("The condition should succeed because the first"
                + " name is invalid", createName("aa214124", "Last Name"));
        assertNull("The condition should succeed because the first name is "
                + "invalid", createName("!#vv", "Last Name"));
        assertNull("The condition should succeed because the last name is "
                + "invalid",createName("First","$$%%aperi??"));
        assertNull("The condition should succeed because the last name"
                + " is invalid", createName("First Name", "123last name"));
        assertNotNull("The condition should succeed because the arguments"
                + "are valid", createName("Lil", "Pump"));
        assertNotNull("The condition should succeed because the arguments"
                + "are valid", createName("nao sei", "USAR CAPS LOCK"));
        assertNotNull("Empty constructor test",new Name());
    }

    /**
     * Test for hashCode method, of class Name.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        String pNome = "Gucci";
        String apelido = "Gang";
        Name instance = createName(pNome, apelido);
        int expResult = pNome.hashCode() + apelido.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Name.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Name instance = createName("dez", "centimos");
        Name instance2 = createName("dez", "centimos");
        Name instance3 = createName("no", "funny name");
        Name instance4 = createName("dez", "euros");
        Name instance5 = createName("cinco", "centimos");
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
     * Test of toString method, of class Name.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Name instance = createName("Lil", "Pump");
        String expResult = "Lil Pump";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Builds a Name with a first name and a surname.
     *
     * @param firstName first name
     * @param surname surname
     * @return Name instance or null in case of an exception
     */
    private Name createName(String firstName, String surname) {
        try {
            return new Name(firstName, surname);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
