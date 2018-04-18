package cdioil.application.authz;

import cdioil.domain.authz.SystemUser;
import cdioil.emails.EmailSenderFactory;

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
    private static final String EMAIL_ACTIVATION_CODE_TITLE="Código de Activação";
    /**
     * Constant that represents the email activation content which is going to be sent to a 
     * certain user email
     */
    private static final String FORMATED_EMAIL_ACTIVATION_CODE_CONTENT="Olá %s!"
                + "\nReparamos que acabaste de te registar na nossa aplicação com o endereço %s."
                + "\nDe modo a provar-mos a tua autenticidade, pedimos que insiras o seguinte código %d"
                + "aquando o inicio da aplicação.\n\nIsto é uma mensagem automática por favor não responda para este endereço!";
    /**
     * SystemUser with the current system user that is going to be sent the email
     */
    private final SystemUser currentSystemUser;
    /**
     * String with the current email address that is going to send a certain email
     */
    private String emailSenderAddress;
    /**
     * String with the current email address password that is going to send a certain email
     */
    private String emailSenderAddressPassword;
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
        String toEmail=currentSystemUser.getID().toString();
        String content=String.format(FORMATED_EMAIL_ACTIVATION_CODE_CONTENT
                ,currentSystemUser.getName().toString(),toEmail,currentSystemUser.getActivationCode());
        return EmailSenderFactory
                .create(emailSenderAddress,emailSenderAddressPassword)
                .sendEmail(toEmail,EMAIL_ACTIVATION_CODE_TITLE,content);
    }
}
