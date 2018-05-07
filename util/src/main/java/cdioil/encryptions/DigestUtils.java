package cdioil.encryptions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ricar
 */
public final class DigestUtils {
    
    public static MessageDigest getMessageDigest(String algorithm){
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException("Algoritmo invalido.");
        }
    }
    
}
