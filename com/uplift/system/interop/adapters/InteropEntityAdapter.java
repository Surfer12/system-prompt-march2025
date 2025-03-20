/* Interface for interop adapters */
package com.uplift.system.interop.adapters;

import com.uplift.system.interop.adapters.InteropException;

/**
 * InteropEntityAdapter defines a common interface for all interop adapters.
 * It standardizes basic operations such as connecting, executing operations,
 * retrieving status, and disconnecting from the underlying entity.
 */
public interface InteropEntityAdapter {

    /**
     * Connect to the underlying entity using the provided configuration.
     * 
     * @param config Configuration object (could be a String, Map, etc.)
     * @return A connection identifier or status message.
     * @throws InteropException if connection fails.
     */
    String connect(Object config) throws InteropException;

    /**
     * Execute an operation on the entity.
     * 
     * @param operation the name of the operation to execute.
     * @param params optional parameters for the operation.
     * @return The result of the operation.
     * @throws InteropException if execution fails.
     */
    Object executeOperation(String operation, Object... params) throws InteropException;

    /**
     * Retrieve the current status or health of the entity.
     * 
     * @return A String representing the status of the entity.
     */
    String getStatus();

    /**
     * Disconnect from the underlying entity.
     * 
     * @return A confirmation message indicating successful disconnection.
     * @throws InteropException if disconnection fails.
     */
    String disconnect() throws InteropException;
} 