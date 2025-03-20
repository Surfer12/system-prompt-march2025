package com.uplift.system.common.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom logging utility for the system.
 */
public class SystemLogger {
    private static final Logger LOGGER = Logger.getLogger(SystemLogger.class.getName());

    /**
     * Log an informational message.
     *
     * @param message the message to log
     */
    public static void info(String message) {
        LOGGER.info(message);
    }

    /**
     * Log a warning message.
     *
     * @param message the message to log
     */
    public static void warning(String message) {
        LOGGER.warning(message);
    }

    /**
     * Log a severe error message.
     *
     * @param message the message to log
     */
    public static void severe(String message) {
        LOGGER.severe(message);
    }

    /**
     * Log an exception with a severe error message.
     *
     * @param message the message to log
     * @param throwable the exception to log
     */
    public static void severe(String message, Throwable throwable) {
        LOGGER.log(Level.SEVERE, message, throwable);
    }
} 