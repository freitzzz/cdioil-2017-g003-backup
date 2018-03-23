package cdioil.domain.authz;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PhoneNumberTest {

    @Test
    public void ensureEncryptAndDecryptNumberIsWorking() {
        String originalNumber = "911111111";
        PhoneNumber phoneNumber = new PhoneNumber(originalNumber);

        assertTrue(phoneNumber.validatesNumber(originalNumber));
    }

}
