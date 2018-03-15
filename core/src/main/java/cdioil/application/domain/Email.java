package cdioil.application.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.persistence.Embeddable;
import javax.persistence.Id;

/**
 * Classe que representa um endereço de email
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
@Embeddable
public class Email implements Serializable {
    /**
     * Código de serialização
     */
    private static final long serialVersionUID=5l;
    /**
     * Constante que representa a mensagem que caracteriza um email inválido
     */
    private static final String MENSAGEM_EMAIL_INVALIDO="O email é invalido!";
    /**
     * Constante que representa a expressão regular para um email geral
     * <br>Expressão regular realizada conforme as regras dos RFC 5322 e 5321 respetivamente
     * <br><a href="https://en.wikipedia.org/wiki/Email_address">Email Address</a>
     * <br><a href="http://rumkin.com/software/email/rules.php">Email Validation Rules</a>
     */
    private static final String REGEX_GERAL="^(([A-Za-z0-9!#$%&'*+/=?^_`{|}~]([A-Za-z0-9!#$%&'*+/=?^_`{|}~]|[.](?![.])){0,62}?[A-Za-z0-9!#$%&'*+/=?^_`{|}~])|(\"([A-Za-z0-9!#$%&'*+/=?^_`{|}~]|[.](?![.])){0,61}\"))@[A-Za-z0-9](([A-Za-z0-9]|[.](?![.]))){0,63}[.][A-Za-z]{2,6}$";
    /**
     * Constante que representa a expressão regular para um email do domino Gmail
     */
    private static final String REGEX_GMAIL="^([A-Za-z0-9]([A-Za-z0-9]|[.](?![.])){4,28}[a-z0-9])@g(oogle)?mail.com$";
    /**
     * Constante que representa a expressão regular para um email do domino Outlook
     */
    private static final String REGEX_HOTMAIL="^[A-Za-z]?([A-Za-z0-9-_]|[.](?![.])){0,58}[A-Za-z0-9-_]@(((live|outlook|hotmail).com)|(live.com|outlook).[a-z]{2})$";
    /**
     * Constante que representa a expressão regular para um email do dominio Yahoo
     */
    private static final String REGEX_YAHOO="^[A-Za-z]([A-Z0-9]|[.](?![.])){2,28}[A-Za-z0-9]@(yahoo|ymail|rocketmail)[.][com|in|co[.]uk]$";
    /**
     * Constante que representa a expressão regular para um email com dominio geral
     */
    private static final String REGEX_DOMINIO_GERAL="$[A-Za-z0-9](([A-Za-z0-9]|[.](?![.]))){0,63}[.][A-Za-z]{2,6}";
    /**
     * Constante que representa a expressão regular que identifica o dominio de um endereço de Gmail
     */
    private static final String REGEX_DOMINIO_GMAIL="g(oogle)?mail.com$";
    /**
     * Constante que representa a expressão regular que identifica o dominio de um endereço de hotmail/outlook/live
     */
    private static final String REGEX_DOMINIO_HOTMAIL="(((live|outlook|hotmail).com)|(live.com|outlook).[a-z]{2})$";
    /**
     * Constante que representa a expressão regular que identifica o dominio de um endereço de Yahoo
     */
    private static final String REGEX_DOMONIO_YAHOO="(yahoo|ymail|rocketmail)[.][com|in|co[.]uk]$";
    /**
     * Long com o ID que representa a identidade da tabela na base de dados
     */
    @Id
    private long idEmail;
    /**
     * String que representa a indentificação do email
     */
    private String email;
    /**
     * Constrói uma nova instância de Email
     * @param email String com o email
     */
    public Email(String email){
        validaEmail(email);
        this.email=email;
    }
    /**
     * Método que verifica se dois Emails são iguais
     * @param obj Email com o email a ser comparado com o Email atual
     * @return boolean true se os emails são iguais, false caso contrário
     */
    @Override
    public boolean equals(Object obj){
        if(this==obj)return true;
        if(obj==null||this.getClass()!=obj.getClass())return false;
        return email.equalsIgnoreCase(((Email)obj).email);
    }
    /**
     * Hashcode do Email
     * @return Integer com o hashcode do email atual
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.email);
        return hash;
    }
    /**
     * Método que representa a informação textual de um Email
     * @return String com a informação textual do Email atual
     */
    @Override
    public String toString(){return "Email: "+email;}
    /**
     * Método que valida se um email é válido ou não
     * @param email String com o email a ser validado
     * <br>Como o método é apenas usado para validar o email na construção do 
     * objecto, caso este seja inválido uma exceção é lançada
     */
    private void validaEmail(String email){
        if(email==null||email.isEmpty())throw new IllegalArgumentException(MENSAGEM_EMAIL_INVALIDO);
        String regexEmail=REGEX_GERAL;
        if(checkDomain(email,REGEX_DOMINIO_GMAIL))regexEmail=REGEX_GMAIL;
        if(checkDomain(email,REGEX_DOMINIO_HOTMAIL))regexEmail=REGEX_HOTMAIL;
        if(checkDomain(email,REGEX_DOMONIO_YAHOO))regexEmail=REGEX_YAHOO;
        if(!checkEmail(email,regexEmail))throw new IllegalArgumentException(MENSAGEM_EMAIL_INVALIDO);
    }
    /**
     * Método que verifica se um certo email pertence a um certo dominio
     * @param email String com o email a ser verificado
     * @param regexDomain String com a expressão regular a ser aplicada
     * @return boolean true se o email pertence ao dominio a ser verificado, false caso contrario
     */
    private boolean checkDomain(String email,String regexDomain){
        return Pattern.compile(regexDomain,Pattern.CASE_INSENSITIVE).matcher(email).find();
    }
    /**
     * Método que verifica se um certo email é valido
     * @param email String com o email a ser verificado
     * @param regexEmail String com a expressão regular a ser aplicada na verificao do email
     * @return boolean true se o email for valido, false caso contrario
     */
    private boolean checkEmail(String email,String regexEmail){
        return Pattern.compile(regexEmail,Pattern.CASE_INSENSITIVE).matcher(email).matches();
    }
    /**
     * Construtor protegido de modo a permitir a persistencia com o JPA
     */
    protected Email(){}
}
