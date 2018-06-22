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
     * warning the user that the review is not valid.
     */
    public static final String JSON_INVALID_REVIEW = "{\n\t\"invalidreview\":\"true\"\n}";

    /**
     * Constant that represents the JSON chunk used on the response message used
     * for warning the user that the review was created successfully.
     */
    public static final String JSON_REVIEW_CREATION_SUCCESS = "{\n\t\"reviewcreated\":true\"\n}";

    /**
     * Constant that represents the JSON chunk used on the response message used
     * for warning the user that the review failed to be created.
     */
    public static final String JSON_REVIEW_CREATION_FAILURE = "{\n\t\"reviewcreated\":\"false\"\n}";

    /**
     * Constant that represents the JSON chunk used on the response message that
     * warns the user that the review was added to their profile successfully.
     */
    public static final String JSON_REVIEW_ADDED_TO_PROFILE_SUCCESS = "{\n\t\"reviewaddedtoprofile\":\"true\"}";

    /**
     * Constant that represents the JSON chunk used on the response message that
     * warns the user that the review failed to be added to their profile.
     */
    public static final String JSON_REVIEW_ADDED_TO_PROFILE_FAILURE = "{\n\t\"reviewaddedtoprofile\":\"false\"}";

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
    /**
     * Constant that represents the JSON used on the response message for
     * warning the user that the account is locked.
     */
    public static final String JSON_ACCOUNT_LOCKED = "{\n\t\"accountlocked\":\"true\"\n}";
    /**
     * Constant that represents the JSON used on the response message for
     * warning the user that his account was created with success
     */
    public static final String JSON_ACCOUNT_CREATED_WITH_SUCCESS = "{\n\n\"accountcreationsuccessful\":\"true\"\n}";

    /**
     * Constant that represents the JSON chunk used on the response message that
     * warns the user that their data was updated sucessfully.
     */
    public static final String JSON_USER_DATA_UPDATED_SUCCESS = "{\n\t\"userdataupdated\":\"true\"\n}";

    /**
     * Constant that represents the JSON chunk used on the response message that
     * warns the user that their data failed to be updated.
     */
    public static final String JSON_USER_DATA_UPDATED_FAILURE = "{\n\t\"userdataupdated\":\"false\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user when the product was not found
     */
    public static final String JSON_PRODUCT_NOT_FOUND = "{\n\t\"productnotfound\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user when no surveys are found
     */
    public static final String JSON_SURVEY_NOT_FOUND = "{\n\t\"surveynotfound\":\"true\"\n}";
    /**
     * Constant that represents the JSON used on the response message for
     * warning the user the registration form is valid
     */
    public static final String JSON_VALID_REGISTRATION_FORM = "{\n\t\"registrationformvalid\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message for
     * warning the user the review was saved with success
     */
    public static final String JSON_REVIEW_SAVED_WITH_SUCCESS = "{\n\t\"reviewsavedsuccess\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message warning
     * the user that the activation code was sent successfully.
     */
    public static final String JSON_ACTIVATION_CODE_DISPATCH_SUCESS = "{\n\t\"activationcodesent\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message warning
     * the user that the activation code failed to be sent.
     */
    public static final String JSON_ACTIVATION_CODE_DISPATCH_FAILED = "{\n\t\"activationcodesent\":\"false\"\n}";

    /**
     * Constant that represents the JSON used on the response message warning
     * the user that the given activation code is valid.
     */
    public static final String JSON_VALID_ACTIVATION_CODE = "{\n\t\"validactivationcode\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message warning
     * the user that the given activation code is invalid.
     */
    public static final String JSON_INVALID_ACTIVATION_CODE = "{\n\t\"validactivationcode\":\"false\"\n}";

    /**
     * Constant that represents the JSON used on the response message warning
     * the user that the password was changed successfully.
     */
    public static final String JSON_COMINHOS_CHANGE_SUCCESS = "{\n\t\"passwordchanged\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message warning
     * the user that the password was unable to be changed.
     */
    public static final String JSON_COMINHOS_CHANGE_FAILED = "{\n\t\"passwordchanged\":\"false\"\n}";
    
    /**
     * Constant that represents the JSON used on the response message warning
     * the user he has no reviews about a survey.
     */
    public static final String JSON_REVIEWS_NOT_FOUND = "{\n\t\"noreviewsfound\":\"true\"\n}";
    
    /**
     * Constant that represents the JSON used on the response message warning the user that the 
     * suggestion (not related to a review) was submitted with success.
     */
    public static final String JSON_SUGGESTION_SUBMISSION_SUCCESS = "{\n\t\"suggestionsubmitted\":\"true\"\n}";

    /**
     * Constant that represents the JSON used on the response message warning the user that the
     * suggestion (not related to a review) wasn't submitted with success.
     */
    public static final String JSON_SUGGESTION_SUBMISSION_FAILED = "{\n\t\"suggestionsubmitted\":\"false\"\n}";
}
