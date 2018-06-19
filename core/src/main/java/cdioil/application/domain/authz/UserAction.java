package cdioil.application.domain.authz;

/**
 * UserAction enum that represents each user action on the application
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */
public enum UserAction {
    /**Enum that represents the action which an user starts to answer a user*/
    STARTED_ANSWER_SURVEY{@Override public String toString(){return "Começou a responder a um Inquérito";}},
    /**Enum that represents the action which an user ends to answer a user*/
    ENDED_ANSWER_SURVEY{@Override public String toString(){return "Acabou de responder a um Inquérito";}},
}
