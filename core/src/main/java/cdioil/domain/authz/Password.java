package cdioil.domain.authz;

import cdioil.framework.domain.ddd.ValueObject;
import cdioil.encryptions.DigestUtils;
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
 * Classe that represents the pwd of a user
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
     * Constant that represents the message that ocures whenever a pwd is identified as <i>weak</i>
     */
    private static final String WEAK_MESSAGE = "Fraca!!!";
    /**
     * Constant that represents the message that ocures whenever a pwd is identified as <i>average</i>
     */
    private static final String AVERAGE_MESSAGE = "Média";
    /**
     * Constant that represents the message that ocures whenever a pwd is identified as <i>strong</i>
     */
    private static final String STRONG_MESSAGE = "Forte";
    /**
     * Constant that represents the regular expression used to identify average passwords
     */
    private static final String AVERAGE_REGEX = "^(?=[a-z0-9]?[A-Z])(?=[A-Z0-9]*?[a-z])(?=[A-Za-z]*?[0-9]).{9,}$";
    /**
     * Constant that represents the regular expression used to identify strong passwords
     */
    private static final String STRONG_REGEX = "^(?=(([a-z0-9])|([^A-Za-z0-9\\s]))?[A-Z])(?=(([A-Z0-9])|([^A-Za-z0-9\\s]))*?[a-z])(?=(([A-Za-z])|([^A-Za-z0-9\\s]))*?[0-9])(?=[A-Za-z0-9]*?[^A-Za-z0-9\\s]).{9,}$";
    /**
     * Constant that represents the digest algorithm used to encrypt the pwd
     */
    private static final String DIGEST_SHA_256 = "SHA-256";

    /**
     * Variable that represents a random number of bytes with the purpose of protecting the pwd against attacks
     */
    private static byte[] salt;
    @Column(name = "Cominhos")
    private String saltInString;

    /**
     * Variable that represents an encrypted pwd
     */
    private String pwd;

    /**
     * Constructor of pwd of a SystemUser
     *
     * @param password pwd
     */
    public Password(String password) {
        if (strength(password).equalsIgnoreCase(WEAK_MESSAGE)) {
            throw new IllegalArgumentException(WEAK_MESSAGE
                    ,new Throwable(this.getClass().getSimpleName()));
        }

        salt = generateSalt();
        this.saltInString = byteToString(salt);
        this.pwd = generateHash(password + this.saltInString);

    }

    /**
     * Empty constructor for JPA.
     */
    protected Password() {
    }

    /**
     * Creastes an hash function SHA-256 (Secure Hash Algorithm 256)
     *
     * @param password pwd of a SystemUser
     * @return pwd hashed
     * @throws NoSuchAlgorithmException
     */
    private static String generateHash(String password) {
        MessageDigest messageDigest = DigestUtils.getMessageDigest(DIGEST_SHA_256);
        byte[] hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder(128);

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
     * Returns the Strength of a pwd. Fraca, Media ou Forte
     *
     * @param password pwd of a SystemUser
     * @return strength of a pwd
     */
    private static String strength(String password) {
        Pattern patternAverage = Pattern.compile(AVERAGE_REGEX);
        Matcher matcherAverage = patternAverage.matcher(password);

        Pattern patternStrong = Pattern.compile(STRONG_REGEX);
        Matcher matcherStrong = patternStrong.matcher(password);

        if (matcherAverage.matches()) {
            return AVERAGE_MESSAGE;
        } else if (matcherStrong.matches()) {
            return STRONG_MESSAGE;
        }

        return WEAK_MESSAGE;
    }

    /**
     * Tranforms a number of bytes into a String
     *
     * @param bytes number of bytes
     * @return return a String
     */
    private String byteToString(byte[] bytes) {
        int size = bytes.length;
        StringBuilder stringBytes = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            stringBytes.append(Byte.toString(bytes[i]));
        }

        return stringBytes.toString();
    }

    /**
     * Verifies if the inserted pwd is the correct one
     *
     * @param password pwd of a SystemUser
     * @return returns true id a pwd is correct and false it's incorrect
     */
    public boolean verifyPassword(String password) {
        String hash;
        hash = generateHash(password + this.saltInString);
        return hash.equals(this.pwd);
    }

}
