package cdioil.application.domain.authz;

/**
 * AuthenticationAction enum that represents an authentication action on the system
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */
public enum AuthenticationAction {
    /**Enum that represents the action which an user succesfuly logins on the system*/
    SUCCESFUL_LOGIN{@Override public String toString(){return "Login com successo";}},
    /**Enum that represents the action which an user fails to login on the system*/
    INVALID_LOGIN{@Override public String toString(){return "Login falhado";}},
}
