package cdioil.encryptions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class for methods related to MessageDigest instances.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public final class DigestUtils {
    /**
     * Returns a MessageDigest instance based on a string that may or may not
     * contain an algorithm identifier (e.g. "SHA-512")
     *
     * @param algorithm string that contains an algorithm id
     * @return MessageDigest instance
     */
    public static MessageDigest getMessageDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException("Algoritmo invalido.");
        }
    }
}
