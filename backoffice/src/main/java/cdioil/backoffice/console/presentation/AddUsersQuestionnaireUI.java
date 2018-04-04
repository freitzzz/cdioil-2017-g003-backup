package cdioil.backoffice.console.presentation;

import cdioil.application.AddUsersQuestionnaireController;
import cdioil.backoffice.utils.Console;
import cdioil.domain.Questionnaire;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.Whitelist;
import java.util.List;

/**
 * UI of US Add Users to Questionnaire
 *
 * @author João
 */
public class AddUsersQuestionnaireUI {

    /**
     * Controller of add users to questionnaire
     */
    private AddUsersQuestionnaireController ctrl;

    /**
     * Operation cancelled message
     */
    private static final String CANCEL_MSG = "Operation cancelled.";

    /**
     * Operation failed message
     */
    private static final String FAIL_MSG = "Operation failed.";

    /**
     * Operation successful message
     */
    private static final String SUCCESS_MSG = "Operation successful.";

    /**
     * Yes String
     */
    private static final String YES = "Yes";

    /**
     * Add users message
     */
    private static final String ADD_USERS_MSG = "Add users to questionnaire? (Yes/No)";

    /**
     * No users found message
     */
    private static final String NO_USERS_FOUND_MSG = "There are no registered users with that domain!";

    /**
     * Choose domain message
     */
    private static final String CHOOSE_DOMAIN_MSG = "Type in the number of the domain:";

    /**
     * No domains found message
     */
    private static final String NO_WHITELISTED_DOMAINS_MSG = "There are no whitelisted domains!";

    /**
     * Choose questionnaire message
     */
    private static final String CHOOSE_QUESTIONNAIRE_MSG = "Type in the number of the Questionnaire:";

    /**
     * No questionnaires found message
     */
    private static final String NO_QUESTIONNAIRES_MSG = "There are no Questionnaires available!";

    /**
     * Constructor of AddUsersQuestionnaireUI
     */
    public AddUsersQuestionnaireUI() {
        ctrl = new AddUsersQuestionnaireController();
    }

    /**
     * Adds users to a questionnaire
     */
    public void addUsersQuestionnaire() {
        List<Questionnaire> lq = ctrl.getQuestionnaires();
        //if list is null
        if (lq == null) {
            System.out.println(NO_QUESTIONNAIRES_MSG);
        } else {
            //print questionnaires along with an int to later fetch the chosen one from the list
            int i = 1;
            for (Questionnaire q : lq) {
                System.out.println(i + ". " + q);
            }
            int chosen = Console.readInteger(CHOOSE_QUESTIONNAIRE_MSG);
            Questionnaire q = lq.get(chosen - 1);

            List<Whitelist> whitelist = ctrl.getWhitelistedDomains();
            //if list is null
            if (whitelist == null) {
                System.out.println(NO_WHITELISTED_DOMAINS_MSG);
            } else {
                //print whitelist along with an int to later fetch the chosen domain from the list
                i = 1;
                for (Whitelist wl : whitelist) {
                    System.out.println(i + ". " + wl);
                }
                chosen = Console.readInteger(CHOOSE_DOMAIN_MSG);
                Whitelist w = whitelist.get(chosen - 1);
                List<RegisteredUser> lru = ctrl.getUsersByDomain(w.getID());
                //if list is null
                if (lru == null) {
                    System.out.println(NO_USERS_FOUND_MSG);
                } else {
                    //print users
                    System.out.println("USERS:");
                    for (RegisteredUser user : lru) {
                        System.out.println(user);
                    }
                    String option = Console.readLine(ADD_USERS_MSG);
                    if (option.equalsIgnoreCase(YES)) {
                        boolean b = ctrl.addUsersQuestionnaire(q);
                        if (b) {
                            System.out.println(SUCCESS_MSG);
                        } else {
                            System.out.println(FAIL_MSG);
                        }
                    } else {
                        System.out.println(CANCEL_MSG);
                    }
                }
            }
        }
    }
}