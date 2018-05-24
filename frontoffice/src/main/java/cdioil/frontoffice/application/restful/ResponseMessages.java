package cdioil.frontoffice.application.restful;

/**
 * Interface that contains all JSONs used on the response messages.
 *
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
 */
public interface ResponseMessages {

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user that its account is not currently authenticated.
     */
    public static final String JSON_INVALID_AUTHENTICATION_TOKEN = "{\n\t\"invalidauthenticationtoken\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user that they are not authorised to perform a certain task.
     */
    public static final String JSON_INVALID_USER = "{\n\t\"invaliduser\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user that the chosen review has already been answered.
     */
    public static final String JSON_FINISHED_REVIEW = "{\n\t\"finishedreview\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user that the chosen survey is not valid.
     */
    public static final String JSON_INVALID_SURVEY = "{\n\t\"invalidsurvey\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user that the review is not valid.
     */
    public static final String JSON_INVALID_REVIEW = "{\n\t\"invalidreview\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user that the review is incomplete
     */
    public static final String JSON_INCOMPLETE_REVIEW = "{\n\t\"reviewdoesnotmeetconditions\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user when the review was not found
     */
    public static final String JSON_REVIEW_NOT_FOUND = "{\n\t\"reviewnotfound\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user that there are currently no available surveys for him to
     * answer.
     */
    public static final String JSON_NO_AVAILABLE_SURVEYS = "{\n\t\"noavailablesurveys\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user that the pagination ID is invalid.
     */
    public static final String JSON_INVALID_PAGINATION_ID = "{\n\t\"invalidPaginationID\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user that his account needs to be activated.
     */
    public static final String JSON_ACTIVATION_CODE_REQUIRED = "{\n\t\"activationcode\":\"required\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user that the credentials that were given are invalid.
     */
    public static final String JSON_INVALID_CREDENTIALS = "{\n\t\"invalidcredentials\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user that the account was activated with success.
     */
    public static final String JSON_ACCOUNT_ACTIVATED_SUCCESS = "{\n\t\"accountactivated\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user that the account was not activated with success.
     */
    public static final String JSON_ACCOUNT_ACTIVATED_FAILURE = "{\n\t\"accountactivated\":\"false\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user that the account has already been activated.
     */
    public static final String JSON_ACCOUNT_ALREADY_ACTIVATED = "{\n\t\"accountalreadyactivated\":\"true\"\n}";
}
