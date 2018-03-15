package cdioil.application.domain;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class PasswordTest {

    @Test(expected = IllegalArgumentException.class)
    public void ensurePasswordsSmallerThan9CharactersAreNotAllowed() {
        System.out.println("ensurePasswordsSmallerThan9CharactersAreNotAllowed");

        try {
            new Password("badPass1");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Não foi possivel criar o hash da password");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensurePasswordIsWeak() {
        System.out.println("ensurePasswordIsWeak");

        try {
            new Password("bananaboa");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Não foi possivel criar o hash da password");
        }

    }

    @Test
    public void passwordCriadaSemProblemas() {
        System.out.println("passwordCriadaSemProblemas");

        try {
            new Password("JoanaDP26");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Não foi possivel criar o hash da password");
        }

    }
}
