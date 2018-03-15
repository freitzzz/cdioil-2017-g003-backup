package cdioil.domain.authz;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Testes unitarios da classe Admin.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class AdminTest {

    private SystemUser sysUser;

    @Before
    public void setUp() {
        Email email = new Email("lilpump@guccigang.com");
        Password pwd = new Password("Bananas19");
        Nome nome = new Nome("Lil", "Pump");
        sysUser = new SystemUser(email, nome, pwd);
    }

    /**
     * Teste para o construtor da classe Admin.
     */
    @Test
    public void testeConstrutor() {
        System.out.println("Testes Construtor");
        assertNull("A condição deve acertar pois os argumentos são inválidos",
                createAdmin(null));
        assertNotNull("A condição deve acertar pois os argumentos são válidos",
                createAdmin(sysUser));
    }

    /**
     * Teste para o metodo hashCode da classe Admin.
     */
    @Test
    public void testeHashCode() {
        System.out.println("hashCode");
        Admin a = createAdmin(sysUser);
        int expResult = sysUser.hashCode();
        int result = a.hashCode();
    }

    /**
     * Teste para o metodo equals da classe Admin.
     */
    @Test
    public void testeEquals() {
        System.out.println("equals");
        Email email = new Email("bananas@bananeira.com");
        Password pwd = new Password("Bananas19");
        Nome nome = new Nome("Lil", "Pump");
        SystemUser sysUser2 = new SystemUser(email, nome, pwd);
        Admin a = createAdmin(sysUser);
        Admin a2 = createAdmin(sysUser2);
        Admin a3 = createAdmin(sysUser);

        assertEquals("A condição deve acertar pois estamos a comparar"
                + "as mesmas instancias", a, a);
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "instancias de classes diferentes", a, "bananas");
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "uma instancia com outra a null", a, null);
        assertEquals("A condição deve acertar pois estamos a comparar"
                + "duas instancias iguais", a, a3);
        assertNotEquals("A condição deve acertar pois estamos a comparar"
                + "duas instancias diferentes", a, a2);
    }

    /**
     * Teste do metodo toString da classe Admin.
     */
    @Test
    public void testeToString() {
        System.out.println("toString");
        Admin a = createAdmin(sysUser);
        String expResult = sysUser.toString();
        String result = a.toString();
        assertEquals("A condição deve acertar pois as strings são iguais",
                 expResult, result);
    }

    /**
     * Cria um novo objecto Admin com um SystemUser
     *
     * @param sysUser utilizador do sistema
     * @return instancia de Admin
     */
    private Admin createAdmin(SystemUser sysUser) {
        try {
            return new Admin(sysUser);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
