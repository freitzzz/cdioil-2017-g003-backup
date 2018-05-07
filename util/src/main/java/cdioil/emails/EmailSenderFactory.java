package cdioil.emails;

import java.util.Properties;
import java.util.regex.Pattern;
import javax.mail.Session;

/**
 * Factory of EmailSender
 * <br><b>TO-DO</b>:
 * <br>- Rename class to Helper/Builder since it's not really a factory
 * <br>- Move regular expressions used to identify the domain to a Interface that 
 * represents the most common regular expressions used to identify email addresses
 * <br>- Analyse better the createSession method
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class EmailSenderFactory {
    /**
     * Constant that represents the SMTP propertie key for the SMTP host identifier
     */
    private static final String EMAIL_SMTP_HOST="mail.smtp.host";
    /**
     * Constant that represents the SMTP propertie key for the SMTP port identifier
     */
    private static final String EMAIL_SMTP_PORT="mail.smtp.port";
    /**
     * Constant that represents the SMTP propertie key for the SMTP auth identifier
     */
    private static final String EMAIL_SMTP_AUTH="mail.smtp.auth";
    /**
     * Constant that represents the SMTP propertie key for the SMTP TLS enable identifier
     */
    private static final String EMAIL_SMTP_TLS_ENABLE="mail.smtp.starttls.enable";
    /**
     * Constant that represents the SMTP propertie key for the SMTP TLS trust identifier
     * <br>Used due to Client Authentication error
     * <br><a href="https://stackoverflow.com/questions/16115453/javamail-could-not-convert
     * -socket-to-tls-gmail">Reference</a>
     */
    private static final String EMAIL_SMTP_TLS_TRUST="mail.smtp.ssl.trust";
    /**
     * Constant that represents the regular expression used to identify emails 
     * that are going to be sent from the Gmail host
     * <br>In the future to be deprecated
     */
    private static final String REGEX_DOMAIN_GMAIL="g(oogle)?mail.com$";
    /**
     * Constant that represents the regular expression used to identify emails 
     * that are going to be sent from the Hotmail/Outlook/Live host
     * <br>In the future to be deprecated
     */
    private static final String REGEX_DOMAIN_HOTMAIL="(((live|outlook|hotmail).com)|(live.com|outlook).[A-Za-z]{2})$";
    /**
     * Constant that represents the regular expression used to identify emails 
     * that are going to be sent from the Yahoo host
     * <br>In the future to be deprecated
     */
    private static final String REGEX_DOMAIN_YAHOO="(yahoo|ymail|rocketmail)[.](com|in|co[.]uk)$";
    /**
     * Constant that represents the SMTP from the Gmail host
     */
    private static final String GMAIL_SMTP_HOST="smtp.gmail.com";
    /**
     * Constant that represents the SMTP from the Outlook host
     */
    private static final String LIVE_SMTP_HOST="smtp.live.com";
    /**
     * Constant that represents the SMTP from the Yahoo host
     */
    private static final String YAHOO_SMTP_HOST="smtp.mail.yahoo.com";
    /**
     * Constant that represents the standard SMTP port used to communicate with email servers
     */
    private static final String STANDARD_SMTP_PORT="587";
    /**
     * Hides default consructor
     */
    private EmailSenderFactory(){}
    /**
     * Method that creates a new EmailSender based on the email client
     * @param clientEmail String with the client email that is going to send the email
     * @param clientEmailPassword String with the client email password that is going to send 
     * the email
     * @return EmailSender with the EmailSender based on the client host protocols
     */
    public static EmailSender create(String clientEmail,String clientEmailPassword){
        if(Pattern.compile(REGEX_DOMAIN_GMAIL).matcher(clientEmail).find()){
            return new EmailSender(clientEmail,createSessionForGmail(clientEmail,clientEmailPassword));
        }else if(Pattern.compile(REGEX_DOMAIN_HOTMAIL).matcher(clientEmail).find()){
            return new EmailSender(clientEmail,createSessionForLive(clientEmail,clientEmailPassword));
        }else if(Pattern.compile(REGEX_DOMAIN_YAHOO).matcher(clientEmail).find()){
            return new EmailSender(clientEmail,createSessionForYahoo(clientEmail,clientEmailPassword));
        }
        return null;
    }
    /**
     * Method that creates a session used for SMTP Gmail client
     * @param emailClient String with the email client
     * @param emailPassword String with the email password
     * @return Session with the session used for SMTP Gmail client
     */
    private static Session createSessionForGmail(String emailClient,String emailPassword){
        Properties properties=new Properties();
        properties.setProperty(EMAIL_SMTP_HOST,GMAIL_SMTP_HOST);
        properties.put(EMAIL_SMTP_PORT,STANDARD_SMTP_PORT);
        properties.put(EMAIL_SMTP_AUTH,true);
        properties.put(EMAIL_SMTP_TLS_ENABLE,true);
        properties.put(EMAIL_SMTP_TLS_TRUST,GMAIL_SMTP_HOST);
        return Session.getDefaultInstance(properties,new SMTPAuthenticator(emailClient,emailPassword));
    }
    /**
     * Method that creates a session used for SMTP Live client
     * @param emailClient String with the email client
     * @param emailPassword String with the email password
     * @return Session with the session used for SMTP Outlook client
     */
    private static Session createSessionForLive(String emailClient,String emailPassword){
        Properties properties=new Properties();
        properties.setProperty(EMAIL_SMTP_HOST,LIVE_SMTP_HOST);
        properties.put(EMAIL_SMTP_PORT,STANDARD_SMTP_PORT);
        properties.put(EMAIL_SMTP_AUTH,true);
        properties.put(EMAIL_SMTP_TLS_ENABLE,true);
        properties.put(EMAIL_SMTP_TLS_TRUST,LIVE_SMTP_HOST);
        return Session.getDefaultInstance(properties,new SMTPAuthenticator(emailClient,emailPassword));
    }
    /**
     * Method that creates a session used for SMTP Live client
     * @param emailClient String with the email client
     * @param emailPassword String with the email password
     * @return Session with the session used for SMTP Outlook client
     */
    private static Session createSessionForYahoo(String emailClient,String emailPassword){
        Properties properties=new Properties();
        properties.setProperty(EMAIL_SMTP_HOST,YAHOO_SMTP_HOST);
        properties.put(EMAIL_SMTP_PORT,STANDARD_SMTP_PORT);
        properties.put(EMAIL_SMTP_AUTH,true);
        properties.put(EMAIL_SMTP_TLS_ENABLE,true);
        properties.put(EMAIL_SMTP_TLS_TRUST,YAHOO_SMTP_HOST);
        return Session.getDefaultInstance(properties,new SMTPAuthenticator(emailClient,emailPassword));
    }
}
