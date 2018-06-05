package cdioil.application.domain.authz.exceptions;
/**
 * AuthenticationException that identifies an exception for an authentication service
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 4.0 of FeedbackMonkey
 */
public final class AuthenticationException extends RuntimeException{
    /**
     * Constant that represents the invalid credentials cause message
     */
    private static final String INVALID_CREDENTIALS_MESSAGE="Credênciais inválidas!";
    /**
     * Constant that represents the account locked cause message
     */
    private static final String ACCOUNT_LOCKED_MESSAGE="A sua conta está bloqueada devido a várias tentativas sucessivas de login sem sucesso. Aguarde uns momentos";
    /**
     * Constant that represents the account not activated cause message
     */
    private static final String ACCOUNT_NOT_ACTIVATED_MESSAGE="Conta não activada";
    /**
     * Constant that represents the account already activated cause message
     */
    private static final String ACCOUNT_ALREADY_ACTIVATED_MESSAGE="Conta já está activada!";
    /**
     * AuthenticationExceptionCause with the authentication exception cause
     */
    private final AuthenticationExceptionCause cause;
    /**
     * Builds a new AuthenticationException with the exception message
     * @param message String with the authentication message
     * @param cause AuthenticationExceptionCause with the authentication exception 
     * cause
     */
    public AuthenticationException(String message,AuthenticationExceptionCause cause){
        super(message);
        this.cause=cause;
    }
    /**
     * Method that gets the authentication exception cause
     * @return AuthenticationExceptionCause enum with the authentication exception 
     * cause
     */
    public AuthenticationExceptionCause getAuthenticationExceptionCause(){return cause;}
    /**
     * Enum that represents the different causes of the AuthenticationException
     */
    public enum AuthenticationExceptionCause{
        INVALID_CREDENTIALS{@Override public String toString(){return INVALID_CREDENTIALS_MESSAGE;}},
        NOT_ACTIVATED{@Override public String toString(){return ACCOUNT_NOT_ACTIVATED_MESSAGE;}},
        ALREADY_ACTIVATED{@Override public String toString(){return ACCOUNT_ALREADY_ACTIVATED_MESSAGE;}},
        ACCOUNT_LOCKED{@Override public String toString(){return ACCOUNT_LOCKED_MESSAGE;}}
    }
}
