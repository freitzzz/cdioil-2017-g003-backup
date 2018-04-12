package cdioil.domain.authz;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PasswordTest {

    @Test
    public void testEmptyConstructor() {
        System.out.println("Password()");
        Password pass = new Password();
    }

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
    public void passwordCreatedWithNoProblems() {
        System.out.println("passwordCreatedWithNoProblems");

        Password password = new Password("Password123");
        assertTrue(password.verifyPassword("Password123"));
    }

    @Test
    public void ensureStrongPasswordIsBuilt() {
        System.out.println("ensureStrongPasswordIsBuilt");
        Password pass = new Password("IAmTheSenate@MaceAnd3Fellows");
    }
}
