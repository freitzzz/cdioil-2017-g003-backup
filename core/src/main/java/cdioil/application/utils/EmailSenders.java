package cdioil.application.utils;

import cdioil.domain.authz.Email;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * EmailSenders class that represents all email addresses used to send 
 * information to users email addresses
 * <br>Does not belong on the bussiness model
 * <br>Entity that aggregates all email addresses used & respective passwords
 * <br>Contains getters for now due to encryption but will be replaced by a DTO 
 * in the future ou some kind of helper that decrypts outsided the class
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
@Entity(name = "Mood")
public class EmailSenders implements Serializable {
    /**
     * Constant that represents the encryption loop times (number of times that the 
     * password is encrypted over & over)
     */
    private static final short ENCRYPTION_LOOP_TIMES=2;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**
     * Email with the email sender email address
     */
    @Embedded
    @Column(name = "Happy")
    private Email senderEmail;
    /**
     * String with the email sender email address password
     */
    @Column(name = "Sad")
    private String senderPassword;
    /**
     * Builds a new EmailSenders with the emailaddress and password of the sender
     * @param email String with the emailaddress of the sender
     * @param password String with the password of the sender email address
     */
    public EmailSenders(String email,String password){
        this.senderEmail=new Email(email);
        this.senderPassword=prepareEncryption(password);
    }
    /**
     * Protected constructor in order to allow JPA persistence
     */
    protected EmailSenders(){}
    /**
     * Returns the sender email
     * @return String with the sender email
     */
    public String getEmail(){return senderEmail.toString();}
    /**
     * Returns the sender password
     * @return String with the sender password
     */
    public String getPassword(){return prepareDecryption(senderPassword);}
    /**
     * Prepares the email sender password encrpytion
     * @param senderPassword String with sender password
     * @return String with the encrypted sender password
     */
    private String prepareEncryption(String senderPassword){
        String encryptedSenderPassword=senderPassword;
        for(int i=0;i<ENCRYPTION_LOOP_TIMES;i++){
            encryptedSenderPassword=encryptSenderPassword(encryptedSenderPassword);
        }
        return encryptedSenderPassword;
    }
    /**
     * Prepares the email sender password encrpytion
     * @param encryptedSenderPassword String with encrypted sender password
     * @return String with the sender password
     */
    private String prepareDecryption(String encryptedSenderPassword){
        String decryptedSenderPassword=encryptedSenderPassword;
        for(int i=0;i<ENCRYPTION_LOOP_TIMES;i++){
            decryptedSenderPassword=decryptSenderPassword(decryptedSenderPassword);
        }
        return decryptedSenderPassword;
    }
    /**
     * Method that encrypts the sender password
     * @param senderPassword String with sender password being encrypted
     * @return String with the encrypted sender password
     */
    private String encryptSenderPassword(String senderPassword){
        return OperatorsEncryption.encrypt(senderPassword);
    }
    /**
     * Method that decrpyts the sender password
     * @param senderPassword String with encrypted sender password
     * @return String with the decrypted sender password
     */
    private String decryptSenderPassword(String senderPassword){
        return OperatorsEncryption.decrypt(senderPassword);
    }
}
