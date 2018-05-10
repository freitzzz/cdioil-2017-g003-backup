package cdioil.persistence.impl;

import cdioil.application.utils.EmailSenders;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.EmailSendersRepository;
import cdioil.persistence.PersistenceUnitNameCore;
import java.util.List;
import java.util.Random;
import javax.persistence.Query;

/**
 * Class that represents the implementation of the EmailSenders repository
 * @see cdioil.persistence.EmailSendersRepository
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class EmailSendersRepositoryImpl extends BaseJPARepository<EmailSenders,Integer> implements EmailSendersRepository{
    /**
     * Method that returns the persistence unit name that the repository uses
     * @return String with the persistence unit name that the repository uses
     */
    @Override
    protected String persistenceUnitName(){
        return PersistenceUnitNameCore.PERSISTENCE_UNIT_NAME;
    }
    /**
     * Method that returns a random email sender
     * <br>TO-DO: Replace query retrival by all results to random result (Somehow JPA doesn't understand queries with different classes names)
     * @return EmailSenders with the random email sender
     */
    @Override
    public EmailSenders getRandomEmailSender(){
        Query randomEmailSenderQuery=entityManager().createQuery("SELECT ES FROM Mood ES");
        List<EmailSenders> emailSenders=(List<EmailSenders>)randomEmailSenderQuery.getResultList();
        if(emailSenders.isEmpty()){
            return null;
        }
        return emailSenders.get(new Random().nextInt(emailSenders.size()));
    }
}
