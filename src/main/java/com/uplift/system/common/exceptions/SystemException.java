package com.uplift.system.common.exceptions;

/**
 * Base exception class for system-wide exceptions.
 */
public class SystemException extends Exception {
    /**
     * Constructs a new SystemException with the specified detail message.
     *
     * @param message the detail message
     */
    public SystemException(String message) {
        super(message);
    }

    /**
     * Constructs a new SystemException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
} 