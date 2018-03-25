package cdioil.domain.authz;

import org.junit.Assert;
import org.junit.Test;

public class RegisteredUserTest {

    @Test(expected = IllegalArgumentException.class)
    public void criaInstanciaInvalidaDeUserRegistadoTest() {
        new RegisteredUser(null);
    }

    @Test
    public void equalsHashCodeTest() {
        RegisteredUser ur = new RegisteredUser(new SystemUser());

        Assert.assertNotEquals(ur, "Dummy");
        Assert.assertNotEquals(ur, null);
        Assert.assertNotEquals(ur, 14);
        Assert.assertEquals(ur, ur);
    }
}
