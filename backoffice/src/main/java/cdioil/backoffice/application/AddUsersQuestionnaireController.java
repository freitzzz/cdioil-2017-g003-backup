package cdioil.backoffice.application;

import cdioil.domain.Questionnaire;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.Whitelist;
import cdioil.persistence.BaseJPARepository;
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
        EventRepositoryImpl repo = new EventRepositoryImpl();
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
        RegisteredUserRepositoryImpl repo = new RegisteredUserRepositoryImpl();
        users = repo.getUsersByDomain(domain);
        return users;
    }

    /**
     * Adds the users to the questionnaire
     *
     * @param questionnaire questionnaire to add users to
     * @return true if users were added successfully, false if not
     */
    public boolean addUsersQuestionnaire(Questionnaire questionnaire) {
        boolean b = questionnaire.addUsersToGroup(users);
        EventRepositoryImpl repo = new EventRepositoryImpl();
        repo.merge(questionnaire);
        return b;
    }
}
