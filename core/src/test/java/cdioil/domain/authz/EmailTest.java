package cdioil.domain.authz;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes unitários relativo a classe Email
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class EmailTest {
    
    public EmailTest(){}
    @Test
    public void testConstrucao(){
        assertNull("A condição deve acertar pois os argumentos são inválidos"
                ,createEmail(null));
        assertNull("A condição deve acertar pois os argumentos são inválidos"
                ,createEmail(""));
        assertNull("A condição deve acertar pois os argumentos são inválidos"
                ,createEmail("214124"));
        assertNull("A condição deve acertar pois os argumentos são inválidos"
                ,createEmail(".@email.com"));
        assertNotNull("A condição deve acertar pois os argumentos são válidos"
                ,createEmail("lil.pump@gmail.com"));
        assertNotNull("A condição deve acertar pois os argumentos são válidos"
                ,createEmail("drake@xxxtentacion.com"));
        assertNotNull("A condição deve acertar pois os argumentos são válidos"
                ,createEmail("1010101@isep.ipp.pt"));
        List<String> gmailValidos=creatValidGmailAddresses();
        List<String> gmailInvalidos=createInvalidGmailAddresses();
        gmailValidos.forEach((t) -> {assertNotNull("A condição deve acertar pois "
                + "os argumentos são válidos",createEmail(t));});
        gmailInvalidos.forEach((t) -> {assertNull("A condição deve acertar pois "
                + "os argumentos são inválidos",createEmail(t));});
    }
    /**
     * Test of equals method, of class Email.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Email emailX=new Email("1111@isep.ipp.pt");
        Email emailY=new Email("1111@isep.ipp.pt");
        Email emailZ=new Email("11111@isep.ipp.pt");
        assertEquals("A condição deve acertar porque os Email a comparar sao a mesma instância"
                ,emailX,emailX);
        assertNotEquals("A condição deve falhar porque o Email a comparar é null",emailX,null);
        assertNotEquals("A condição deve falhar porque está se a comparar um Email a uma String"
                ,emailX,"");
        assertEquals("A condição deve acertar porque os Email a comparar sao iguais"
                ,emailX,emailY);
        assertNotEquals("A condição deve falhar porque os Email sao diferentes",emailX,emailZ);
    }

    /**
     * Test of hashCode method, of class Email.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Email instance = new Email("1111@isep.ipp.pt");
        int expResult = 705951839;
        int result = instance.hashCode();
        assertEquals("A condição deve acertar pois os valores são iguais",expResult,result);
    }

    /**
     * Test of toString method, of class Email.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String email="1111@isep.ipp.pt";
        Email emailX = new Email(email);
        String expResult = "Email: "+email;
        assertEquals("A condição deve acertar porque o contéudo das Strings é o mesmo"
                ,emailX.toString(),expResult);
    }
    /**
     * Cria e devolve uma lista com endereços de email válidos do dominio Gmail
     * @return List com uma lista de endereços de email válidos do dominio Gmail
     */
    private List<String> creatValidGmailAddresses(){
        List<String> validGmail=new ArrayList<>();
        validGmail.add("pump.smartest.guy@gmail.com");
        validGmail.add("a.d.3.5.f.3.1.r.6@gmail.com");
        validGmail.add("drake.stole.x.flow@googlemail.com");
        validGmail.add("a.really.long.username.ok@gmail.com");
        validGmail.add("wooooooooooooooooooooooooooo.w@gmail.com");
        return validGmail;
    }
    /**
     * Cria e devolve uma lista com endereços de email invalidos do dominio Gmail
     * @return List com uma lista de endereços de email invalidos do dominio Gmail
     */
    private List<String> createInvalidGmailAddresses(){
        List<String> invalidGmail=new ArrayList<>();
        invalidGmail.add(".pump.not.so.smartest.guy@gmail.com");
        invalidGmail.add("a.d..3.5.f.3.1.r.6@gmail.com");
        invalidGmail.add("drake_stole.x.flow@googlemail.com");
        invalidGmail.add("a.really.really.re.username.ok2@gmail.com");
        return invalidGmail;
    }
    /**
     * Cria um novo objecto Email com um determinado email
     * @param email String com o email
     * @return Email com um determinado email ou null caso tenha ocurrido alguma excecão
     */
    private Email createEmail(String email){try{return new Email(email);}catch(IllegalArgumentException e){return null;}}
    
}
