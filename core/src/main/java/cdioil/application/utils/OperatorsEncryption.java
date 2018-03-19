package cdioil.application.utils;

import java.util.Random;

/**
 * Classe utilitária que permite a encriptação de Strings usando operadores
 * <br>Encriptação muito <b><i>secreta</i></b> <b>!!!</b>
 * <br>Como funciona a encriptação por <i>operadores</i>?
 * <br>Muito fácil, a encriptação usa operações a nivel bitwise (&lt;&lt;) e matemáticas (+*) 
 * fazendo a seguinte operação para cada byte/char da cadeia de caratéres a ser encriptada:
 * <br>&#09;<b>CARATER_ENCRIPTADO=VALOR_BYTE_CARATER (&lt;&lt;*+) VALOR_ENCRIPTAÇÃO</b>
 * <br>O resto dos operadores (-/&gt;&gt;) não são usados pois podem levar a carateres nulos (0)
 * <br>Regras:
 * <br>- Os primeiros dois campos representam respetivamente o valor do operador 
 * e o valor da encriptação usado
 * <br>- É apenas constituida por digitos ou carateres do seguinte alfabeto [!#$%&/()?»«] 
 * que servem como demilitadores entre cada carater encriptado em forma de digitos
 * <br><b>Palavras-Chave</b>:
 * <br>- Valor Operador: Valor que identifica o operador a ser usado
 * <br>- Valor Encriptação: Valor que é aplicado na operação entre cada valor 
 * do byte do carater a ser encriptado
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class OperatorsEncryption {
    /**
     * Constante que representa o valor maximo que um operador bitwise pode usar 
     * na operação da encriptação
     */
    public static final int VALOR_ENCRIPTACAO_OPERADOR_BITWISE=15;
    /**
     * Constante que representa o valor maximo que um operador de adicao pode usar 
     * na operação da encriptação
     */
    public static final int VALOR_ENCRIPTACAO_OPERADOR_ADICAO=892198234;
    /**
     * Constante que representa o valor maximo que um operador de multiplicacao pode usar 
     * na operação da encriptação
     */
    public static final int VALOR_ENCRIPTACAO_OPERADOR_MULTIPLICACAO=950;
    /**
     * Constante que representa o valor dos operadores usados na encriptação
     */
    private static final int[] VALORES_OPERADORES={VALOR_ENCRIPTACAO_OPERADOR_BITWISE
            ,VALOR_ENCRIPTACAO_OPERADOR_ADICAO,VALOR_ENCRIPTACAO_OPERADOR_MULTIPLICACAO};
    /**
     * Constante que representa o alfabeto usado como demilitador na encriptação
     */
    private static final String ALFABETO_ENCRIPTACAO="!#$%&/()?»«";
    /**
     * Constante que representa a expressão regular usada para demilitar a 
     * String encriptada no processo de encriptação
     */
    private static final String REGEX_ENCRIPTACAO="["+ALFABETO_ENCRIPTACAO+"]";
    /**
     * Integer com o valor da encriptação a ser usado
     */
    private static int randomValue;
    /**
     * Integer com o valor do operador a ser usado
     */
    private static int operation;
    /**
     * Método que aplica a encriptação por operadores a uma determinada String
     * @param word String com a palavra a ser encriptada
     * @return String com a palavra encriptada
     */
    public static String encrypt(String word){
        return generateEncryptionOperation(word);
    }
    /**
     * Método que aplica a encriptação por operadores a uma determinada String, 
     * usando um certo operador e um certo valor de encriptação
     * <br>Caso os valores do operador não sejam iguais aos três possiveis, por 
     * default é usado o operador da multiplicação
     * <br><b><i>WARNING</i></b>: É recomendado que o valor da encriptação seja 
     * menor ou igual ao valor do operador a ser usado, caso contrário, o resultado 
     * da decriptação poderá não ser o esperador, especialmente quando usado o operador 
     * bitwise shift à esquerda
     * @param word String com a palavra a ser encriptada
     * @param operator Integer com o valor do operador a ser usado
     * @param value Integer com o value da encriptação a ser usado
     * @return String com a palavra encriptada
     */
    public static String encrypt(String word,int operator,int value){
        operation=operator;
        randomValue=value;
        return encrypt(word);
    }
    /**
     * Método que aplica a decriptação por operadores a uma determinada String
     * @param encryptedWord String com a palavra a ser descriptada
     * @return String com a palavra decriptada
     */
    public static String decrypt(String encryptedWord){
        return generateDecryptionOperation(encryptedWord);
    }
    /**
     * Encripta uma palavra usando a encriptação por operadores
     * @param word String com a palavra a ser encriptada
     * @return String com a palavra encriptada
     */
    private static String generateEncryptionOperation(String word){
        if(word==null||word.isEmpty())return word;
        if(operation==0)operation=VALORES_OPERADORES[new Random().nextInt(VALORES_OPERADORES.length)];
        if(randomValue==0)randomValue=new Random().nextInt(operation)+1;
        String encryptedString=""+operation;
        encryptedString+=ALFABETO_ENCRIPTACAO.charAt(new Random().nextInt(ALFABETO_ENCRIPTACAO.length()));
        encryptedString+=randomValue;
        for(int i=0;i<word.length();i++){
            encryptedString+=ALFABETO_ENCRIPTACAO.charAt(new Random().nextInt(ALFABETO_ENCRIPTACAO.length()));
            encryptedString+=encryptCharacter(operation,word.charAt(i),randomValue);
        }
        resetOperators();
        return encryptedString;
    }
    /**
     * Decripta palavra que foi previamente encriptada com a encriptação por operadores
     * @param word String com a palavra a ser decriptada
     * @return String com a palavra decriptada
     */
    private static String generateDecryptionOperation(String encryptedWord){
        if(encryptedWord==null||encryptedWord.isEmpty())return encryptedWord;
        String decryptedString="";
        String[] encryptedWordSplitted=encryptedWord.split(REGEX_ENCRIPTACAO);
        int operationX=Integer.parseInt(encryptedWordSplitted[0]);
        long value=Long.parseLong(encryptedWordSplitted[1]);
        for(int i=2;i<encryptedWordSplitted.length;i++){
            decryptedString+=decryptCharacter(operationX,encryptedWordSplitted[i],value);
        }
        return decryptedString;
    }
    /**
     * Método que encripta um certo carater dianta uma certa operação, com um 
     * certo valor
     * @param operation Integer com a operação a ser usada
     * @param character Character com o carater a ser encriptado
     * @param value Integer com o valor a ser usado na operação
     * @return String com o carater encriptado
     */
    private static String encryptCharacter(int operation,char character,long value){
        String encrypted="";
        switch(operation){
            case VALOR_ENCRIPTACAO_OPERADOR_BITWISE:
                return encrypted+=character<<value;
            case VALOR_ENCRIPTACAO_OPERADOR_ADICAO:
                return encrypted+=character+value;
            default:
                return encrypted+=character*value;
        }
    }
    /**
     * Método que decritpa um certo carater dianta uma certa operação, com um 
     * certo valor
     * @param operation Integer com a operação a ser usada
     * @param encryptedCharacter String com o carater encriptado
     * @param value Integer com o valor a ser usado na operação
     * @return Character com o carater decriptado
     */
    private static char decryptCharacter(int operation,String encryptedCharacter,long value){
        long encryptedValue=Long.parseLong(encryptedCharacter);
        switch(operation){
            case VALOR_ENCRIPTACAO_OPERADOR_BITWISE:
                return (char)(encryptedValue>>value);
            case VALOR_ENCRIPTACAO_OPERADOR_ADICAO:
                return (char)(encryptedValue-value);
            default:
                return (char)(encryptedValue/value);
        }
    }
    /**
     * Faz reset ao valor dos operadores
     */
    private static void resetOperators(){
        operation=0;
        randomValue=0;
    }
    /**
     * Esconde o construtor privado
     */
    private OperatorsEncryption(){}
}