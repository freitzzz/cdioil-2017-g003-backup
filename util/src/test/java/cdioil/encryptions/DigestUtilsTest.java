package cdioil.encryptions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit testing class for DigestUtils class
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class DigestUtilsTest {

    /**
     * Ensure getMessageDigest method, of class DigestUtils succeeds.
     * @throws java.security.NoSuchAlgorithmException
     */
    @Test
    public void ensureGetMessageDigestSucceeds() throws NoSuchAlgorithmException {
        System.out.println("ensureGetMessageDigestSucceeds");
        String algorithm = "SHA-256";
        MessageDigest expResult = MessageDigest.getInstance(algorithm);
        MessageDigest result = DigestUtils.getMessageDigest(algorithm);
        assertEquals(expResult.getAlgorithm(), result.getAlgorithm());
    }
    
    /**
     * Ensure getMessageDigest method, of class DigestUtils throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureGetMessageDigestThrowsException(){
        System.out.println("ensureGetMessageDigestThrowsException");
        String algorithm = "Bananas";
        DigestUtils.getMessageDigest(algorithm);
    }
}
