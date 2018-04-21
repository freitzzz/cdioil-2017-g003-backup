package cdioil.application.domain.authz.exceptions;

/**
 * AuthenticationException that identifies an exception for an authentication service
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 4.0 of FeedbackMonkey
 */
public final class AuthenticationException extends RuntimeException{
    /**
     * Builds a new AuthenticationException with the exception message
     * @param message String with the authentication message
     */
    public AuthenticationException(String message){
        super(message);
    }
}
