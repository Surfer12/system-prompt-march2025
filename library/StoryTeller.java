import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Story Teller: User Interaction Interface
 *
 * This module provides an interactive interface for users to explore
 * the system's metaphorical and technical narratives.
 */
public class StoryTeller {

    private Librarian librarian;

    /**
     * Initialize the StoryTeller with an optional Librarian instance.
     *
     * @param librarian Librarian instance to use. Creates a new one if not provided.
     */
    public StoryTeller(Librarian librarian) {
        this.librarian = (librarian != null) ? librarian : new Librarian();
    }

    /**
     * Default constructor creating a new Librarian.
     */
    public StoryTeller() {
        this(new Librarian());
    }

    /**
     * Provide an interactive narrative exploration of the system.
     */
    public void interactiveNarrative() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Nested Librarian System");
        System.out.println(
            "Exploring the realms of technological interoperability...\n"
        );

        while (true) {
            System.out.println("Choose an exploration path:");
            System.out.println("1. System Overview");
            System.out.println("2. Technology Gate Details");
            System.out.println("3. Creative Principles");
            System.out.println("4. Exit");

            System.out.print("Enter your choice (1-4): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println(librarian.tellStory());
                    break;
                case "2":
                    exploreTechnologies();
                    break;
                case "3":
                    exploreCreativePrinciples();
                    break;
                case "4":
                    System.out.println(
                        "Farewell, traveler of technological realms!"
                    );
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Explore and display details about system technologies.
     */
    private void exploreTechnologies() {
        Map<String, Object> registry = librarian.getRegistry();
        Map<String, Object> components = (Map<
                String,
                Object
            >) registry.getOrDefault("components", Map.of());
        Map<String, Object> gate = (Map<
                String,
                Object
            >) components.getOrDefault("gate", Map.of());
        List<Map<String, Object>> technologies = (List<
                Map<String, Object>
            >) gate.getOrDefault("technologies", List.of());

        System.out.println("\n--- Technology Gate Details ---");
        for (Map<String, Object> tech : technologies) {
            System.out.println(
                "\n" +
                tech.getOrDefault("name", "Unknown Technology") +
                " Technology:"
            );
            System.out.println(
                "  Role: " + tech.getOrDefault("role", "Unspecified")
            );
            System.out.println(
                "  Version: " + tech.getOrDefault("version", "Unknown")
            );
        }
    }

    /**
     * Display and explain the system's creative principles.
     */
    private void exploreCreativePrinciples() {
        Map<String, Object> registry = librarian.getRegistry();
        List<Map<String, Object>> principles = (List<
                Map<String, Object>
            >) registry.getOrDefault("creative_principles", List.of());

        System.out.println("\n--- Creative Principles ---");
        for (Map<String, Object> principle : principles) {
            for (Map.Entry<String, Object> entry : principle.entrySet()) {
                String name = entry.getKey().replace("_", " ");
                Map<String, Object> details = (Map<
                        String,
                        Object
                    >) entry.getValue();

                System.out.println(
                    "\n" +
                    name.substring(0, 1).toUpperCase() +
                    name.substring(1) +
                    ":"
                );
                System.out.println(
                    "  " +
                    details.getOrDefault(
                        "description",
                        "No description available"
                    )
                );
            }
        }
    }

    /**
     * Generate a narrative based on the system's current state.
     *
     * @param context Specific context for the narrative
     * @return Generated narrative
     */
    public String generateNarrative(String context) {
        return librarian.tellStory(context);
    }

    /**
     * Generate a narrative without a specific context.
     *
     * @return Generated narrative
     */
    public String generateNarrative() {
        return librarian.tellStory();
    }

    /**
     * Main entry point for the StoryTeller.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        StoryTeller storyTeller = new StoryTeller();
        storyTeller.interactiveNarrative();
    }
}
