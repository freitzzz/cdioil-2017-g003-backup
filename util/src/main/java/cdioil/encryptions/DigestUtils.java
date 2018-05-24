package cdioil.encryptions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Class for methods related to MessageDigest instances.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public final class DigestUtils {
    /**
     * Private constructor to hide implicit public one.
     */
    private DigestUtils() {}
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
    /**
     * Method that hashes a certain message with a certain algorithm
     * @param message String with the message being hashed
     * @param algorithm String wiht the algorithm being used for the hash
     * @return String with the message hashed with a certain algorithm
     */
    public static String hashify(String message,String algorithm){
        MessageDigest algorithmDigest=getMessageDigest(algorithm);
        String preHashedString=message+byteArrayAsString(generateSecureSalt());
        byte[] hashedStringInBytes=algorithmDigest.digest(preHashedString.getBytes());
        StringBuilder builder=new StringBuilder(hashedStringInBytes.length<<1);
        String hashed;
        for(int i=0;i<hashedStringInBytes.length;i++){
            hashed=Integer.toHexString(hashedStringInBytes[i] & 0xFF);
            if(hashed.length()!=1){
                builder.append(hashed);
            }else{
                builder.append('0');
            }
        }
        return builder.toString();
    }
    /**
     * Method that generates a secure salt to be used on digest hashe's
     * @return Byte array with a secure salt ready to be used on a digest
     */
    private static byte[] generateSecureSalt(){
        final Random random = new SecureRandom();
        byte[] generatedSalt = new byte[15];
        random.nextBytes(generatedSalt);
        return generatedSalt;
    }
    /**
     * Method that converts a byte array as a String
     * @param bytes Byte array with the array of bytes being converted in String
     * @return String with the byte array as String
     */
    private static String byteArrayAsString(byte[] bytes){
        StringBuilder builder=new StringBuilder(bytes.length);
        for(int i=0;i<bytes.length;i++){
            builder.append(Byte.toString(bytes[i]));
        }
        return builder.toString();
    }
}
