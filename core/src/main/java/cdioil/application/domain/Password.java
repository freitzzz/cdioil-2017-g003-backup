package cdioil.application.domain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe que representa o conceito de password
 *
 * @author Joana Pinheiro
 */
public class Password {

    private static final String WEAK_PASSWORD = "Fraca!!!";
    private static final String AVERAGE_PASSWORD = "Média";
    private static final String STRONG_PASSWORD = "Forte";

    /**
     * Constante que representa un conjunto aleatório de bytes com o objetivo de defender contra ataques à password
     */
    private static byte[] salt;

    /**
     * Constante que representa uma password encriptada
     */
    private String password;


    /**
     * Construtor de password
     *
     * @param password
     * @throws NoSuchAlgorithmException
     */
    public Password(String password) throws NoSuchAlgorithmException {
        if (strength(password).equalsIgnoreCase(WEAK_PASSWORD)) {
            throw new IllegalArgumentException(WEAK_PASSWORD);
        }
        salt = generateSalt();
        this.password = generateHash(password + salt);
    }

    /**
     * Cria uma hash com a função de encriptação SHA-256
     *
     * @param password password de utilizador
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
     * Gerar um número random
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
     * Devolve a força da password. Fraca, Media ou Forte
     *
     * @param password password do utilizador
     * @return força da password
     */
    private static String strength(String password) {
        Pattern patternAverage = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z]{9,}$|^(?=.*[a-z])(?=.*\\d)[a-z\\d]{9,}$");
        Matcher matcherAverage = patternAverage.matcher(password);

        Pattern patternStrong = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z\\d])[a-zA-Z\\d]{9,}$");
        Matcher matcherStrong = patternStrong.matcher(password);

        if (matcherAverage.matches()) {
            return AVERAGE_PASSWORD;
        } else if (matcherStrong.matches()) {
            return STRONG_PASSWORD;
        }

        return WEAK_PASSWORD;
    }

}

