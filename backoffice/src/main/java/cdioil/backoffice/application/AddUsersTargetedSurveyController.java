package cdioil.backoffice.application;

import cdioil.domain.TargetedSurvey;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.Whitelist;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import cdioil.persistence.impl.WhitelistRepositoryImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller of US-280 and US-282.
 *
 * @author João
 * @author António Sousa [1161371]
 * @author Pedro Portela
 */
public class AddUsersTargetedSurveyController {

    /**
     * Selected email domain.
     */
    private String selectedDomain;

    /**
     * Selected username.
     */
    private String selectedUsername;

    /**
     * Selected birth year.
     */
    private String selectedBirthYear;

    /**
     * Selected location.
     */
    private String selectedLocation;

    /**
     * Currently selected survey.
     */
    private TargetedSurvey selectedSurvey;

    /**
     * Repository responsible for storing all the surveys.
     */
    private SurveyRepositoryImpl surveyRepository;

    /**
     * List with all the active targeted surveys.
     */
    private List<TargetedSurvey> activeTargetedSurveys;

    /**
     * List with all the whitelisted email domains.
     */
    private List<Whitelist> whitelistedDomains;

    /**
     * Constructs a new instance of AddUsersTargetedSurveyController.
     */
    public AddUsersTargetedSurveyController() {
        selectedDomain = null;
        selectedUsername = null;
        selectedBirthYear = null;
        selectedLocation = null;
        surveyRepository = new SurveyRepositoryImpl();
        whitelistedDomains = new ArrayList<>();
    }

    /**
     * Retrieves a list with the textual representation of all the currently
     * active targeted surveys.
     *
     * @return list of strings representing active surveys.
     */
    public List<String> getActiveTargetedSurveys() {

        List<String> result = new ArrayList<>();
        activeTargetedSurveys = surveyRepository.getActiveTargetedSurveys();

        if (activeTargetedSurveys == null) {
            return null;
        }

        for (TargetedSurvey targetedSurvey : activeTargetedSurveys) {
            result.add(targetedSurvey.toString());
        }

        return result;
    }

    /**
     * Retrieves a list with the textual representation of all the currently
     * whitelisted email domains.
     *
     * @return list of string representing whitelisted email domains.
     */
    public List<String> getWhitelistedDomains() {

        WhitelistRepositoryImpl whitelistRepository = new WhitelistRepositoryImpl();
        Iterable<Whitelist> domainWhitelist = whitelistRepository.findAll();
        List<String> result = new ArrayList<>();

        for (Whitelist w : domainWhitelist) {
            whitelistedDomains.add(w);
            result.add(w.getID());
        }
        return result;
    }

    /**
     * Adds the users to the targeted survey.
     *
     * @return number of users added to the survey.
     */
    public int addUsersTargetedSurvey() {

        RegisteredUserRepositoryImpl userRepository = new RegisteredUserRepositoryImpl();

        List<RegisteredUser> users = userRepository.getUsersByFilters(selectedDomain, selectedUsername, selectedBirthYear, selectedLocation);

        boolean addedSuccessfully = selectedSurvey.addUsersToGroup(users);

        if (!addedSuccessfully) {
            return 0;
        }
        surveyRepository = new SurveyRepositoryImpl();
        surveyRepository.merge(selectedSurvey);

        return users.size();
    }

    /**
     * Sets the selected survey.
     *
     * @param index index of the survey on the list.
     */
    public void selectSurvey(int index) {
        selectedSurvey = activeTargetedSurveys.get(index);
    }

    /**
     * Sets the selected whitelisted domain.
     *
     * @param index index of the whitelist on the list.
     */
    public void selectDomain(int index) {
        selectedDomain = whitelistedDomains.get(index).getID();
        selectedUsername = null; //if option for filtering by domain is selected, discard username filter
    }

    /**
     * Sets the selected username.
     *
     * @param username
     */
    public void selectUsername(String username) {
        selectedUsername = username;
        selectedDomain = null; //if option for filtering by username is selected, discard domain filter
    }

    /**
     * Sets the selected birth year.
     *
     * @param birthYear
     */
    public void selectBirthYear(String birthYear) {
        selectedBirthYear = birthYear;
    }

    /**
     * Sets the selected location.
     *
     * @param location
     */
    public void selectLocation(String location) {
        selectedLocation = location;
    }

}
