package com.uplift.system.library;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.yaml.snakeyaml.Yaml;

/**
 * The Librarian: Central Orchestration Module
 *In this edited version:

1. **Custom Exception**: A custom `RegistryLoadingException` is introduced to handle specific exceptions that occur when loading the registry.

2. **Error Handling**: The `loadRegistry` method now throws `RegistryLoadingException`, which encapsulates any errors encountered during file reading or YAML parsing.

3. **Logging**: Java's built-in logging framework (`java.util.logging.Logger`) is used for error messages, allowing better tracking of issues when they occur. This replaces direct calls to `System.err.println`.

4. **Method Signature Update**: The signature of the `loadRegistry` method is updated to indicate that it throws a custom exception.

These changes ensure more robust and maintainable error handling practices within the code.

 * This module represents the librarian in our metaphorical system,
 * responsible for navigating and describing all system components.
 */
public class Librarian {
    private Map<String, Object> registry;
    private static final String DEFAULT_REGISTRY_PATH = "library/registry.yaml";
    private static final Logger logger = Logger.getLogger(Librarian.class.getName());

    /**
     * Initialize the Librarian with the system's registry.
     *
     * @param registryPath Path to the system's configuration registry
     */
    public Librarian(String registryPath) {
        this.registry = loadRegistry(registryPath);
    }

    /**
     * Default constructor using default registry path.
     */
    public Librarian() {
        this(DEFAULT_REGISTRY_PATH);
    }

    /**
     * Load the system registry from a YAML file.
     *
     * @param registryPath Path to the registry YAML file
     * @return Loaded registry configuration
     * @throws RegistryLoadingException if an error occurs during loading
     */
    private Map<String, Object> loadRegistry(String registryPath) throws RegistryLoadingException {
        try (FileInputStream inputStream = new FileInputStream(registryPath)) {
            Yaml yaml = new Yaml();
            Map<String, Object> loadedRegistry = yaml.load(inputStream);
            return loadedRegistry != null ? loadedRegistry : new HashMap<>();
        } catch (FileNotFoundException e) {
            logger.severe("Registry not found at " + registryPath + ". Creating an empty registry.");
            throw new RegistryLoadingException("Registry file not found: " + registryPath, e);
        } catch (Exception e) {
            logger.severe("Error parsing registry: " + e.getMessage());
            throw new RegistryLoadingException("Error parsing the registry file: " + registryPath, e);
        }
    }

    /**
     * Metaphorical method to describe system state and interactions.
     *
     * @param context Specific context for the story
     * @return A narrative description of the system
     */
    public String tellStory(String context) {
        // Implement a more dynamic storytelling mechanism
        StringBuilder storyBuilder = new StringBuilder("In the library of technologies, where gates connect different realms...\n");

        if (context != null && !context.isEmpty()) {
            storyBuilder.append("Today's tale revolves around: ").append(context).append("\n");
        }

        // Add some dynamic content from the registry
        storyBuilder.append("Generated on: ").append(LocalDateTime.now()).append("\n");
        storyBuilder.append(generateRegistryBasedNarrative());

        return storyBuilder.toString();
    }

    /**
     * Generate narrative content based on registry information.
     *
     * @return Dynamically generated narrative snippet
     */
    private String generateRegistryBasedNarrative() {
        StringBuilder narrativeBuilder = new StringBuilder();

        // Example of extracting narrative elements from registry
        Map<String, Object> components = (Map<String, Object>) registry.getOrDefault("components", new HashMap<>());
        components.forEach((componentName, componentValue) -> {
            narrativeBuilder.append(String.format("The %s component whispers its secrets...\n", componentName)); // componentValue is not used
        });

        return narrativeBuilder.toString();
    }

    /**
     * Overloaded method to tell a story without a specific context.
     *
     * @return A narrative description of the system
     */
    public String tellStory() {
        return tellStory(null);
    }

    /**
     * Retrieve details about a specific system component.
     *
     * @param componentName Name of the component to retrieve
     * @return Component details from the registry
     */
    public Map<String, Object> getComponentDetails(String componentName) {
        Map<String, Object> components = (Map<String, Object>) registry.getOrDefault("components", new HashMap<>());
        return (Map<String, Object>) components.getOrDefault(componentName, new HashMap<>());
    }

    /**
     * Get the entire registry.
     *
     * @return The system registry
     */
    public Map<String, Object> getRegistry() {
        return new HashMap<>(registry); // Return a copy to prevent direct modification
    }
}

// Custom exception class for handling errors during registry loading
class RegistryLoadingException extends RuntimeException {
    public RegistryLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}

