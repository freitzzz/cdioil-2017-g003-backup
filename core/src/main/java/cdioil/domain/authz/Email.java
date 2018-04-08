package cdioil.domain.authz;

import cdioil.application.utils.OperatorsEncryption;
import cdioil.domain.ValueObject;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.persistence.Embeddable;

/**
 * Class that represents an email address
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
@Embeddable
public class Email implements Serializable,ValueObject {
    /**
     * Serializable ID
     */
    private static final long serialVersionUID=5l;
    /**
     * Constant that represents the message of an invalid email
     */
    private static final String INVALID_EMAIL_MESSAGE="O email é invalido!";
    /**
     * Constant that represents the regular expression for the general email address
     * <br>Regular expression that expresses the rules of RFC 5322 and 5321
     * <br><a href="https://en.wikipedia.org/wiki/Email_address">Email Address</a>
     * <br><a href="http://rumkin.com/software/email/rules.php">Email Validation Rules</a>
     * <br>An email of the general domain must match the following rules in order to be valid
     * <br>- It needs to have a minimum of 2 to 64 characters on the local part of the email address
     * <br>- It can only have alpha-numerics, the special characters [!#$%&'*+/=?^_`{|}~] and the character . that can establish connection between the alpha-numerics and special characters
     * <br>- It cannot start or end with an dot on the local part and domain
     * <br>- It cannot have two preeceding dots
     * <br>- The domain part can have a subdomain and it needs to end with at least one dot proceeded by two to six charactersinimo entre 2 a 64 carateres na local part do email
     */
    private static final String REGEX_GENERAL="^(([A-Za-z0-9!#$%&'*+/=?^_`{|}~]([A-Za-z0-9!#$%&'*+/=?^_`{|}~]|[.](?![.])){0,62}[A-Za-z0-9!#$%&'*+/=?^_`{|}~])|(\"([A-Za-z0-9!#$%&'*+/=?^_`{|}~]|[.](?![.])){0,61}\"))@[A-Za-z0-9](([A-Za-z0-9]|[.](?![.]))){0,63}[.][A-Za-z]{2,6}$";
    /**
     * Constant that represents the regular expression for the Gmail email address
     * <br>An email of the Gmail domain must match the following rules in order to be valid
     * <br>- It needs to have a minium of 6 to 30 characters on the local part of the email address
     * <br>- It can only have alpha-numerics and the character . that can establish connection between the alpha-numerics
     * <br>- It cannot start or end with an dot on the local part and domain
     * <br>- It cannot have two preeceding dots
     * <br>- The domain part must end with googlemail.com or gmail.com
     */
    private static final String REGEX_GMAIL="^([A-Za-z0-9]([A-Za-z0-9]|[.](?![.])){4,28}[A-Za-z0-9])@g(oogle)?mail.com$";
    /**
     * Constant that represents the regular expression for the Hotmail/Outlook/Live email address
     * <br>An email of the Hotmail/Outlook/Live domain must match the following rules in order to be valid
     * <br>- It needs to have a minium of 2 to 60 characters on the local part of the email address
     * <br>- It can only have alpha-numerics, the character (_) and the character . that can establish connection between the alpha-numerics and the character (_)
     * <br>- It needs to start with an letter of the alphabet
     * <br>- It cannot start or end with an dot on the local part and domain
     * <br>- It cannot have two preeceding dots
     * <br>- The domain part must end with hotmail/outlook/live proceeded of the identifier (eg: .pt/.com/.uk)
     */
    private static final String REGEX_HOTMAIL="^([A-Za-z]{1,59}[A-Za-z0-9_])|([A-Za-z](([A-Za-z0-9-_]|[.](?![.]))){0,58}[A-Za-z0-9-_])@(((live|outlook|hotmail).com)|(live.com|outlook).[A-Za-z]{2})$";
    /**
     * Constant that represents the regular expression for the Yahoo email address
     * <br>An email of the Yahoo domain must match the following rules in order to be valid
     * <br>- It needs to have a minium of 4 to 30 characters on the local part of the email address
     * <br>- It can only have alpha-numerics and the character . that can establish connection between the alpha-numerics
     * <br>- It needs to start with an letter of the alphabet
     * <br>- It cannot start or end with an dot on the local part and domain
     * <br>- It cannot have two preeceding dots
     * <br>- The domain part must end with yahoo/rocketmail/ymail proceeded with the identifiers (com/in/co.uk)
     */
    private static final String REGEX_YAHOO="^[A-Za-z]([A-Za-z0-9]|[.](?![.])){2,28}[A-Za-z0-9]@(yahoo|ymail|rocketmail)[.](com|in|co[.]uk)$";
    /**
     * Constant that represents the regular expression of the domain part of the general email address
     */
    private static final String REGEX_DOMINIO_GERAL="$[A-Za-z0-9](([A-Za-z0-9]|[.](?![.]))){0,63}[.][A-Za-z]{2,6}";
    /**
     * Constant that represents the regular expression of the domain part of the Gmail email address
     */
    private static final String REGEX_DOMINIO_GMAIL="g(oogle)?mail.com$";
    /**
     * Constant that represents the regular expression of the domain part of the Hotmail/Outlook/Live email address
     */
    private static final String REGEX_DOMINIO_HOTMAIL="(((live|outlook|hotmail).com)|(live.com|outlook).[A-Za-z]{2})$";
    /**
     * Constant that represents the regular expression of the domain part of the Yahoo email address
     */
    private static final String REGEX_DOMONIO_YAHOO="(yahoo|ymail|rocketmail)[.](com|in|co[.]uk)$";
    /**
     * String that represents the identifier of an email
     */
    private String email;
    /**
     * Constant that represents the encryption value being used
     */
    public static final int ENCRYPTION_VALUE=0xAC67F;
    /**
     * Constant that represents the encryption codes being used
     */
    public static final int ENCRYPTION_CODE=OperatorsEncryption.ADDITION_ENCRYPTION_CODE;
    /**
     * Builds a new instance of email
     * @param email String with the email address
     */
    public Email(String email){
        validateEmail(email);
        this.email=encryptEmail(email.toLowerCase());
    }
    /**
     * Method that verifies if two Emails are equal
     * @param obj Email with the email to be compared with the actual Email
     * @return boolean true if both emails are equal, false if not
     */
    @Override
    public boolean equals(Object obj){
        if(this==obj)return true;
        if(obj==null||this.getClass()!=obj.getClass())return false;
        return email.equalsIgnoreCase(((Email)obj).email);
    }
    /**
     * Hashcode of Email
     * @return Integer with the current hashcode of the email address
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(email);
        return hash;
    }
    /**
     * Methods that represents the textual representation of an Email
     * @return String with the textual information of the current email
     */
    @Override
    public String toString(){return decryptEmail();}
    /**
     * Method that validates an email address
     * @param email String with the email address to validated
     * <br><b>An exception may be thrown if the email address is invalid</b>
     */
    private void validateEmail(String email){
        if(email==null||email.isEmpty())throw new IllegalArgumentException(INVALID_EMAIL_MESSAGE);
        String regexEmail=REGEX_GENERAL;
        if(checkDomain(email,REGEX_DOMINIO_GMAIL)){
            regexEmail=REGEX_GMAIL;
        }else if(checkDomain(email,REGEX_DOMINIO_HOTMAIL)){
            regexEmail=REGEX_HOTMAIL;
        }else if(checkDomain(email,REGEX_DOMONIO_YAHOO)){
            regexEmail=REGEX_YAHOO;
        }
        if(!checkEmail(email,regexEmail))throw new IllegalArgumentException(INVALID_EMAIL_MESSAGE);
    }
    /**
     * Method that verifies if an email belongs to a certain domain
     * @param email String with the email to be verified
     * @param regexDomain String with the domain regular expression to be applied
     * @return boolean true if the email belongs to the domain, false if not
     */
    private boolean checkDomain(String email,String regexDomain){
        return Pattern.compile(regexDomain,Pattern.CASE_INSENSITIVE).matcher(email).find();
    }
    /**
     * Method that verifies if an email address is valid
     * @param email String with the email to be verified
     * @param regexEmail String with the regular expression of the email address to be applied
     * @return boolean true if the email is valid, false if not
     */
    private boolean checkEmail(String email,String regexEmail){
        return Pattern.compile(regexEmail,Pattern.CASE_INSENSITIVE).matcher(email).matches();
    }
    /**
     * Method that encrypts a certain email
     * @param email String with the email being encrypted
     * @return String with the email being encrypted
     */
    private String encryptEmail(String email){
        return OperatorsEncryption.encrypt(email,ENCRYPTION_CODE,ENCRYPTION_VALUE);
    }
    /**
     * Method that decrypts the current email
     * @return String with the current email decrypted
     */
    private String decryptEmail(){
        return OperatorsEncryption.decrypt(this.email);
    }
    /**
     * Protected constructor in order to persist with JPA
     */
    protected Email(){}
}
