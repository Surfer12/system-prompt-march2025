package com.uplift.system.interop.adapters;

/**
 * Interface for adapters that can process data from different technology sources
 */
public interface EntityAdapter {
    /**
     * Process data in the technology-specific format
     * 
     * @param data The data to process
     * @return The processed result
     * @throws Exception If processing fails
     */
    Object processData(String data) throws Exception;
} 