package cdioil.feedbackmonkey.restful.exceptions;

/**
 * RESTfulException class that represents an exception that is thrown when
 * a RESTful request is unsuccessful
 * @author @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */

public final class RESTfulException extends RuntimeException {
    /**
     * Short with the request code
     */
    private final short code;
    /**
     * String with the request response body message
     */
    private final String message;

    /**
     * Builds a new RESTfulException with the message and code of the RESTful Request that has failed
     * @param message String with the request responde body message
     * @param code Short with the request code
     */
    public RESTfulException(String message,short code){
        super(message);
        this.code=code;
        this.message=message;
    }

    /**
     * Method that returns the code of the RESTful Request which the exception is in cause
     * @return String with request code which the exception is in cause
     */
    public short getCode(){return code;}

    /**
     * Method that returns the message of the RESTful Request which the exception is in cause
     * @return String with the request response body message
     */
    public String getMessage(){return message;}
}
