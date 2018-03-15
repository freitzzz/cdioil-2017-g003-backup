package cdioil.domain.authz;

import org.junit.Assert;
import org.junit.Test;

public class UserRegistadoTest {

    @Test(expected = IllegalArgumentException.class)
    public void criaInstanciaInvalidaDeUserRegistadoTest() {
        new UserRegistado(null);
    }

    @Test
    public void equalsHashCodeTest() {
        UserRegistado ur = new UserRegistado(new SystemUser());

        Assert.assertNotEquals(ur, "Dummy");
        Assert.assertNotEquals(ur, null);
        Assert.assertNotEquals(ur, 14);
        Assert.assertEquals(ur, ur);
    }
}
