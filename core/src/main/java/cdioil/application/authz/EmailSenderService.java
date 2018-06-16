package cdioil.application.authz;

import cdioil.application.utils.EmailSenders;
import cdioil.domain.authz.SystemUser;
import cdioil.emails.EmailSenderFactory;
import cdioil.persistence.impl.EmailSendersRepositoryImpl;

/**
 * Service class that sends the account activation code to the SystemUser email
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class EmailSenderService {
    /**
     * Constant that represents the email activation title which is going to be sent to a 
     * certain user email
     */
    private static final String EMAIL_ACTIVATION_CODE_TITLE="Código de Ativação 🍌";
    /**
     * Constant that represents the email activation content which is going to be sent to a 
     * certain user email
     */
    private static final String FORMATED_EMAIL_ACTIVATION_CODE_CONTENT="Olá %s!<p>"
            + "Reparamos que acabaste de te registar na nossa aplicação com o endereço %s.<p>"
            + "De modo a provarmos a tua autenticidade, pedimos que insiras o seguinte código <strong>%s</strong> "
            + "aquando o início da aplicação."
            + "<p><p><i>Isto é uma mensagem automática, por favor não responda para este endereço.</i>";
    
    /**
     * Constant that represents the email title when resetting a user's password.
     */
    private static final String EMAIL_COMINHOS_RESET_CODE_TITLE = "Reposição de Password 🍌";
    
    /**
     * Constant that represents the email content when resetting a user's password.
     */
    private static final String FORMATED_EMAIL_COMINHOS_RESET_CODE_CONTENT = "Olá %s!<p>"
            + "No Feedback Monkey sabemos que as passwords podem por vezes ser esquecidas.<p>"
            + "É por isso que lhe estamos a enviar este email!<p>"
            + "Basta inserir o código <strong>%s</strong> na nossa aplicação para que possa "
            + "proceder com a renovação da sua password!<p>"
            + "Se não pediu a renovação da sua password, por favor ignore esta mensagem.<p><p>"
            + "<i>Isto é uma mensagem automática, por favor não responda para este endereço.</i>";
    
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
        if(sender==null){
            return false;
        }
        String toEmail=currentSystemUser.getID().toString();
        String userName=currentSystemUser.getName()!=null ? currentSystemUser.getName().toString() : "";
        String content=String.format(FORMATED_EMAIL_ACTIVATION_CODE_CONTENT
                ,userName,toEmail,currentSystemUser.getActivationCode());
        return EmailSenderFactory
                .create(sender.getEmail(),sender.getPassword())
                .sendEmail(toEmail,EMAIL_ACTIVATION_CODE_TITLE,content);
    }

    /**
     * Sends an email to the SystemUser's email address with the activation code
     * when attempting to recover their password.
     *
     * @return true - if the email with the password recovery code was sent
     * successfully<p>
     * false - if not
     */
    public boolean sendPasswordResetCode() {
        EmailSenders sender = new EmailSendersRepositoryImpl().getRandomEmailSender();
        if (sender == null) {
            return false;
        }
        String destinationEmail = currentSystemUser.getID().toString();
        String userName = currentSystemUser.getName() != null ? currentSystemUser.getName().toString() : "";

        String content = String.format(FORMATED_EMAIL_COMINHOS_RESET_CODE_CONTENT,
                userName, currentSystemUser.getActivationCode());

        return EmailSenderFactory
                .create(sender.getEmail(), sender.getPassword())
                .sendEmail(destinationEmail, EMAIL_COMINHOS_RESET_CODE_TITLE, content);
    }
}
