package cdioil.application.authz;

import cdioil.application.utils.EmailSenders;
import cdioil.domain.authz.SystemUser;
import cdioil.emails.EmailSenderFactory;
import cdioil.persistence.impl.EmailSendersRepositoryImpl;

/**
 * Service class that sends the account activation code to the SystemUser email
 * <br>TO-DO: DTO's ??
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class EmailSenderService {
    /**
     * Constant that represents the email activation title which is going to be sent to a 
     * certain user email
     */
    private static final String EMAIL_ACTIVATION_CODE_TITLE="Código de Ativação";
    /**
     * Constant that represents the email activation content which is going to be sent to a 
     * certain user email
     */
    private static final String FORMATED_EMAIL_ACTIVATION_CODE_CONTENT="Olá %s!"
                + "\nReparamos que acabaste de te registar na nossa aplicação com o endereço %s."
                + "\nDe modo a provarmos a tua autenticidade, pedimos que insiras o seguinte código %s "
                + "aquando o inicio da aplicação.\n\nIsto é uma mensagem automática, por favor não responda para este endereço!";
    /**
     * SystemUser with the current system user that is going to be sent the email
     */
    private final SystemUser currentSystemUser;
    /**
     * Builds a new EmailSenderService with the System User which email is going to 
     * be sent to
     * @param currentSystemUser SystemUser with the system user which email is going 
     * to be sent to
     */
    public EmailSenderService(SystemUser currentSystemUser){this.currentSystemUser=currentSystemUser;}
    /**
     * Method that sends the activation code of the current System User to the respective 
     * user email account
     * @return boolean true if the email with the activation code was sent succesfully 
     * false if not
     */
    public boolean sendActivationCode(){
        EmailSenders sender=new EmailSendersRepositoryImpl().getRandomEmailSender();
        if(sender==null)return false;
        String toEmail=currentSystemUser.getID().toString();
        String content=String.format(FORMATED_EMAIL_ACTIVATION_CODE_CONTENT
                ,currentSystemUser.getName().toString(),toEmail,currentSystemUser.getActivationCode());
        return EmailSenderFactory
                .create(sender.getEmail(),sender.getPassword())
                .sendEmail(toEmail,EMAIL_ACTIVATION_CODE_TITLE,content);
    }
}
