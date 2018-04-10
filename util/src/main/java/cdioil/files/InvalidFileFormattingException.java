package cdioil.application.utils;

/**
 * Exception used for identifying when a file's structure is not compliant with
 * the defined guidelines.
 *
 * @author António Sousa [1161371]
 */
public class InvalidFileFormattingException extends RuntimeException {

    private static final long serialVersionUID = -5365630124325606114L;

    /**
     * Constructs an <code>InvalidFileFormattingException</code> with no detail
     * message.
     */
    public InvalidFileFormattingException() {
        super();
    }

    /**
     * Constructs an <code>InvalidFileFormattingException</code> with a given
     * detail message.
     *
     * @param s detail message
     */
    public InvalidFileFormattingException(String s) {
        super(s);
    }

    /**
     * Constructs an <code>InvalidFileFormattingException</code> with a given
     * detail message and cause.
     *
     *
     * @param message the detail message (which is saved for later retrieval by
     * the {@link Throwable#getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     * {@link Throwable#getCause()} method). (A <tt>null</tt> value is
     * permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public InvalidFileFormattingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an <code>InvalidFileFormattingException</code> with a given
     * cause.
     *
     * @param cause the cause (which is saved for later retrieval by the
     * {@link Throwable#getCause()} method). (A <tt>null</tt> value is
     * permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public InvalidFileFormattingException(Throwable cause) {
        super(cause);
    }

}
