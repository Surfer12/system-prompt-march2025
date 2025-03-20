package com.uplift.system.interop;

/**
 * Custom exception for interoperability-related errors.
 * Provides a standardized way to handle connection, operation, 
 * and disconnection failures across different entities.
 */
public class InteropException extends Exception {
    /**
     * Constructs a new InteropException with a specific error message.
     * 
     * @param message Detailed error description
     */
    public InteropException(String message) {
        super(message);
    }

    /**
     * Constructs a new InteropException with a message and underlying cause.
     * 
     * @param message Detailed error description
     * @param cause The original exception that triggered this InteropException
     */
    public InteropException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Provides an enum of standard error types for more precise error categorization.
     */
    public enum ErrorType {
        CONNECTION_FAILED,
        OPERATION_FAILED,
        DISCONNECTION_FAILED,
        CONFIGURATION_ERROR,
        UNSUPPORTED_OPERATION
    }
}