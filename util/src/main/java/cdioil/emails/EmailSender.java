package cdioil.emails;

import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * EmailSender class that sends emails to a certain email
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class EmailSender {
    /**
     * Constant that represents the string representation for the UTF-8 charset
     */
    private static final String UTF8_CHARSET="UTF-8";
    /**
     * Constant that represents the string representation for the UTF-8 Base 64 encoding
     */
    private static final String UTF8_BASE_64_ENCODING="B";
    /**
     * String that represents the client email address that is going to send a certain email
     */
    private final String clientEmail;
    /**
     * Session with the current client sesson
     */
    private final Session clientSession;
    /**
     * Builds a new EmailSender
     * @param clientEmail String with the client email address
     * @param clientSession Session with the current client session
     */
    public EmailSender(String clientEmail,Session clientSession){
        this.clientEmail=clientEmail;
        this.clientSession=clientSession;
    }
    /**
     * Method that sends a certain email from the client email address to a certain 
     * client email address
     * <br>Uses HTML as content type
     * @param clientToSend String with the client email address that is going to 
     * receive the email
     * @param title String with the email title
     * @param message String with the email message
     * @return boolean true if the message was sent succesfuly, false if not
     */
    public boolean sendEmail(String clientToSend,String title,String message){
        try{
            Message messageToSent=new MimeMessage(clientSession);
            messageToSent.setFrom(new InternetAddress(clientEmail));
            messageToSent.addRecipient(Message.RecipientType.TO,new InternetAddress(clientToSend));
            messageToSent.setSubject(encodeText(title));
            messageToSent.setText(message);
            Transport.send(messageToSent);
            return true;
        }catch(MessagingException|UnsupportedEncodingException e){
            ExceptionLogger.logException(LoggerFileNames.UTIL_LOGGER_FILE_NAME,
                    Level.SEVERE, e.getMessage());
            return false;
        }
    }
    /**
     * Encodes a certain text with the UTF-8 encoding
     * @param text String with the text to be encrypted with the UTF-8 encoding
     * @return String with the text encrypted with the UTF-8 encoding
     * @throws UnsupportedEncodingException Doesn't actually throw
     */
    private String encodeText(String text) throws UnsupportedEncodingException{
        return MimeUtility.encodeText(text,UTF8_CHARSET,UTF8_BASE_64_ENCODING);
    }
}
