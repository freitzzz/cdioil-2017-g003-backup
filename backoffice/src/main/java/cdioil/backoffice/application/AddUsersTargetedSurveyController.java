package cdioil.backoffice.application;

import cdioil.domain.TargetedSurvey;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.Whitelist;
import cdioil.persistence.BaseJPARepository;
import cdioil.persistence.SurveyRepository;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import cdioil.persistence.impl.WhitelistRepositoryImpl;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Controller of US Add users to targeted survey
 *
 * @author João
 */
public class AddUsersTargetedSurveyController {

    /**
     * List of users to be added
     */
    private List<RegisteredUser> users;

    /**
     * Constructor of AddUsersQuestionnaireController
     */
    public AddUsersTargetedSurveyController() {
        users = new LinkedList<>();
    }

    /**
     * Retrieves all questionnaires in the database
     *
     * @return list of questionnaires
     */
    public List<TargetedSurvey> getTragetedSurveys() {
        SurveyRepository repo = new SurveyRepositoryImpl();
        List<TargetedSurvey> listTS = new ArrayList<>();
        repo.getTargetedSurveys();
        return listTS;
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
     * Adds the users to the targeted survey
     *
     * @param targetedSurvey survey to add users to
     * @return true if users were added successfully, false if not
     */
    public boolean addUsersTargetedSurvey(TargetedSurvey targetedSurvey) {
        boolean b = targetedSurvey.addUsersToGroup(users);
        BaseJPARepository repo = new SurveyRepositoryImpl();
        repo.merge(targetedSurvey);
        return true;
    }
}
