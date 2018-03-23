package cdioil.domain.authz;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PhoneNumberTest {

    @Test
    public void ensureEncryptAndDecryptNumberIsWorkingTest() {
        System.out.println("ensureEncryptAndDecryptNumberIsWorking");
        String originalNumber = "911111111";
        PhoneNumber phoneNumber = new PhoneNumber(originalNumber);

        assertTrue(phoneNumber.validatesNumber(originalNumber));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensurePhoneNumberCantHaveLesThan9CharacteresTest() {
        System.out.println("ensurePhoneNumberCantHaveLesThan9Characteres");
        new PhoneNumber("911111");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureItDoesntAcceptNonPortugueseNumbersTest() {
        System.out.println("ensureItDoesntAcceptNonPortugueseNumbers");
        new PhoneNumber("901111111");
    }
}
