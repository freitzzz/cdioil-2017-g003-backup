package cdioil.application.utils;

import java.util.Random;

/**
 * Utilitary class that permists encryption of Strings using operators
 * <br>Highly <b><i>secrect</i></b> encryption !!!
 * <br>But how does <i>operators</i> encryption work?
 * <br>The encryption uses bitwise operations (&lt;&lt;) and "mathematicals" (+*) 
 * doing the following operation for every byte/char of the String being encrypted:
 * <br>&#09;<b>ENCRYPTED_CHARACTER=CHARACTER_BYTE_VALUE (&lt;|&lt|;*+) ENCRYPTION_VALUE</b>
 * <br>All the other operatores (-/&gt;&gt;) are not used since they can lead to null characters (0)
 * <br>Rules:
 * <br>- The first two fields of the encrypted String represent the encrypted code 
 * followed by the encrypted code
 * <br>- The encrypted String only contains numbers and the following characters [#] 
 * that serve as a way to delimit characters
 * <br><b>Keywords</b>:
 * <br>- Encryption Code: Value that identifies the operator used on the encryption
 * <br>- Encryption Value: Value that is applied on each operation
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class OperatorsEncryption {
    /**
     * Constant that represents the encryption code and maximum value for the 
     * bitwise left operator (&lt;)
     */
    public static final int BITWISE_ENCRYPTION_CODE=15;
    /**
     * Constant that represents the encryption code and maximum value for the 
     * addition operator (+)
     */
    public static final int ADDITION_ENCRYPTION_CODE=892198234;
    /**
     * Constant that represents the encryption code and maximum value for the 
     * multiplication operator (*)
     */
    public static final int MULTIPLICATION_ENCRYPTION_CODE=950;
    /**
     * Constant that represents the encryptions codes used in the encryption
     */
    private static final int[] OPERATORS_VALUES={BITWISE_ENCRYPTION_CODE
            ,ADDITION_ENCRYPTION_CODE,MULTIPLICATION_ENCRYPTION_CODE};
    /**
     * Constant that represents the alphabet used to separate each character 
     * on the encryption
     */
    private static final String ENCRYPTION_ALPHABET="#";
    /**
     * Constant that represents the regular expression used to delimit the 
     * characters of the String being encrypted
     */
    private static final String ENCRYPTION_REGEX="["+ENCRYPTION_ALPHABET+"]";
    /**
     * Integer with the encryption value being used
     */
    private static int randomValue;
    /**
     * Integer with the encryption operation code being used
     */
    private static int operation;
    /**
     * Hides default constructor
     */
    private OperatorsEncryption(){}
    /**
     * Method that encrypts a certain String with the Operators Encryption
     * @param word String with the word being encrypted
     * @return String with the word encrypted with the Operators Encryption
     */
    public static String encrypt(String word){
        return generateEncryptionOperation(word);
    }
    /**
     * Method that encrypts a certain String with the Operators Encryption using 
     * a certain encryption operator and value
     * <br>If the operator value isn't the same as any of the Operators Code, 
     * by default it's used the multiplication code
     * <br><b><i>WARNING</i></b>: It's highly recommended to used values that are 
     * less or equal to the encryption operator code, or else there is a chance 
     * that the word could not be decrypted like the original word
     * @param word String with the word being encrypted
     * @param operator Integer with the encryption operator code being used
     * @param value Integer with the encryption value being used
     * @return String with the word encrypted with the Operators Encryption
     */
    public static String encrypt(String word,int operator,int value){
        operation=operator;
        randomValue=value;
        return encrypt(word);
    }
    /**
     * Method that decrypts a certain String encrypted with the Operators Encryption
     * @param encryptedWord String with the word being decrypted
     * @return String with the word decrypted
     */
    public static String decrypt(String encryptedWord){
        return generateDecryptionOperation(encryptedWord);
    }
    /**
     * Method that removes the encryption header from the encrypted word
     * @param encryptedWord String with encrypted word
     * @return String with the encrypted word with the header removed
     */
    public static String removeEncryptionHeader(String encryptedWord){
        if(encryptedWord==null)return encryptedWord;
        String[] encryptedWordSplitted=encryptedWord.split(ENCRYPTION_ALPHABET);
        if(encryptedWord.length()<=2)return "";
        StringBuilder builder=new StringBuilder();
        for(int i=2;i<encryptedWordSplitted.length-1;i++){
            builder.append(encryptedWordSplitted[i]).append(ENCRYPTION_ALPHABET);
        }
        builder.append(encryptedWordSplitted[encryptedWordSplitted.length-1]);
        return builder.toString();
    }
    /**
     * Method that applies the operators encryption on a certain String
     * @param word String with the word being encrypted
     * @return String with the word encrypted with the Operators Encryption
     */
    private static String generateEncryptionOperation(String word){
        if(word==null||word.isEmpty())return word;
        if(operation==0)operation=OPERATORS_VALUES[new Random().nextInt(OPERATORS_VALUES.length)];
        if(randomValue==0)randomValue=new Random().nextInt(operation)+1;
        StringBuilder builder=new StringBuilder();
        builder.append(operation);
        builder.append(ENCRYPTION_ALPHABET.charAt(new Random().nextInt(ENCRYPTION_ALPHABET.length())));
        builder.append(randomValue);
        for(int i=0;i<word.length();i++){
            builder.append(ENCRYPTION_ALPHABET.charAt(new Random().nextInt(ENCRYPTION_ALPHABET.length())));
            builder.append(encryptCharacter(operation,word.charAt(i),randomValue));
        }
        resetOperators();
        return builder.toString();
    }
    /**
     * Method that applies a decryption to a String previously encrypted with the 
     * Operators Encryption
     * @param encryptedWord String with the word being encrypted
     * @return String with the word decrypted
     */
    private static String generateDecryptionOperation(String encryptedWord){
        if(encryptedWord==null||encryptedWord.isEmpty())return encryptedWord;
        StringBuilder builder=new StringBuilder();
        String[] encryptedWordSplitted=encryptedWord.split(ENCRYPTION_REGEX);
        int operationX=Integer.parseInt(encryptedWordSplitted[0]);
        long value=Long.parseLong(encryptedWordSplitted[1]);
        for(int i=2;i<encryptedWordSplitted.length;i++){
            builder.append(decryptCharacter(operationX,encryptedWordSplitted[i],value));
        }
        return builder.toString();
    }
    /**
     * Method that encrypts a certain character using a certain encryption operator and value
     * @param operation Integer with the encryption operator being used
     * @param character Character with the character being encrypted
     * @param value Integer with the encryption value being used
     * @return String with the encrypted character
     */
    private static String encryptCharacter(int operation,char character,long value){
        String encrypted="";
        switch(operation){
            case BITWISE_ENCRYPTION_CODE:
                return encrypted+=character<<value;
            case ADDITION_ENCRYPTION_CODE:
                return encrypted+=character+value;
            default:
                return encrypted+=character*value;
        }
    }
    /**
     * Method that decrypts a certain previously encrypted character
     * @param operation Integer with the encryption operator used
     * @param encryptedCharacter String with the encrypted character
     * @param value Integer with the encryption value used
     * @return Character with the decrypted character
     */
    private static char decryptCharacter(int operation,String encryptedCharacter,long value){
        long encryptedValue=Long.parseLong(encryptedCharacter);
        switch(operation){
            case BITWISE_ENCRYPTION_CODE:
                return (char)(encryptedValue>>value);
            case ADDITION_ENCRYPTION_CODE:
                return (char)(encryptedValue-value);
            default:
                return (char)(encryptedValue/value);
        }
    }
    /**
     * Resets Operators Encryption operator code and value
     */
    private static void resetOperators(){
        operation=0;
        randomValue=0;
    }
}