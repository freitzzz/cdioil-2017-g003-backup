package cdioil.emails;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * EmailSender class that sends emails to a certain email
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class EmailSender {
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
            messageToSent.setSubject(title);
            messageToSent.setText(message);
            Transport.send(messageToSent);
            return true;
        }catch(MessagingException e){
            return false;
        }
    }
}
