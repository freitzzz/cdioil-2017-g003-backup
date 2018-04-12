package cdioil.domain.authz;

import cdioil.framework.domain.ddd.ValueObject;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Classe that represents the password of a user
 *
 * @author Joana Pinheiro
 */
@Embeddable
public class Password implements Serializable, ValueObject {
    private static final long serialVersionUID=10l;
    private static final String WEAK_PASSWORD = "Fraca!!!";
    private static final String AVERAGE_PASSWORD = "Média";
    private static final String STRONG_PASSWORD = "Forte";
    /**
     * Password default given to SystemUsers imported by Admin
     */
    public static final String DEFAULT_PASSWORD="Password123";

    /**
     * Variable that represents a random number of bytes with the purpose of protecting the password against attacks
     */
    private static byte[] salt;
    @Column(name = "Gominhos")
    private String saltInString;

    /**
     * Variable that represents an encrypted password
     */
    private String password;
    
    /**
     * Constructor of password of a SystemUser
     *
     * @param password password
     */
    public Password(String password) {
        if (strength(password).equalsIgnoreCase(WEAK_PASSWORD)) {
            throw new IllegalArgumentException(WEAK_PASSWORD);
        }

        try {
            salt = generateSalt();
            this.saltInString= byteToString(salt);
            this.password = generateHash(password + this.saltInString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    /**
     * Creastes an hash function SHA-256 (Secure Hash Algorithm 256)
     *
     * @param password password of a SystemUser
     * @return password hashed
     * @throws NoSuchAlgorithmException
     */
    private static String generateHash(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexString = new StringBuffer();

        for (byte b : hash) {
            String hashedString = Integer.toHexString(0xff & b);

            if (hashedString.length() == 1) {
                hexString.append('0');
            } else {
                hexString.append(hashedString);
            }
        }

        return hexString.toString();
    }

    /**
     * Generates a random number
     *
     * @return salt
     */
    private static byte[] generateSalt() {
        final Random random = new SecureRandom();
        byte[] salt = new byte[10];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Returns the Strength of a password. Fraca, Media ou Forte
     *
     * @param password password of a SystemUser
     * @return strength of a password
     */
    private static String strength(String password) {
        Pattern patternAverage = Pattern.compile("^(?=[a-z0-9]?[A-Z])(?=[A-Z0-9]*?[a-z])(?=[A-Za-z]*?[0-9]).{9,}$");
        Matcher matcherAverage = patternAverage.matcher(password);

        Pattern patternStrong = Pattern.compile("^(?=(([a-z0-9])|([^A-Za-z0-9\\s]))?[A-Z])(?=(([A-Z0-9])|([^A-Za-z0-9\\s]))*?[a-z])(?=(([A-Za-z])|([^A-Za-z0-9\\s]))*?[0-9])(?=[A-Za-z0-9]*?[^A-Za-z0-9\\s]).{9,}$");
        Matcher matcherStrong = patternStrong.matcher(password);

        if (matcherAverage.matches()) {
            return AVERAGE_PASSWORD;
        } else if (matcherStrong.matches()) {
            return STRONG_PASSWORD;
        }

        return WEAK_PASSWORD;
    }

    /**
     * Tranforms a number of bytes into a String
     * @param bytes number of bytes
     * @return return a String
     */
    private String byteToString(byte[] bytes){
        String stringBytes = "";
        for (int i = 0; i < bytes.length; i++) stringBytes += bytes[i];

        return stringBytes ;
    }

    /**
     * Verifies if the inserted password is the correct one
     * @param password password of a SystemUser
     * @return returns true id a password is correct and false it's incorrect
     */
    public boolean verifyPassword(String password) {
        String hash = " ";
        try {
            hash = generateHash(password + this.saltInString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.equals(this.password);
    }

    protected Password(){}
}

