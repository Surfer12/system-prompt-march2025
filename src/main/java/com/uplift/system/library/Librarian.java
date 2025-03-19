package com.uplift.system.library;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
/**
 * The Librarian: Central Orchestration Module
 *
 * This module represents the librarian in our metaphorical system,
 * responsible for navigating and describing all system components.
 */
public class Librarian {
    private Map<String, Object> registry;
    private static final String DEFAULT_REGISTRY_PATH = "config/registry.yaml";

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
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> loadRegistry(String registryPath) {
        try (FileInputStream inputStream = new FileInputStream(registryPath)) {
            Yaml yaml = new Yaml();
            Map<String, Object> loadedRegistry = yaml.load(inputStream);
            return loadedRegistry != null ? loadedRegistry : new HashMap<>();
        } catch (FileNotFoundException e) {
            System.err.println("Registry not found at " + registryPath + ". Creating an empty registry.");
            return new HashMap<>();
        } catch (Exception e) {
            System.err.println("Error parsing registry: " + e.getMessage());
            return new HashMap<>();
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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
