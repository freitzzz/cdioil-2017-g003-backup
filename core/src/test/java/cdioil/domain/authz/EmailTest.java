package cdioil.domain.authz;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes unitários relativo a classe Email
 * @see cdioil.domain.authz.Email
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class EmailTest {
    
    public EmailTest(){}
    @Test
    /**
     * Teste unitário relativamente à construção de um Email
     */
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
     * Testes unitários relativamente à criacao dos emails de dominio Gmail
     * <br>Ver constante da regex de um email do dominio de Gmail para informações 
     * mais detalhadas
     */
    @Test
    public void testEmailGmail(){
        String emailComecarPonto=".aaaaaa@gmail.com";
        String emailAcabarPonto="aaaaa.@gmail.com";
        String emailDoisPontosConsecutivos="aa..aa@gmail.com";
        String emailCarateresEspeciais="aaaaa_a@gmail.com";
        String emailMenorSeisCarateres="aaaaa@gmail.com";
        String emailMaiorTrintaCarateres="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@gmail.com";
        String emailValido="a.a.a.b.d.r.ew.a.d.waaaa.9.d.9@gmail.com";
        testarEmail(emailComecarPonto,true,"O email é invalido pois começa com um ponto");
        testarEmail(emailAcabarPonto,true,"O email é invalido pois acaba com um ponto");
        testarEmail(emailDoisPontosConsecutivos,true,"O email é invalido pois tem dois pontos consecutivos");
        testarEmail(emailCarateresEspeciais,true,"O email é invalido pois tem carateres especiais");
        testarEmail(emailMenorSeisCarateres,true,"O email é invalido pois tem menos de seis carateres");
        testarEmail(emailMaiorTrintaCarateres,true,"O email é invalido pois tem mais de trinta carateres");
        testarEmail(emailValido,false,"O email é valido pois não infringe nenhuma regra");
    }
    /**
     * Testes unitários relativamente à criacao dos emails de dominio Hotmail/Outlook/Live
     * <br>Ver constante da regex de um email do dominio de Hotmail/Outlook/Live para informações 
     * mais detalhadas
     */
    @Test
    public void testEmailHotmail(){
        String emailComecarPonto=".aaaaaa@hotmail.com";
        String emailAcabarPonto="aaaaa.@hotmail.com";
        String emailDoisPontosConsecutivos="aa...aa@hotmail.com";
        String emailCarateresEspeciais="aaaaa*a@hotmail.com";
        String emailSemCarateres="@hotmail.com";
        String emailMaiorSessentaCarateres="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@hotmail.com";
        String emailComecarSemAlfabeto="9asd@hotmail.com";
        String emailValido="A_adddd.adsdsads-dd9a-@Hotmail.com";
        testarEmail(emailComecarPonto,true,"O email é invalido pois começa com um ponto");
        testarEmail(emailAcabarPonto,true,"O email é invalido pois acaba com um ponto");
        testarEmail(emailDoisPontosConsecutivos,true,"O email é invalido pois tem dois pontos consecutivos");
        testarEmail(emailCarateresEspeciais,true,"O email é invalido pois tem carateres especiais");
        testarEmail(emailSemCarateres,true,"O email é invalido pois não tem carateres");
        testarEmail(emailMaiorSessentaCarateres,true,"O email é invalido pois tem mais de sessenta carateres");
        testarEmail(emailComecarSemAlfabeto,true,"O email é invalido pois não começa com uma letra do alfabeto");
        testarEmail(emailValido,false,"O email é valido pois não infringe nenhuma regra");
    }
    /**
     * Testes unitários relativamente à criacao dos emails de dominio Yahoo/Rocketmail/Ymail
     * <br>Ver constante da regex de um email do dominio de Yahoo para informações 
     * mais detalhadas
     */
    @Test
    public void testEmailYahoo(){
        String emailComecarPonto=".aaaaaa@yahoo.com";
        String emailAcabarPonto="aaaaa.@yahoo.com";
        String emailDoisPontosConsecutivos="aa...aa@yahoo.com";
        String emailCarateresEspeciais="aaaaa*a@yahoo.com";
        String emailMenosQuatroCarateres="aaa@yahoo.com";
        String emailMaiorTrintaCarateres="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@yahoo.com";
        String emailComecarSemAlfabeto="9asd@yahoo.com";
        String emailValido="Aadddd.adsdsads333dd9a@yahoo.com";
        testarEmail(emailComecarPonto,true,"O email é invalido pois começa com um ponto");
        testarEmail(emailAcabarPonto,true,"O email é invalido pois acaba com um ponto");
        testarEmail(emailDoisPontosConsecutivos,true,"O email é invalido pois tem dois pontos consecutivos");
        testarEmail(emailCarateresEspeciais,true,"O email é invalido pois tem carateres especiais");
        testarEmail(emailMenosQuatroCarateres,true,"O email é invalido pois tem menos de quatro carateres");
        testarEmail(emailMaiorTrintaCarateres,true,"O email é invalido pois tem mais de trinta carateres");
        testarEmail(emailComecarSemAlfabeto,true,"O email é invalido pois não começa com uma letra do alfabeto");
        testarEmail(emailValido,false,"O email é valido pois não infringe nenhuma regra");
    }
    /**
     * Testes unitários relativamente à criacao dos emails de dominio geral
     * <br>Ver constante da regex de um email do dominio de geral para informações 
     * mais detalhadas
     */
    @Test
    public void testEmailGeral(){
        String emailComecarPonto=".aaaaaa@email.com";
        String emailAcabarPonto="aaaaa.@emailY.com";
        String emailDoisPontosConsecutivos="aa...aa@24d.asd2.com";
        String emailCarateresEspeciais="aaaaa«a@isep.com";
        String emailMenosSemCarateres="@email.com";
        String emailMaiorSessentaQuatraCarateres="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@as12rd.com";
        String emailDoisCarater="aa@l.com";
        String emailSessentaQuatroCarateres="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@vodafone.com";
        String emailValido="A_adddd.a#~~~~d!sd?sadsdd9a@email.com.pt.xk.abcdef";
        testarEmail(emailComecarPonto,true,"O email é invalido pois começa com um ponto");
        testarEmail(emailAcabarPonto,true,"O email é invalido pois acaba com um ponto");
        testarEmail(emailDoisPontosConsecutivos,true,"O email é invalido pois tem dois pontos consecutivos");
        testarEmail(emailCarateresEspeciais,true,"O email é invalido pois tem carateres especiais");
        testarEmail(emailMenosSemCarateres,true,"O email é invalido pois tem menos de quatro carateres");
        testarEmail(emailMaiorSessentaQuatraCarateres,true,"O email é invalido pois tem mais de trinta carateres");
        testarEmail(emailDoisCarater,false,"O email é válido pois tem no minimo dois carater");
        testarEmail(emailSessentaQuatroCarateres,false,"O email é válido pois tem no maximo sessenta e quatro carateres");
        testarEmail(emailValido,false,"O email é valido pois não infringe nenhuma regra");
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
        String expResult = email;
        assertEquals("A condição deve acertar porque o contéudo das Strings é o mesmo"
                ,emailX.toString(),expResult);
    }
    /**
     * Miscellaneous tests
     */
    @Test
    public void testMisc(){
        assertNotNull("Creation of the object shouldn't be null",new Email());
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
    /**
     * Método que testa a construção de um email
     * @param email String com o email a ser testado
     * @param falhar boolean true se o email é invalido, false se válido
     * @param mensagem String com a mensagem do teste
     */
    private void testarEmail(String email,boolean falhar,String mensagem){
        System.out.println(mensagem);
        System.out.print("Testando a criação do Email: "+email+"...\nÉ suposto falhar? ");
        if(falhar){
            System.out.println("Sim");
            assertNull("A condição deve falhar porque o email é invalido",createEmail(email));
            System.out.println("A validação do email falhou como esperado");
        }else{
            System.out.println("Não");
            assertNotNull(createEmail(email));
        }
        System.out.println("Teste validado com successo!");
    }
}
