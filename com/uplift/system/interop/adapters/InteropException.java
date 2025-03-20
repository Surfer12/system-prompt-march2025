package com.uplift.system.interop.adapters;

/**
 * Custom exception for interop adapter operations.
 */
public class InteropException extends Exception {

    /**
     * Constructs a new InteropException with the specified detail message.
     * 
     * @param message the detail message
     */
    public InteropException(String message) {
        super(message);
    }

    /**
     * Constructs a new InteropException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public InteropException(String message, Throwable cause) {
        super(message, cause);
    }
} 