package cdioil.emails;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Authenticator class that is used to authenticate with SMTP
 * <br>Used to Authenticate with JavaMail
 * <br>Extends <b>Authenticator</b>
 * @see javax.mail.Authenticator
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class SMTPAuthenticator extends Authenticator{
    /**
     * Current Authentication used
     */
    private final PasswordAuthentication auth;
    /**
     * Builds a new SMTPAuthenticator with the username and password used to 
     * authenticate with SMTP
     * @param username String with the username used to authenticate with SMTP
     * @param password String with the password used to authenticate with SMTP
     */
    public SMTPAuthenticator(String username,String password){
        auth=new PasswordAuthentication(username,password);
    }
    /**
     * Returns the current Authentication
     * @return PasswordAuthentication with the current authentication
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication(){
        return auth;
    }
    
}
