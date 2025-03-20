package com.uplift.system.interop;
/**
 * Interface defining standard operations for interoperability adapters.
 */
public interface InteropEntityAdapter {
    /**
     * Connect to an entity using provided configuration.
     * 
     * @param config Configuration for connection
     * @return Connection status or identifier
     * @throws InteropException if connection fails
     */
    String connect(Object config) throws InteropException;

    /**
     * Execute an operation on the entity.
     * 
     * @param operation Name of the operation
     * @param params Parameters for the operation
     * @return Result of the operation
     * @throws InteropException if operation fails
     */
    Object executeOperation(String operation, Object... params) throws InteropException;

    /**
     * Get current status of the entity.
     * 
     * @return Status as a string
     */
    String getStatus();

    /**
     * Disconnect from the entity.
     * 
     * @return Disconnection confirmation
     * @throws InteropException if disconnection fails
     */
    String disconnect() throws InteropException;
} 