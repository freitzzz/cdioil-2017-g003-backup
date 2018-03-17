package cdioil.application.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Testes unitários relativamente à encriptação de operadores
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class OperatorsEncryptionTest {
    /**
     * Constante que representa o numero de Strings aleartorias a serem criadas 
     * de modo a testar a encriptação dos operadores
     */
    private static final int NUMERO_STRINGS_ALEARTORIAS=750;
    public OperatorsEncryptionTest() {
    }
    /**
     * Testa a encriptação e decriptação por operadores
     */
    @Test
    public void testOperatorsEncryption(){
        List<String> listRandomStrings=createListRandomStrings(NUMERO_STRINGS_ALEARTORIAS);
        List<String> listRandomEncryptedStrings=new ArrayList<>(NUMERO_STRINGS_ALEARTORIAS);
        List<String> listRandomDecryptedStrings=new ArrayList<>(NUMERO_STRINGS_ALEARTORIAS);
        for(int i=0;i<NUMERO_STRINGS_ALEARTORIAS;i++){
            listRandomEncryptedStrings.add(OperatorsEncryption.encrypt(listRandomStrings.get(i)));
            listRandomDecryptedStrings.add(OperatorsEncryption.decrypt(listRandomEncryptedStrings.get(i)));
        }
        assertEquals("A condição deve acertar pois as listas são iguais",listRandomStrings,listRandomDecryptedStrings);
    }
    
    /**
     * Cria uma lista de Strings aleartorias com o comprimento inferior a 15
     * @param numberOfStrings Integer com o numero de Strings aleartorias a serem criadas
     * @return List com as Strings aleartorias criadas
     */
    private List<String> createListRandomStrings(int numberOfStrings){
        List<String> listRandomStrings=new ArrayList<>(numberOfStrings);
        for(int i=0;i<numberOfStrings;i++){
            String randomString="";
            int randomValue=new Random().nextInt(15);
            for(int j=0;j<randomValue;j++)randomString+=(char)(new Random().nextInt('z'));
            listRandomStrings.add(randomString);
        }
        return listRandomStrings;
    }
    
}
