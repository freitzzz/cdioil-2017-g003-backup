package cdioil.persistence;

import cdioil.application.utils.EmailSenders;

/**
 * Interface for the EmailSenders Repository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public interface EmailSendersRepository {
    /**
     * Method that returns a random email sender
     * @return EmailSenders with the random email sender
     */
    public abstract EmailSenders getRandomEmailSender();
}
