/**
 * Simple custom exception for interoperability operations.
 */
public class InteropException extends Exception {
    /**
     * Constructs a new exception with a specific message.
     * 
     * @param message The detail message
     */
    public InteropException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with a message and cause.
     * 
     * @param message The detail message
     * @param cause The underlying cause
     */
    public InteropException(String message, Throwable cause) {
        super(message, cause);
    }
} 