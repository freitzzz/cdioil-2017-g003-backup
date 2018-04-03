package cdioil.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes unitários relativo a classe Preco
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class PrecoTest {
    /**
     * Constante com um array englobando os preços válidos usados nos testes
     */
    private static final String[] PRECOS_VALIDOS={"20 EUR","30 €","55 €","45EUR"
            ,"65 GBP","265 £","665 $","535 USD","665 $","535 USD","555 gBp","USD 33"
            ,"90 €","5000 GBP","5.5 €","50.5 £","2 EUR"};
    /**
     * Constante com um array englobando os preços válidos usados nos testes
     */
    private static final String[] PRECOS_INVALIDOS={null,"","   ","sadads","5000"
            ,"50.50.50 €","Pump"};
    public PrecoTest(){}
    
    
    /**
     * Testes relativos à construção do objecto Preco
     */
    @Test
    public void testConstrucao(){
        for(int i=0;i<PRECOS_VALIDOS.length;i++){
            assertNotNull("A construção do objecto não deve lançar uma exceção pois "
                    + "os seus argumentos são válidos",createPreco(PRECOS_VALIDOS[i]));}
        for(int i=0;i<PRECOS_INVALIDOS.length;i++)
        assertNull("A construção do objecto deve lançar uma exceção pois os seus "
                + "argumentos são inválidos",createPreco(PRECOS_INVALIDOS[i]));
    }
    /**
     * Test of equals method, of class Preco.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Preco precoX = new Preco("50 €");
        Preco precoY=new Preco("50 EUR");
        Preco precoZ=new Preco("50 $");
        assertEquals("A condição deve acertar porque os Preco a comparar sao a mesma instância"
                ,precoX,precoX);
        assertNotEquals("A condição deve falhar porque o Preco a comparar é null",precoX,null);
        assertNotEquals("A condição deve falhar porque está se a comparar um Preco a uma String"
                ,precoX,"");
        assertEquals("A condição deve acertar porque os Preco a comparar sao iguais"
                ,precoX,precoY);
        assertNotEquals("A condição deve falhar porque os Preco sao diferentes",precoX,precoZ);
    }

    /**
     * Test of hashCode method, of class Preco.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Preco instance = new Preco("50 €");
        int expResult = 1643727029;
        int result = instance.hashCode();
        assertEquals("A condição deve acertar pois os valores são iguais",expResult,result);
    }

    /**
     * Test of toString method, of class Preco.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Preco instanceX = new Preco("50 €");
        Preco instanceY=new Preco("50 EUR");
        String expResult = "50.0 EUR";
        assertEquals("A condição deve acertar porque o contéudo das Strings é o mesmo"
                ,instanceX.toString(),expResult);
        assertEquals("A condição deve acertar porque o contéudo das Strings é o mesmo"
                ,instanceY.toString(),expResult);
    }
    /**
     * Miscellaneous tests
     */
    @Test
    public void testMisc(){
        assertNotNull("Creation of the object shouldn't be null",new Preco());
    }
    /**
     * Cria um novo objecto Preco com um determinado preço
     * @param preco String com o preço
     * @return Preco com um determinado preço ou null caso tenha ocurrido alguma excecão
     */
    private Preco createPreco(String preco){try{return new Preco(preco);}catch(IllegalArgumentException e){return null;}}
    
}
