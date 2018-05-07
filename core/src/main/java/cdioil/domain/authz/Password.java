package cdioil.domain.authz;

import cdioil.framework.domain.ddd.ValueObject;
import encryptions.DigestUtils;
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
    /**
     * Constant that represents the current class serial version
     */
    private static final long serialVersionUID = 10l;
    /**
     * Constant that represents the message that ocures whenever a password is 
     * identified as <i>weak</i>
     */
    private static final String WEAK_PASSWORD_MESSAGE = "Fraca!!!";
    /**
     * Constant that represents the message that ocures whenever a password is 
     * identified as <i>average</i>
     */
    private static final String AVERAGE_PASSWORD_MESSAGE = "Média";
    /**
     * Constant that represents the message that ocures whenever a password is 
     * identified as <i>strong</i>
     */
    private static final String STRONG_PASSWORD_MESSAGE = "Forte";
    /**
     * Constant that represents the regular expression used to identify average passwords
     */
    private static final String AVERAGE_PASSWORD_REGEX="^(?=[a-z0-9]?[A-Z])(?=[A-Z0-9]*?[a-z])(?=[A-Za-z]*?[0-9]).{9,}$";
    /**
     * Constant that represents the regular expression used to identify strong passwords
     */
    private static final String STRONG_PASSWORD_REGEX="^(?=(([a-z0-9])|([^A-Za-z0-9\\s]))?[A-Z])(?=(([A-Z0-9])|([^A-Za-z0-9\\s]))*?[a-z])(?=(([A-Za-z])|([^A-Za-z0-9\\s]))*?[0-9])(?=[A-Za-z0-9]*?[^A-Za-z0-9\\s]).{9,}$";
    /**
     * Constant that represents the digest algorithm used to encrypt the password
     */
    private static final String DIGEST_SHA_256 = "SHA-256";

    /**
     * Variable that represents a random number of bytes with the purpose of
     * protecting the password against attacks
     */
    private static byte[] salt;
    @Column(name = "Cominhos")
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
        if (strength(password).equalsIgnoreCase(WEAK_PASSWORD_MESSAGE)) {
            throw new IllegalArgumentException(WEAK_PASSWORD_MESSAGE);
        }
        
        salt = generateSalt();
        this.saltInString = byteToString(salt);
        this.password = generateHash(password + this.saltInString);

    }

    /**
     * Creastes an hash function SHA-256 (Secure Hash Algorithm 256)
     *
     * @param password password of a SystemUser
     * @return password hashed
     * @throws NoSuchAlgorithmException
     */
    private static String generateHash(String password) {
        MessageDigest messageDigest = DigestUtils.getMessageDigest(DIGEST_SHA_256);
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
        byte[] generatedSalt = new byte[10];
        random.nextBytes(generatedSalt);
        return generatedSalt;
    }

    /**
     * Returns the Strength of a password. Fraca, Media ou Forte
     *
     * @param password password of a SystemUser
     * @return strength of a password
     */
    private static String strength(String password) {
        Pattern patternAverage = Pattern.compile(AVERAGE_PASSWORD_REGEX);
        Matcher matcherAverage = patternAverage.matcher(password);

        Pattern patternStrong = Pattern.compile(STRONG_PASSWORD_REGEX);
        Matcher matcherStrong = patternStrong.matcher(password);

        if (matcherAverage.matches()) {
            return AVERAGE_PASSWORD_MESSAGE;
        } else if (matcherStrong.matches()) {
            return STRONG_PASSWORD_MESSAGE;
        }

        return WEAK_PASSWORD_MESSAGE;
    }

    /**
     * Tranforms a number of bytes into a String
     *
     * @param bytes number of bytes
     * @return return a String
     */
    private String byteToString(byte[] bytes) {
        String stringBytes = "";
        for (int i = 0; i < bytes.length; i++) {
            stringBytes += bytes[i];
        }

        return stringBytes;
    }

    /**
     * Verifies if the inserted password is the correct one
     *
     * @param password password of a SystemUser
     * @return returns true id a password is correct and false it's incorrect
     */
    public boolean verifyPassword(String password) {
        String hash;
        hash = generateHash(password + this.saltInString);
        return hash.equals(this.password);
    }

    /**
     * Empty constructor for JPA.
     */
    protected Password() {
    }
}
