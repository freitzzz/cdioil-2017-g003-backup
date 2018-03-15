package cdioil.domain.authz;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes unitarios da classe Admin.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class AdminTest {

    /**
     * Teste para o construtor da classe Admin.
     */
    @Test
    public void testeConstrutor() {
        System.out.println("Testes Construtor");
        Email email = new Email("lilpump@guccigang.com");
        Password pwd = new Password("Bananas19");
        Nome nome = new Nome("Lil","Pump");
        SystemUser sysUser = new SystemUser(email,nome,pwd);
        assertNull("A condição deve acertar pois os argumentos são inválidos",
                createAdmin(null));
        assertNotNull("A condição deve acertar pois os argumentos são válidos",
                createAdmin(sysUser));
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
