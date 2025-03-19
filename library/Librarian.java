
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;


/**
 * The Librarian: Central Orchestration Module
 *
 * This module represents the librarian in our metaphorical system,
 * responsible for navigating and describing all system components.
 */
public class Librarian {
    private Map<String, Object> registry;
    protected Map<String, Object> yaml; 

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
        this("library/registry.yaml");
    }

    /**
     * Load the system registry from a YAML file.
     *
     * @param registryPath Path to the registry YAML file
     * @return Loaded registry configuration
     */
    private Map<String, Object> loadRegistry(String registryPath) {
        try {
            Yaml yaml = new Yaml();
            try (FileInputStream inputStream = new FileInputStream(registryPath)) {
                return yaml.load(inputStream);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Registry not found at " + registryPath + ". Creating an empty registry.");
            return new HashMap<>();
        } catch (Exception e) {
            System.out.println("Error parsing registry: " + e.getMessage());
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
        // Implement a storytelling mechanism that describes system state
        String baseStory = "In the library of technologies, where gates connect different realms...";

        if (context != null && !context.isEmpty()) {
            baseStory += "\nToday's tale revolves around: " + context;
        }

        return baseStory;
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
        return registry;
    }
} 