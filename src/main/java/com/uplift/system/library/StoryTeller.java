// src/main/java/com/uplift/system/library/StoryTeller.java
package com.uplift.system.library;

import com.uplift.system.gate.Connector;
import com.uplift.system.adapters.Adapter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StoryTeller {
    private Librarian librarian;

    public StoryTeller() {
        this(new Librarian());
    }

    public StoryTeller(Librarian librarian) {
        this.librarian = librarian;
    }

    public void interactiveNarrative() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Nested Librarian System");
        System.out.println("Exploring the realms of technological interoperability...\n");

        while (true) {
            System.out.println("Choose an exploration path:");
            System.out.println("1. System Overview");
            System.out.println("2. Technology Gate Details");
            System.out.println("3. Creative Principles");
            System.out.println("4. Test Connector"); // New option
            System.out.println("5. Exit");

            System.out.print("Enter your choice (1-5): ");
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
                    testConnector(); // New method
                    break;
                case "5":
                    System.out.println("Farewell, traveler of technological realms!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void exploreTechnologies() {
        // Existing implementation
    }

    private void exploreCreativePrinciples() {
        // Existing implementation
    }

    private void testConnector() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter source technology (e.g., Java): ");
        String sourceTech = scanner.nextLine();
        System.out.print("Enter destination technology (e.g., C++): ");
        String destTech = scanner.nextLine();

        Connector sourceConnector = librarian.getConnector(sourceTech);
        if (sourceConnector == null) {
            System.out.println("No connector found for " + sourceTech);
            return;
        }

        Adapter destAdapter = librarian.getAdapter(destTech);
        if (destAdapter == null) {
            System.out.println("No adapter found for " + destTech);
            return;
        }

        // Simulate a connection
        Object source = new Object(); // Replace with actual source object
        Object destination = new Object(); // Replace with actual destination object

        Map<String, Object> connectionResult = sourceConnector.connect(source, destination);
        System.out.println("Connection Result: " + connectionResult);

        // Simulate executing a method on the destination
        Map<String, Object> executionResult = destAdapter.execute("someMethod", "arg1", 123);
        System.out.println("Execution Result: " + executionResult);
    }

    public String generateNarrative(String context) {
        return librarian.tellStory(context);
    }

    public String generateNarrative() {
        return librarian.tellStory();
    }

    public static void main(String[] args) {
        StoryTeller storyTeller = new StoryTeller();
        storyTeller.interactiveNarrative();
    }
}