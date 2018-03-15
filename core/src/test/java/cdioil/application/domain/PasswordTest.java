package cdioil.application.domain;

import cdioil.domain.authz.Password;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PasswordTest {

    @Test(expected = IllegalArgumentException.class)
    public void ensurePasswordsSmallerThan9CharactersAreNotAllowed() {
        System.out.println("ensurePasswordsSmallerThan9CharactersAreNotAllowed");
        new Password("badPass1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensurePasswordIsWeak() {
        System.out.println("ensurePasswordIsWeak");
        new Password("bananaboa");
    }

    @Test
    public void passwordCriadaSemProblemas() {
        System.out.println("passwordCriadaSemProblemas");

        Password password = new Password("Password123");
        assertTrue(password.verifyPassword("Password123"));

    }
}
