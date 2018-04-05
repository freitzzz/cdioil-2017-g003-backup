package cdioil.application;

import cdioil.domain.Questionnaire;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.Whitelist;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.EventRepository;
import cdioil.persistence.RegisteredUserRepository;
import cdioil.persistence.impl.EventRepositoryImpl;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.persistence.impl.WhitelistRepositoryImpl;
import java.util.LinkedList;
import java.util.List;

/**
 * Controller of US Add users to questionnaire
 *
 * @author João
 */
public class AddUsersQuestionnaireController {

    /**
     * List of users to be added
     */
    private List<RegisteredUser> users;

    /**
     * Constructor of AddUsersQuestionnaireController
     */
    public AddUsersQuestionnaireController() {
        users = new LinkedList<>();
    }

    /**
     * Retrieves all questionnaires in the database
     *
     * @return list of questionnaires
     */
    public List<Questionnaire> getQuestionnaires() {
        EventRepository repo = new EventRepositoryImpl();
        List<Questionnaire> lq = repo.getQuestionnaires();
        return lq;
    }

    /**
     * Retrieves all whitelisted domains
     *
     * @return list of domains
     */
    public List<Whitelist> getWhitelistedDomains() {
        BaseJPARepository repo = new WhitelistRepositoryImpl();
        List<Whitelist> whitelist = (List<Whitelist>) repo.findAll();
        return whitelist;
    }

    /**
     * Retrieves all users from the database based on a domain
     *
     * @param domain domain of the users to retrieve
     * @return list of users
     */
    public List<RegisteredUser> getUsersByDomain(String domain) {
        RegisteredUserRepository repo = new RegisteredUserRepositoryImpl();
        return repo.getUsersByDomain(domain);
    }

    /**
     * Adds the users to the questionnaire
     *
     * @param questionnaire questionnaire to add users to
     */
    public boolean addUsersQuestionnaire(Questionnaire questionnaire) {
        return questionnaire.addUsersToGroup(users);
    }
}
